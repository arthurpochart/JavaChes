package chesslogic;

import javafx.scene.layout.StackPane;

public class Cell extends StackPane {
    public Position position;
    boolean empty;
    int color;
    Piece piece;

    public Cell(Position position, boolean isEmpty, int color) {
        this.position = position;
        this.empty = isEmpty;
    }
    @Override
    public String toString(){
        //return this.position.toString();
        if (empty){
            return ".";
        }
        else
            return this.piece.toString();

    }
    public boolean checkEmpty(){
        if (this.piece == null)
            this.empty = true;
        else
            this.empty = false;
        return this.empty;
    }
    public void setPiece(int color, String  type){
        switch (type) {
            case "r" -> {
                piece = new Rook(new Position(this.position.column,this.position.row), color);
                empty = false;
            }
            case "n" -> {
                piece = new Knight(new Position(this.position.column,this.position.row), color);
                empty = false;
            }
            case "b" -> {
                piece = new Bishop(new Position(this.position.column,this.position.row), color);
                empty = false;
            }
            case "k" -> {
                piece = new King(new Position(this.position.column,this.position.row), color);
                empty = false;
            }
            case "q" -> {
                piece = new Queen(new Position(this.position.column,this.position.row), color);
                empty = false;
            }
            case "p" -> {
                piece = new Pawn(new Position(this.position.column,this.position.row), color);
                empty = false;
            }
        }
    }
    public Piece getPiece(){
        return this.piece;
    }

    public void movePiece(Cell newCell){
        if (!this.checkEmpty())

            this.piece.setPosition(newCell.position.column,newCell.position.row);
            newCell.piece = this.piece;
            newCell.piece.hasMoved = true;
            this.piece = null;

            this.checkEmpty();
            newCell.checkEmpty();



    }

}
