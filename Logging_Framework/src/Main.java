// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Logger orderLogger = LoggerRegistry.getLogger("OrderService");
        Logger paymentLogger = LoggerRegistry.getLogger("PaymentService");

        orderLogger.info("Order placed successfully.");

        // This will be ignored because default level is INFO (2) > DEBUG (1)
        orderLogger.debug("Fetching from DB...");

        paymentLogger.error("Payment Gateway timeout!");
    }
}