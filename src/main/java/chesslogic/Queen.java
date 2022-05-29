package chesslogic;

import java.util.HashMap;

import static java.lang.Math.abs;

public class Queen extends Piece{
    private Position position;
    public Queen(Position position, int color) {
        super(position, color);
        this.position = position;
    }
    public boolean isValidMove(Position newPosition, HashMap<String,Cell> board){
        int deltaX = newPosition.getX()-this.position.getX();
        int deltaY = newPosition.getY()-this.position.getY();
        if ((abs(deltaX) == abs(deltaY)||(abs(deltaX)>0&&deltaY==0)||(deltaX==0&&abs(deltaY)>0) ))
            return true;
        else
            return false;

    }
    @Override
    public String getType(){
        return "queen";
    }
    @Override
    public String toString(){
        if(this.getColor()==0){
            return "q";
        }
        else
            return "Q";

    }
}
