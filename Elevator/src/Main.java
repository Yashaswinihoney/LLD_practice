// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ElevatorController controller = new ElevatorController(2, new OptimalDispatchStrategy());
        controller.requestElevator(3, Direction.UP);
        controller.requestElevator(7, Direction.DOWN);

        int ticks = 0;
        while (controller.hasActiveRequests() && ticks < 15) {
            System.out.printf("--- Tick %d ---\n", ++ticks);
            controller.step();
        }
    }
}