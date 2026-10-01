public class MobileApp implements StockObserver{
    private final String name;
    public MobileApp(String name){
        this.name=name;
    }
    @Override
    public void update(String symbol, Double price) {
        System.out.println("Mobile app "+name+ " symbol "+symbol+" now at ₹"+price);
    }

}
