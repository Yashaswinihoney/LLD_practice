import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowRateLimiter implements RateLimiter {
    private final int maxRequests;
    private final long windowSizeInMillis;

    // volatile guarantees strict memory visibility across threads when the window rolls over.
    private volatile long windowStartTime;
    private final AtomicInteger counter;

    public FixedWindowRateLimiter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.windowStartTime = System.currentTimeMillis();
        this.counter = new AtomicInteger(0);
    }

    @Override
    public boolean grantAccess() {
        long now = System.currentTimeMillis();

        // 1. Check if the current time has crossed into a new window
        if (now - windowStartTime >= windowSizeInMillis) {
            // 2. Object-level locking minimizes the critical section strictly to the reset operation[cite: 2].
            // Double-checked locking ensures only one thread resets the counter.
            synchronized (this) {
                if (now - windowStartTime >= windowSizeInMillis) {
                    windowStartTime = now;
                    counter.set(0);
                }
            }
        }

        // 3. Hardware-level atomic read-modify-write prevents race conditions without blocking threads[cite: 2].
        return counter.incrementAndGet() <= maxRequests;
    }
}