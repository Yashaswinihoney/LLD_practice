import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Map<Integer, Integer> specials = new HashMap<>();
        specials.put(14, 4);  // Snake
        specials.put(37, 17); // Snake
        specials.put(3, 38);  // Ladder
        specials.put(40, 59); // Ladder

        Board board = new Board(100, specials);
        DiceStrategy dice = new StandardDice(6);

        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");
        Player p3 = new Player("Charlie");
        List<Player> players = Arrays.asList(p1, p2, p3);

        GameLobbyManager lobby = GameLobbyManager.getInstance();
        GameManager game = lobby.getOrCreateLobby("LOBBY_1", board, dice, players);

        System.out.println("--- Snake & Ladder Game Starting ---");

        // Simulate concurrent execution where threads race, but the engine enforces strict turn order
        Thread t1 = new Thread(() -> game.takeTurn(p1));
        Thread t2 = new Thread(() -> game.takeTurn(p2));
        Thread t3 = new Thread(() -> game.takeTurn(p3));

        t1.start();
        t2.start();
        t3.start();
    }
}