import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Show {
    private final int id;
    private final Movie movie;
    private final Map<Integer,SeatStatus> seatStatusMap;
    private final Map<Integer,Seat> seats;
    ReentrantLock lock=new ReentrantLock();

    public Show(int id, Movie movie, Map<Integer, SeatStatus> seatStatusMap, Map<Integer,Seat> seats){
        this.id=id;
        this.movie=movie;
        this.seats=seats;
        this.seatStatusMap=seatStatusMap;
    }

    public Map<Integer,SeatStatus> getSeatStatusSnapshot(){
        return new ConcurrentHashMap<>(seatStatusMap);
        //return a copy
    }

    public boolean bookSeat(int seatId){
        //locking using concurrenthashmap
        if(!seatStatusMap.containsKey(seatId)) return false;
        //hardware level thread safety
        return seatStatusMap.replace(seatId,SeatStatus.AVAILABLE,SeatStatus.BOOKED);

//        lock.lock();
//        try {
//            //validate seat's existence
//            if(!seatStatusMap.containsKey(seatId)){
//                System.out.println("ERROR: seat "+ seatId+" does not exist");
//                return false;
//            }
//
//            //check seat status, if available then only proceed
//            if(seatStatusMap.get(seatId)!=SeatStatus.AVAILABLE){
//                System.out.println("Failure: Seat "+ seatId+" is already taken");
//                return false;
//            }
//
//            seatStatusMap.put(seatId,SeatStatus.BOOKED);
//            System.out.println("SUCCESS: Seat "+ seatId+" is booked");
//            return true;
//        }
//        finally {
//            lock.unlock();
//        }
    }
}
