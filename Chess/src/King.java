public class King implements Piece{
    private final Color color;
    private int row;
    private int col;

    public King(Color color, int row, int col){
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
        int rDiff=Math.abs(targetRow-row);
        int cDiff=Math.abs(targetCol-col);
        if(rDiff>1||cDiff>1) return false;
        return grid[targetRow][targetCol]==null||grid[targetRow][targetCol].getColor()!=this.color;
    }
}
