public class CardPaymentHandler implements PaymentHandler{
    @Override
    public void process(double amount){
        System.out.println("Processing amount "+amount+" by card");
    }
}
