public class Reservation {
    private final String id;
    private final String userId;
    private final Vehicle vehicle;
    private final int plannedDays;
    private final long timeStamp;

    public Reservation(String id, String userId, Vehicle vehicle, int plannedDays){
        this.id=id;
        this.userId=userId;
        this.vehicle=vehicle;
        this.plannedDays=plannedDays;
        timeStamp=System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public int getPlannedDays() {
        return plannedDays;
    }

    public String getUserId() {
        return userId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
