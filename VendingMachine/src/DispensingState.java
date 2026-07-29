public class DispensingState implements State{
    public void insertCoin(VendingMachine vm, Coin coin) {
        throw new IllegalStateException("Wait, dispensing in progress...");
    }

    public void selectProduct(VendingMachine vm, String code) {
        throw new IllegalStateException("Already dispensing.");
    }

    public void dispense(VendingMachine vm, String code) {
        Product p = vm.getInventory().getProduct(code);
        vm.getInventory().reduceStock(code);

        int change = vm.getBalance() - p.price;

        System.out.println(">>> DISPENSING: " + p.name + " <<<");
        if (change > 0) {
            System.out.println("Change returned: " + change);
        }

        vm.resetBalance();
        vm.setState(new IdleState());
    }

    public void cancelRequest(VendingMachine vm) {
        throw new IllegalStateException("Cannot cancel, item already dispensing!");
    }
}
