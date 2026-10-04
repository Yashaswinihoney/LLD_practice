import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class GameManager {
    private final Board board;
    private final DiceStrategy dice;
    private final Queue<Player> turnQueue;

    private volatile Player winner=null;
    private final ReentrantLock lock=new ReentrantLock(true);
    private final Condition turnCondition= lock.newCondition();
    private Player currentPlayer;

    public GameManager(Board board, DiceStrategy dice, List<Player> initialPlayers){
        this.board=board;
        this.dice=dice;
        this.turnQueue=new LinkedList<>(initialPlayers);
        this.currentPlayer=turnQueue.peek();
    }

    public  boolean isGameOver(){
        return winner!=null;
    }

    //executed concurrently by multiple player threads
    public void takeTurn(Player executingPlayer){
        //first check
        while(!isGameOver()){
            //explicitly acquire lock
            lock.lock();
            try{
                while (currentPlayer!=executingPlayer&&!isGameOver()){
                    turnCondition.await(); //pause thread and release monitor lock
                    //we basically wait until the current player is finished executing his chance and
                    //the current player becomes the executing player
                }
                if (isGameOver()) break; //double check after waking up

                executeMove(executingPlayer);

                if (!isGameOver()){
                    //rotate the queue to the next player
                    turnQueue.add(turnQueue.poll());
                    currentPlayer=turnQueue.peek();
                }

                //signals all waiting threads to wake up and execute the while loop
                turnCondition.signalAll();
            }
            catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println(executingPlayer.getName()+" was interrupted");
            }
            finally {
                //always unlock the lock explicitly in the final block to prevent deadlocks
                lock.unlock();
            }
        }
    }

    private void executeMove(Player player){
        int roll= dice.roll();
        int currentPosition=player.getPosition();
        int nextPosition=currentPosition+roll;

        System.out.println("[Game] "+player.getName()+" rolled a "+roll+" (Current "+ currentPosition+")");

        if (nextPosition> board.getSize()){
            System.out.println("Overshoot");
            return;
        }

        nextPosition= board.getNextPosition(nextPosition);
        player.setPosition(nextPosition);
        System.out.println("[Game] "+player.getName()+" moves to cell "+ nextPosition);

        if (nextPosition== board.getSize()){
            winner=player;
            System.out.println("[Victory] "+player.getName()+" has won the game");
        }
    }
}
