// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        RateLimiterManager manager=new RateLimiterManager(5,2);
        String userA="userA";

        System.out.println("--Simulating Traffic Burst --");

        for(int i=1;i<=7;i++){
            boolean allowed=manager.isAllowed(userA);
            System.out.println("Request " + i + " -> " + (allowed ? "ALLOWED" : "DENIED"));
        }

        System.out.println("\n--- Waiting for 2 seconds to refill tokens ---");
        Thread.sleep(2000);

        for (int i = 8; i <= 10; i++) {
            boolean allowed = manager.isAllowed(userA);
            System.out.println("Request " + i + " -> " + (allowed ? "ALLOWED" : "DENIED"));
        }
    }
}