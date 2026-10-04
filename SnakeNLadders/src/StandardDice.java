import java.util.Random;

public class StandardDice implements DiceStrategy{
    private final int sides;
    private final Random random=new Random();

    public StandardDice(int sides){
        this.sides=sides;
    }

    @Override
    public int roll(){
        //to generate a random number, bound to size, (not greater than size)
        return random.nextInt(sides)+1;
    }
}
