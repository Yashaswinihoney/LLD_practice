import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

public class Elevator {
    private final int id;
    //volatile guarantees memory visibility for lock-free reads
    private volatile int currentFloor=0;
    private volatile Direction direction=Direction.IDLE;
    //TreeSet to store the requests by the time they were added, so we can priorities the requests that come first
    private final TreeSet<Integer> upRequests=new TreeSet<>();
    private final TreeSet<Integer> downRequests=new TreeSet<>();
    //explicit programmaeble locking limits the critical section scope
    private final ReentrantLock lock=new ReentrantLock();
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

    public  void addRequests(int floor){
        lock.lock();
        try{
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
        finally {
            lock.unlock();
        }
    }

    public void step(){
        lock.lock();
        try{
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
        finally {
            lock.unlock();
        }
    }

    public synchronized boolean hasRequests(){
        lock.lock();
        try{
            return !upRequests.isEmpty()||!downRequests.isEmpty();
        }
        finally {
            lock.unlock();
        }
    }
}
