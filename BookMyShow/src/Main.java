// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
//        System.out.printf("Hello and welcome!");
//
//        // Press Ctrl+R or click the green arrow button in the gutter to run the code.
//        for (int i = 1; i <= 5; i++) {
//
//            // Press Ctrl+D to start debugging your code. We have set one breakpoint
//            // for you, but you can always add more by pressing Cmd+F8.
//            System.out.println("i = " + i);
//        }

        BookMyShow manager=new BookMyShow();

        Movie avengers=new Movie("Avengers",180);

        manager.addShow(1,avengers);
        Show avengersShow=manager.getShow(1);

        //user wants to see all available seats
        avengersShow.displayAvailableSeats();

        //user tries to book seat 5
        avengersShow.bookSeat(5);

        //another user tries to book seeat 5
        avengersShow.bookSeat(5);

        //another user tries to book seat 6
        avengersShow.bookSeat(6);

        avengersShow.displayAvailableSeats();;
    }
}