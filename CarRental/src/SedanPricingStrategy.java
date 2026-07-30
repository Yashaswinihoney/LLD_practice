public class SedanPricingStrategy implements RentalPricingStrategy{

    @Override
    public double calculateBase(int days) {
        return days*40.0;
    }

    @Override
    public double calculateLateFees(int lateHours) {
        return lateHours*15.0;
    }

    @Override
    public double calculateMileageFees(double miles) {
        return miles*0.15;
    }
}
