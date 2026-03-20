package client;

import model.*;
import ui.boardDraw;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientFunctions {
    private final ServerFacade facade;
    private final String url= "http://localhost:8080";
    private boolean loggedIn = false;

    ClientFunctions(){facade = new ServerFacade(url);}

    public void runDefault(){
        Scanner scanner = new Scanner(System.in);
        String responce = "";
        System.out.println(help1());
        while(!responce.equals("quit")){
            responce = scanner.nextLine();
            if(!loggedIn) {
                String output = input1(responce);
                System.out.println(output);
            }
            else{
                String output = input2(responce);
                System.out.println(output);
            }

        }
    }

    public String input1(String req){
        String check = req.toLowerCase();
        return switch (check){
            case "log in" -> logIn();
            case "quit" -> escape();
            case "help" -> help1();
            case "register" -> register();
            case "burnt chicken" -> secrets();
            default-> zeroedOut();
        };
    }

    public String input2(String req){
        String check = req.toLowerCase();
        return switch (check){
            case "log out" -> logOut();
            case "quit" -> escape();
            case "help" -> help2();
            case "create game" -> create();
            case "list games" -> list();
            case "join game" -> join();
            case "observe" -> observe();
            case "burnt chicken" -> secrets();
            default-> zeroedOut();
        };
    }

    private String secrets(){
        return("Ahh, I see you are trying to discover secrets.  Come back at a later time.....");
    }

    private String zeroedOut(){
        return "Hmmm..... It looks like you entered a value that isn't there.";
    }

    private String escape(){
        return "\n";
    }

    private String list(){
        System.out.println("Don't know how this is going to work yet, but we'll print a list of games here.");
        return "a";
    }

    private String join(){
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
            return "A";
        }
        else{
            return "Check the values you are passing in.";
        }
    }

    private String observe(){
        Scanner log = new Scanner(System.in);
        String id = "";
        String color = "";
        System.out.println("What game do you want to watch? ");
        id = log.nextLine();
        if(id.equals("1")){
            boardDraw artist = new boardDraw();
            var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
            artist.drawTicTacToeBoard(out);
            return("success");
        }
        else{
            return"Invalid game id passed in";
        }
    }

    private String create(){
        Scanner log = new Scanner(System.in);
        String id = "";
        System.out.println("Game name: ");
        id = log.nextLine();
//        Create the game here.
        return "A";
    }

    private String logIn(){
        Scanner log = new Scanner(System.in);
        String user = "";
        String pass = "";
        System.out.println("Enter your user name: ");
        user = log.nextLine();
        System.out.println("Enter your password: ");
        pass = log.nextLine();
        UserData passIn = new UserData(user, pass, null);
        try {
            AuthData authorized = facade.logI(passIn);
            String token = authorized.authToken();
            loggedIn = true;
            System.out.println(help2());
            return "Log in successful.  You'll want this later: "+ token;
        } catch (Exception e) {
            return "log in failed";
        }
    }

    private String register() {
        Scanner log = new Scanner(System.in);
        String user = "";
        String pass = "";
        String email = "";
        System.out.println("Enter your user name: ");
        user = log.nextLine();
        System.out.println("Enter your password: ");
        pass = log.nextLine();
        System.out.println("Enter your email");
        email = log.nextLine();
        UserData passIn = new UserData(user, pass, email);
        try {
            AuthData authorized = facade.addUser(passIn);
            String token = authorized.authToken();
            loggedIn = true;
            System.out.println(help2());
            return "Registered.  You'll want this later: "+token;
        } catch (Exception e) {
            return "Registration failed.";
        }
    }

    private String logOut(){
        Scanner log = new Scanner(System.in);
        String token = "";
        System.out.println("What is your authtoken?  (The very long string you got when you logged in today.)");
        token = log.nextLine();
        try {
            facade.logO(token);
            return "logged out, you may quit the program now.";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    public String help1(){
        return """
            Optional commands for this window:
            Help: returns this menu
            Log in: If you are registered as an existing user log in with username and password credentials
            Register:  If you do not have an existing account create one
            Quit:  Leave program.
            
            What would you like to do?
            """;
    }
    public String help2(){
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
