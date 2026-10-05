public class Queen implements Piece{
    private final Color color;
    private int row;
    private int col;

    public Queen(Color color, int row, int col) {
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
    public boolean isValidMove(int tr, int tc, Piece[][] grid) {
        int rDiff = Math.abs(row - tr);
        int cDiff = Math.abs(col - tc);

        boolean isStraight = (row == tr || col == tc);
        boolean isDiagonal = (rDiff == cDiff);

        // 1. Must move like a Rook OR a Bishop
        if (!isStraight && !isDiagonal) return false;

        // 2. Determine step direction cleanly
        int rowStep = Integer.compare(tr, row);
        int colStep = Integer.compare(tc, col);

        int r = row + rowStep;
        int c = col + colStep;

        // 3. Scan the path for blocking pieces
        while (r != tr || c != tc) {
            if (grid[r][c] != null) return false;
            r += rowStep;
            c += colStep;
        }

        // 4. Validate target capture
        return grid[tr][tc] == null || grid[tr][tc].getColor() != this.color;
    }
}
