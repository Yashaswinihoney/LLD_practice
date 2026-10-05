import java.util.concurrent.locks.ReentrantLock;

public class ChessGame {
    private final String gameId;
    private final Board board=new Board();
    private Color activeTurn=Color.WHITE;
    private final ReentrantLock lock=new ReentrantLock();

    public ChessGame(String gameId) {
        this.gameId = gameId;
    }

    public boolean playMove(int sr, int sc, int er, int ec){
        lock.lock();
        try{
            Piece p= board.getPiece(sr,sc);
            if(p==null||p.getColor()!=activeTurn){
                System.out.println(gameId+" ERROR: invalid selection or not your turn");
                return false;
            }

            if(!p.isValidMove(er,ec, board.getGrid())){
                System.out.println(gameId+" ERROR: invalid move");
                return false;
            }

            Piece target= board.getPiece(er,ec);
            Move tempMove=new Move(sr,sc,er,ec,p,target);
            tempMove.execute(board);

            // Rollback internal state if the move places own king in check
            if (board.isKingInCheck(activeTurn)) {
                System.out.println("[" + gameId + "] Rejected: Leaves King in check.");
                tempMove.rollback(board);
                return false;
            }

            System.out.println("[" + gameId + "] Move Executed: " + p.getClass().getSimpleName() +
                    " to (" + er + "," + ec + ")");

            Color opponentColor = (activeTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;
            if (board.isKingInCheck(opponentColor)) {
                System.out.println("[" + gameId + "] Check! " + opponentColor + " King attacked!");
            }

            activeTurn = opponentColor;
            return true;
        }finally {
            // Crucial: Always unlock in a finally block to prevent deadlocks[cite: 3].
            lock.unlock();
        }
    }
}
