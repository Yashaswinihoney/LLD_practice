import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//thread safe singleton manager
//implemented using BILL-PUGH singleton technique
public class BookMyShowManager {
    private final Map<Integer,Show> activeShows=new ConcurrentHashMap<>();
    private static class InstanceHolder{
        private static final BookMyShowManager INSTANCE=new BookMyShowManager();
    }

    public static BookMyShowManager getInstance(){
        return InstanceHolder.INSTANCE;
    }

    public void addShow(int id, Show show){
        activeShows.putIfAbsent(id,show);
    }
    public Show getShow(int id){
        return activeShows.get(id);
    }
}
