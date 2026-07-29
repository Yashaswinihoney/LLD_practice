public class HasMoneyState implements State{
    @Override
    public void insertCoin(VendingMachine vm, Coin coin) {
        vm.addBalance(coin.getValue());
        System.out.println("Coin added. New Total: "+ vm.getBalance());
    }

    @Override
    public void selectProduct(VendingMachine vm, String code) {
        Product p=vm.getInventory().getProduct(code);

        if (p == null || !vm.getInventory().isAvailable(code)) {
            throw new IllegalArgumentException("Product unavailable.");
        }
        if (vm.getBalance() < p.price) {
            throw new InsufficientFundsException("Insufficient funds. Price: " + p.price);
        }

        vm.setState(new DispensingState());
        vm.triggerDispense(code);
    }

    @Override
    public void cancelRequest(VendingMachine vm) {
        System.out.println("Refunding " + vm.getBalance());
        vm.resetBalance();
        vm.setState(new IdleState());
    }

    @Override
    public void dispense(VendingMachine vm, String code) {
        throw new IllegalStateException("Select a product first.");
    }

}
