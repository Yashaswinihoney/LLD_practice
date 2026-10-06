import java.util.Arrays;

//CORE DOMAIN 2
public class Board {
    private final int size;
    private final char[][] grid;
    private int movesCount=0;

    public Board(int size) {
        this.size = size;
        this.grid = new char[size][size];
        for(int i=0;i<size;i++){
            Arrays.fill(grid[i],' ');
        }
    }

    public boolean placeMove(int r, int c, char symbol){
        if(r<0||r>=size||c<0||c>=size||grid[r][c]!=' ') return false;
        grid[r][c]=symbol;
        movesCount++;
        return true;
    }
    public boolean isFull(){
        return movesCount==size*size;
    }

    public int getSize() {
        return size;
    }

    public char getSymbolAt(int r, int c){
        return grid[r][c];
    }

    public void printBoard(){
        for(int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                System.out.print(" " + grid[i][j] + " ");
                if (j < size - 1) System.out.print("|");
            }
            System.out.println();
            if (i < size - 1) System.out.println("-".repeat(size * 4 - 1));
        }
    }
}
