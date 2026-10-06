import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Ticket{
    private static final AtomicInteger counter= new AtomicInteger(1);
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime entryTime;

    public Ticket(Vehicle vehicle, ParkingSpot spot){
        this.vehicle=vehicle;
        this.spot=spot;
        this.entryTime=LocalDateTime.now();
        this.ticketId="TKT-"+counter.getAndIncrement();
    }

    public String getTicketId(){
        return ticketId;
    }
    public Vehicle getVehicle(){
        return vehicle;
    }

    public ParkingSpot getSpot(){
        return spot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

}
