import java.util.List;

public class OptimalDispatchStrategy implements DispatchStrategy{
    @Override
    public Elevator selectElevator(List<Elevator> elevators, Request request) {
        Elevator best=null;
        int minCost=Integer.MAX_VALUE;

        for(Elevator e: elevators){
            int cost=calculateCost(e,request);
            if(minCost>cost){
                minCost=cost;
                best=e;
            }
        }

        return best!=null?best:elevators.get(0);
    }

    private int calculateCost(Elevator e, Request request){
        int currentFloor=e.getCurrentFloor();
        Direction dir=e.getDirection();
        int target=request.getTargetFloor();

        if(dir==Direction.IDLE){
            return Math.abs(target-currentFloor);
        }
        else if(dir==Direction.UP&&target>=currentFloor&& request.getDirection()==Direction.UP){
            return target-currentFloor;
        }
        else if(dir==Direction.DOWN&&target<=currentFloor&& request.getDirection()==Direction.DOWN){
            return currentFloor-target;
        }
        else{
            return Math.abs(target-currentFloor)+100;
        }
    }
}
