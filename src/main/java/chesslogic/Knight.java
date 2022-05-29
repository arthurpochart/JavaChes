package chesslogic;

import java.util.HashMap;

import static java.lang.Math.abs;

public class Knight extends Piece{
    private Position position;
    public Knight(Position position, int color) {
        super(position, color);
        this.position = position;
    }
    @Override
    public boolean isValidMove(Position newPosition, HashMap<String,Cell> board){
        int deltaX = newPosition.getX()-this.position.getX();
        int deltaY = newPosition.getY()-this.position.getY();
        if (abs(deltaX) == 1 && abs(deltaY) == 2){
            return true;
        }
        else return abs(deltaX) == 2 && abs(deltaY) == 1;

    }
    @Override
    public String getType(){
        return "knight";
    }
    @Override
    public String toString(){
        if(this.getColor()==0){
            return "n";
        }
        else
            return "N";

    }
}
