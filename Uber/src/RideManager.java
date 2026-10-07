import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RideManager {

    //thread safe registry for all rides
    private final Map<String,Ride> activeRides= new ConcurrentHashMap<>();

    //idempotency gaurd, maps ridersId to ride id to prevent double booking
    private final Map<String, String> activeRiderRequests=new ConcurrentHashMap<>();

    private final Map<String,ConcurrentLinkedQueue<Driver>> gridToDrivers=new ConcurrentHashMap<>();
    private RideManager(){}

    private static class InstanceHolder{
        private static final RideManager INSTANCE=new RideManager();
    }

    //double checked locked singleton
    public static RideManager getInstance(){
        return InstanceHolder.INSTANCE;
    }

    public void addAvailableDriver(Driver driver) {
        String gridId = driver.getLocation().getGridId();
        gridToDrivers.putIfAbsent(gridId, new ConcurrentLinkedQueue<>());
        gridToDrivers.get(gridId).add(driver);
        System.out.println("Driver " + driver.getId() + " online in Grid " + gridId);
    }

    public Ride requestRide(String riderId, Location src, Location dest, TripObserver riderApp) {
        // 2. Idempotency Guard
        if (activeRiderRequests.putIfAbsent(riderId, "PENDING") != null) {
            System.err.println("Idempotency guard triggered: " + riderId + " already has an active request.");
            return null;
        }

        String gridId = src.getGridId();
        ConcurrentLinkedQueue<Driver> localDrivers = gridToDrivers.get(gridId);

        System.out.println("Rider " + riderId + " searching Grid " + gridId + "...");

        if (localDrivers != null) {
            Driver matchedDriver = localDrivers.poll();
            while (matchedDriver != null) {

                // 3. Atomic CAS Booking
                if (matchedDriver.tryBook()) {
                    String rideId = "RIDE_" + UUID.randomUUID().toString().substring(0, 5);

                    // Inject pricing strategy (Mocking surge if demand is high)
                    PricingStrategy pricing = localDrivers.isEmpty() ? new SurgePricingStrategy(2.5) : new StandardPricingStrategy();

                    Ride newRide = new Ride(rideId, riderId, src, dest, pricing);
                    newRide.addObserver(riderApp); // Register rider phone
                    newRide.addObserver(new MobileAppClient("Driver_" + matchedDriver.getId())); // Register driver phone

                    if (newRide.acceptRide(matchedDriver.getId())) {
                        activeRides.put(rideId, newRide);
                        activeRiderRequests.put(riderId, rideId);
                        return newRide;
                    } else {
                        // Rollback if ride was cancelled instantly
                        matchedDriver.release();
                        localDrivers.add(matchedDriver);
                    }
                }
                matchedDriver = localDrivers.poll(); // Keep searching if driver was CAS-locked by another thread
            }
        }

        System.err.println("No drivers available for Rider " + riderId + " in this grid.");
        activeRiderRequests.remove(riderId); // Clear idempotency lock
        return null;
    }

    public void finishRide(String rideId, Driver driver) {
        Ride ride = activeRides.get(rideId);
        if (ride != null) {
            ride.completeTrip();
            activeRides.remove(rideId);
            activeRiderRequests.remove(ride.getRiderId());

            // Release driver back into the geospatial pool
            driver.release();
            addAvailableDriver(driver);
        }
    }
}
