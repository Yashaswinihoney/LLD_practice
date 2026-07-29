public class UPIPaymentStrategy implements PaymentStrategy{
    private final String upiId;

    public UPIPaymentStrategy(String upiId){
        this.upiId=upiId;
    }
    @Override
    public boolean pay(Transaction transaction) {
        System.out.println("Routing upi payment of ammount "+ transaction.getAmount()+" to upi id "+ upiId);
        return false;
    }

    @Override
    public boolean refund(Transaction transaction) {
        System.out.println("Inittiating refund of amount "+transaction.getAmount()+" to upiId "+ upiId);
        return false;
    }

    @Override
    public String getMethodName() {
        return "UPI";
    }
}
