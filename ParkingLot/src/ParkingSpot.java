public class ParkingSpot {
    private final String id;
    private final SpotType type;
    private Vehicle currentVehicle;
    private boolean isFree=true;

    public ParkingSpot(String id, SpotType type){
        this.id=id;
        this.type=type;
    }

    public synchronized boolean isAvailable(){
        return isFree;
    }

    public synchronized boolean reserve(Vehicle vehicle){
        if (!isFree) return false;
        this.isFree=false;
        this.currentVehicle=vehicle;
        return true;
    }

    public synchronized void release(){
        this.isFree=true;
        this.currentVehicle=null;
    }

    public String getId() {
        return id;
    }

    public SpotType getType() {
        return type;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }
}
