// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ParkingLot lot = ParkingLot.getInstance("Grand Arena");

        // Setup Floor & Spots
        ParkingFloor f1 = new ParkingFloor("Floor 1");
        f1.addSpot(new ParkingSpot("F1-M1", SpotType.MOTORCYCLE));
        f1.addSpot(new ParkingSpot("F1-C1", SpotType.COMPACT));
        f1.addSpot(new ParkingSpot("F1-L1", SpotType.LARGE));
        lot.addFloor(f1);

        // Initialize Vehicles
        Vehicle car1 = new Car("KA-01-AB-1234");
        Vehicle moto1 = new Motorcycle("KA-01-XYZ-567");

        System.out.println("--- 1. Parking Vehicles ---");
        Ticket t1 = lot.parkVehicle(car1);
        Ticket t2 = lot.parkVehicle(moto1);

        System.out.println("\n--- 2. Checking Out Vehicles ---");
        lot.checkOutVehicle(t1.getTicketId(), PaymentType.UPI);
        lot.checkOutVehicle(t2.getTicketId(), PaymentType.CARD);
    }
}