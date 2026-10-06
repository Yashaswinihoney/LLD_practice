public class ParkingSpot {
    private final String spotId;
    private final SpotType spotType;
    private Vehicle vehicle;
    private volatile boolean isOccupied;

    public ParkingSpot(String id, SpotType type){
        this.spotId=id;
        this.spotType=type;
        this.isOccupied=false;
    }

    public boolean canFit(VehicleType vehicleType){
        if (isOccupied) return false;

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

    public void park(Vehicle v){
        this.vehicle=v;
        this.isOccupied=true;
    }

    public void vacate(){
        this.vehicle=null;
        this.isOccupied=false;
    }

    public String getSpotId(){
        return spotId;
    }
}
