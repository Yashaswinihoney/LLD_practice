import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// ==========================================
// BILL PUGH SINGLETON MANAGER[cite: 17]
// ==========================================
class CacheManager {
    // ConcurrentHashMap provides atomic bucket-level locking for dynamic cache registration
    private final Map<String, InMemoryCache<?, ?>> caches = new ConcurrentHashMap<>();

    private CacheManager() {}

    private static class InstanceHolder {
        private static final CacheManager INSTANCE = new CacheManager();
    }

    public static CacheManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <K, V> InMemoryCache<K, V> createOrGetCache(
            String cacheId, int capacity, long ttlMillis, EvictionStrategy<K> strategy) {

        return (InMemoryCache<K, V>) caches.computeIfAbsent(
                cacheId, id -> new InMemoryCache<>(id, capacity, ttlMillis, strategy)
        );
    }
}