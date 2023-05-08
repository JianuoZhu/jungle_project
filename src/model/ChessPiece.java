package model;


public class ChessPiece {
    // the owner of the chess
    private PlayerColor owner;

    public void setOwner(PlayerColor owner) {
        this.owner = owner;
    }

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;

    public int getRank(){
        return this.rank;
    }

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        // TODO: Finish this method!
        if(this.rank == 8 && target.getRank() == 1) return false;
        if(this.rank == 1 && target.getRank() == 8) return true;
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
