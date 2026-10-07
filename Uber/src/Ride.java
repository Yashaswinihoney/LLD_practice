import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Ride {
    //core auditable data, immutable
    private final String id;
    private final String riderId;
    private final Location source;
    private final Location destination;
    private final PricingStrategy pricingStrategy;
    //mutable data
    private String driverId; //null until ride found
    private RideStatus status;
    private final List<TripObserver> observers = new ArrayList<>();
    private final ReentrantLock rideLock=new ReentrantLock();

    public Ride(String rideId, String riderId, Location src, Location dest, PricingStrategy strategy){
        this.id=rideId;
        this.riderId=riderId;
        this.source=src;
        this.destination=dest;
        this.status=RideStatus.REQUESTED;
        this.pricingStrategy=strategy;
    }

    public void addObserver(TripObserver observer) { observers.add(observer); }
    private void notifyObservers() {
        for (TripObserver obs : observers) obs.onTripStatusChanged(this);
    }

    public String getId(){
        return id;
    }

    public String getRiderId(){
        return riderId;
    }

    public String getDriverId(){
        return driverId;
    }

    public RideStatus getStatus(){
        return status;
    }
    public double getEstimatedFare(){
        return pricingStrategy.calculateFare(source.distanceTo(destination));
    }

    public boolean acceptRide(String assignedDriverId){
        rideLock.lock();
        try{
            if(this.status==RideStatus.REQUESTED){
                this.status=RideStatus.ACCPETED;
                this.driverId=assignedDriverId; //record the assigned driver to the job/ride
                notifyObservers();
                return true;
            }
            //ride is already in progress
            return false;
        }
        finally {
            rideLock.unlock();
        }
    }

    public void startTrip(){
        rideLock.lock();
        try{
            if (this.status==RideStatus.ACCPETED){
                this.status=RideStatus.IN_PROGRESS;
                notifyObservers();
            }
        }
        finally {
            rideLock.unlock();
        }
    }

    public void completeTrip() {
        rideLock.lock();
        try {
            if (this.status == RideStatus.IN_PROGRESS) {
                this.status = RideStatus.COMPLETED;
                notifyObservers();
                System.out.println("Receipt for " + id + ": $" + getEstimatedFare());
            }
        } finally {
            rideLock.unlock();
        }
    }
}
