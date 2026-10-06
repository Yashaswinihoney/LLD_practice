import java.util.Arrays;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ExpenseManager manager=SplitwiseManager.getInstance().getExpenseManager();

        Thread t1=new Thread(()->{
            List<Split> splits= Arrays.asList(new Split("A"),new Split("B"),new Split("C"));
            manager.addExpense("A",10.00,splits,new EqualSplitStrategy());
        });

        Thread t2=new Thread(()->{
            List<Split> splits= Arrays.asList(new Split("C",50.00,0.0));
            manager.addExpense("B",50.00,splits,new ExactSplitStrategy());
        });

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();;
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}