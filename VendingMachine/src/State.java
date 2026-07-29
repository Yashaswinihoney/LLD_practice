public interface State {
    void insertCoin(VendingMachine vm, Coin coin);
    void selectProduct(VendingMachine v, String code);
    void dispense(VendingMachine vm, String code);
    void cancelRequest(VendingMachine vm);
}
