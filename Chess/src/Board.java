public class Board {
    private final Piece[][] grid=new Piece[8][8];

    public Board() {
        // 1. Initialize major pieces for BLACK (Row 0)
        grid[0][0] = new Rook(Color.BLACK, 0, 0);
        grid[0][1] = new Knight(Color.BLACK, 0, 1);
        grid[0][2] = new Bishop(Color.BLACK, 0, 2);
        grid[0][3] = new Queen(Color.BLACK, 0, 3);
        grid[0][4] = new King(Color.BLACK, 0, 4);
        grid[0][5] = new Bishop(Color.BLACK, 0, 5);
        grid[0][6] = new Knight(Color.BLACK, 0, 6);
        grid[0][7] = new Rook(Color.BLACK, 0, 7);

        // 2. Initialize major pieces for WHITE (Row 7)
        grid[7][0] = new Rook(Color.WHITE, 7, 0);
        grid[7][1] = new Knight(Color.WHITE, 7, 1);
        grid[7][2] = new Bishop(Color.WHITE, 7, 2);
        grid[7][3] = new Queen(Color.WHITE, 7, 3);
        grid[7][4] = new King(Color.WHITE, 7, 4);
        grid[7][5] = new Bishop(Color.WHITE, 7, 5);
        grid[7][6] = new Knight(Color.WHITE, 7, 6);
        grid[7][7] = new Rook(Color.WHITE, 7, 7);

        // 3. Initialize all 16 Pawns using a loop
        for (int i = 0; i < 8; i++) {
            grid[1][i] = new Pawn(Color.BLACK, 1, i);
            grid[6][i] = new Pawn(Color.WHITE, 6, i);
        }
    }

    public Piece[][] getGrid() {
        return grid;
    }

    public Piece getPiece(int r, int c){
        return grid[r][c];
    }
    public void setPiece(int r, int c, Piece p){
        grid[r][c]=p;
    }

    //vvimp, checked before every move
    public boolean isKingInCheck(Color kingColor) {
        int kingRow = -1, kingCol = -1;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p instanceof King && p.getColor() == kingColor) {
                    kingRow = r; kingCol = c; break;
                }
            }
        }
        if (kingRow == -1) return false;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p != null && p.getColor() != kingColor) {
                    if (p.isValidMove(kingRow, kingCol, grid)) return true;
                }
            }
        }
        return false;
    }
}
