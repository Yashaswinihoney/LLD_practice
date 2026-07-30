import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

class CarRentalService {
    private final Map<String, Vehicle> fleet = new ConcurrentHashMap<>();
    private final Map<String, Reservation> reservations = new ConcurrentHashMap<>();
    private final List<FleetObserver> observers = new CopyOnWriteArrayList<>();
    private final ReentrantLock systemLock = new ReentrantLock();

    public void registerObserver(FleetObserver obs) { observers.add(obs); }
    public void addVehicle(Vehicle v) { fleet.put(v.getId(), v); }

    public Reservation reserveVehicle(String userId, VehicleType type, int plannedDays) {
        systemLock.lock();
        try {
            Vehicle target = fleet.values().stream()
                    .filter(v -> v.getType() == type && v.getStatus() == VehicleStatus.AVAILABLE)
                    .findFirst()
                    .orElse(null);

            if (target != null && target.reserve()) {
                notifyStatusChange(target.getId(), VehicleStatus.AVAILABLE, VehicleStatus.RESERVED);
                checkLowAvailability(type);
                Reservation res = new Reservation(UUID.randomUUID().toString(), userId, target, plannedDays);
                reservations.put(res.getId(), res);
                return res;
            }
            return null;
        } finally {
            systemLock.unlock();
        }
    }

    public boolean pickupVehicle(String reservationId) {
        Reservation res = reservations.get(reservationId);
        if (res != null && res.getVehicle().rent()) {
            notifyStatusChange(res.getVehicle().getId(), VehicleStatus.RESERVED, VehicleStatus.RENTED);
            return true;
        }
        return false;
    }

    public Invoice returnVehicle(String reservationId, int actualDays, int lateHours, double extraMiles, double damageFee) {
        Reservation res = reservations.get(reservationId);
        if (res == null) return null;

        Vehicle v = res.getVehicle();
        RentalPricingStrategy pricing = v.getPricingStrategy();

        double base = pricing.calculateBase(res.getPlannedDays());
        double late = pricing.calculateLateFees(lateHours);
        double mileage = pricing.calculateMileageFees(extraMiles);

        boolean needsMaint = damageFee > 1000.0;
        v.release(needsMaint);
        notifyStatusChange(v.getId(), VehicleStatus.RENTED, v.getStatus());

        Invoice inv = new Invoice(reservationId, base, late, mileage, damageFee);
        reservations.remove(reservationId);
        return inv;
    }

    private void notifyStatusChange(String id, VehicleStatus oldS, VehicleStatus newS) {
        for (FleetObserver obs : observers) obs.onVehicleStatusChanged(id, oldS, newS);
    }

    private void checkLowAvailability(VehicleType type) {
        long count = fleet.values().stream().filter(v -> v.getType() == type && v.getStatus() == VehicleStatus.AVAILABLE).count();
        if (count < 2) {
            for (FleetObserver obs : observers) obs.onLowAvailabilityAlert(type, (int) count);
        }
    }
}