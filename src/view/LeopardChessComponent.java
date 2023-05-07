package view;
import model.ChessPiece;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
public class LeopardChessComponent extends ChessComponent {
    public LeopardChessComponent(PlayerColor owner, int size) {
        super(owner, size);
        String currentDir = System.getProperty("user.dir");
        String ImagePath;
        if(owner.getColor() == Color.BLUE) ImagePath = currentDir + "\\resource\\leo_b.jpg";
        else  ImagePath = currentDir + "\\resource\\leo_r.jpg";
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
        g2.drawImage(this.image, 0, 0, getWidth(), getHeight(), this);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
