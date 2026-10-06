// VendingState.java
public interface VendingState {
    // The VendingMachine context is passed into every method so the states remain stateless
    void insertCoin(VendingMachine vm, Coin coin);
    void selectProduct(VendingMachine vm, String code);
    void dispense(VendingMachine vm, String code);
    void cancelAndRefund(VendingMachine vm);
}