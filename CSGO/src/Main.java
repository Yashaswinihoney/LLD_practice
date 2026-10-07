// ==========================================
// 6. CONCURRENT EXECUTION
// ==========================================
public class Main {
    public static void main(String[] args) {
        System.out.println("=== DE SHAW CS:GO SIMULATION ===");

        MatchEngine engine = MatchEngine.getInstance();
        LiveKillFeed uiBoard = new LiveKillFeed();

        Player p1 = new Player("Terrorist_1", 10, 10);
        Player p2 = new Player("Counter_Terrorist_1", 11, 11);

        // Wire the Observer Pub-Sub
        p1.addObserver(uiBoard);
        p2.addObserver(uiBoard);

        engine.spawnPlayer(p1);
        engine.spawnPlayer(p2);

        // p1 switches strategy dynamically
        p1.equipWeapon(new Knife());

        // Simulate concurrent crossfire
        Thread t1 = new Thread(() -> p1.fireAt(p2));
        Thread t2 = new Thread(() -> p2.fireAt(p1)); // P2 fires back with AK47

        t1.start();
        t2.start();
    }
}