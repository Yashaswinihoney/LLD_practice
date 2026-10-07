import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

//seperate orderbook for each symbol/stock
public class OrderBook {
    private final String symbol;

// BUYERS want to pay the LOWEST price, but the market prioritizes the HIGHEST bidder (Max-Heap)
    private final PriorityQueue<Order> buyBook=new PriorityQueue<>((a,b)->{
        if (Double.compare(b.getPrice(),a.getPrice())==0){
            return Long.compare(a.getTimestamp(),b.getTimestamp());
            //if bidding prices are same, time is priority
        }
        return Double.compare(b.getPrice(),a.getPrice());
    });

    // SELLERS want to charge the HIGHEST price, but the market prioritizes the LOWEST offer (Min-Heap)
    private final PriorityQueue<Order> sellBook=new PriorityQueue<>((a,b)->{
        if (Double.compare(a.getPrice(),b.getPrice())==0){
            return Long.compare(a.getTimestamp(),b.getTimestamp());
            //if bidding prices are same, time is priority
        }
        return Double.compare(a.getPrice(),b.getPrice());
    });

    private final ReentrantLock bookLock=new ReentrantLock();
    public OrderBook(String symbol){
        this.symbol=symbol;
    }
    public void processOrder(Order newOrder){
        bookLock.lock();
        try {
            matchOrder(newOrder);
        }
        finally {
            bookLock.unlock();
        }
    }
    private void matchOrder(Order newOrder){
        if (newOrder.getSide()==Side.BUY){
            match(newOrder,sellBook,buyBook,true);
        }
        else{
            match(newOrder,buyBook,sellBook,false);
        }
    }

   //reusable matching logic for both buy and sell sides
    private void match(Order aggressiveOrder, PriorityQueue<Order> restingBook, PriorityQueue<Order> ownBook, boolean isBuy) {
        while (aggressiveOrder.getQuantity() > 0 && !restingBook.isEmpty()) {
            Order restingOrder = restingBook.peek();

            // Check if prices cross (Buy price >= Sell price)
            boolean pricesCross = isBuy ? aggressiveOrder.getPrice() >= restingOrder.getPrice()
                    : aggressiveOrder.getPrice() <= restingOrder.getPrice();

            if (!pricesCross) break; // Gap in the market, no more matches possible

            // Execute the trade
            int tradeQuantity = Math.min(aggressiveOrder.getQuantity(), restingOrder.getQuantity());
            double executionPrice = restingOrder.getPrice(); // The resting order always dictates the execution price

            System.out.println("TRADE EXECUTED: " + tradeQuantity + " shares of " + symbol +
                    " @ $" + executionPrice + " (" + aggressiveOrder.getOrderId() + " matched with " + restingOrder.getOrderId() + ")");

            // Deduct quantities
            aggressiveOrder.reduceQuantity(tradeQuantity);
            restingOrder.reduceQuantity(tradeQuantity);

            // If the resting order is completely filled, remove it from the top of the heap
            if (restingOrder.getQuantity() == 0) {
                restingBook.poll();
            }
        }

        // If the aggressive order still has unfilled quantity, add it to its own side of the book
        if (aggressiveOrder.getQuantity() > 0) {
            ownBook.add(aggressiveOrder);
            System.out.println("ORDER RESTING: " + aggressiveOrder.getQuantity() + " shares of " + symbol + " added to book.");
        }
    }
}
