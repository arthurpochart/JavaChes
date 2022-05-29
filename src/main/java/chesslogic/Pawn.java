package chesslogic;

import java.util.HashMap;

import static java.lang.Math.abs;

public class Pawn extends Piece{
    private Position position;
    private int color;
    public Pawn(Position position, int color) {
        super(position, color);
        this.position = position;
        this.color = color;
    }
    @Override
    public boolean isValidMove(Position newPosition, HashMap<String,Cell> board){
        Cell newCell = board.get(newPosition.toString());
        int deltaX = newPosition.getX()-this.position.getX();
        int deltaY = newPosition.getY()-this.position.getY();
        if (abs(deltaY)> 1 ) {
            return !this.hasMoved && abs(deltaY) == 2;
        }
        else if(((deltaX==1 && deltaY==1)||(deltaX==-1&&deltaY==1)&&this.color==1)){
            return true;
        }
        else if(((deltaX==1 && deltaY==-1)||(deltaX==-1&&deltaY==-1)&&this.color==0)){
            return true;
        }
        else if (deltaX != 0 ) {
            return false;
        }
        else
            return newCell.checkEmpty();
    }
    @Override
    public String getType(){
        return "pawn";
    }
    @Override
    public String toString(){
        if(this.getColor()==0){
            return "p";
        }
        else
            return "P";

    }
}
