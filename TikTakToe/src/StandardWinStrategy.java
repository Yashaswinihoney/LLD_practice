import java.util.HashMap;
import java.util.Map;

public class StandardWinStrategy implements WinStrategy{
    //O(1) check, to track the exact count of symbols in every row, col, diagonal, antidiagonal
    //Memory is strictly bounded to O(N) per player
    private final Map<Character,int[]> rowCounts=new HashMap<>();
    private final Map<Character,int[]> colCounts=new HashMap<>();
    private final Map<Character,Integer> diagonalCounts=new HashMap<>();
    private final Map<Character,Integer> antiDiagCounts=new HashMap<>();
    @Override
    public boolean isWinningMove(int r, int c, char symbol, Board board) {
        int n=board.getSize();
        rowCounts.putIfAbsent(symbol, new int[n]);
        colCounts.putIfAbsent(symbol,new int[n]);

        int rCount=++rowCounts.get(symbol)[r];
        int cCount=++colCounts.get(symbol)[c];

        int dCount=0, aCount=0;
        if(r==c){
            diagonalCounts.put(symbol,diagonalCounts.getOrDefault(symbol,0)+1);
            dCount=diagonalCounts.get(symbol);
        }

        if(r+c==n-1){
            antiDiagCounts.put(symbol,antiDiagCounts.getOrDefault(symbol,0)+1);
            aCount=antiDiagCounts.get(symbol);
        }

        return rCount==n||cCount==n||dCount==n||aCount==n;
    }
}
