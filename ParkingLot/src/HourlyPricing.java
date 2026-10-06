import java.time.Duration;
import java.time.LocalDateTime;

public class HourlyPricing implements PricingStrategy{
    private final double hourlyRates;

    public HourlyPricing(double rates){
        this.hourlyRates=rates;
    }

    @Override
    public double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        long hours= Duration.between(exitTime,entryTime).toHours();
        return Math.max(1,hours)*hourlyRates;
    }
}
