package controller;


import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Chessboard;
import model.ChessboardPoint;
import view.*;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {


    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    private void win(PlayerColor winnerColor) {
        JFrame frame = new JFrame("Pop-up Window");

        // Set the size and location of the frame
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create a new JLabel object with some text
        JLabel label = new JLabel("Hello, world!");

        // Add the label to the frame's content pane
        frame.getContentPane().add(label);

        // Set the frame to be visible
        frame.setVisible(true);
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point, view.isWater(point))) {
                model.moveChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
                if(model.getTrapUsed()[point.getRow()][point.getCol()] && !model.getTrapRemoved()[point.getRow()][point.getCol()]){
                    view.getGridComponents()[point.getRow()][point.getCol()].setImage(null);
                    //view.getGridComponents()[point.getRow()][point.getCol()].add(new CellComponent(Color.LIGHT_GRAY, view.calculatePoint(point.getRow(), point.getCol()), view.CHESS_SIZE));
                    model.getTrapRemoved()[point.getRow()][point.getCol()] = true;
                }
                if(model.isHome(point)){
                    if((point.getRow() < 5 && model.getChessPieceOwner(point) == PlayerColor.BLUE)
                        || (point.getRow() > 5 && model.getChessPieceOwner(point) == PlayerColor.RED)){
                        win(model.getChessPieceOwner(point));
                    }
                }
            // TODO: if the chess enter Dens or Traps and so on
        }
        else{

        }
    }

    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ElephantChessComponent component) {

    }

    // click a cell with a chess

    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        }
        // TODO: Implement capture function
        else{
            /*if (model.getChessPieceOwner(point).equals(currentPlayer) && model.getGridAt(point).getPiece().getRank() == -1){
                model.captureChessPiece(selectedPoint, point);
                ChessComponent removedChess = view.removeChessComponentAtGrid(selectedPoint);
                TrapChessComponent trap = (TrapChessComponent) view.getChessComponentAtGrid(point);
                trap.setStacked(true);
                trap.setStackedChess(removedChess);
                trap.setStackedImage(removedChess.getImage());
                selectedPoint = null;
                swapColor();
                view.repaint();
                return;
            }*/
            if(view.isWater(selectedPoint) || view.isWater(point)){
                component.setSelected(false);
                component.repaint();
                view.getChessComponentAtGrid(selectedPoint).setSelected(false);
                view.getChessComponentAtGrid(selectedPoint).repaint();
                selectedPoint = null;
            }
            if(model.isTrap(point)){
                model.captureChessPiece(selectedPoint, point);
                model.getGridAt(point).getPiece().setRank(0);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            }
            if (model.getChessPieceOwner(point).equals(currentPlayer)){
                component.setSelected(false);
                component.repaint();
                view.getChessComponentAtGrid(selectedPoint).setSelected(false);
                view.getChessComponentAtGrid(selectedPoint).repaint();
                selectedPoint = null;
            }
            else if(!model.isValidCapture(selectedPoint, point)){
                component.setSelected(false);
                component.repaint();
                view.getChessComponentAtGrid(selectedPoint).setSelected(false);
                view.getChessComponentAtGrid(selectedPoint).repaint();
                selectedPoint = null;
            }
            else{
                model.captureChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
            }
        }
    }


}
