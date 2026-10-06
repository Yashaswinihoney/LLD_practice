public class SplitwiseManager {
    private final ExpenseManager expenseManager=new ExpenseManager();
    private SplitwiseManager(){}
    private static class InstanceHolder{
        private static final SplitwiseManager INSTANCE=new SplitwiseManager();
    }
    public static SplitwiseManager getInstance(){
        return InstanceHolder.INSTANCE;
    }
    public ExpenseManager getExpenseManager(){
        return expenseManager;
    }
}
