//IMMUTABLE CACHE ENTRY
public class CacheEntry<V> {
    private final V value;
    private final long expirationTime; //epoch time in milliseconds

    public CacheEntry(V value, long ttl) {
        this.value = value;
        this.expirationTime = (ttl>0)?System.currentTimeMillis()+ttl: Long.MAX_VALUE;
    }
    public V getValue(){
        return value;
    }
    public boolean isExpired(){
        return System.currentTimeMillis()>expirationTime;
    }
}
