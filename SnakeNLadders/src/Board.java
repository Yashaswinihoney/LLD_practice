import java.util.HashMap;
import java.util.Map;

public class Board {
    private final int size;
    private final Map<Integer,Integer> specialCells;
    public Board(int size, Map<Integer,Integer> specialCells){
        this.size=size;
        this.specialCells=new HashMap<>(specialCells);
    }

    public int getSize() {
        return size;
    }

    public int getNextPosition(int position){
        if(specialCells.containsKey(position)){
            int dest=specialCells.get(position);
            if(dest<position){
                System.out.println("[Board] "+position+" swallowed by snake to " +dest);
            }
            else{
                System.out.println("[Board] "+position+" climbed up the ladder to " +dest);
            }
            return dest;
        }
        return position;
    }
}
