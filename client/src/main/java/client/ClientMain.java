package client;

import chess.*;
import ui.boardDraw;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        runDefault();
        System.out.println("Goodbye");
    }

    public static void runDefault(){
        Scanner scanner = new Scanner(System.in);
        String responce = "";
        System.out.println(help1());
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
            case "help" -> help1();
            case "register" -> register();
            case "burnt chicken" -> secrets();
            default-> zeroedOut();
        }
    }

    public static void input2(String req){
        String check = req.toLowerCase();
        switch (check){
            case "log out" -> logIn();
            case "quit" -> escape();
            case "help" -> help2();
            case "create game" -> create();
            case "list games" -> list();
            case "join game" -> join();
            case "observe" -> observe();
            case "burnt chicken" -> secrets();
            default-> zeroedOut();
        }
    }

    private static void secrets(){
        System.out.println("Ahh, I see you are trying to discover secrets.  Come back at a later time.....");
    }

    private static String zeroedOut(){
        System.out.println("Hmmm..... It looks like you entered a value that isn't there.");
        return "a";
    }

    private static int escape(){
        return 0;
    }

    private static String list(){
        System.out.println("Don't know how this is going to work yet, but we'll print a list of games here.");
        return "a";
    }

    private static void join(){
        Scanner log = new Scanner(System.in);
        String id = "";
        String color = "";
        System.out.println("What game do you want to join? ");
        id = log.nextLine();
        System.out.println("Which color would you like to play as? ");
        color = log.nextLine();
        if(id.equals("1") && color.equals("white")){
            System.out.println("Joining as the white team");
            boardDraw artist = new boardDraw();
//            Talk to the TA's about this one.
        }
        else{
            System.out.println("Check the values you are passing in.");
        }
    }

    private static void observe(){
        Scanner log = new Scanner(System.in);
        String id = "";
        String color = "";
        System.out.println("What game do you want to watch? ");
        id = log.nextLine();
        if(id.equals("1")){
            boardDraw artist = new boardDraw();
        }
        else{
            System.out.println("Invalid game id passed in");
        }
    }

    private static void create(){
        Scanner log = new Scanner(System.in);
        String id = "";
        System.out.println("Game name: ");
        id = log.nextLine();
//        Create the game here.
    }

    public static void runLevel2(){
        System.out.println("Inside level 2");
        System.out.println(help2());
        Scanner scanner = new Scanner(System.in);
        String responce = "";
        while(!responce.equals("quit")){
            responce = scanner.nextLine();
            input2(responce);
        }
        System.out.println(help1());
    }

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
            runLevel2();
            return "Logged in";
        }
        else{
            System.out.println("Incorrect log in credentials.");
            return "Invalid log inn credentials";
        }
    }

    private static String register() {
        Scanner log = new Scanner(System.in);
        String user = "";
        String pass = "";
        System.out.println("Enter your user name: ");
        user = log.nextLine();
        System.out.println("Enter your password: ");
        pass = log.nextLine();
        runLevel2();
        return "Registered";
    }

    public static String help1(){
        return """
            Optional commands for this window:
            Help: returns this menu
            Log in: If you are registered as an existing user log in with username and password credentials
            Register:  If you do not have an existing account create one
            Quit:  Leave program.
            
            What would you like to do?
            """;
        }
    public static String help2(){
        return """
            Optional commands for this window:
            Help: Returns this menu.
            Log out: Log out and return to previous menu.
            Create game:  Create a new game of chess.
            List game:  Returns all games on the system.
            Join game:  Join an available game.
            Observe:  Watch a game (board orientation will be from white players perspective).
            What would you like to do?
            """;
    }
    }
