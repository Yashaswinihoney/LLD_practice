public class Main {
    public static void main(String[] args) {
        ChessGameManager manager=ChessGameManager.getInstance();

        ChessGame game1=manager.getOrCreateGame("TABLE1");
        ChessGame game2=manager.getOrCreateGame("TABLE2");

        new Thread(()->game1.playMove(7,2,5,0),"THREAD-G1-WHITE").start();
        new Thread(()->game2.playMove(7,7,5,7),"THREAD-G2-WHITE").start();
        // Thread 1 moves the White Pawn at (6,1) forward to (4,1)
        new Thread(() -> game1.playMove(6, 1, 4, 1), "Thread-G1-White").start();

        // Thread 2 moves the White Pawn at (6,7) forward to (5,7)
        new Thread(() -> game2.playMove(6, 7, 5, 7), "Thread-G2-White").start();
    }
}