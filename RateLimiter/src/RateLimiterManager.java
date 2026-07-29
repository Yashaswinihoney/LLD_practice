import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiterManager {
    private final Map<String, RateLimiter> clientRateLimiters;
    private final long defaultMaxBucketSize;
    private final long defaultRefillRate;

    public RateLimiterManager(long defaultRefillRate, long defaultMaxBucketSize){
        this.defaultRefillRate=defaultRefillRate;
        this.defaultMaxBucketSize=defaultMaxBucketSize;
        this.clientRateLimiters=new ConcurrentHashMap<>();
    }

    public boolean isAllowed(String clientId){
        clientRateLimiters.putIfAbsent(clientId,new TokenBucketRateLimiter(defaultRefillRate,defaultMaxBucketSize));
        return clientRateLimiters.get(clientId).grantAccess();
    }
}
