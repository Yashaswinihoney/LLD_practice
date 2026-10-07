import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MatchEngine {
    private final Map<String, Player> players=new ConcurrentHashMap<>();

    // 2D Spatial Grid Hashing (O(1) Proximity lookups for grenades/AOE)[cite: 24, 25]
    // Maps a grid coordinate (e.g., "Sector_1_1") to a list of players in that sector
    private final Map<String, List<Player>> spatialGrid = new ConcurrentHashMap<>();

    private MatchEngine() {}

    private static class InstanceHolder {
        private static final MatchEngine INSTANCE = new MatchEngine();
    }

    public static MatchEngine getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void spawnPlayer(Player p) {
        players.put(p.getId(), p);
    }

    public Player getPlayer(String id) {
        return players.get(id);
    }
}
