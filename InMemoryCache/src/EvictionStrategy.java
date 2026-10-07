public interface EvictionStrategy<K> {
    void keyAccessed(K key);
    void keyRemoved(K key);
    K evictKey();
}
