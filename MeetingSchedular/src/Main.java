// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        MeetingSchedular scheduler=MeetingSchedular.getInstance();

        scheduler.addRoom("room1",20);
        scheduler.addRoom("room2",5);

        // Epoch timestamps (e.g., 10:00 AM to 11:00 AM)
        long startSlot = 1700000000L;
        long endSlot =   1700003600L;

        // Simulate massive concurrency (Two managers trying to book the same room at the exact same millisecond)
        Thread manager1 = new Thread(() -> {
            scheduler.bookRoom("room1", startSlot, endSlot);
        }, "Thread-Manager-1");

        Thread manager2 = new Thread(() -> {
            scheduler.bookRoom("room2", startSlot, endSlot);
        }, "Thread-Manager-2");

        manager1.start();
        manager2.start();
    }
}