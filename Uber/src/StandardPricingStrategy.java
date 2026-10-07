public class StandardPricingStrategy implements PricingStrategy{
    @Override
    public double calculateFare(double distance) {
        return Math.max(5.0,distance*2.0);
    }
}
