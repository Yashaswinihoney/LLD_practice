import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ElevatorController {
    private final List<Elevator> elevators;
    private final DispatchStrategy strategy;

    public ElevatorController(int elevatorCount, DispatchStrategy strategy){
        this.strategy=strategy;
        elevators=new CopyOnWriteArrayList<>();
        for(int i=0;i<elevatorCount;i++){
            elevators.add(new Elevator(i+1));
        }
    }

    public void requestElevator(int floor, Direction direction){
        Request req=new Request(floor,direction);
        Elevator elevatorSelected= strategy.selectElevator(elevators,req);
        System.out.println("Dispatched Elevator "+ elevatorSelected.getId()+" to the floor "+ floor);
        elevatorSelected.addRequests(floor);
    }

    public void step(){
        for(Elevator e: elevators){
            e.step();
        }
    }

    public  boolean hasActiveRequests(){
        for (Elevator e: elevators){
            if(e.hasRequests()) return true;
        }
        return false;
    }

    public List<Elevator> getElevators(){
        return elevators;
    }
}
