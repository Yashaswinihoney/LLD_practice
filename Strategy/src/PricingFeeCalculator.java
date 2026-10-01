public class PricingFeeCalculator {
    private final PricingStrategy strategy;
    public PricingFeeCalculator(PricingStrategy strategy){
        this.strategy=strategy;
    }
    public double calculate(double basePrice, int hours){
        return strategy.calculatePrice(basePrice,hours);
    }
}
