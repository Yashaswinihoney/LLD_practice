public interface FleetObserver {
    void onVehicleStatusChanged(String vehicleId, VehicleStatus oldStatuc, VehicleStatus newStatus);
    void onLowAvailabilityAlert(VehicleType type, int count);
}
