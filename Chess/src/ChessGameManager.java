import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//singleton manager class, implemented using BILL PUGH SINGLETON METHOD
public class ChessGameManager {
    private final Map<String, ChessGame> activeGames=new ConcurrentHashMap<>();
    private ChessGameManager(){}

    private static class InstanceHolder{
        private static ChessGameManager INSTANCE=new ChessGameManager();
    }
    public static ChessGameManager getInstance(){
        return InstanceHolder.INSTANCE;
    }

    public ChessGame getOrCreateGame(String gameId){
        return activeGames.computeIfAbsent(gameId,id->new ChessGame(id));
    }
}
