import java.util.concurrent.ConcurrentHashMap;

public class PaymentRouter {
    private final ConcurrentHashMap<String,PaymentHandler> handlers=new ConcurrentHashMap<>();

    public PaymentRouter() {
        handlers.put("CARD",new CardPaymentHandler());
        handlers.put("UPI",new UPIPaymentHandler());
        handlers.put("WALLET",new WalletPaymentHandler());
    }

    public PaymentHandler getHandler(String type){
        PaymentHandler handler=handlers.get(type.toUpperCase());
        if(handler==null) throw new IllegalArgumentException("Unknown payment type "+ type);
        return handler;
    }
}
