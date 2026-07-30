import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParkingFloor {
    private final String name;
    private final List<ParkingSpot> spots=new CopyOnWriteArrayList<>();

    public ParkingFloor(String name){
        this.name=name;
    }

    public void addSpot(ParkingSpot spot){
        spots.add(spot);
    }

    public List<ParkingSpot> getSpots(){
        return spots;
    }

    public String getName() {
        return name;
    }
}
