// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
       StockMarket market=new StockMarket();
        market.addObserver(new MobileApp("Alice"));
        market.addObserver(new MobileApp("Bob"));
        market.addObserver(new EmailAlert());

        System.out.println("--- Price Update: AAPL ---");
        market.setPrice("AAPL", 150.0);

        System.out.println("\n--- Price Update: TSLA ---");
        market.setPrice("TSLA", 520.0);
    }
}