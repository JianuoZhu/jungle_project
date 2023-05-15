package controller;


import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Chessboard;
import model.ChessboardPoint;
import view.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;


import javax.swing.JFrame;
import javax.swing.JLabel;
//>>>>>>> origin/JnZ

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
 */
public class GameController implements GameListener,Serializable {
    public int turn=1;

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    private Chessboard model=null;
    private ChessboardComponent view=null;
    private PlayerColor currentPlayer=null;
    private ChessGameFrame gameFrame=null;

    private int leftChess[] = new int[2];
    public Chessboard getModel() {
        return model;
    }

    public ChessboardComponent getView() {
        return view;
    }
    String playerColor = null;


    public  PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessboardPoint getSelectedPoint() {
        return selectedPoint;
    }


    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public GameController(ChessboardComponent view, Chessboard model, ChessGameFrame frame) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.gameFrame = frame;
        this.leftChess[0] = this.leftChess[1] = 8;
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
        gameFrame.remove(ChessGameFrame.current_currentPlayer_JLabel);
        ChessGameFrame.current_currentPlayer_JLabel = gameFrame.addCurrentPlayers();
        gameFrame.remove(ChessGameFrame.current_turn_JLabel);
        ChessGameFrame.current_turn_JLabel = gameFrame.addCurrentTurns();
        gameFrame.repaint();
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
//saving and loading

    public void save() {
        String filePath = "object.ser";
        ArrayList<Object> objectsToSave = new ArrayList<>();

        // Add the objects to the ArrayList
        objectsToSave.add(view);
        //objectsToSave.add(model);
        //objectsToSave.add(gameFrame);

        System.out.println("Serialized data is saved in " + filePath);
        // Serialize the ArrayList to a file
        try {
            ObjectOutputStream outputStream = new MyObjectOutputStream(new FileOutputStream(filePath, true));
            //outputStream.writeObject(objectsToSave);
            outputStream.writeObject(view);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void load(){
        String filePath = "object.ser";
        ArrayList<Object> loadedObjects = new ArrayList<>();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath));
            //loadedObjects = (ArrayList<Object>) inputStream.readObject();
            inputStream.close();
        } catch (IOException /*| ClassNotFoundException*/ e) {
            e.printStackTrace();
        }
        //view.removeAll();
        view.repaint();
        // Process the loaded objects
        for (Object obj : loadedObjects) {
            if (obj instanceof ChessboardComponent) {
                view.setGridComponents(((ChessboardComponent) obj).getGridComponents());
                //view.
                //view.set
                //view.set
            } else if (obj instanceof Chessboard) {
                model = (Chessboard) obj;
            }
            else if(obj instanceof ChessGameFrame){
                gameFrame = (ChessGameFrame) obj;
            }
        }

        //view.initiateChessComponent(model);

        view.registerController(this);
        //view.repaint();

        view.paintComponents(view.getGraphics());
        //gameFrame.repaint();
    }




    // restart the game
    public void Restart(){
        model.RestartPieces();//right;
        view.initiateGridComponents();//right;
        view.initiateChessComponent(model);
        currentPlayer=PlayerColor.BLUE;
        view.paintComponents(view.getGraphics());
    }//easy init to be finished;

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point, view.isWater(point))) {
                model.moveChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                turn++;
                selectedPoint = null;
                repaintGreenCell();
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
                paintAvailableCell();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            repaintGreenCell();
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
                repaintGreenCell();
            }
            if(model.isTrap(point)){
                model.captureChessPiece(selectedPoint, point);
                model.getGridAt(point).getPiece().setRank(0);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                repaintGreenCell();
                turn++;
                minusChess();
                swapColor();
                view.repaint();
            }
            if (model.getChessPieceOwner(point).equals(currentPlayer)){
                component.setSelected(false);
                component.repaint();
                view.getChessComponentAtGrid(selectedPoint).setSelected(false);
                view.getChessComponentAtGrid(selectedPoint).repaint();
                selectedPoint = null;
                repaintGreenCell();
            }
            else if(!model.isValidCapture(selectedPoint, point)){
                component.setSelected(false);
                component.repaint();
                view.getChessComponentAtGrid(selectedPoint).setSelected(false);
                view.getChessComponentAtGrid(selectedPoint).repaint();
                selectedPoint = null;
                repaintGreenCell();
            }
            else{
                model.captureChessPiece(selectedPoint, point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                repaintGreenCell();
                turn++;
                minusChess();
                swapColor();
                view.repaint();
            }
        }
    }
    private void minusChess(){
        if(currentPlayer == PlayerColor.RED) {
            leftChess[0]--;
            if(leftChess[0] == 0) win(PlayerColor.RED);
        }
        if(currentPlayer == PlayerColor.BLUE) {
            leftChess[1]--;
            if(leftChess[1] == 0) win(PlayerColor.BLUE);
        }
    }

    private void paintAvailableCell(){
        for(int i=0; i < CHESSBOARD_ROW_SIZE.getNum(); i++){
            for(int j=0; j < CHESSBOARD_COL_SIZE.getNum(); j++){
                ChessboardPoint dest = new ChessboardPoint(i, j);
                if(getModel().isValidMove(selectedPoint, dest, view.isWater(dest)) && model.getGridAt(dest).getPiece() == null){
                    view.getGridComponents()[i][j].setBackground(Color.GREEN);
                    view.paintComponents(view.getGraphics());
                }
                if(model.getGridAt(selectedPoint).getPiece().getRank() == 6 || model.getGridAt(selectedPoint).getPiece().getRank() == 7){
                    if(!model.isWaterCell(dest) && model.isValidJump(selectedPoint, dest) && model.getGridAt(dest).getPiece() == null) {
                        view.getGridComponents()[i][j].setBackground(Color.GREEN);
                        view.paintComponents(view.getGraphics());
                    }
                }
                if(model.getGridAt(dest).getPiece() == null) continue;
                if(getModel().isValidCapture(selectedPoint, dest)){
                    view.getGridComponents()[i][j].setBackground(Color.GREEN);
                    view.paintComponents(view.getGraphics());
                }
            }
        }
    }
    private void repaintGreenCell(){
        for(int i=0; i < CHESSBOARD_ROW_SIZE.getNum(); i++){
            for(int j=0; j < CHESSBOARD_COL_SIZE.getNum(); j++){
                if(view.getGridComponents()[i][j].getBackground() == Color.GREEN){
                    view.getGridComponents()[i][j].setBackground(Color.LIGHT_GRAY);
                    if(model.isWaterCell(new ChessboardPoint(i, j))) view.getGridComponents()[i][j].setBackground(Color.CYAN);
                }
            }
        }
    }
}