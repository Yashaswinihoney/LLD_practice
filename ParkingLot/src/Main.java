import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // 1. Initialize the Bill Pugh Singleton Manager[cite: 43]
        ParkingLotManager manager = ParkingLotManager.getInstance();

        // Injecting the Hourly Pricing Strategy (Dependency Inversion)
        manager.initialize(2, new HourlyPricing(10.0));

        // 3. Define Vehicles
        Vehicle car1 = new Vehicle("CAR-100", VehicleType.CAR);
        Vehicle car2 = new Vehicle("CAR-200", VehicleType.CAR);
        Vehicle moto1 = new Vehicle("MOTO-99", VehicleType.MOTORCYCLE);
        Vehicle truck1 = new Vehicle("TRK-555", VehicleType.TRUCK);

        // 4. Concurrent Parking Simulation (Thread Pool)
        // Utilizing ExecutorService to simulate high-throughput concurrent arrivals
        ExecutorService entryGatePool = Executors.newFixedThreadPool(4);

        Runnable parkCar1 = () -> manager.parkVehicle(car1);
        Runnable parkCar2 = () -> manager.parkVehicle(car2);
        Runnable parkMoto = () -> manager.parkVehicle(moto1);
        Runnable parkTruck = () -> manager.parkVehicle(truck1);

        // All vehicles attempt to enter the exact same millisecond
        entryGatePool.submit(parkCar1);
        entryGatePool.submit(parkCar2);
        entryGatePool.submit(parkMoto);
        entryGatePool.submit(parkTruck);

        entryGatePool.shutdown();
        try {
            entryGatePool.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n=== SIMULATING EXITS & PAYMENTS ===");

        // 5. Simulating Exits using Strategy Pattern
        PaymentStrategy cardPayment = new CardPaymentStrategy();

        // In a real system, the Ticket ID would be scanned at the boom barrier.
        // We simulate extracting the ticket ID from the manager's active registry.
        String dummyTicketId = manager.getActiveTickets().keySet().stream().findFirst().orElse(null);

        if (dummyTicketId != null) {
            System.out.println("Vehicle approaching exit with Ticket ID: " + dummyTicketId);
            manager.processExit(dummyTicketId, cardPayment);
        }
    }
}