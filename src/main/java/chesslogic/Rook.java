package chesslogic;

import java.util.HashMap;

import static java.lang.Math.abs;

public class Rook extends Piece{
    private Position position;
    public Rook(Position position, int color) {
        super(position, color);
        this.position = position;
    }
    public boolean isValidMove(Position newPosition, HashMap<String,Cell> board){
        int deltaX = newPosition.getX()-this.position.getX();
        int deltaY = newPosition.getY()-this.position.getY();
        if (abs(deltaX)>0 && abs(deltaY)==0)
            return true;
        else if (abs(deltaX)==0&&abs(deltaY)>0)
            return true;
        else
            return false;

    }
    @Override
    public String getType(){
        return "rook";
    }
    @Override
    public String toString(){
        if(this.getColor()==0){
            return "r";
        }
        else
            return "R";

    }
}
