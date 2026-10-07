import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// ==========================================
// THREAD-SAFE IN-MEMORY CACHE
// ==========================================
class InMemoryCache<K, V> {
    private final String cacheId;
    private final int capacity;
    private final long ttlMillis;

    private final Map<K, CacheEntry<V>> store = new HashMap<>();
    private final EvictionStrategy<K> evictionStrategy;
    private final CacheStats stats = new CacheStats();

    // ReadWriteLock maximizes throughput for read-heavy operations[cite: 15]
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public InMemoryCache(String cacheId, int capacity, long ttlMillis, EvictionStrategy<K> strategy) {
        this.cacheId = cacheId;
        this.capacity = capacity;
        this.ttlMillis = ttlMillis;
        this.evictionStrategy = strategy;
    }

    public void put(K key, V value) {
        rwLock.writeLock().lock();
        try {
            if (store.size() >= capacity && !store.containsKey(key)) {
                K evictedKey = evictionStrategy.evictKey();
                if (evictedKey != null) {
                    store.remove(evictedKey);
                    stats.recordEviction();
                    System.out.println("[" + cacheId + "] Evicted Key: " + evictedKey);
                }
            }
            store.put(key, new CacheEntry<>(value, ttlMillis));
            evictionStrategy.keyAccessed(key);
        } finally {
            rwLock.writeLock().unlock(); // Always release in finally block[cite: 14]
        }
    }

    public V get(K key) {
        rwLock.writeLock().lock();
        try {
            CacheEntry<V> entry = store.get(key);

            if (entry == null) {
                stats.recordMiss();
                return null;
            }

            // DYNAMIC TTL INVALIDATION (Lazy Checking)
            if (entry.isExpired()) {
                store.remove(key);
                evictionStrategy.keyRemoved(key);
                stats.recordMiss();
                System.out.println("[" + cacheId + "] Key Expired: " + key);
                return null;
            }

            stats.recordHit();
            evictionStrategy.keyAccessed(key);
            return entry.getValue();
        } finally {
            // Note: We use a WriteLock here because tracking LRU/LFU mutates the internal strategy state.
            rwLock.writeLock().unlock();
        }
    }

    public void printTelemetry() {
        rwLock.readLock().lock();
        try {
            stats.printStats(cacheId);
        } finally {
            rwLock.readLock().unlock();
        }
    }
}