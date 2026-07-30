import java.util.concurrent.atomic.AtomicReference;

public class Vehicle {
    private final String id;
    private final VehicleType type;
    private final String licensePlate;
    private final RentalPricingStrategy pricingStrategy;
    private final AtomicReference<VehicleStatus> status= new AtomicReference<>(VehicleStatus.AVAILABLE);

    public Vehicle(String id, VehicleType type, String licensePlate, RentalPricingStrategy pricingStrategy){
        this.type=type;
        this.id=id;
        this.licensePlate=licensePlate;
        this.pricingStrategy=pricingStrategy;
    }

    public String getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public RentalPricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public VehicleStatus getStatus() {
        return status.get();
    }

    public boolean reserve(){
        return  status.compareAndSet(VehicleStatus.AVAILABLE,VehicleStatus.RESERVED);
    }

    public boolean rent(){
        return status.compareAndSet(VehicleStatus.RESERVED,VehicleStatus.RENTED);
    }

    public void release(boolean needsMaintainance){
        status.set(needsMaintainance?VehicleStatus.UNDER_MAINTAINANCE:VehicleStatus.AVAILABLE);
    }
}
