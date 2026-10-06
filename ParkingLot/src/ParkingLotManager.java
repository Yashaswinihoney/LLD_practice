//singleton manager using BILL PUGH TECHNIQUE

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLotManager {
    private final List<ParkingFloor> floors=new ArrayList<>();
    //lock free atomic map to track the tickets globally
    private final Map<String, Ticket> activeTickets=new ConcurrentHashMap<>();
    private PricingStrategy pricingStrategy;

    //PRIVATE CONSTRUCTOR
    private ParkingLotManager(){}

    public Map<String, Ticket> getActiveTickets() {
        return activeTickets;
    }

    //PRIVATE STATIC INSTANCE HOLDER
    private static class InstanceHolder{
        private static final ParkingLotManager INSTANCE=new ParkingLotManager();
    }

    public static ParkingLotManager getInstance(){
        return InstanceHolder.INSTANCE;
    }

    public void initialize(int numFloors, PricingStrategy pricingStrategy){
        this.pricingStrategy=pricingStrategy;
        for(int i=1;i<=numFloors;i++){
            floors.add(new ParkingFloor(i,10,25,5));
        }
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.findAndReserveSpot(vehicle);
            if (spot != null) {
                Ticket ticket = new Ticket(vehicle, spot);
                activeTickets.put(ticket.getTicketId(), ticket);
                System.out.println("Assigned Spot " + spot.getSpotId() + " to " + vehicle.getLicensePlate());
                return ticket;
            }
        }
        System.out.println("PARKING FULL for " + vehicle.getType());
        return null;
    }

    public boolean processExit(String ticketId, PaymentStrategy paymentStrategy) {
        Ticket ticket = activeTickets.remove(ticketId); // Atomic removal
        if (ticket == null) {
            System.err.println("Invalid Ticket ID.");
            return false;
        }

        double fee = pricingStrategy.calculateFee(ticket.getEntryTime(), LocalDateTime.now());
        boolean isPaid = paymentStrategy.process(fee);

        if (isPaid) {
            // Free the spot
            ticket.getSpot().vacate();
            System.out.println("Vehicle exited cleanly. Spot " + ticket.getSpot().getSpotId() + " is now free.");
            return true;
        }

        // Rollback if payment fails
        activeTickets.put(ticketId, ticket);
        return false;
    }
}
