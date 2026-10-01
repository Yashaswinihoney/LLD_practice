public class HourlyPricing implements PricingStrategy{

    @Override
    public double calculatePrice(double basePrice, int hours) {
        return basePrice*hours;
    }
}
