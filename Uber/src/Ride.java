import java.util.concurrent.locks.ReentrantLock;

public class Ride {
    //core auditable data, immutable
    private final String id;
    private final String riderId;
    private final Location source;
    private final Location destination;

    //mutable data
    private String driverId; //null until ride found
    private RideStatus status;
    private final ReentrantLock rideLock=new ReentrantLock();

    public Ride(String rideId, String riderId, Location src, Location dest){
        this.id=rideId;
        this.riderId=riderId;
        this.source=src;
        this.destination=dest;
        this.status=RideStatus.REQUESTED;
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

    public boolean acceptRide(String assignedDriverId){
        rideLock.lock();
        try{
            if(this.status==RideStatus.REQUESTED){
                this.status=RideStatus.ACCPETED;
                this.driverId=assignedDriverId; //record the assigned driver to the job/ride
                return true;
            }
            return false;
        }
        finally {
            rideLock.unlock();
        }
    }
}
