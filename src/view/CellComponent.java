package view;

import controller.GameController;
import model.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel implements Serializable {
    private Color background;
    protected String imagePath;
    protected boolean isWater;
    public int x;
    public int y;
    public void setWater(boolean water) {
        isWater = water;
    }

    @Override
    public Color getBackground() {
        return background;
    }

    public CellComponent(Color background, Point location, int size, int x, int y) {
        this.x = x;
        this.y = y;
        isWater = false;
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        String currentDir = System.getProperty("user.dir");
        String ImagePath;
        ImagePath = currentDir + "\\resource\\bk8.png";
        setImage(ImagePath);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.background = background;
    }

    public Image getImage() {
        return new ImageIcon(this.imagePath).getImage();
    }

    @Override
    public void setBackground(Color background) {
        this.background = background;
    }

    public void setImage(String ImagePath) {
        this.imagePath = ImagePath;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(background);
        g.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
        if(!isWater){
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(getBackground());
            g2.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
            g2.setComposite(alpha);
            g2.drawImage(this.getImage(), 0, 0, getWidth(), getHeight(), this);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        }
    }
    public ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/ChessboardComponent.CHESS_SIZE +  ", " +point.x/ChessboardComponent.CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/ChessboardComponent.CHESS_SIZE, point.x/ChessboardComponent.CHESS_SIZE);
    }
    JComponent clickedComponent;

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.print("None chess here and ");
            try {
                GameController.current_controller.onPlayerClickCell(new ChessboardPoint(x, y), (CellComponent) clickedComponent);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
