// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //pick strategy at runtime
        PricingFeeCalculator weekday=new PricingFeeCalculator(new HourlyPricing());
        PricingFeeCalculator weekend=new PricingFeeCalculator(new DailyPricing());

        System.out.println("Weekday 5h: " + weekday.calculate(10, 5));   // 50.0
        System.out.println("Weekend 5h: " + weekend.calculate(10, 5));   // 75.0
        System.out.println("Daily 30h: " + new PricingFeeCalculator(new DailyPricing()).calculate(10, 30)); // 240.0
    }
}