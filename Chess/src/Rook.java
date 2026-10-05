//root or hathi
public class Rook implements Piece{
    private final Color color;
    private int row;
    private int col;

    public Rook(Color color, int row, int col){
        this.color=color;
        this.row=row;
        this.col=col;
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
        if(row!=targetRow&&col!=targetCol) return false;
        if(row==targetRow){
            int step=(targetCol>col)?1:-1;
            for(int curr=col+step;curr!=targetCol;curr+=step){
                if(grid[row][curr]!=null) return false;
                //if to achive the target cell, if there are any other pieces in the way, its an invalid move
            }
        }
        else{
            int step=(targetRow>row)?1:-1;
            for(int curr=row+step;curr!=targetRow;curr+=step){
                if(grid[curr][col]!=null) return false;
                //if to achive the target cell, if there are any other pieces in the way, its an invalid move
            }
        }
        return grid[targetRow][targetCol]==null||grid[targetRow][targetCol].getColor()!=this.color;
    }
}
