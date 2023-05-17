package listener;

import model.ChessboardPoint;
import view.CellComponent;
import view.ElephantChessComponent;

public interface GameListener {

    void onPlayerClickCell(ChessboardPoint point, CellComponent component) throws InterruptedException;


    void onPlayerClickChessPiece(ChessboardPoint point, ElephantChessComponent component);

}
