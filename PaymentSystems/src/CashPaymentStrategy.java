class CashPaymentStrategy implements PaymentStrategy {
    // Shared state: must be handled carefully in a multithreaded environment
    private double vaultBalance;

    public CashPaymentStrategy(double initialVaultBalance) {
        this.vaultBalance = initialVaultBalance;
    }

    @Override
    public synchronized boolean pay(Transaction transaction) {
        System.out.println("Processing Cash Payment of $" + transaction.getAmount());
        // When a user pays in cash, the physical vault balance increases
        vaultBalance += transaction.getAmount();
        return true;
    }

    @Override
    public synchronized boolean refund(Transaction transaction) {
        // NEW REQUIREMENT 2: Check if the vault has enough cash
        if (vaultBalance < transaction.getAmount()) {
            System.err.println("CASH REFUND FAILED: Insufficient funds in physical vault. " +
                    "Available: $" + vaultBalance + ", Requested: $" + transaction.getAmount());
            return false;
        }

        // Deduct from vault safely
        vaultBalance -= transaction.getAmount();
        System.out.println("Dispensing physical cash refund of $" + transaction.getAmount() +
                ". Remaining Vault Balance: $" + vaultBalance);
        return true;
    }

    @Override
    public String getMethodName() { return "CASH"; }
}