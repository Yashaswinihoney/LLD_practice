public class FourCornersWinStrategy implements WinStrategy{
    @Override
    public boolean isWinningMove(int r, int c, char symbol, Board board) {
        int n=board.getSize();
        if(n<2) return false;
        return board.getSymbolAt(0,0)==symbol&&board.getSymbolAt(0,n-1)==symbol&&board.getSymbolAt(n-1,0)==symbol&&board.getSymbolAt(n-1,n-1)==symbol;
    }
}
