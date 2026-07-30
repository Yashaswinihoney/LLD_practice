public interface RentalPricingStrategy {
    double calculateBase(int days);
    double calculateLateFees(int lateHours);
    double calculateMileageFees(double miles);
}
