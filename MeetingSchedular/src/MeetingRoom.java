import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class MeetingRoom {
    private final String roomId;
    private final int capacity;
    private final List<Meeting> calendar;
    //private lock for every room
    private final ReentrantLock roomLock=new ReentrantLock();
    public MeetingRoom(String roomId, int capacity) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.calendar = new CopyOnWriteArrayList<>();
    }

    public String getRoomId(){
        return roomId;
    }

    public int getCapacity() {
        return capacity;
    }

    //read only to check if room is available
    public boolean isAvailable(long start, long end){
        roomLock.lock();
        try{
            return isAvailableInternal(start,end);
        }
        finally {
            roomLock.unlock();
        }
    }

    //autoatically hread safe coz called from inside a lock
    private boolean isAvailableInternal(long start, long end){
        for(Meeting m: calendar){
            if(end<m.getStartTime()&&start<m.getEndTime()) return false;
        }
        return true;
    }

    //strict check then act, inside the locked state
    public Meeting book(long start, long end){
        roomLock.lock();
        try{
            if (!isAvailableInternal(start,end)){
                System.err.println(roomId+"Booking failed, room unavailable during this time");
                return null;
            }
            Meeting meeting=new Meeting(UUID.randomUUID().toString(),start,end);
            calendar.add(meeting);
            calendar.sort(Comparator.comparingLong(Meeting::getEndTime));

            System.out.println(roomId+" Booked");
            return meeting;
        }
        finally {
            roomLock.unlock();
        }
    }
}
