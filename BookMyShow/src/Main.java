import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        BookMyShowManager manager=BookMyShowManager.getInstance();
        Map<Integer, Seat> seatConfig=new ConcurrentHashMap<>();
        Map<Integer,SeatStatus> statusConfig=new ConcurrentHashMap<>();

        for(int i=1;i<=10;i++){
            seatConfig.put(i,new Seat(i,SeatCategory.SILVER,100.0));
            statusConfig.put(i,SeatStatus.AVAILABLE);
        }

        Show matrixShow = new Show(1, new Movie("The Matrix", 136), statusConfig, seatConfig);
        manager.addShow(1, matrixShow);

        // Simulating Concurrent Booking
        Show show = manager.getShow(1);

        Runnable bookSeat5 = () -> {
            boolean success = show.bookSeat(5);
            System.out.println(Thread.currentThread().getName() + " booking Seat 5: " + (success ? "SUCCESS" : "FAILED"));
        };

        // Threads race, but the ConcurrentHashMap replace() ensures only one succeeds
        new Thread(bookSeat5, "Thread-A").start();
        new Thread(bookSeat5, "Thread-B").start();


        //creating threads using Executor service
        //initialise the pool with a fixed no of resusable threads
//        ExecutorService threadPool= Executors.newFixedThreadPool(2);
//        Runnable runnable=()->{
//            System.out.println(Thread.currentThread().getName());
//        };
//        //submit tasks
//        //you no longer care how or when the threads start, we just hand the task to the manager
//        threadPool.submit(runnable);
//        threadPool.submit(runnable);
//        threadPool.submit(runnable);
//
//        //graceful shutdown
//        threadPool.shutdown();
        //forced shutdown
        //threadPool.shutdownNow();
    }
}