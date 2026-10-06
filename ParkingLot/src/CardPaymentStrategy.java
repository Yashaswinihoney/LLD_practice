public class CardPaymentStrategy implements PaymentStrategy{
    @Override
    public boolean process(double amount) {
        System.out.println("Processing payment of $"+amount);
        return true;
    }
}
