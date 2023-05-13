package view;
import model.ChessPiece;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class TrapChessComponent extends ChessComponent implements Serializable {

    private Image stackedImage;

    public Image getStackedImage() {
        return stackedImage;
    }

    private ChessComponent stackedChess;

    public void setStackedImage(Image stackedImage) {
        this.stackedImage = stackedImage;
    }

    public void setStackedChess(ChessComponent stackedChess) {
        this.stackedChess = stackedChess;
    }

    public ChessComponent getStackedChess() {
        return stackedChess;
    }

    public TrapChessComponent(PlayerColor owner, int size) {
        super(owner, size);
        String currentDir = System.getProperty("user.dir");
        String ImagePath;
        ImagePath = currentDir + "\\resource\\trap_c.jpg";
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
        if (stacked){
            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.99f);
            g2.setComposite(alpha);
            //Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
            //g2.setFont(font);
            g2.setColor(owner.getColor());
            //g2.drawString("象", getWidth() / 4, getHeight() * 5 / 8);
            g2.drawImage(this.image, 0, 0, getWidth(), getHeight(), this);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g2.drawImage(stackedImage, 0, 0, getWidth(), getHeight(), this);
        }
        else g2.drawImage(this.image, 0, 0, getWidth(), getHeight(), this);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
