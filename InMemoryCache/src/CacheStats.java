import java.util.concurrent.atomic.AtomicInteger;

public class CacheStats {
    private final AtomicInteger hits=new AtomicInteger(0);
    private final AtomicInteger misses=new AtomicInteger(0);
    private final AtomicInteger evictions=new AtomicInteger(0);
    public void recordHit(){
        hits.incrementAndGet();
    }
    public void recordMiss(){
        misses.incrementAndGet();
    }
    public void recordEviction(){
        evictions.incrementAndGet();
    }
    public AtomicInteger getEvictons() {
        return evictions;
    }

    public AtomicInteger getHits() {
        return hits;
    }

    public AtomicInteger getMisses() {
        return misses;
    }
    public void printStats(String cacheName) {
        System.out.printf("[%s] Hits: %d | Misses: %d | Evictions: %d\n",
                cacheName, hits.get(), misses.get(), evictions.get());
    }
}
