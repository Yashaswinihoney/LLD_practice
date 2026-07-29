// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
//        System.out.printf("Hello and welcome!");
//
//        // Press Ctrl+R or click the green arrow button in the gutter to run the code.
//        for (int i = 1; i <= 5; i++) {
//
//            // Press Ctrl+D to start debugging your code. We have set one breakpoint
//            // for you, but you can always add more by pressing Cmd+F8.
//            System.out.println("i = " + i);
//        }

        MessageBroker broker = MessageBroker.getInstance();

        // 1. Create Topics
        broker.createTopic("transactions");
        broker.createTopic("alerts");

        // 2. Create Subscribers
        ISubscriber fraudService = new Subscriber("Fraud-Service");
        ISubscriber analyticsService = new Subscriber("Analytics-Service");
        ISubscriber notificationService = new Subscriber("Notification-Service");

        // 3. Subscribe
        broker.subscribe("transactions", fraudService);
        broker.subscribe("transactions", analyticsService);
        broker.subscribe("alerts", notificationService);

        // 4. Publish
        broker.publish("transactions", new Message("User A paid User B $500"));
        broker.publish("alerts", new Message("High volume of transactions detected!"));

        // Wait a moment for async threads to finish printing before shutdown
        try { Thread.sleep(100); } catch (InterruptedException e) { }

        broker.shutdown();
    }
}