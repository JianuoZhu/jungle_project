package model;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    boolean trapUsed[][] = new boolean[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces() {
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));

        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion",7));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion",7));

        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger",6));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger",6));

        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard",5));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard",5));

        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf",4));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf",4));

        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog",3));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog",3));

        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat",2));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat",2));

        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat",1));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat",1));

        /*grid[0][2].setPiece(new ChessPiece(PlayerColor.RED, "Trap", -1));
        grid[0][4].setPiece(new ChessPiece(PlayerColor.RED, "Trap", -1));
        grid[1][3].setPiece(new ChessPiece(PlayerColor.RED, "Trap", -1));

        grid[8][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Trap", -1));
        grid[8][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Trap", -1));
        grid[7][3].setPiece(new ChessPiece(PlayerColor.BLUE, "Trap", -1));

        grid[0][3].setPiece(new ChessPiece(PlayerColor.RED, "Home", 0));
        grid[8][3].setPiece(new ChessPiece(PlayerColor.BLUE, "Home", 0));*/
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public boolean isTrap(ChessboardPoint point){
        /*trapCell.add(new ChessboardPoint(0,2));
        trapCell.add(new ChessboardPoint(0,4));
        trapCell.add(new ChessboardPoint(1,3));
        trapCell.add(new ChessboardPoint(8,2));
        trapCell.add(new ChessboardPoint(8,4));
        trapCell.add(new ChessboardPoint(7,3));

        homeCell.add(new ChessboardPoint(0,3));
        homeCell.add(new ChessboardPoint(8,3));*/
        if((point.getRow() == 0 && point.getRow() == 2)
        || (point.getRow() == 0 && point.getRow() == 4)
        || (point.getRow() == 1 && point.getRow() == 3)
        || (point.getRow() == 8 && point.getRow() == 2)
        || (point.getRow() == 8 && point.getRow() == 4)
        || (point.getRow() == 7 && point.getRow() == 3)){
            if(!trapUsed[point.getRow()][point.getCol()]) return true;
        }
        return false;
    }
    public boolean isHome(ChessboardPoint point){
        /*trapCell.add(new ChessboardPoint(0,2));
        trapCell.add(new ChessboardPoint(0,4));
        trapCell.add(new ChessboardPoint(1,3));
        trapCell.add(new ChessboardPoint(8,2));
        trapCell.add(new ChessboardPoint(8,4));
        trapCell.add(new ChessboardPoint(7,3));

        homeCell.add(new ChessboardPoint(0,3));
        homeCell.add(new ChessboardPoint(8,3));*/
        if((point.getRow() == 0 && point.getRow() == 3)
                || (point.getRow() == 8 && point.getRow() == 3)){
            return true;
        }
        return false;
    }
    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        if (isTrap(dest)){
            if((getChessPieceOwner(src) == PlayerColor.BLUE && dest.getRow() < 5)
            || (getChessPieceOwner(dest) == PlayerColor.RED && dest.getRow() > 5)){
                trapUsed[dest.getRow()][dest.getCol()] = true;
                getChessPieceAt(src).setRank(0);
            }
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        // TODO: Finish the method.
        /*if(getChessPieceAt(dest).getRank() == -1){
            if(getChessPieceAt(dest).isStacked() && (getChessPieceAt(dest).getStackedChess().getOwner() != getChessPieceAt(src).getOwner())){
                //capture stacked chess than rank go -1;
            }
            else if(getChessPieceAt(dest).isStacked() && (getChessPieceAt(dest).getStackedChess().getOwner() == getChessPieceAt(src).getOwner())){
                throw new IllegalArgumentException("Illegal chess capture!");
            }
            else if(!getChessPieceAt(dest).isStacked() && (getChessPieceAt(dest).getOwner() != getChessPieceAt(src).getOwner())){
                //walk in and set rank -1;
            }
            else if(!getChessPieceAt(dest).isStacked() && (getChessPieceAt(dest).getOwner() == getChessPieceAt(src).getOwner())){
                //walk in;
                ChessPiece removedChess = removeChessPiece(src);
                getChessPieceAt(dest).setStackedChess(removedChess);
                getChessPieceAt(dest).setStacked(true);
            }
        }*/
        removeChessPiece(dest);
        setChessPiece(dest, removeChessPiece(src));
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        /*if(getChessPieceAt(src).getRank()==-1){
            if(!getChessPieceAt(src).isStacked()) return false;
            else return  true;
        }*/
        if(getGrid()[src.getRow()][src.getCol()].getPiece().getOwner()==PlayerColor.BLUE
                &&dest.getCol()==3&&dest.getRow()==8){
            return false;

        }else if(getGrid()[src.getRow()][src.getCol()].getPiece().getOwner()==PlayerColor.RED
                &&dest.getCol()==3&&dest.getRow()==0){
            return false;

        } else if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }else
        return calculateDistance(src, dest) == 1;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        // TODO:Fix this method
        if(getChessPieceAt(src).canCapture(getChessPieceAt(dest)) && calculateDistance(src, dest) == 1)
            return true;
        //if(getChessPieceAt(dest).getRank() == -1) return true;
        return false;
    }
}
