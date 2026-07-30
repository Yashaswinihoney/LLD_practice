import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParkingLot {
    private static volatile ParkingLot instance;
    private final String name;

    private final List<ParkingFloor> floors=new CopyOnWriteArrayList<>();
    private final Map<String,Ticket> activeTickets=new ConcurrentHashMap<>();
    private PricingStrategy pricingStrategy=new HourlyPricing(5.0);

    private ParkingLot(String name){
        this.name=name;
    }

    public static ParkingLot getInstance(String name){
        if(instance==null){
            synchronized (ParkingLot.class){
                instance=new ParkingLot(name);
            }
        }
        return instance;
    }

    public void addFloor(ParkingFloor floor){
        floors.add(floor);
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    private boolean isSpotCompatible(VehicleType vehicleType, SpotType spotType){
        switch (vehicleType){
            case MOTORCYCLE -> {
                return spotType==SpotType.MOTORCYCLE;
            }
            case CAR -> {
                return spotType==SpotType.COMPACT;
            }
            case TRUCK -> {
                return spotType==SpotType.LARGE;
            }
            default -> {
                return false;
            }
        }
    }

    public synchronized Ticket parkVehicle(Vehicle vehicle){
        for(ParkingFloor floor: floors){
            for (ParkingSpot parkingSpot: floor.getSpots()){
                if(parkingSpot.isAvailable()&&isSpotCompatible(vehicle.getType(),parkingSpot.getType())){
                    if (parkingSpot.reserve(vehicle)){
                        Ticket ticket=new Ticket(vehicle,parkingSpot);
                        activeTickets.put(ticket.getTicketId(),ticket);
                        System.out.println("PARKING SUCCESS "+vehicle.getLicensePlate()+" is parked at "+parkingSpot.getId()+" "+floor.getName()+" ticket "+ticket.getTicketId());
                        return ticket;
                    }
                }
            }
        }
        System.out.println("NO PARKING SPOT AVAILABLE");
        return null;
    }

    public synchronized boolean checkOutVehicle(String ticketId, PaymentType paymentType){
        Ticket ticket=activeTickets.get(ticketId);
        if(ticket==null){
            System.out.println("Ticket ID "+ ticketId+" not found");
            return false;
        }

        ticket.setExitTime(System.currentTimeMillis());
        long duration=ticket.getExitTime()-ticket.getEntryTime();
        double fee= pricingStrategy.calculateFee(duration);
        ticket.setFee(fee);

        TransactionRecord txn=new TransactionRecord(ticketId,fee);

        PaymentStrategy strategy=PaymentFactory.getStrategy(paymentType);

        if (strategy.process(txn)){
            ticket.pay();
            ticket.getSpot().release();
            activeTickets.remove(ticketId);
            System.out.println("CHECKOUT SUCCESS Ticket "+ ticketId+" is resolved. Fee "+ fee+" is paid");
            return true;
        }

        System.out.println("CHECKOUT FAILED for Ticket "+ticketId);
        return false;
    }
}
