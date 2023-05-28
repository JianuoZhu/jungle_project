package view;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class HomeChessComponent extends ChessComponent implements Serializable {


    public HomeChessComponent(PlayerColor owner, int size, int x, int y) {
        super(owner, size, x, y);
        String currentDir = System.getProperty("user.dir");
        String ImagePath;
        ImagePath = currentDir + "\\resource\\home.png";
        this.imagePath = ImagePath;
    }

    @Override
    protected void paintComponent(Graphics g) {//paints the piece
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
        g2.setComposite(alpha);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
        //g2.setFont(font);
        g2.setColor(owner.getColor());
        //g2.drawString("象", getWidth() / 4, getHeight() * 5 / 8);
        Image Icon2 = new ImageIcon((String)System.getProperty("user.dir")+"\\resource\\trap_c.jpg").getImage();
        g2.drawImage(Icon2, 0, 0, getWidth(), getHeight(), this);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g2.drawImage(this.getImage(), 0, 0, getWidth(), getHeight(), this);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}