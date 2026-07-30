import java.util.TreeSet;

public class Elevator {
    private final int id;
    private int currentFloor=0;
    private Direction direction=Direction.IDLE;
    private final TreeSet<Integer> upRequests=new TreeSet<>();
    private final TreeSet<Integer> downRequests=new TreeSet<>();

    public Elevator(int id){
        this.id=id;
    }

    public int getId(){
        return id;
    }

    public synchronized int getCurrentFloor(){
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public synchronized void addRequests(int floor){
        if(floor>currentFloor){
            upRequests.add(floor);
            if (direction==Direction.IDLE){
                direction=Direction.UP;
            }
        }
        else if(floor<currentFloor){
            downRequests.add(floor);
            if (direction==Direction.IDLE){
                direction=Direction.DOWN;
            }
        }
        else{
            System.out.println("Elevator is already on "+currentFloor+" Doors open");
        }
    }

    public synchronized void step(){
        if (direction==Direction.UP){
            if (!upRequests.isEmpty()){
                currentFloor++;
                System.out.println("Elevator "+ id+" moving UP, reached floor "+ currentFloor);
                if(upRequests.contains(currentFloor)){
                    upRequests.remove(currentFloor);
                    System.out.println("Elevator "+ id+ " STOPPED at floor "+ currentFloor);
                }
                if(upRequests.isEmpty()){
                    direction=(downRequests.isEmpty()?Direction.IDLE: Direction.DOWN);
                }
            }
            else{
                direction=(downRequests.isEmpty()?Direction.IDLE: Direction.DOWN);
            }
        }
        else if(direction==Direction.DOWN){
            if (!downRequests.isEmpty()){
                currentFloor--;
                System.out.println("Elevator "+ id+" moving DOWN, reached floor "+ currentFloor);
                if(downRequests.contains(currentFloor)){
                    downRequests.remove(currentFloor);
                    System.out.println("Elevator "+ id+ " STOPPED at floor "+ currentFloor);
                }
                if(downRequests.isEmpty()){
                    direction=(upRequests.isEmpty()?Direction.IDLE: Direction.UP);
                }
            }
            else{
                direction=(upRequests.isEmpty()?Direction.IDLE: Direction.UP);
            }
        }
    }

    public synchronized boolean hasRequests(){
        return !upRequests.isEmpty()||!downRequests.isEmpty();
    }
}
