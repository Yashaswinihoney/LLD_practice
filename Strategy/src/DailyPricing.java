public class DailyPricing implements PricingStrategy{
    @Override
    public double calculatePrice(double basePrice, int hours) {
        int days=(int)Math.ceil(hours/24.0);
        return basePrice*days*0.8;
    }
}
