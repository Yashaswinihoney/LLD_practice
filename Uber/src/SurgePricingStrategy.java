public class SurgePricingStrategy implements PricingStrategy{
    private final double surgeMultiplier;
    public SurgePricingStrategy(double surgeMultiplier){
        this.surgeMultiplier=surgeMultiplier;
    }
    @Override
    public double calculateFare(double distance) {
        return Math.max(5.0,distance*2.0)*surgeMultiplier;
    }
}
