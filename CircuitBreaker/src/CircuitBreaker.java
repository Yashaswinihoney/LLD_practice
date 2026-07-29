import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CircuitBreaker {
    private final String serviceName;
    private final int failureThreshold;
    private final long openTimeoutMs;
    private final AtomicReference<CircuitState> state;
    private final AtomicInteger consequtiveFailures;
    private volatile long lastOpenedTime;

    public CircuitBreaker(String serviceName, int failureThreshold, long openTimeoutMs){
        this.serviceName=serviceName;
        this.failureThreshold=failureThreshold;
        this.openTimeoutMs=openTimeoutMs;
        this.state=new AtomicReference<>(CircuitState.CLOSED);
        this.consequtiveFailures=new AtomicInteger(0);
    }

    //executes the protected call
    public void execute(Runnable downstreamCall){
        if(!allowRequest()){
            throw new CircuitBreakerOpenException("CB is OPEN for service :"+serviceName);
        }

        try{
            downstreamCall.run();
            recordSuccess();
        }
        catch (Exception e){
            recordFailure();
            throw e; //rethrow the downstream error
        }
    }

    //STATE TRANSITION LOGIC
    private boolean allowRequest(){
        CircuitState currentState=state.get();

        if(currentState==CircuitState.CLOSED){
            return true;
        }

        if(currentState==CircuitState.OPEN){
            //check if last open time - current time> openTimeout then transition to half open
            if(System.currentTimeMillis()-lastOpenedTime>=openTimeoutMs){
                //compareAndSet to set the state to halfOpen to make it thread safe
                if(state.compareAndSet(CircuitState.OPEN,CircuitState.HALF_OPEN)){
                    System.out.println("["+serviceName+"] State changed to HALF_OPEN state (PROBE ALLOWED)");
                    return true;
                }

                //check if compareand set failed, that means another thread updated the state to HALF_OPEN
                //we still can process this transaction
                return state.get()==CircuitState.HALF_OPEN;
            }
            return false; //timeout hasnt elapsed, reject request
        }
        //half open state
        return true;
    }

    private void recordSuccess(){
        consequtiveFailures.set(0); //reset failures on success

        if(state.get()==CircuitState.HALF_OPEN){
            state.set(CircuitState.CLOSED);
            System.out.println(serviceName+" Probe success -> State changed to CLOSED");
        }
    }

    private void recordFailure(){
        int failures=consequtiveFailures.incrementAndGet();

        if (state.get()==CircuitState.HALF_OPEN){
            //IF PROBE FAILED, REOPEN IMMEDIATELY
            openCircuit("Probe failed");
        }
        else if(failures>=failureThreshold){
            //if threshold reached, OPEN CRCUIT
            openCircuit("Failure threshold reached");
        }
    }

    private void openCircuit(String reason){
        //only update timestamp if circuit is already open
        //use compareAndSet for threadSafety

        if(state.compareAndSet(CircuitState.CLOSED,CircuitState.OPEN)||state.compareAndSet(CircuitState.HALF_OPEN,CircuitState.OPEN)){
            lastOpenedTime=System.currentTimeMillis();
            System.out.println(serviceName+" State changed to OPEN "+ reason);
        }
    }
}
