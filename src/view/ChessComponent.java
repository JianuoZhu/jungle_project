package view;

import controller.GameController;
import model.ChessPiece;
import model.ChessboardPoint;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class ChessComponent extends JComponent implements Serializable{
    protected PlayerColor owner;

    protected boolean selected;
    protected boolean Entered;
    public boolean isStacked() {
        return stacked;
    }

    public Image getImage() {
        return new ImageIcon(imagePath).getImage();
    }

    protected String imagePath;
    protected boolean stacked;
    public int x;
    public int y;
    public void setStacked(boolean stacked) {
        this.stacked = stacked;
    }

    public ChessComponent(PlayerColor owner, int size, int _x, int _y) {
        this.owner = owner;
        this.selected = false;
        this.x = _x;
        this.y = _y;
        setSize(size / 2, size / 2);
        setLocation(0, 0);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setVisible(true);
    }

    public ChessComponent() {

    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setEntered(boolean entered) {
        Entered = entered;
    }
    public ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/ChessboardComponent.CHESS_SIZE +  ", " +point.x/ChessboardComponent.CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/ChessboardComponent.CHESS_SIZE, point.x/ChessboardComponent.CHESS_SIZE);
    }
    JComponent clickedComponent;

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
                System.out.print("One chess here and ");
                try {
                    GameController.current_controller.onPlayerClickChessPiece(new ChessboardPoint(x, y), (ChessComponent) clickedComponent);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
        }
        if(e.getID() == MouseEvent.MOUSE_ENTERED){
            clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if(GameController.current_controller.getCurrentPlayer() != owner) return;
            //clickedComponent.setForeground(Color.BLACK);
            /*clickedComponent.getGraphics().setColor(Color.RED);
            clickedComponent.getGraphics().drawOval(0, 0, getWidth(), getHeight());
            clickedComponent.paint(clickedComponent.getGraphics());*/
            try {
                GameController.current_controller.onPlayerEnterCell(new ChessboardPoint(x, y), (ChessComponent) clickedComponent);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getID() == MouseEvent.MOUSE_EXITED){
            /*clickedComponent.getGraphics().setColor(Color.RED);
            clickedComponent.getGraphics().drawOval(0, 0, getWidth(), getHeight());
            clickedComponent.paint(clickedComponent.getGraphics());*/
            try {
                GameController.current_controller.onPlayerExitCell(new ChessboardPoint(x, y), (ChessComponent) clickedComponent);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

