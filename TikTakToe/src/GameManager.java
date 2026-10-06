import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//singleton game manager, implemented through BILL POUGH SINGLETON TECHNIQUE
public class GameManager {
    //concurrent hashmap allows bucket level atomic operations for multithreading programs without blocking the whole map
    private final Map<String,TicTakToeGame> activeGames=new ConcurrentHashMap<>();
    private GameManager(){}
    private static class InstanceHolder{
        private static final GameManager INSTANCE=new GameManager();
    }
    public static GameManager getInstance(){
        return InstanceHolder.INSTANCE;
    }

    public TicTakToeGame createGame(String gameId, int size, List<Player> players, WinStrategy strategy){
        TicTakToeGame game=new TicTakToeGame(gameId, size, players,strategy);
        activeGames.putIfAbsent(gameId,game);
        return activeGames.get(gameId);
    }
}
