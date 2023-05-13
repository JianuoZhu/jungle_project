package view;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class TrapComponent extends CellComponent implements Serializable {
    public TrapComponent(Color background, Point location, int size) {
        super(background, location, size);
        String currentDir = System.getProperty("user.dir");
        String ImagePath;
        ImagePath = currentDir + "\\resource\\trap_c.jpg";
        this.setImage(ImagePath);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getBackground());
        g2.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
        g2.setComposite(alpha);
        g2.drawImage(this.getImage(), 0, 0, getWidth(), getHeight(), this);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }
}
