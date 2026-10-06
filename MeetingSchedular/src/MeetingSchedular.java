import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//BILL PUGH SINGLETON
public class MeetingSchedular {
    private final Map<String,MeetingRoom> rooms=new ConcurrentHashMap<>();
    private MeetingSchedular(){}
    private static class InstanceHolder{
        private static final MeetingSchedular INSTANCE=new MeetingSchedular();
    }
    public static MeetingSchedular getInstance(){
        return InstanceHolder.INSTANCE;
    }

    //no need for locks coz we are using concurrenthashmap
    public void addRoom(String roomId, int capacity, Set<Facility> facilities) {
        rooms.putIfAbsent(roomId, new MeetingRoom(roomId, capacity, facilities));
    }

    //filters all rooms matching the capacity constrain available during the given time slot
    public List<MeetingRoom> searchAvailableRooms(long start, long end, int minCap, Set<Facility> requiredFacilities){
        List<MeetingRoom> res=new ArrayList<>();
        for(var room: rooms.values()){
            if (room.getCapacity()>=minCap&& room.isAvailable(start,end)&& room.getFacilities().containsAll(requiredFacilities)){
                res.add(room);
            }
        }
        return res;
    }

    //routes the booking request directly to the room's direct internal lock, coz we use concurrenthashmap
    public Meeting bookRoom(String roomId, long start, long end){
        MeetingRoom room=rooms.get(roomId);
        if(room==null){
            throw new IllegalArgumentException("Room "+roomId+" does not exist");
        }
        return room.book(start,end);
    }
}
