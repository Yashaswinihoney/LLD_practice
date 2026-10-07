import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LibraryManager {
    private final Catalog catalog=new Catalog();
    private final Map<String,Member> members=new ConcurrentHashMap<>();
    private final Map<String,BookCopy> inventory=new ConcurrentHashMap<>();
    private FineStrategy fineStrategy=new StandardFineStrategy();
    private LibraryManager() {}

    private static class InstanceHolder {
        private static final LibraryManager INSTANCE = new LibraryManager();
    }

    public static LibraryManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public Catalog getCatalog() { return catalog; }

    public void registerMember(Member member) { members.put(member.getMemberId(), member); }
    public void addInventory(BookCopy copy) { inventory.put(copy.getBarcode(), copy); }

    /**
     * Executes the checkout pipeline with atomic guards.
     */
    public boolean checkoutBook(String memberId, String barcode) {
        Member member = members.get(memberId);
        BookCopy copy = inventory.get(barcode);

        if (member == null || copy == null) return false;

        // 1. Audit Block: Reject if fines exceed limit
        if (!member.canBorrow()) {
            System.err.println("Checkout Blocked: " + memberId + " exceeds fine limits.");
            return false;
        }

        // 2. Atomic CAS Checkout: Instantly grab the book or fail lock-free
        if (copy.tryCheckout()) {
            member.recordCheckout(copy);
            System.out.println("Checkout Success: " + memberId + " borrowed " + copy.getBook().getTitle());
            return true;
        }

        System.out.println("Checkout Failed: Book currently unavailable.");
        return false;
    }

    /**
     * Executes the return pipeline, audits fines, and notifies the FIFO waitlist.
     */
    public void returnBook(String memberId, String barcode, int daysLate) {
        Member member = members.get(memberId);
        BookCopy copy = inventory.get(barcode);

        if (member == null || copy == null) return;

        // 1. Audit Penalty Fines
        double fine = fineStrategy.calculateFine(daysLate);
        if (fine > 0) {
            member.addFine(fine);
        }

        // 2. Return physical copy
        member.recordReturn(copy);
        copy.returnCopy();
        System.out.println("Return Processed: " + copy.getBook().getTitle() + " returned by " + memberId);

        // 3. FIFO Reservation check
        String nextInLine = copy.getBook().popNextWaitlistMember();
        if (nextInLine != null) {
            System.out.println(">>> NOTIFICATION: Hold available for " + nextInLine + " on " + copy.getBook().getTitle());
        }
    }
}
