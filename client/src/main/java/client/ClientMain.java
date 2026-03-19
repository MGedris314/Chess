package client;

import chess.*;


import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        String responce = "";
        System.out.println(help(state));
        while(!responce.equals("quit")){
            responce = scanner.nextLine();
            input1(responce);
        }
    }

    public static void input1(String req){
        String check = req.toLowerCase();
        switch (check){
            case "log in" -> logIn();
            case "quit" -> escape();
            default-> zeroedOut();
        }
    }

    private static String zeroedOut(){
        System.out.println("Hmmm..... It looks like you entered a value that isn't there.");
        return "a";
    }

    private static int escape(){
        return 0;
    }

    public static void runLevel2(){}

    private static String logIn(){
        Scanner log = new Scanner(System.in);
        String user = "";
        String pass = "";
        System.out.println("Enter your user name: ");
        user = log.nextLine();
        System.out.println("Enter your password: ");
        pass = log.nextLine();
        if(user.equals("Steve") && pass.equals("a")){
            System.out.println("Logged in");
            return "Logged in";
        }
        else{
            System.out.println("Incorrect log in credentials.");
            return "Invalid log inn credentials";
        }
    }


    public static String help(int state){
        if (state == 0){
            return """
                    Optional commands for this window:
                    Help: returns this menu
                    Log in: If you are registered as an existing user log in with username and password credentials
                    Register:  If you do not have an existing account create one
                    Quit:  Leave program.
                    
                    What would you like to do?
                    """;
        }
        else{
            return "Work in progress.";
        }
    }


}
