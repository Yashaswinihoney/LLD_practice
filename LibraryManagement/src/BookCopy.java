import java.util.concurrent.atomic.AtomicBoolean;

//PHYSICAL COPY OF BOOK
public class BookCopy {
    private final String barcode;
    private final Book book;
    private final AtomicBoolean isAvailable=new AtomicBoolean(true);

    public BookCopy(String barcode, Book book) {
        this.barcode = barcode;
        this.book = book;
    }

    public String getBarcode() {
        return barcode;
    }

    public Book getBook() {
        return book;
    }
    public boolean tryCheckout(){
        return isAvailable.compareAndSet(true,false);
    }
    public void returnCopy(){
        isAvailable.set(true);
    }
}
