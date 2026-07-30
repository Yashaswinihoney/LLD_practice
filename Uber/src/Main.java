import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        RideManager manager = RideManager.getInstance();
        Location mockLoc = new Location(0, 0);

        System.out.println("--- 1. Initialization ---");
        manager.addAvailableDriver(new Driver("D1"));
        manager.addAvailableDriver(new Driver("D2"));

        System.out.println("\n--- 2. Simulating Idempotency (The Double-Click Scenario) ---");
        // We use an ExecutorService to simulate a user's phone sending two requests at the exact same millisecond
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable aggressiveClick = () -> {
            manager.requestRide("Rider_Alice", mockLoc, mockLoc);
        };

        executor.submit(aggressiveClick); // Click 1
        executor.submit(aggressiveClick); // Click 2 (Accidental double tap)

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.SECONDS);

        System.out.println("\n--- 3. Standard Request (Taking the remaining driver) ---");
        manager.requestRide("Rider_Bob", mockLoc, mockLoc);

        System.out.println("\n--- 4. Surge / No Drivers Available ---");
        // Charlie requests a ride, but both D1 and D2 are currently busy
        manager.requestRide("Rider_Charlie", mockLoc, mockLoc);
    }
}