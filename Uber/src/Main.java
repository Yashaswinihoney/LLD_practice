public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== THREAD-SAFE RIDE SHARING SIMULATION ===");
        RideManager manager = RideManager.getInstance();

        // Mock Grid Coordinate (e.g., Downtown)
        Location downtown = new Location(37.77, -122.41);
        Location airport = new Location(37.62, -122.38);

        Driver d1 = new Driver("D1", downtown);
        Driver d2 = new Driver("D2", downtown);
        manager.addAvailableDriver(d1);
        manager.addAvailableDriver(d2);

        MobileAppClient alicePhone = new MobileAppClient("Rider_Alice");

        // Simulate Alice booking a ride
        Ride aliceRide = manager.requestRide("Alice", downtown, airport, alicePhone);

        if (aliceRide != null) {
            System.out.println("\n--- SIMULATING TRIP LIFECYCLE ---");
            aliceRide.startTrip();
            // Simulating time passage
            Thread.sleep(100);
            manager.finishRide(aliceRide.getId(), d1);
        }
    }
}