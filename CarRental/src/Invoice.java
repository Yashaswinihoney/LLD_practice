public class Invoice {
    private final String reservationId;
    private final double baseCost;
    private final double lateFees;
    private final double mileageFees;
    private final double damageFees;
    private final double totalCost;

    public Invoice(String reservationId, double baseCost, double lateFees, double mileageFees, double damageFees) {
        this.reservationId = reservationId;
        this.baseCost = baseCost;
        this.lateFees = lateFees;
        this.mileageFees = mileageFees;
        this.damageFees = damageFees;
        this.totalCost = lateFees+mileageFees+damageFees+baseCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "reservationId='" + reservationId + '\'' +
                ", baseCost=" + baseCost +
                ", lateFees=" + lateFees +
                ", mileageFees=" + mileageFees +
                ", damageFees=" + damageFees +
                ", totalCost=" + totalCost +
                '}';
    }
}
