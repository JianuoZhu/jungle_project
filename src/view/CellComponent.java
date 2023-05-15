package view;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel implements Serializable {
    private Color background;
    protected String imagePath;

    @Override
    public Color getBackground() {
        return background;
    }

    public CellComponent(Color background, Point location, int size) {
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
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
    }
}
