public class PaymentFactory {
    public static PaymentStrategy getStrategy(PaymentType type){
        switch (type){
            case CASH -> {
                return new CashPaymentStrategy();
            }
            case CARD -> {
                return new CardPaymentStrategy();
            }
            case UPI ->{
                return new UPIPaymentStrategy();
            }
            default -> throw new IllegalArgumentException("Unknown Payment type "+type);
        }
    }
}
