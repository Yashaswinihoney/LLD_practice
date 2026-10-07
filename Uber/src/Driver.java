import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Driver {
    private final String id;
    private Location currentLocation;
    private final AtomicBoolean isAvailable=new AtomicBoolean(true);

    public Driver(String id, Location startLocation){
        this.id=id;
        this.currentLocation=startLocation;
    }

    public String getId(){
        return id;
    }

    public Location getLocation() {
        return currentLocation;
    }

    public void setLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public boolean tryBook(){
        return isAvailable.compareAndSet(true,false);
    }

    public void release() {
        isAvailable.set(true);
    }
}
