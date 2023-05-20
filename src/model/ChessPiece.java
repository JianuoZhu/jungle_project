package model;


import java.io.Serializable;

public class ChessPiece implements Serializable {
    // the owner of the chess
    private PlayerColor owner;

    public void setOwner(PlayerColor owner) {
        this.owner = owner;
    }

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;

    private boolean stacked = false;

    private ChessPiece StackedChess;

    private  boolean onTrap;
    public boolean isOnTrap() {
        return onTrap;
    }

    public void setOnTrap(boolean onTrap) {
        this.onTrap = onTrap;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setStacked(boolean stacked) {
        this.stacked = stacked;
    }

    public void setStackedChess(ChessPiece stackedChess) {
        StackedChess = stackedChess;
    }

    public boolean isStacked() {
        return stacked;
    }

    public ChessPiece getStackedChess() {
        return StackedChess;
    }

    public int getRank(){
        return this.rank;
    }

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
        this.onTrap = false;
    }

    public boolean canCapture(ChessPiece target) {
        // TODO: Finish this method!
        if(target.getRank() == -1) return false;
        if(this.rank == 8 && target.getRank() == 1) return false;
        if(this.rank == 1 && target.getRank() == 8) return true;
        if(target.onTrap) return true;
        if(this.rank >= target.getRank()) return true;
        return false;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }
}
