import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiterManager {
    private final Map<String, RateLimiter> clientRateLimiters; //mappings of client ids and rate limiter algos used by them
    private final long defaultMaxBucketSize;
    private final long defaultRefillRate;

    // 1. Private constructor strictly prevents external instantiation
    private RateLimiterManager() {
        this.defaultRefillRate = 5;
        this.defaultMaxBucketSize = 2;
        this.clientRateLimiters = new ConcurrentHashMap<>();
    }

    // 2. Private static inner class acts as the instance holder
    private static class InstanceHolder {
        private static final RateLimiterManager INSTANCE = new RateLimiterManager();
    }

    // 3. Global access point[cite: 1]
    public static RateLimiterManager getInstance() {
        return InstanceHolder.INSTANCE; // Triggers the inner class to load[cite: 1]
    }

    public boolean isAllowed(String clientId) {
        // computeIfAbsent is used over putIfAbsent to avoid instantiating an unused RateLimiter object on every call
        clientRateLimiters.computeIfAbsent(clientId,
                id -> new TokenBucketRateLimiter(defaultMaxBucketSize, defaultRefillRate));
        return clientRateLimiters.get(clientId).grantAccess();
    }
}
