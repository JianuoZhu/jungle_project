package view;
import model.ChessPiece;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class RatChessComponent extends  ChessComponent {
    public RatChessComponent(PlayerColor owner, int size) {
        super(owner, size);
        String currentDir = System.getProperty("user.dir");
        String ImagePath;
        if(owner.getColor() == Color.BLUE) ImagePath = currentDir + "\\resource\\rat_b.jpg";
        else  ImagePath = currentDir + "\\resource\\rat_r.jpg";
        this.image = new ImageIcon(ImagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {//paints the piece
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
        //g2.setFont(font);
        g2.setColor(owner.getColor());
        //g2.drawString("象", getWidth() / 4, getHeight() * 5 / 8);
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
        g2.setComposite(alpha);
        g2.drawImage(this.getImage(), 0, 0, getWidth(), getHeight(), this);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
