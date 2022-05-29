package chesslogic;

import java.util.HashMap;

abstract class Piece {
    private Position position;
    private int color;
    public boolean hasMoved = false;
    public Piece(Position position,int color){
        this.position = position;
        this.color = color;
    }
    public void setPosition(String column,int row){
        this.position.column = column;
        this.position.row = row;
    }
    public String getType(){
        return null;
    }
    public int getColor() {
        return color;
    }

    public boolean isValidMove(Position newPosition, HashMap<String,Cell> board) {
        return false;
    }
}
