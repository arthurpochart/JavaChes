package chesslogic;


public class Position
{
    String  column;
    int row;
    public Position(String  column,int row){
        this.column = column;
        this.row = row;
    }
    @Override
    public String toString(){

        return this.column+String.valueOf(this.row);
    }
    public int getX(){
        return switch (this.column) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            case "e" -> 4;
            case "f" -> 5;
            case "g" -> 6;
            case "h" -> 7;

            default -> throw new IllegalStateException("Unexpected value: " + column);
        };
    }
    public int getY(){
        return this.row-1;
    }
    public void setX(int x){
        if(x==0){
            this.column = "a";
        }
        else if (x==1){
            this.column="b";
        }
        else if (x==2){
            this.column="c";
        }
        else if (x==3){
            this.column="d";
        }
        else if (x==4){
            this.column="e";
        }
        else if (x==5){
            this.column="f";
        }
        else if (x==6){
            this.column="g";
        }
        else if (x==7){
            this.column="h";
        }


    }

    public void setY(int y){
        this.row = y+1;
    }
}

