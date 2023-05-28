package view;


import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent implements Serializable {
    public Image getImage() {
        return new ImageIcon(imagePath).getImage();
    }

    protected String imagePath;
    public GameController getGameController() {
        return gameController;
    }

    private CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    public static int CHESS_SIZE = 0;
    private JComponent clickedComponent;

    public Set<ChessboardPoint> getRiverCell() {
        return riverCell;
    }

    public Set<ChessboardPoint> getTrapCell() {
        return trapCell;
    }

    public Set<ChessboardPoint> getHomeCell() {
        return homeCell;
    }

    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> trapCell = new HashSet<>();

    public void setGridComponents(CellComponent[][] gridComponents) {
        this.gridComponents = gridComponents;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    private final Set<ChessboardPoint> homeCell = new HashSet<>();
    private GameController gameController;

    public CellComponent[][] getGridComponents() {
        return gridComponents;
    }
    public boolean isWater(ChessboardPoint point){
        return riverCell.contains(point);
    }
    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        final int width = CHESS_SIZE * 7;
        final int height = CHESS_SIZE * 9;//
        //enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);


        initiateGridComponents();
    }///

    protected void paintComponent1(Graphics g) {//paints the piece
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
        //g2.setFont(font);
        //g2.drawString("象", getWidth() / 4, getHeight() * 5 / 8);
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
        g2.setComposite(alpha);
        g2.drawImage(this.getImage(), 0, 0, getWidth(), getHeight(), this);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */

    public void removeChessComponent(Chessboard chessboard){
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                gridComponents[i][j].removeAll();
            }
        }
    }


    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                gridComponents[i][j].removeAll();
            }
        }

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {

                if (grid[i][j].getPiece() != null) {
                    if (grid[i][j].getPiece().getName().equals("Elephant")){
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new ElephantChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE, i, j));
                    }

                    if(grid[i][j].getPiece().getName().equals("Lion")){
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new LionChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE, i, j));
                    }
                    if (grid[i][j].getPiece().getName().equals("Tiger")){
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new TigerChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE, i, j));
                    }
                    if (grid[i][j].getPiece().getName().equals("Leopard")){
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new LeopardChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE, i, j));
                    }
                    if (grid[i][j].getPiece().getName().equals("Wolf")){
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new WolfChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE, i, j));
                    }
                    if (grid[i][j].getPiece().getName().equals("Dog")){
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new DogChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE, i, j));
                    }
                    if (grid[i][j].getPiece().getName().equals("Cat")){
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new CatChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE, i, j));
                    }
                    if (grid[i][j].getPiece().getName().equals("Rat")){
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new RatChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE, i, j));
                    }
                    /*if (grid[i][j].getPiece().getName().equals("Trap")){
>>>>>>> origin/JnZ
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new TrapChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (grid[i][j].getPiece().getName().equals("Home")){
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new HomeChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }*/


                }
            }
        }

    }

    public void initiateGridComponents() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (gridComponents[i][j] != null) {
                    this.remove(gridComponents[i][j]);
                    gridComponents[i][j] = null;
                }
            }
        }//remove all first

        riverCell.add(new ChessboardPoint(3,1));
        riverCell.add(new ChessboardPoint(3,2));
        riverCell.add(new ChessboardPoint(4,1));
        riverCell.add(new ChessboardPoint(4,2));
        riverCell.add(new ChessboardPoint(5,1));
        riverCell.add(new ChessboardPoint(5,2));

        riverCell.add(new ChessboardPoint(3,4));
        riverCell.add(new ChessboardPoint(3,5));
        riverCell.add(new ChessboardPoint(4,4));
        riverCell.add(new ChessboardPoint(4,5));
        riverCell.add(new ChessboardPoint(5,4));
        riverCell.add(new ChessboardPoint(5,5));
        trapCell.add(new ChessboardPoint(0,2));
        trapCell.add(new ChessboardPoint(0,4));
        trapCell.add(new ChessboardPoint(1,3));
        trapCell.add(new ChessboardPoint(8,2));
        trapCell.add(new ChessboardPoint(8,4));
        trapCell.add(new ChessboardPoint(7,3));

        homeCell.add(new ChessboardPoint(0,3));
        homeCell.add(new ChessboardPoint(8,3));
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE, i, j);
                    cell.setWater(true);
                    this.add(cell);
                }
                else if(trapCell.contains(temp)){
                    cell = new TrapComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE, i, j);
                    this.add(cell);
                }
                else if(homeCell.contains(temp)){
                    cell = new HomeComponent(Color.WHITE, calculatePoint(i, j), CHESS_SIZE, i, j);
                    this.add(cell);
                }
                else {
                    cell = new CellComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE, i, j);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).add(chess);
    }

    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    public ChessComponent getChessComponentAtGrid(ChessboardPoint point){
        return (ChessComponent) getGridComponentAt(point).getComponents()[0];
    }
    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    public ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    public Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }//here is problem

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                try {
                    gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.out.print("One chess here and ");
                try {
                    gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if(e.getID() == MouseEvent.MOUSE_ENTERED){
            clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            //clickedComponent.setForeground(Color.BLACK);
            if (clickedComponent.getComponentCount() != 0) {
                /*clickedComponent.getGraphics().setColor(Color.RED);
                clickedComponent.getGraphics().drawOval(0, 0, getWidth(), getHeight());
                clickedComponent.paint(clickedComponent.getGraphics());*/
                try {
                    gameController.onPlayerEnterCell(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if(e.getID() == MouseEvent.MOUSE_EXITED){
            if (clickedComponent.getComponentCount() != 0) {
                /*clickedComponent.getGraphics().setColor(Color.RED);
                clickedComponent.getGraphics().drawOval(0, 0, getWidth(), getHeight());
                clickedComponent.paint(clickedComponent.getGraphics());*/
                try {
                    gameController.onPlayerExitCell(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }


}
