// HasMoneyState.java
public class HasMoneyState implements VendingState {
    @Override
    public void insertCoin(VendingMachine vm, Coin coin) {
        vm.addBalance(coin.getValue());
        vm.ingestPhysicalCoin(coin);
        System.out.println("[" + vm.getMachineId() + "] Coin Accepted: " + coin.name() + " | Balance: " + vm.getBalance());
    }

    @Override
    public void selectProduct(VendingMachine vm, String code) {
        Product p = vm.getProduct(code);
        int stock = vm.getStock(code);

        if (p == null || stock <= 0) {
            System.out.println("[" + vm.getMachineId() + "] FAILED: Product unavailable or out of stock.");
            return;
        }
        if (vm.getBalance() < p.getPrice()) {
            System.out.println("[" + vm.getMachineId() + "] FAILED: Insufficient funds. Price is " + p.getPrice());
            return;
        }

        int changeRequired = vm.getBalance() - p.getPrice();

        // Verify exact physical change is possible before committing
        if (changeRequired > 0 && !vm.dispenseChange(changeRequired)) {
            System.out.println("[" + vm.getMachineId() + "] ERROR: Insufficient physical coins for change. Refunding.");
            cancelAndRefund(vm);
            return;
        }

        // Lock into dispensing phase
        vm.setState(vm.getDispensingState());

        // Immediately trigger the physical dispense step within the same thread lock boundary
        vm.getCurrentState().dispense(vm, code);
    }

    @Override
    public void dispense(VendingMachine vm, String code) {
        System.out.println("[" + vm.getMachineId() + "] REJECTED: Select a product first.");
    }

    @Override
    public void cancelAndRefund(VendingMachine vm) {
        System.out.println("[" + vm.getMachineId() + "] Canceling... Refunding: " + vm.getBalance());
        vm.dispenseChange(vm.getBalance()); // Eject coins
        vm.resetBalance();
        vm.setState(vm.getIdleState());
    }
}