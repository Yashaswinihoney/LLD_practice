public class VendingMachine {
    private State currentState;
    private int balance;
    private final Inventory inventory;
    private static volatile VendingMachine instance;

    private VendingMachine(){
        currentState=new IdleState();
        inventory=new Inventory();
        inventory.addProduct("A1",new Product("Coke",25),5);
        inventory.addProduct("A2",new Product("Pepsi",15),5);
    }

    public static VendingMachine getInstance(){
        if(instance==null){
            synchronized (VendingMachine.class){
                if(instance==null){
                    instance=new VendingMachine();
                }
            }
        }
        return instance;
    }

    public void setState(State state) { this.currentState = state; }
    public int getBalance() { return balance; }
    public void addBalance(int amount) { this.balance += amount; }
    public void resetBalance() { this.balance = 0; }
    public Inventory getInventory() { return inventory; }

    public void insertCoin(Coin coin) { currentState.insertCoin(this, coin); }
    public void selectProduct(String code) { currentState.selectProduct(this, code); }
    public void cancel() { currentState.cancelRequest(this); }

    // Package-private method to allow state classes to trigger dispensing
    void triggerDispense(String code) { currentState.dispense(this, code); }
}
