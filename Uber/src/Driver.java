import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Driver {
    private final String id;
    private final ReentrantLock driverLock= new ReentrantLock();

    public Driver(String id){
        this.id=id;
    }

    public String getId(){
        return id;
    }

    public boolean tryBook(){
        try {
                //try to lock the driver for 50 milliseconds, give up if the driver is already booked by another thread
                if (driverLock.tryLock(50, TimeUnit.MILLISECONDS)){
                    return true;
                }
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        return false;
    }

    public void release() {
        if (driverLock.isHeldByCurrentThread()) {
            driverLock.unlock();
        }
    }
}
