public class Pawn implements Piece{
    private final Color color;
    private int row;
    private int col;

    public Pawn(Color color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public void setPosition(int r, int c) {
        this.row=r;
        this.col=c;
    }

    @Override
    public boolean isValidMove(int targetRow, int targetCol, Piece[][] grid) {
        //white moves up the grid(-1), black moves down the grid(+1)
        int dir=(this.color==Color.WHITE)?-1:1;
        //WHITE IS ALWAYS PLACED ON THE UPPER SIDE
        int startingRow=(this.color==Color.WHITE)?6:1;
        //Standard chess game board is 8*8

        //1. standard move, 1 row forward into a empty cell
        if(targetCol==col&&targetRow==row+dir&&grid[targetRow][targetCol]==null){
            return true;
        }
        //2. initial double move: 2 squares forward from the starting move
        if(targetCol==col&&row==startingRow&&targetRow==row+2*dir){
            //both intermediate and target node should be empty
            if(grid[row+dir][col]==null&&grid[targetRow][targetCol]==null){
                return true;
            }
        }

        //3. diagonal capture: 1 square diagonally forward into an opponent's piece
        if(Math.abs(targetCol-col)==1&&targetRow==row+dir){
            if(grid[targetRow][targetCol]!=null&&grid[targetRow][targetCol].getColor()!=this.color){
                return true;
            }
        }
        return false;
    }
}
