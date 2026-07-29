public class IdleState implements State{

    @Override
    public void insertCoin(VendingMachine vm, Coin coin) {
        vm.addBalance(coin.getValue());
        System.out.println("Coin accepted "+coin.name()+". Total balance "+vm.getBalance());
    }

    @Override
    public void selectProduct(VendingMachine v, String code) {
        throw new IllegalStateException("Insert Money first");
    }

    @Override
    public void dispense(VendingMachine vm, String code) {
        throw new IllegalStateException("Payment required");
    }

    @Override
    public void cancelRequest(VendingMachine vm) {
        System.out.println("Nothing to refund");
    }
}
