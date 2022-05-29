package chesslogic;

import java.util.HashMap;

import static java.lang.Math.abs;

public class King extends Piece{
        private Position position;
    public King(Position position, int color) {
        super(position, color);
        this.position = position;
    }
    public boolean isValidMove(Position newPosition, HashMap<String,Cell> board){
        int deltaX = newPosition.getX()-this.position.getX();
        int deltaY = newPosition.getY()-this.position.getY();

        return abs(deltaX) <= 1 && abs(deltaY) <= 1;
    }
    @Override
    public String getType(){
        return "king";
    }
    @Override
    public String toString(){
        if(this.getColor()==0){
            return "k";
        }
        else
            return "K";

    }
}
