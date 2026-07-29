// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        CircuitBreaker cb = new CircuitBreaker("PaymentService", 3, 2000);

        Runnable failingCall = () -> {
            throw new RuntimeException("Downstream timeout");
        };

        Runnable successfulCall = () -> {
            System.out.println("Downstream call succeeded!");
        };

        System.out.println("--- 1. Simulating Failures ---");
        for (int i = 0; i < 4; i++) {
            try {
                System.out.print("Call " + (i + 1) + ": ");
                cb.execute(failingCall);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("\n--- 2. Waiting for Timeout ---");
        Thread.sleep(2500); // Wait longer than the 2s timeout

        System.out.println("\n--- 3. Simulating Probe (Success) ---");
        try {
            // This call will be allowed through in HALF_OPEN
            cb.execute(successfulCall);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- 4. Subsequent Calls ---");
        try {
            // Circuit should now be CLOSED
            cb.execute(successfulCall);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}