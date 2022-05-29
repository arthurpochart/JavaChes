package chesslogic;

public class Player {
    private String name;
    private int color;
    public Player(String name,int color){
        this.name = name;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
