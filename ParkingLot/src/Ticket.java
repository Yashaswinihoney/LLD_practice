import java.util.concurrent.atomic.AtomicInteger;

public class Ticket{
    private static final AtomicInteger counter= new AtomicInteger(1);
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final long entryTime;
    private long exitTime;
    private double fee;
    private boolean isPaid;

    public Ticket(Vehicle vehicle, ParkingSpot spot){
        this.vehicle=vehicle;
        this.spot=spot;
        this.entryTime=System.currentTimeMillis();
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

    public long getEntryTime() {
        return entryTime;
    }

    public void setExitTime(long exitTime) {
        this.exitTime = exitTime;
    }

    public long getExitTime() {
        return exitTime;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void pay() {
        isPaid = true;
    }
}
