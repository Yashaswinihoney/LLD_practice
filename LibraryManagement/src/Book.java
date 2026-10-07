import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Book {
    private final String isbn;
    private final String title;
    private final String author;

    //threadsafe FIFO quque for waitlisted members for each book
    private final Queue<String> waitlist=new ConcurrentLinkedDeque<>();

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void addToWaitlist(String memberId){
        waitlist.add(memberId);
        System.out.println("Member "+memberId+" added to waitlist for book"+ this.getTitle());
    }

    public String popNextWaitlistMember(){
        return waitlist.poll(); //return null if queue is empty
    }
}
