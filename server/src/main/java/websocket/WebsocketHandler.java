package websocket;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.SQLDataAccess;
import exception.UserExceptions;
import handler.UserHandler;
import io.javalin.websocket.*;
import org.eclipse.jetty.websocket.api.Session;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.websocket.core.internal.WebSocketConnection;
import org.jetbrains.annotations.NotNull;
import service.GameService;
import websocket.commands.MoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessages;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.HashMap;

public class WebsocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {
    HashMap<String, Integer> sessionInfo = new HashMap<>();
    HashMap<String, Session> sessions = new HashMap<>();
    HashMap<Integer, Boolean> gameLog = new HashMap<>();
    HashMap<String, Integer> players = new HashMap<>();
//  If the boolean is true in game log, the game is open, if it's false the game is closed(finished).

    @Override
    public void handleConnect(@NotNull WsConnectContext wsConnectContext) throws Exception {
        System.out.println("Connection established");
    }


    @Override
    public void handleMessage(@NotNull WsMessageContext wsMessageContext) throws Exception {
        UserGameCommand command = new Gson().fromJson(wsMessageContext.message(), UserGameCommand.class);
        switch (command.getCommandType()){
            case CONNECT -> connect(wsMessageContext, command);
            case MAKE_MOVE -> move(wsMessageContext, command);
            case LEAVE -> leave();
            case RESIGN -> resign(wsMessageContext, command);
        }
    }



