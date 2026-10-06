import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// ==========================================
// 4. BILL PUGH SINGLETON SYSTEM MANAGER
// ==========================================
class VendingMachineManager {
    private final Map<String, VendingMachine> activeMachines = new ConcurrentHashMap<>();

    private VendingMachineManager() {}
    private static class InstanceHolder {
        private static final VendingMachineManager INSTANCE = new VendingMachineManager();
    }
    public static VendingMachineManager getInstance() { return InstanceHolder.INSTANCE; }

    public VendingMachine getOrCreateMachine(String machineId) {
        return activeMachines.computeIfAbsent(machineId, id -> new VendingMachine(id));
    }
}