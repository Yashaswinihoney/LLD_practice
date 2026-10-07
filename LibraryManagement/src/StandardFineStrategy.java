public class StandardFineStrategy implements FineStrategy{
    private static final double DAILY_RATE=2.50;
    @Override
    public double calculateFine(int daysLate) {
        return daysLate>0?daysLate*DAILY_RATE:0.0;
    }
}
