// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ElevatorManager manager=ElevatorManager.getInstance();
        manager.initialize(4,new OptimalDispatchStrategy());

        //cConcurrent requests simulation
        new Thread(()->manager.requestElevator(3,Direction.UP),"User-Thread-1").start();
        new Thread(()->manager.requestElevator(7,Direction.DOWN),"User-Thread-2").start();
        new Thread(()->manager.requestElevator(9,Direction.UP),"User-Thread-3").start();
        new Thread(()->manager.requestElevator(5,Direction.DOWN),"User-Thread-4").start();

        int ticks = 0;
        // In a real system, step() would run in a continuous background daemon thread.
        while (manager.hasActiveRequests() && ticks < 15) {
            System.out.printf("--- Tick %d ---\n", ++ticks);
            manager.step();
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
}