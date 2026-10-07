import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBlockingQueue <T>{
    private final Queue<T> queue=new LinkedList<>();
    private final int capacity;
    //fair locking to prevent thread starvation
    private final ReentrantLock lock=new ReentrantLock(true); //fair locking

    //condition variables for precise thread signalling
    private final Condition notFull=lock.newCondition();
    private final Condition notEmpty= lock.newCondition();

    public BoundedBlockingQueue(int cap){
        if(cap<=0) throw new IllegalArgumentException("Capacity mut be positive");
        this.capacity=cap;
    }
    public void enqueue(T item) throws InterruptedException{
        lock.lockInterruptibly(); // Allows graceful thread shutdown
        try{
            while (queue.size()==capacity){
                notFull.await(); // Releases lock and sleeps the Producer
            }

            queue.add(item);
            System.out.println(Thread.currentThread().getName()+"Produced "+item);
            // Signal waiting Consumers that the queue is no longer empty
            notEmpty.signalAll();
        }
        finally {
            lock.unlock();// Always release in finally to prevent deadlocks
        }
    }

    public T deque() throws InterruptedException{
        lock.lockInterruptibly();
        try {
            while (queue.isEmpty()){
                notEmpty.await(); //release locks and sleeps the consumer
            }
            T item=queue.poll();
            System.out.println(Thread.currentThread().getName()+" Consumer "+item);

            //signal waiting producers that space has freed up
            notFull.signalAll();
            return item;
        }
        finally {
            lock.unlock();
        }
    }
}
