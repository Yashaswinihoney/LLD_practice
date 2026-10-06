import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ExpenseManager {
    //Single aggregated net balance grid, Positive==owes money to other, Negative== is owed money

    //ConcurrentHashMap handles granular reads, but we will lock writes for strict atomic consistency
    private final Map<String, Double> netBalances=new ConcurrentHashMap<>();
    private final ReentrantLock lock=new ReentrantLock();

    public void addExpense(String paidBy, double totalAmount, List<Split> splits, SplitStrategy strategy){
        strategy.calculate(totalAmount,splits);

        lock.lock();
        try{
            //1. Credit the payer
            netBalances.put(paidBy,netBalances.getOrDefault(paidBy,0.0)+totalAmount);

            //2. Debit the participants
            for(Split split: splits){
                netBalances.put(split.getUserId(),netBalances.getOrDefault(split.getUserId(),0.0)-split.getAmount());
            }
            System.out.println("Expense added, totalAmount "+totalAmount+" paid by "+paidBy);
        }
        finally {
            lock.unlock();
        }
    }
}
