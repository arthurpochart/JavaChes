package chesslogic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Chess extends Application {
    private final HashMap<String,Player> players = new HashMap<>();
    private Player currentPlayer;
    public HashMap<String,Cell> board = new HashMap<>();
    public String[] alphabet = {"a","b","c","d","e","f","g","h"};
    public Position whiteKingPos;
    public Position blackKingPos;
    private String moveLine = "";
    private final AiEngine aiEngine= new AiEngine();
    private GridPane grid = new GridPane();

    @Override
    public void start(Stage stage) throws Exception {

        Scene mainScene = new Scene(grid,800,800);


        int count = 0;
        double s = 100; // side of rectangle
        for (int i = 0; i < 8; i++) {
            count++;
            for (int j = 0; j < 8; j++) {
                Rectangle r = new Rectangle(s, s, s, s);
                if (count % 2 == 0)
                    r.setFill(Color.BLACK);
                grid.add(r, j, i);
                count++;
            }
        }
        stage.setScene(mainScene);
        stage.show();
        this.play();

    }

    private void initialiseBoard(){
        int count = 0;
        for (String letter:alphabet){
            for (int i=1;i<9;i++){
                if(count%2==0){
                    Cell newcell = new Cell(new Position(letter,i),true,0);
                    board.put(letter+ i,newcell);
                    count++;
                }
                else{
                    Cell newcell = new Cell(new Position(letter,i),true,1);
                    board.put(letter+ i,newcell);
                    count++;
                }


            }
        }
        //Create Rooks
        board.get("a8").setPiece(0,"r");
        board.get("h8").setPiece(0,"r");
        board.get("a1").setPiece(1,"r");
        board.get("h1").setPiece(1,"r");

        //Create Knights
        board.get("b8").setPiece(0,"n");
        board.get("g8").setPiece(0,"n");
        board.get("g1").setPiece(1,"n");
        board.get("b1").setPiece(1,"n");

        //Create Bishops
        board.get("f8").setPiece(0,"b");
        board.get("c8").setPiece(0,"b");
        board.get("f1").setPiece(1,"b");
        board.get("c1").setPiece(1,"b");

        //Create Queens
        board.get("d8").setPiece(0,"q");
        board.get("d1").setPiece(1,"q");

        //Create Kings
        board.get("e8").setPiece(0,"k");
        board.get("e1").setPiece(1,"k");


        //Create pawns
        for(String letter:alphabet){
            board.get(letter+"7").setPiece(0,"p");
            board.get(letter+"2").setPiece(1,"p");
        }
    }

    public void printBoard(){
        for (int i=8;i>0;i--){
            for (String letter:alphabet){
                System.out.print(board.get(letter+ i)+" ");
            }
            System.out.println();
        }
    }

    private Position getWhiteKingPos(){
        for(Cell cell:board.values()){
            if(cell.piece.toString().equals("K")){
                return cell.position;
            }
        }
        return null;
    }

    private Position getBlackKingPos(){
        for(Cell cell:board.values()){
            if(cell.piece.toString().equals("k")){
                return cell.position;
            }
        }
        return null;
    }

    public void play(){
        System.out.println();
        System.out.println("Bienvenue sur ArthurChess, vous allez jouer contre moi!");
        System.out.println("Pour faire un coup entrez la position de depart puis celle d'arrivée comme ceci");
        System.out.println("Ex: e2 e4");
        System.out.println("Les colonnes sont designées par une lettre de a-h et les lignes par un nombre de 1-8");
        System.out.println("Pour quitter appuyer sur Enter");
        System.out.println();

        this.initialiseBoard();
        this.printBoard();
        this.createPlayers();

        boolean running = true;
        String[] move = {"",""};
        while(running){
            if(currentPlayer==players.get("white")){
                move = this.askMove();
            }
            else{
                move = aiEngine.getMove(moveLine);
            }
            if(move[0].equals("")){
                running=false;
            }
            if (isValidMove(move)){
                moveLine = moveLine+move[0]+move[1];
                this.updateBoard(move);
                this.switchPlayer();
            }
            else System.out.println("Not a valid move");
        }
    }
    private String[] askMove(){
        //a demander au joueur en cours de saisir son prochain
        //coup en spécifiant la pièce à déplacer puis l’emplacement suite au coup. Par exemple, si
        //on reprend l’état initial de l’échiquier illustré en Fig. 3, alors les coups suivants sont tous
        //valides : Nb1 Nc3, Pd1 Pd3, Pc1 Pc4
        System.out.println(currentPlayer.getName()+" make a move :");
        Scanner scan = new Scanner(System.in);

        return scan.nextLine().split(" ");
    }

    private boolean isValidMove(String[] move){
        Cell startcell = board.get(move[0]);
        Cell endcell;
        try{
            endcell = board.get(move[1]);
        }
        catch (IndexOutOfBoundsException e){
            return false;
        }
        Piece piece = startcell.piece;

        piece = startcell.getPiece();
        if (piece==null || piece.getColor()!=currentPlayer.getColor() || !clearTrajectory(startcell,endcell))
            return false;

        return piece.isValidMove(endcell.position,board);

    }

    private boolean clearTrajectory(Cell startcell, Cell endcell){
        Piece piece = startcell.piece;
        int deltaX = endcell.position.getX()-startcell.position.getX();
        int deltaY = endcell.position.getY()-startcell.position.getY();

        Position nextPosition = new Position(startcell.position.column,startcell.position.row);
        if(piece.toString().equals("b")||piece.toString().equals("B")){
            for(int i=1;i<=abs(deltaX);i++){
                nextPosition.setX(startcell.position.getX()+(deltaX/abs(deltaX))*i);
                nextPosition.setY(startcell.position.getY()+(deltaY/abs(deltaY))*i);

                if(!board.get(nextPosition.toString()).checkEmpty()){
                    if (board.get(nextPosition.toString())!=endcell||endcell.piece.getColor()== piece.getColor())
                    return false;
                }
            }
        }
        else if(piece.toString().equals("r")||piece.toString().equals("R")){
            //Going y direction
            if (horizontalClearance(startcell, endcell, piece, deltaX, deltaY, nextPosition)) return false;
        }
        else if(piece.toString().equals("q")||piece.toString().equals("Q")){
            if(abs(deltaX)==abs(deltaY)){
                for(int i=1;i<=abs(deltaX);i++){
                    nextPosition.setX(startcell.position.getX()+(deltaX/abs(deltaX))*i);
                    nextPosition.setY(startcell.position.getY()+(deltaY/abs(deltaY))*i);

                    if(!board.get(nextPosition.toString()).checkEmpty()){
                        if (board.get(nextPosition.toString())!=endcell||endcell.piece.getColor()== piece.getColor())
                            return false;
                    }
                }
            }
            else{
                if (horizontalClearance(startcell, endcell, piece, deltaX, deltaY, nextPosition)) return false;
            }
        }
        return true;
    }

    private boolean horizontalClearance(Cell startcell, Cell endcell, Piece piece, int deltaX, int deltaY, Position nextPosition) {
        if(deltaX==0){
            for(int i=1;i<=abs(deltaY);i++){
                nextPosition.setY(startcell.position.getY()+(deltaY/abs(deltaY))*i);
                if(!board.get(nextPosition.toString()).checkEmpty()){
                    if (board.get(nextPosition.toString())!=endcell||endcell.piece.getColor()== piece.getColor()) {
                        return true;
                    }
                }
            }
        }
        else{
            for(int i=1;i<=abs(deltaX);i++){
                nextPosition.setX(startcell.position.getX()+(deltaX/abs(deltaX))*i);
                if(!board.get(nextPosition.toString()).checkEmpty()){
                    if (board.get(nextPosition.toString())!=endcell||endcell.piece.getColor()== piece.getColor()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCheck(){
        Position kingPosition;
        if(currentPlayer.getColor()==0){
            kingPosition=this.getWhiteKingPos();
        }
        else{
            kingPosition=this.getBlackKingPos();
        }
        Cell kingcell = board.get(kingPosition);
        for(int i=1;i<=8;i++){
            assert kingPosition != null;
            if(board.get(kingPosition.column+ i
            ).piece.getType().equals("queen")||board.get(kingPosition.column+Integer.toString(i)).piece.getType().equals("rook")){
                return true;
            }
        }
        for(String letter:alphabet){
            if(board.get(letter+ kingPosition.row).piece.getType().equals("queen")||board.get(letter+ kingPosition.row).piece.getType().equals("rook")){
                return true;
            }
        }
        ArrayList<Position> knightPositions = new ArrayList<>();
        for(int i=0;i<8;i++)
            knightPositions.add(kingPosition);
        knightPositions.get(0).setX(kingPosition.getX()+2);
        knightPositions.get(0).setY(kingPosition.getX()+1);

        knightPositions.get(1).setX(kingPosition.getX()+2);
        knightPositions.get(1).setY(kingPosition.getX()-1);

        knightPositions.get(2).setX(kingPosition.getX()-2);
        knightPositions.get(2).setY(kingPosition.getX()+1);

        knightPositions.get(3).setX(kingPosition.getX()-2);
        knightPositions.get(3).setY(kingPosition.getX()-1);

        knightPositions.get(4).setX(kingPosition.getX()+1);
        knightPositions.get(4).setY(kingPosition.getX()+2);

        knightPositions.get(5).setX(kingPosition.getX()-1);
        knightPositions.get(5).setY(kingPosition.getX()+2);

        knightPositions.get(6).setX(kingPosition.getX()+1);
        knightPositions.get(6).setY(kingPosition.getX()-2);

        knightPositions.get(7).setX(kingPosition.getX()-1);
        knightPositions.get(7).setX(kingPosition.getX()-2);

        for(Position knightPosition:knightPositions){
            if(board.get(knightPosition.toString()).piece.getType().equals("knight")){
                return true;
            }
        }


        return false;
    }

    private void updateBoard(String[] move){
        Cell startcell = board.get(move[0]);
        Cell endcell = board.get(move[1]);

        startcell.movePiece(endcell);
        this.printBoard();
        System.out.println();
    }

    private void createPlayers(){
        players.put("white",new Player("white",1));
        players.put("black",new Player("black",0));
        currentPlayer = players.get("white");
    }
    private void switchPlayer(){
        //change valeur de currentplayer
        if (currentPlayer == players.get("white")){
            currentPlayer = players.get("black");
        }
        else
            currentPlayer = players.get("white");
    }

    public  static void main(String[] args){
        Chess myGame = new Chess();
        myGame.play();
        //launch();


    }


}
