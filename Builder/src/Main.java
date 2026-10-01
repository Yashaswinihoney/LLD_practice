// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        HotelBooking booking=new HotelBooking.Builder("Mumbai","2025-06-01","2025-06-05").guests(2).breakfast(true).parking(true).build();
        System.out.println(booking);
    }
}