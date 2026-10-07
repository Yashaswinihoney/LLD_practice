import java.util.LinkedHashSet;
import java.util.Set;

public class LRUEvictionStrategy<K> implements EvictionStrategy<K> {
    //LinkedHashSet natively maintains insertion/access order in O(1) time
    private final Set<K> lruTracker=new LinkedHashSet<>();
    @Override
    public void keyAccessed(K key) {
        //remove and readd the key to the tail(most recently used)
        lruTracker.remove(key);
        lruTracker.add(key);
    }

    @Override
    public void keyRemoved(K key) {
        lruTracker.remove(key);
    }

    @Override
    public K evictKey() {
        if (lruTracker.isEmpty()) return null;

        //the first element is the least recently used element
        K eldest=lruTracker.iterator().next();
        lruTracker.remove(eldest);
        return eldest;
    }
}
