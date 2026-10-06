// DispensingState.java
public class DispensingState implements VendingState {
    @Override
    public void insertCoin(VendingMachine vm, Coin coin) {
        System.out.println("[" + vm.getMachineId() + "] WAIT: Dispensing in progress...");
    }

    @Override
    public void selectProduct(VendingMachine vm, String code) {
        System.out.println("[" + vm.getMachineId() + "] WAIT: Already dispensing an item.");
    }

    @Override
    public void dispense(VendingMachine vm, String code) {
        Product p = vm.getProduct(code);
        vm.reduceStock(code);

        System.out.println("[" + vm.getMachineId() + "] >>> DISPENSING: " + p.getName() + " <<<");

        vm.resetBalance();
        vm.setState(vm.getIdleState());
    }

    @Override
    public void cancelAndRefund(VendingMachine vm) {
        System.out.println("[" + vm.getMachineId() + "] REJECTED: Cannot cancel, item is already dropping!");
    }
}