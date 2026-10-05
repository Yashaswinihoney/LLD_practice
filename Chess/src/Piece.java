public interface Piece {
    Color getColor();
    int getRow();
    int getCol();
    void setPosition(int r, int c);
    boolean isValidMove(int targetRow, int targetCol, Piece[][] grid);
}