    public int connect(WsMessageContext context, UserGameCommand command){
        int id = command.getGameID();
        DataAccess access = new SQLDataAccess();
        GameService service = new GameService(access);
        try{
            AuthData name = service.findUser(command.getAuthToken());
            GameData trial = service.observe(id);
            if(trial == null || name == null){
                System.out.println("We need to error out here");
                ErrorMessage errored = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "ERROR");
                String msg = new Gson().toJson(errored);
                sender(null, msg, context, 0,-1);
                return -1;
            }else {
                if(trial.blackUsername() != null){
                    players.put(trial.blackUsername(), id);
                }
                if(trial.whiteUsername() != null){
                    players.put(trial.whiteUsername(), id);
                }
                LoadGameMessage load = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, trial);
                String serialzed = new Gson().toJson(load);
                context.session.getRemote().sendString(serialzed);
                sessionInfo.put(name.userName(), id);
                sessions.put(name.userName(), context.session);
                gameLog.put(id, true);
                NotificationMessages note = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, "Has joined the game");
                String msg = new Gson().toJson(note);
                sender(name.userName(), msg, context, id,0);
                return 0;
            }
        } catch (UserExceptions e) {
            return 1;
        } catch (IOException e) {
            return 2;
        }
    }

    public int move(WsMessageContext context, UserGameCommand command){
        int id = command.getGameID();
        DataAccess access = new SQLDataAccess();
        GameService service = new GameService(access);
//      Everythig bellow is to make sure the person is authenticated to make a move.
        try {
            AuthData name = service.findUser(command.getAuthToken());
            GameData trial = service.observe(id);
            if(trial == null || name == null ||gameLog.get(id) == false){
                System.out.println("We need to error out here");
                ErrorMessage errored = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "ERROR");
                String msg = new Gson().toJson(errored);
                sender(null, msg, context, 0,-1);
                return -1;
            }else {
                System.out.println("We'll fix this later");
            }
            try {
                if(players.containsKey(name.userName())) {
                    System.out.println("Hit the if");
                }
                else {
                    ErrorMessage errored = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "The game is over, why are you resigning?");
                    String msg = new Gson().toJson(errored);
                    try {
                        sender(null, msg, context, id, -1);
                    } catch (IOException e) {
                        System.out.println("Something has gone wrong sending the message");
                    }
                    return -1;
                }
            } catch (NullPointerException e) {
                System.out.println("How did we get here");
            }

            if(trial == null){
                return -1;
            }
            ChessGame game = trial.game();
            MoveCommand commander = new Gson().fromJson(context.message(), MoveCommand.class);
            ChessMove move = commander.move;
            if(game.getTeamTurn() == ChessGame.TeamColor.WHITE){
                if(!name.userName().equals(trial.whiteUsername())){
                    ErrorMessage errored = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "The game is over, why are you resigning?");
                    String msg = new Gson().toJson(errored);
                    try {
                        sender(null, msg, context, id, -1);
                    } catch (IOException e2) {
                        System.out.println("Something has gone wrong sending the message");
                    }
                    return -1;
                }
            }
            if(game.getTeamTurn() == ChessGame.TeamColor.BLACK){
                if(!name.userName().equals(trial.blackUsername())){
                    ErrorMessage errored = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "The game is over, why are you resigning?");
                    String msg = new Gson().toJson(errored);
                    try {
                        sender(null, msg, context, id, -1);
                    } catch (IOException e2) {
                        System.out.println("Something has gone wrong sending the message");
                    }
                    return -1;
                }
            }
            try{
                game.makeMove(move);
                LoadGameMessage note = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, trial);
                String msg = new Gson().toJson(note);
                sender(name.userName(), msg, context, id,2);
            } catch (InvalidMoveException e) {
                ErrorMessage errored = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "The game is over, why are you resigning?");
                String msg = new Gson().toJson(errored);
                try {
                    sender(null, msg, context, id, -1);
                } catch (IOException e2) {
                    System.out.println("Something has gone wrong sending the message");
                }
                return -1;
            }

        } catch (UserExceptions e) {
            System.out.println("Errored");
        } catch (IOException e) {
            System.out.println("Errored again.");
        }
        return 0;
    }

    public void leave(){}

    public int resign(WsMessageContext context, UserGameCommand command){
        int id = command.getGameID();
        DataAccess access = new SQLDataAccess();
        GameService service = new GameService(access);
        try {
            AuthData name = service.findUser(command.getAuthToken());
            if(players.containsKey(name.userName())){
                System.out.println("Hit the if");
            }
            else {
                ErrorMessage errored = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "The game is over, why are you resigning?");
                String msg = new Gson().toJson(errored);
                try {
                    sender(null, msg, context, id, -1);
                } catch (IOException e) {
                    System.out.println("Something has gone wrong sending the message");
                }
                return -1;
            }
        } catch (UserExceptions e) {
            System.out.println("How did we get here");
        }
        if(gameLog.get(id) == true){
            gameLog.replace(id, true, false);
            NotificationMessages note = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, "has resigned");
            String msg = new Gson().toJson(note);
            try{
                sender(null, msg, context, id, 0);
            }
            catch (IOException e){
                System.out.println("Something has gone wrong sending the message");
            }
        }
        else{
            ErrorMessage errored = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "The game is over, why are you resigning?");
            String msg = new Gson().toJson(errored);try{
                sender(null, msg, context, id, -1);
            }
            catch (IOException e){
                System.out.println("Something has gone wrong sending the message");
            }
        }
        return 0;
    }

    public void sender (String exclude, String notification, WsMessageContext context, int id, int e) throws IOException {
        if(e == -1){
            context.session.getRemote().sendString(notification);
        } else if (e == 2) {
            for (String c : sessionInfo.keySet()) {
                if (sessionInfo.get(c) == id) {
                    Session neededSession = sessions.get(c);
                    neededSession.getRemote().sendString(notification);
                    System.out.println("Sent a message in 2 " + c);
                }
            }
        } else {
            for (String c : sessionInfo.keySet()) {
                if (!c.equals(exclude) && sessionInfo.get(c) == id) {
                    Session neededSession = sessions.get(c);
                    neededSession.getRemote().sendString(notification);
                    System.out.println("Sent a message " + c);
                }
            }
        }
    }

    @Override
    public void handleClose(@NotNull WsCloseContext wsCloseContext) throws Exception {
        sessions.clear();
        sessionInfo.clear();
        gameLog.clear();
        players.clear();
    }
}
