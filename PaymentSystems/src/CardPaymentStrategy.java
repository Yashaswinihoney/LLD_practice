public class CardPaymentStrategy implements PaymentStrategy{
    private final String cardNumber;
    private final String cvv;

    public CardPaymentStrategy(String cardNumber, String cvv){
        this.cardNumber=cardNumber;
        this.cvv=cvv;
    }
    @Override
    public boolean pay(Transaction transaction) {
        System.out.println("Charging card ending in "+cardNumber.substring(cardNumber.length()-4)+ " of amount "+transaction.getAmount());
        //external api call for payment
        return true;
    }

    @Override
    public boolean refund(Transaction transaction) {
        System.out.println("Reversing charge on card ending in "+cardNumber.substring(cardNumber.length()-4)+" of amount "+ transaction.getAmount());
        return true;
    }

    @Override
    public String getMethodName() {
        return "CARD";
    }
}
