import java.util.concurrent.locks.ReentrantLock;

public class TokenBucketRateLimiter implements RateLimiter{
    private final long maxBucketSize;
    private final long refillRate;

    private long currentBucketSize;
    private long lastRefillTimestamp;
    private  final ReentrantLock lock;
    public TokenBucketRateLimiter(long maxBucketSize, long refillRate){
        this.maxBucketSize=maxBucketSize;
        this.refillRate=refillRate;
        this.currentBucketSize=maxBucketSize;
        this.lastRefillTimestamp=System.currentTimeMillis();
        this.lock=new ReentrantLock();
    }

    @Override
    public boolean grantAccess(){
        lock.lock();

        try{
            refill();

            if(currentBucketSize>=1){
                currentBucketSize-=1;
                return true;
            }
            return false;
        }
        finally {
            lock.unlock();
        }
    }

    private void refill(){
        long now=System.currentTimeMillis();

        long timeElapsed=(now-lastRefillTimestamp);
        long tokenToAdd=timeElapsed*refillRate;

        currentBucketSize=Math.min(currentBucketSize+tokenToAdd,maxBucketSize);
        lastRefillTimestamp=now;
    }
}
