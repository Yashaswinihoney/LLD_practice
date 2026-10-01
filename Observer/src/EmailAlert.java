public class EmailAlert implements StockObserver{
    @Override
    public void update(String symbol, Double price){
        System.out.println("Email alert "+symbol+" trading at price ₹"+price);
    }
}
