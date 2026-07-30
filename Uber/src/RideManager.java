import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RideManager {
    private static volatile RideManager instance;

    //thread safe registry for all rides
    private final Map<String,Ride> activeRides= new ConcurrentHashMap<>();

    //idempotency gaurd, maps ridersId to ride id to prevent double booking
    private final Map<String, String> activeRiderRequests=new ConcurrentHashMap<>();

    //Threadsafe queue strictly for drivers currently looking for rides
    private final ConcurrentLinkedQueue<Driver> availableDrivers=new ConcurrentLinkedQueue<>();

    private RideManager(){}

    //double checked locked singleton
    public static RideManager getInstance(){
        if (instance==null){
            synchronized (RideManager.class){
                if (instance==null) instance=new RideManager();
             }
        }
        return instance;
    }

    public  void addAvailableDriver(Driver driver){
        availableDrivers.add(driver);
        System.out.println("Driver "+ driver.getId()+" is now online");
    }

    public Ride requestRide(String riderId, Location src, Location dest){

        //idempotency check
        String placeholderRide="PENDING_"+ UUID.randomUUID().toString();
        String existingRide=activeRiderRequests.putIfAbsent(riderId,placeholderRide);

        if(existingRide!=null){
            System.err.println("Idempotency gaurd triggered, Rider "+ riderId+" already has an active request");
            return null;
        }

        System.out.println("Rider "+riderId+" searching for drivers");

        //fetching next available driver
        Driver matchedDriver=availableDrivers.poll();

        while (matchedDriver!=null){
            if (matchedDriver.tryBook()){
                String finalRideId="RIDE_"+UUID.randomUUID().toString().substring(0,5);

                //constructing a fully auditable ride id
                Ride newRide=new Ride(finalRideId, riderId, src, dest);

                if (newRide.acceptRide(matchedDriver.getId())){
                    activeRides.put(finalRideId,newRide);

                    //update idempotency map to track the new ride
                    activeRiderRequests.put(riderId,finalRideId);

                    System.out.println("Rider "+ riderId+" matched with driver "+ matchedDriver.getId()+ " for "+ finalRideId);
                }
                else{
                    matchedDriver.release();
                    availableDrivers.add(matchedDriver);
                }
            }
            matchedDriver=availableDrivers.poll();
        }

        System.err.println("No drivers available for the Rider "+riderId);
        //clear the idempotency lock so they can try again later
        activeRiderRequests.remove(riderId);
        return null;
    }
}
