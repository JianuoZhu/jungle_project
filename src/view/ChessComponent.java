package view;

import model.ChessPiece;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class ChessComponent extends JComponent implements Serializable{
    protected PlayerColor owner;

    protected boolean selected;
    public boolean isStacked() {
        return stacked;
    }

    public Image getImage() {
        return new ImageIcon(imagePath).getImage();
    }

    protected String imagePath;
    protected boolean stacked;

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

