import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

// ==========================================
// CREATIONAL PATTERN: System Scale Lobby, Singleton
// ==========================================
class GameLobbyManager {
    // High-performance bucket-level locking[cite: 5]
    private final ConcurrentHashMap<String, GameManager> activeGames = new ConcurrentHashMap<>();

    private GameLobbyManager() {}

    // Lock-free Bill Pugh Singleton implementation[cite: 5, 11]
    private static class InstanceHolder {
        private static final GameLobbyManager INSTANCE = new GameLobbyManager();
    }

    public static GameLobbyManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public GameManager getOrCreateLobby(String gameId, Board board, DiceStrategy dice, List<Player> players) {
        return activeGames.computeIfAbsent(gameId, id -> new GameManager(board, dice, players));
    }
}