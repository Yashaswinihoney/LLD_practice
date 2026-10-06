import java.util.Arrays;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        GameManager manager=GameManager.getInstance();
        // Setup Player Rosters
        List<Player> players2 = Arrays.asList(new Player("Alice", 'X'), new Player("Bob", 'O'));
        List<Player> players3 = Arrays.asList(new Player("P1", 'A'), new Player("P2", 'B'), new Player("P3", 'C'));

        // Instantiate isolated game sessions concurrently
        TicTakToeGame standardGame = manager.createGame("TABLE_1", 3, players2, new StandardWinStrategy());
        TicTakToeGame multiPlayerGame = manager.createGame("TABLE_2", 4, players3, new StandardWinStrategy());

        // Simulate multi-threading (Thread A executes a move on TABLE_1 while Thread B executes on TABLE_2)
        new Thread(() -> standardGame.playMove(0, 0)).start();
        new Thread(() -> multiPlayerGame.playMove(0, 0)).start();
    }
}