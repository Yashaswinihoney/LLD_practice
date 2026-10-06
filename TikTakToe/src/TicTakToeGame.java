import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class TicTakToeGame {
    private final String gameId;
    private final Board board;
    private final List<Player> players;
    private final WinStrategy winStrategy;
    private int currentPlayerIndex=0;
    private boolean isGameOver=false;
    private final ReentrantLock lock=new ReentrantLock();


    public TicTakToeGame(String gameId, int size, List<Player> players, WinStrategy winStrategy) {
        this.gameId = gameId;
        this.board = new Board(size);
        this.players = players;
        this.winStrategy = winStrategy;
    }

    public boolean playMove(int r, int c){
        lock.lock();
        try{
            if (isGameOver){
                System.out.println(gameId+" Move rejected, Game is already over");
                return false;
            }

            Player current=players.get(currentPlayerIndex);
            if (!board.placeMove(r,c, current.getSymbol())){
                System.out.println(gameId+" Move rejected, cell "+r+" "+c+" is invalid");
                return false;
            }

            System.out.println(gameId+" "+current.getName()+" placed "+ current.getSymbol()+" at ("+r+","+c+")");
            board.printBoard();

            // The Strategy's internal HashMap state is inherently thread-safe here
            // because it is only accessed while the thread holds the gameLock.
            if (winStrategy.isWinningMove(r, c, current.getSymbol(), board)) {
                isGameOver = true;
                System.out.println("[" + gameId + "] 🏆 GAME OVER! Winner: " + current.getName());
                return true;
            }

            if (board.isFull()) {
                isGameOver = true;
                System.out.println("[" + gameId + "] 🤝 GAME OVER! It's a Draw.");
                return true;
            }

            // Dynamic turn routing for N-players
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            return true;
        }finally {
            lock.unlock();
        }
    }
}
