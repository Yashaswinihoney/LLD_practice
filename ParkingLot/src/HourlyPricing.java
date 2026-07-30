public class HourlyPricing implements PricingStrategy{
    private final double hourlyRates;

    public HourlyPricing(double rates){
        this.hourlyRates=rates;
    }
    @Override
    public double calculateFee(long durationMs) {
        double hours=Math.ceil(durationMs/3600000.0);
        return Math.max(hours,1.0)*hourlyRates;
    }
}
