import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Show {
    private final int id;
    private final Movie movie;
    private final Map<Integer,SeatStatus> seatStatusMap=new ConcurrentHashMap<>();
    private final List<Seat> seats= new ArrayList<>();
    ReentrantLock lock=new ReentrantLock();

    public Show(int id, Movie movie){
        this.id=id;
        this.movie=movie;

        //initialising seats
        for(int i=1;i<=10;i++){
            seats.add(new Seat(i,SeatCategory.SILVER,100.0));
            seatStatusMap.put(i,SeatStatus.AVAILABLE);
        }
//        System.out.println(" ");
        for(int i=11;i<=20;i++){
            seats.add(new Seat(i,SeatCategory.GOLD,200.0));
            seatStatusMap.put(i,SeatStatus.AVAILABLE);
        }
    }

    public  void displayAvailableSeats(){
        lock.lock();
        try{
            System.out.print("Show " + id + " (" + movie.getTitle() + "): ");
            seatStatusMap.forEach((seatId, status) -> {
                if (status == SeatStatus.AVAILABLE) System.out.print("[S" + seatId + "] ");
                else if (status == SeatStatus.LOCKED) System.out.print("[LOCKED] ");
                else System.out.print("[X] ");
            });
            System.out.println();
        }
        finally {
            lock.unlock();
        }
    }

    public boolean bookSeat(int seatId){
        lock.lock();
        try {
            //validate seat's existence
            if(!seatStatusMap.containsKey(seatId)){
                System.out.println("ERROR: seat "+ seatId+" does not exist");
                return false;
            }

            //check seat status, if available then only proceed
            if(seatStatusMap.get(seatId)!=SeatStatus.AVAILABLE){
                System.out.println("Failure: Seat "+ seatId+" is already taken");
                return false;
            }

            seatStatusMap.put(seatId,SeatStatus.BOOKED);
            System.out.println("SUCCESS: Seat "+ seatId+" is booked");
            return true;
        }
        finally {
            lock.unlock();
        }
    }
}
