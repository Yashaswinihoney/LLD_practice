import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookMyShow {
    private final Map<Integer,Show> shows=new ConcurrentHashMap<>();
    public void addShow(int id, Movie movie){
        shows.put(id,new Show(id, movie));
    }

    public Show getShow(int id){
        return shows.get(id);
    }
}
