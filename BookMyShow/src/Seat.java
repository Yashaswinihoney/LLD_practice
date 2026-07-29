public class Seat {
    private final int id;
    private final SeatCategory category;
    private final double price;

    public Seat(int id, SeatCategory cat, double price){
        this.id=id;
        this.category=cat;
        this.price=price;
    }

    public int getId(){
        return id;
    }

    public double getPrice(){
        return price;
    }
}
