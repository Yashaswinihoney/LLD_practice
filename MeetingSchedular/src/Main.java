import java.util.EnumSet;
import java.util.List;
import java.util.Set;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        MeetingSchedular scheduler=MeetingSchedular.getInstance();

        scheduler.addRoom("room1",20,EnumSet.of(Facility.PROJECTOR, Facility.VIDEO_CONFERENCING));
        scheduler.addRoom("room2",5, EnumSet.of(Facility.WHITEBOARD));

        // Epoch timestamps (e.g., 10:00 AM to 11:00 AM)
        long startSlot = 1700000000L;
        long endSlot =   1700003600L;

        // User needs a room for at least 8 people that has a Projector
        Set<Facility> requirements = EnumSet.of(Facility.PROJECTOR);
        // Simulate massive concurrency (Two managers trying to book the same room at the exact same millisecond)

        List<MeetingRoom> availableRooms = scheduler.searchAvailableRooms(startSlot, endSlot, 8, requirements);
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