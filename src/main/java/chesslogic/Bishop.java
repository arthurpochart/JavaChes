package chesslogic;

import java.util.HashMap;

import static java.lang.Math.abs;

public class Bishop extends Piece{
    private Position position;
    public Bishop(Position position, int color) {
        super(position, color);
        this.position = position;
    }
    @Override
    public boolean isValidMove(Position newPosition, HashMap<String,Cell> board){
        int deltaX = newPosition.getX()-this.position.getX();
        int deltaY = newPosition.getY()-this.position.getY();
        return abs(deltaX) == abs(deltaY);


    }
    @Override
    public String getType(){
        return "bishop";
    }
    @Override
    public String toString(){
        if(this.getColor()==0){
            return "b";
        }
        else
            return "B";

    }
}
