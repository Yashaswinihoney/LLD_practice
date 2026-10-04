import java.util.concurrent.locks.ReentrantLock;

public class LeakyBucketRateLimiter implements RateLimiter {
    private final long capacity;
    private final double leakRatePerMillis;

    private double currentWaterLevel;
    private long lastLeakTimestamp;

    // Dedicated lock to prevent race conditions during concurrent access[cite: 1, 3]
    private final ReentrantLock lock = new ReentrantLock();

    public LeakyBucketRateLimiter(long capacity, long allowedRequestsPerSecond) {
        this.capacity = capacity;
        // Convert per-second rate to per-millisecond for precise time calculations
        this.leakRatePerMillis = (double) allowedRequestsPerSecond / 1000.0;
        this.currentWaterLevel = 0;
        this.lastLeakTimestamp = System.currentTimeMillis();
    }

    @Override
    public boolean grantAccess() {
        lock.lock(); // Explicitly acquire the monitor[cite: 3]
        try {
            long now = System.currentTimeMillis();

            // 1. Calculate how much water has leaked out since the last request
            long timeElapsed = now - lastLeakTimestamp;
            double leakedAmount = timeElapsed * leakRatePerMillis;

            // Only update if time has actually elapsed to prevent precision loss
            if (leakedAmount > 0) {
                currentWaterLevel = Math.max(0, currentWaterLevel - leakedAmount);
                lastLeakTimestamp = now;
            }

            // 2. Evaluate if the bucket can hold one more drop
            if (currentWaterLevel + 1 <= capacity) {
                currentWaterLevel += 1;
                return true; // Access granted
            }

            return false; // Bucket is full, request drops (overflow)

        } finally {
            // Crucial: Always release the lock in a finally block to prevent deadlocks[cite: 3]
            lock.unlock();
        }
    }
}