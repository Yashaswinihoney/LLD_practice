import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LFUEvictionStrategy<K> implements EvictionStrategy<K> {
    private final Map<K,Integer> frequencyMap=new HashMap<>();

    @Override
    public void keyAccessed(K key) {
        frequencyMap.put(key,frequencyMap.getOrDefault(key,0)+1);
    }

    @Override
    public void keyRemoved(K key) {
        frequencyMap.remove(key);
    }

    @Override
    public K evictKey() {
        if (frequencyMap.isEmpty()) return null;
        K lfuKey= Collections.min(frequencyMap.entrySet(),Map.Entry.comparingByValue()).getKey();
        frequencyMap.remove(lfuKey);
        return lfuKey;
    }
}
