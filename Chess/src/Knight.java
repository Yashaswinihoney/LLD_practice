//Knight or ghoda/horse
public class Knight implements Piece{
    private final Color color;
    private int row;
    private int col;

    public Knight(Color color, int r, int c){
        this.color=color;
        this.row=r;
        this.col=c;
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
        if(!((rDiff==2&&cDiff==1)||(rDiff==1&&cDiff==2))) return false;
        return grid[targetRow][targetCol]==null||grid[targetRow][targetCol].getColor()!=color;
    }
}
