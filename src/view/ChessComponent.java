package view;

import model.ChessPiece;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class ChessComponent extends JComponent {
    protected PlayerColor owner;

    protected boolean selected;

    public boolean isStacked() {
        return stacked;
    }

    public Image getImage() {
        return image;
    }

    protected Image image;
    protected boolean stacked;

    public void setImage(Image image) {
        this.image = image;
    }

    public void setStacked(boolean stacked) {
        this.stacked = stacked;
    }

    public ChessComponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size / 2, size / 2);
        setLocation(0, 0);
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
}

