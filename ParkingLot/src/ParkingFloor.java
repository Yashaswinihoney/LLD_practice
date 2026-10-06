import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingFloor {
    private final int floorLevel;
    private final List<ParkingSpot> spots=new ArrayList<>();
    //dedicated lock per floor
    private final ReentrantLock floorLock=new ReentrantLock();
    public ParkingFloor(int floorLevel, int motorcyclespots, int carspots, int truckspots){
        this.floorLevel=floorLevel;
        for(int i=0;i<motorcyclespots;i++){
            spots.add(new ParkingSpot(floorLevel+"-M"+i,SpotType.MOTORCYCLE));
        }
        for(int i=0;i<carspots;i++){
            spots.add(new ParkingSpot(floorLevel+"-C"+i,SpotType.COMPACT));
        }
        for(int i=0;i<truckspots;i++){
            spots.add(new ParkingSpot(floorLevel+"-L"+i,SpotType.LARGE));
        }
    }

    public ParkingSpot findAndReserveSpot(Vehicle vehicle){
        floorLock.lock();
        try{
            for(ParkingSpot spot: spots){
                if (spot.canFit(vehicle.getType())){
                    spot.park(vehicle);
                    return spot;
                }
            }
            return null;
        }
        finally {
            floorLock.unlock();
        }
    }
}
