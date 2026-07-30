public class SUVPricingStrategy implements RentalPricingStrategy{
    @Override
    public double calculateBase(int days) {
        return days*75.0;
    }

    @Override
    public double calculateLateFees(int lateHours) {
        return lateHours*25.0;
    }

    @Override
    public double calculateMileageFees(double miles) {
        return miles*0.25;
    }
}
