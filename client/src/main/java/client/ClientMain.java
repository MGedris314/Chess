package client;

import chess.*;

public class ClientMain {

    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        runDefault();
        System.out.println("Goodbye");
    }

    public static void runDefault(){
//        System.out.println("Hey this works");
        int state = 0;
        System.out.println(help(state));
    }

    public static void runLevel2(){}

    public static String help(int state){
        if (state == 0){
            return """
                    Optional commands for this window:
                    Help: returns this menu
                    Log in: If you are registered as an existing user log in with username and password credentials
                    Register:  If you do not have an existing account create one
                    Quit:  Leave program.
                    """;
        }
        else{
            return "Work in progress.";
        }
    }


}
