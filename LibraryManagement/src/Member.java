import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Member {
    private final String memberId;
    private double unpaidFines=0.0;
    private static final double FINE_LIMIT=50.0;
    private final ReentrantLock accountLock=new ReentrantLock();

    //tracks active checkouts mapped by barcode
    private final Map<String,BookCopy> borrowedCopies=new HashMap<>();

    public Member(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }

    public boolean canBorrow() {
        accountLock.lock();
        try {
            return unpaidFines < FINE_LIMIT;
        } finally {
            accountLock.unlock();
        }
    }

    public void addFine(double amount) {
        accountLock.lock();
        try {
            unpaidFines += amount;
            System.out.printf("[Account %s] Fine applied: $%.2f. Total: $%.2f\n", memberId, amount, unpaidFines);
        } finally {
            accountLock.unlock();
        }
    }
    public void payFine(double amount) {
        accountLock.lock();
        try {
            unpaidFines = Math.max(0, unpaidFines - amount);
            System.out.printf("[Account %s] Paid $%.2f. Remaining Fines: $%.2f\n", memberId, amount, unpaidFines);
        } finally {
            accountLock.unlock();
        }
    }

    public void recordCheckout(BookCopy copy) {
        accountLock.lock();
        try { borrowedCopies.put(copy.getBarcode(), copy); }
        finally { accountLock.unlock(); }
    }

    public void recordReturn(BookCopy copy) {
        accountLock.lock();
        try { borrowedCopies.remove(copy.getBarcode()); }
        finally { accountLock.unlock(); }
    }
}
