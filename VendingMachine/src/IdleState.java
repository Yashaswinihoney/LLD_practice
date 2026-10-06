// IdleState.java
public class IdleState implements VendingState {
    @Override
    public void insertCoin(VendingMachine vm, Coin coin) {
        vm.addBalance(coin.getValue());
        vm.ingestPhysicalCoin(coin);
        System.out.println("[" + vm.getMachineId() + "] Coin Accepted: " + coin.name() + " | Balance: " + vm.getBalance());
        vm.setState(vm.getHasMoneyState());
    }

    @Override
    public void selectProduct(VendingMachine vm, String code) {
        System.out.println("[" + vm.getMachineId() + "] REJECTED: Please insert coins first.");
    }

    @Override
    public void dispense(VendingMachine vm, String code) {
        System.out.println("[" + vm.getMachineId() + "] REJECTED: Payment required.");
    }

    @Override
    public void cancelAndRefund(VendingMachine vm) {
        System.out.println("[" + vm.getMachineId() + "] REJECTED: No active transaction to cancel.");
    }
}