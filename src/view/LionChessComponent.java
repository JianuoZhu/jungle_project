package view;

import model.ChessPiece;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class LionChessComponent extends ChessComponent {

    public LionChessComponent(PlayerColor owner, int size) {
        super(owner, size);
    }

    protected void paintComponent(Graphics g) {//paints the piece
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
        g2.setFont(font);
        g2.setColor(owner.getColor());
        g2.drawString("狮", getWidth() / 4, getHeight() * 5 / 8);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
