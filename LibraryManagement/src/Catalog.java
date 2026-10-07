import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Catalog {
    private final Map<String, Book> isbnIndex=new ConcurrentHashMap<>();
    private final Map<String, List<Book>> authorIndex=new ConcurrentHashMap<>();
    private final Map<String,List<Book>> titleIndex=new ConcurrentHashMap<>();
    public void addBook(Book book){
        isbnIndex.put(book.getIsbn(), book);
        authorIndex.computeIfAbsent(book.getAuthor(),k->new CopyOnWriteArrayList<>());
        titleIndex.computeIfAbsent(book.getTitle(), k->new CopyOnWriteArrayList<>());
    }

    public Book searchByIsbn(String isbn){
        return isbnIndex.get(isbn);
    }
    public List<Book> searchByAuthor(String author) { return authorIndex.getOrDefault(author, Collections.emptyList()); }
    public List<Book> searchByTitle(String title) { return titleIndex.getOrDefault(title, Collections.emptyList()); }
}
