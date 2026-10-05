//bishop or oont
public class Bishop implements Piece{
    private final Color color;
    private int row;
    private int col;

    public Bishop(Color color, int row, int col) {
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
        //only moves in diagnols;
        if(Math.abs(row-targetRow)!=Math.abs(col-targetCol)) return false;
        int rowStep=(targetRow>row)?1:-1;
        int colStep=(targetCol>col)?1:-1;
        int r=row+rowStep;
        int c=col+colStep;
        while(r!=targetRow&&c!=targetCol){
            if(grid[r][c]!=null) return false;
            r+=rowStep;
            c+=colStep;
        }
        return grid[r][c]==null||grid[r][c].getColor()!=color;
    }
}
