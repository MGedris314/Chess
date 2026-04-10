package client;

import chess.*;
import model.*;
import ui.BoardDraw;
import websocket.commands.MoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.Scanner;

public class ClientFunctions implements Notifications {
    private final ServerFacade facade;
    private final WebsockFacade websock;
    public ChessGame gamePlay;
    private boolean loggedIn = false;
    public String aToken = "";
    private int gameId;
    private boolean joined = false;
    private boolean isWhite = false;
    private boolean observe = false;
    ClientFunctions(){
        facade = new ServerFacade(8080);
        websock = new WebsockFacade("8080", this);
    }
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
            if(joined){
                if (!observe) {
                    String output = input3(responce);
                    System.out.println(output);
                }
                else{
                    String output = input4(responce);
                    System.out.println(output);
                }
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
            case "draw" -> doodle();
            case "burnt chicken" -> secrets();
            default-> zeroedOut();
        };
    }

    public String input2(String req){
        String check = req.toLowerCase();
        return String.valueOf(switch (check){
            case "log out" -> logOut();
            case "quit" -> escape2();
            case "help" -> help2();
            case "create game" -> create();
            case "list games" -> list();
            case "join game" -> join();
            case "observe" -> observe();
            case "burnt chicken" -> secrets();
            default-> zeroedOut();
        });
    }

    public String input3(String req){
        String check = req.toLowerCase();
        return String.valueOf(switch (check){
            case "redraw" -> doodle();
            case "leave" -> leave();
            case "help" -> help3();
            case "make move" -> move();
            case "resign" -> resign();
            case "legal moves" -> legal();
            default-> zeroedOut();
        });
    }

    public String input4(String req){
        String check = req.toLowerCase();
        return String.valueOf(switch (check){
            case "redraw" -> doodle();
            case "leave" -> escape2();
            case "help" -> help4();
            case "legal moves" -> legal();
            default-> zeroedOut();
        });
    }
    private String resign(){
        Scanner log = new Scanner(System.in);
        String hold = "";
        System.out.println("Are you sure you wish to resign? ");
        hold = log.nextLine();
        if(hold.equals("yes")) {
            joined = false;
            UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.RESIGN, aToken, gameId);
            websock.leave(command);
            return "";
        }
        else{
            return "Resuming game.";
        }
    }
    private String leave(){
        joined = false;
        UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.LEAVE, aToken, gameId);
        websock.leave(command);
        return "";
    }
    private int rowFind(String row){
        switch (row){
            case "a":
                return 1;
            case "b":
                return 2;
            case "c":
                return 3;
            case "d":
                return 4;
            case "e":
                return 5;
            case "f":
                return 6;
            case "g":
                return 7;
            case "h":
                return 8;
        }
        return -1;
    }
    private ChessPiece.PieceType prmote(String part){
        String piece = part.toLowerCase();
        switch (piece){
            case "queen":
                return ChessPiece.PieceType.QUEEN;
            case "rook":
                return ChessPiece.PieceType.ROOK;
            case "bishop":
                return ChessPiece.PieceType.BISHOP;
            case "knight":
                return ChessPiece.PieceType.KNIGHT;
        }
        return null;
    }
    private String move(){
        Scanner log = new Scanner(System.in);
        String rS = "";
        String cS = "";
        String rE = "";
        String cE = "";
        System.out.println("Starting row of the piece");
        rS = log.nextLine();
        System.out.println("Starting column of the piece");
        cS = log.nextLine();
        System.out.println("Ending row of the piece");
        rE = log.nextLine();
        System.out.println("Ending column of the piece");
        cE = log.nextLine();
        ChessBoard board = gamePlay.getBoard();
        int rowS = rowFind(rS);
        int colS;
        int rowE = rowFind(rE);
        int colE;
        try{
            colS = Integer.parseInt(cS);
            colE = Integer.parseInt(cE);
        } catch (NumberFormatException e) {
            return "Invalid row or collumn space entered.";
        }
        ChessPosition start = new ChessPosition(colS, rowS);
        ChessPosition end = new ChessPosition(colE, rowE);
        ChessPiece mover = board.getPiece(start);
        if (mover.getPieceType() == ChessPiece.PieceType.PAWN){
            if(colE == 8 || colE == 1){
                String promotion = "";
                System.out.println("What piece would you like to promote your pawn to?");
                promotion = log.nextLine();
                ChessPiece.PieceType promoter = prmote(promotion);
                ChessMove movement = new ChessMove(start, end, promoter);
                MoveCommand move = new MoveCommand(UserGameCommand.CommandType.MAKE_MOVE, aToken, gameId, movement);
                websock.makeMove(move);
            }
            else{
                ChessMove movement = new ChessMove(start, end, null);
                MoveCommand move = new MoveCommand(UserGameCommand.CommandType.MAKE_MOVE, aToken, gameId, movement);
                websock.makeMove(move);
                return "We got to this point";
            }
        }
        else{
            ChessMove movements = new ChessMove(start, end, null);
            MoveCommand move = new MoveCommand(UserGameCommand.CommandType.MAKE_MOVE, aToken, gameId, movements);
            websock.makeMove(move);
            return "We got to this point";
        }
        return "This doesn't work yet, but we'll figure that out later.";
    }

    private String legal(){
        Scanner log = new Scanner(System.in);
        String rS = "";
        String cS = "";
        System.out.println("Starting row of the piece");
        rS = log.nextLine();
        System.out.println("Starting column of the piece");
        cS = log.nextLine();
        int rowS = rowFind(rS);
        int colS;
        try {
            colS = Integer.parseInt(cS);
        } catch (NumberFormatException e) {
            return"format columns as numbers, not letters.";
        }
        ChessBoard board = gamePlay.getBoard();
        ChessPosition start = new ChessPosition(colS, rowS);
        ChessPiece finder = board.getPiece(start);
        finder.pieceMoves(board, start);
        ArrayList<ChessMove> hold = new ArrayList<ChessMove>();
        hold.addAll(finder.pieceMoves(board, start));
        ArrayList<ChessPosition> ends = new ArrayList<ChessPosition>();
        for(int x = 0; x<hold.size(); x++){
            ChessPosition end = hold.get(x).getEndPosition();
            if(isWhite){
                ChessPosition alter = new ChessPosition(9-end.getRow(), end.getColumn());
                ends.add(alter);
            }
            else {
                ends.add(end);
            }
        }
        ends.add(start);
        BoardDraw artist = new BoardDraw();
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ChessBoard tester = gamePlay.getBoard();
        artist.doodle_with_highlight(out,tester,isWhite,ends);
        return " ";
    }
    private String secrets(){
        return("Ahh, I see you are trying to discover secrets.  Come back at a later time.....");
    }
    private String zeroedOut(){
        return "Hmmm..... It looks like you entered a value that isn't availabe on this menu.";
    }
    private String escape(){
        return "\n";
    }
    private String escape2(){
        joined = false;
        return "\n";
    }
    private String list(){
        try {
            GameRetrun check = facade.listGame(aToken);
            ArrayList setup = new ArrayList(check.games());
            System.out.println("Game id, game name, white player, black player");
            for(int x = 0; x<check.games().size(); x++){
                PublicGame hold = (PublicGame) setup.get(x);
                int y = x +1;
                System.out.println(y + " "+hold.gameName() + " white player: "+hold.whiteUsername() + "black player: "+hold.blackUsername());
            }
            return "";
        }
        catch (Exception e) {
            return null;
        }
    }
    private String join(){
        Scanner log = new Scanner(System.in);
        String hold = "";
        String color = "";
        System.out.println("What game do you want to join? ");
        hold = log.nextLine();
        System.out.println("Which color would you like to play as? ");
        color = log.nextLine();
        int id;
        try {
            id = Integer.parseInt(hold);
        } catch (NumberFormatException e) {
            return "Pleas pass the id in as number not a string.";
        }
        gameId = id;
        JoinGameData joiner = new JoinGameData(color, id);
        try{
            facade.joinGame(joiner, aToken);
            joined = true;
            if(color.equalsIgnoreCase("white")){
                isWhite = true;
            }
            else{
                isWhite = false;
            }
            UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.CONNECT, aToken, id);
            websock.connect(command);
            return "Joined game";
        } catch (Exception e) {
            joined = false;
            return e.getMessage();
        }
    }
    private String observe(){
        Scanner log = new Scanner(System.in);
        String hold = "";
        String color = "";
        System.out.println("What game do you want to join? ");
        hold = log.nextLine();
        System.out.println("Which color would you like to view as? ");
        color = log.nextLine();
        int id;
        try {
            id = Integer.parseInt(hold);
        } catch (NumberFormatException e) {
            return "Pleas pass the id in as number not a string.";
        }
        gameId = id;
        JoinGameData joiner = new JoinGameData(color, id);
        try{
//            facade.joinGame(joiner, aToken);
            joined = true;
            if(color.equalsIgnoreCase("white")){
                isWhite = true;
            }
            else{
                isWhite = false;
            }
            UserGameCommand command = new UserGameCommand(UserGameCommand.CommandType.CONNECT, aToken, id);
            websock.connect(command);
            observe = true;
            return "Joined game";
        } catch (Exception e) {
            joined = false;
            return e.getMessage();
        }
    }
    private String create(){
        Scanner log = new Scanner(System.in);
        String val = "";
        System.out.println("Game name: ");
        val = log.nextLine();
        GameName name = new GameName(val);
        try {
            facade.createGame(name, aToken);
            return "Game successfully created.  Use command list games to see available games.";
        }
        catch (Exception e){
            return e.getMessage();
        }
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
            aToken = authorized.authToken();
            loggedIn = true;
            System.out.println(help2());
            return "Log in successful.";
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
            aToken = authorized.authToken();
            loggedIn = true;
            System.out.println(help2());
            return "Registered.";
        } catch (Exception e) {
            return "Registration failed.";
        }
    }
    private String logOut(){
        Scanner log = new Scanner(System.in);
        String test = "";
        System.out.println("Are you sure you want to log out?");
        test = log.nextLine();
        try {
            facade.logO(aToken);
            loggedIn = false;
            return "logged out, you may quit the program now.";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
    private String draw(){
        BoardDraw artist = new BoardDraw();
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ChessBoard tester = gamePlay.getBoard();
        if(tester.getPiece(new ChessPosition(3,2)) != null){
            System.out.println("there should be something here.");
        }
        if(isWhite){
            artist.drawTicTacToeBoard(out, "b", gamePlay);
            artist.setWhite(out);
        }
        else{
            artist.drawTicTacToeBoard(out, "w", gamePlay);
            artist.setWhite(out);
        }
        return "";
    }

    private String doodle(){
        BoardDraw artist = new BoardDraw();
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        ChessBoard tester = gamePlay.getBoard();
        artist.doodle(out, tester, isWhite);
        return "";
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
            List games:  Returns all games on the system.
            Join game:  Join an available game.
            Observe:  Watch a game (board orientation will be from white players perspective).
            What would you like to do?
            """;
    }
    public String help3(){
        return """
            Optional commands for this window:
            Help: Returns this menu.
            Leave:  Leave this game.
            Redraw board:  Redraws the board state
            Make move:  Select a piece to move and move it
            Resign:  Surrender the game:
            Legal moves:  Select a piece and view all legal options to move that piece
            What would you like to do?
            """;
    }
    public String help4(){
        return """
            Optional commands for this window:
            Help: Returns this menu.
            Leave:  Leave this game.
            Redraw board:  Redraws the board state
            Legal moves:  Select a piece and view all legal options to move that piece
            What would you like to do?
            """;
    }
    @Override
    public void notify(ServerMessage message) {
        System.out.println(message.getServerMessageType());
        if(message.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME){
            LoadGameMessage load = (LoadGameMessage) message;
            gamePlay = load.returning().game();
            doodle();
        }
    }
}
