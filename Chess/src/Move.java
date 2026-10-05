public class Move {
    private final int startRow, startCol, endRow, endCol;
    private final Piece movedPiece, capturedPiece;

    public Move(int startRow, int startCol, int endRow, int endCol, Piece movedPiece, Piece capturedPiece) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
    }

    public void execute(Board board){
        board.setPiece(startRow,startCol,null);
        board.setPiece(endRow,endCol,movedPiece);
        movedPiece.setPosition(endRow, endCol);
    }

    // Strictly internal method to simulate moves for check validation. Not a user feature.
    protected void rollback(Board board) {
        board.setPiece(startRow, startCol, movedPiece);
        movedPiece.setPosition(startRow, startCol);
        board.setPiece(endRow, endCol, capturedPiece);
    }
}
