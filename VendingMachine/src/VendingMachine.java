// VendingMachine.java
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class VendingMachine {
    private final String machineId;
    private final ReentrantLock lock = new ReentrantLock();

    // Stateless State Singletons (Saves memory across 1,000s of machines)
    private static final VendingState IDLE_STATE = new IdleState();
    private static final VendingState HAS_MONEY_STATE = new HasMoneyState();
    private static final VendingState DISPENSING_STATE = new DispensingState();

    private VendingState currentState;

    // Internal Data
    private int currentBalance = 0;
    private final Map<Coin, Integer> coinVault = new EnumMap<>(Coin.class);
    private final Map<String, Product> catalog = new HashMap<>();
    private final Map<String, Integer> inventory = new HashMap<>();

    public VendingMachine(String machineId) {
        this.machineId = machineId;
        this.currentState = IDLE_STATE;
        for (Coin c : Coin.values()) coinVault.put(c, 0);
    }

    // ==========================================
    // PUBLIC API (Thread-Safe Delegation boundaries)
    // ==========================================
    public void insertCoin(Coin coin) {
        lock.lock();
        try { currentState.insertCoin(this, coin); } finally { lock.unlock(); }
    }

    public void selectProduct(String code) {
        lock.lock();
        try { currentState.selectProduct(this, code); } finally { lock.unlock(); }
    }

    public void cancel() {
        lock.lock();
        try { currentState.cancelAndRefund(this); } finally { lock.unlock(); }
    }

    // ==========================================
    // PACKAGE-PRIVATE CONTEXT METHODS
    // (Exposed strictly for State classes in the same package)
    // ==========================================
    String getMachineId() { return machineId; }
    int getBalance() { return currentBalance; }
    void addBalance(int amount) { currentBalance += amount; }
    void resetBalance() { currentBalance = 0; }

    void ingestPhysicalCoin(Coin coin) { coinVault.put(coin, coinVault.get(coin) + 1); }
    Product getProduct(String code) { return catalog.get(code); }
    int getStock(String code) { return inventory.getOrDefault(code, 0); }
    void reduceStock(String code) { inventory.put(code, inventory.get(code) - 1); }

    VendingState getCurrentState() { return currentState; }
    void setState(VendingState state) { this.currentState = state; }
    VendingState getIdleState() { return IDLE_STATE; }
    VendingState getHasMoneyState() { return HAS_MONEY_STATE; }
    VendingState getDispensingState() { return DISPENSING_STATE; }

    boolean dispenseChange(int changeRequired) {
        Map<Coin, Integer> changeToReturn = new EnumMap<>(Coin.class);
        int remaining = changeRequired;

        for (Coin coin : Coin.values()) {
            if (remaining <= 0) break;
            int coinsToTake = Math.min(remaining / coin.getValue(), coinVault.get(coin));
            if (coinsToTake > 0) {
                changeToReturn.put(coin, coinsToTake);
                remaining -= (coinsToTake * coin.getValue());
            }
        }

        if (remaining > 0) return false; // Exact change impossible

        for (Map.Entry<Coin, Integer> entry : changeToReturn.entrySet()) {
            coinVault.put(entry.getKey(), coinVault.get(entry.getKey()) - entry.getValue());
            System.out.println("[" + machineId + "] Dispensed Change: " + entry.getValue() + "x " + entry.getKey().name());
        }
        return true;
    }

    // --- ADMINISTRATION API (Restocking) ---

    /**
     * Safely updates the product catalog and increments available inventory.
     * Protected by the machine's ReentrantLock to prevent overwriting ongoing sales.
     */
    public void restockProduct(String code, Product product, int quantity) {
        lock.lock();
        try {
            // Update or add the product details in the catalog
            catalog.put(code, product);

            // Atomically add the new quantity to any existing stock
            inventory.put(code, inventory.getOrDefault(code, 0) + quantity);

            System.out.println("[" + machineId + "] ADMIN: Restocked " + quantity + "x " + product.getName() + " (Code: " + code + ")");
        } finally {
            // Crucial: Always release the lock in a finally block to prevent deadlocks.
            lock.unlock();
        }
    }

    /**
     * Safely loads physical coin denominations into the machine's vault to ensure
     * the Greedy Change algorithm has enough physical float to return exact change.
     */
    public void loadPhysicalCoins(Coin coin, int quantity) {
        lock.lock();
        try {
            // Atomically add the new coins to the specific enum denomination bucket
            coinVault.put(coin, coinVault.get(coin) + quantity);

            System.out.println("[" + machineId + "] ADMIN: Loaded " + quantity + "x " + coin.name() + " coins.");
        } finally {
            lock.unlock();
        }
    }
}