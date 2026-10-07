public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== THREAD-SAFE IN-MEMORY CACHE ===");

        CacheManager manager = CacheManager.getInstance();

        // Initialize an LRU Cache with Capacity of 2, and a TTL of 1 second (1000ms)
        InMemoryCache<String, String> userCache = manager.createOrGetCache(
                "USER_CACHE", 2, 1000, new LRUEvictionStrategy<>()
        );

        userCache.put("U1", "Alice");
        userCache.put("U2", "Bob");

        // Access U1 so U2 becomes the Least Recently Used
        userCache.get("U1");

        // This will trigger an eviction of U2 because capacity is 2
        userCache.put("U3", "Charlie");

        System.out.println("Fetching U2 (Should be evicted): " + userCache.get("U2")); // Miss
        System.out.println("Fetching U1: " + userCache.get("U1")); // Hit

        System.out.println("\n--- Testing Dynamic TTL Expiration ---");
        Thread.sleep(1100); // Wait for TTL to expire

        System.out.println("Fetching U3 after 1.1s: " + userCache.get("U3")); // Miss (Expired)

        System.out.println("\n--- Final Telemetry ---");
        userCache.printTelemetry();
    }
}