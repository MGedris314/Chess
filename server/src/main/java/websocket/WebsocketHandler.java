package websocket;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.SQLDataAccess;
import exception.UserExceptions;
import handler.UserHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import org.eclipse.jetty.websocket.api.Session;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.websocket.core.internal.WebSocketConnection;
import org.jetbrains.annotations.NotNull;
import service.GameService;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessages;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.HashMap;

public class WebsocketHandler implements WsConnectHandler, WsMessageHandler {
    HashMap<String, Integer> sessionInfo = new HashMap<>();
    HashMap<String, Session> sessions = new HashMap<>();
    HashMap<Integer, Boolean> gameLog = new HashMap<>();
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
    public void move(WsMessageContext context, UserGameCommand command){
        int id = command.getGameID();
        DataAccess access = new SQLDataAccess();
        GameService service = new GameService(access);
        try {
            AuthData name = service.findUser(command.getAuthToken());
            GameData trial = service.observe(id);
            if(trial == null || name == null){
                System.out.println("We need to error out here");
                ErrorMessage errored = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "ERROR");
                String msg = new Gson().toJson(errored);
                sender(null, msg, context, 0,-1);
            }else {
                System.out.println("We'll fix this later");
            }
        } catch (UserExceptions e) {
            System.out.println("Errored");
        } catch (IOException e) {
            System.out.println("Errored again.");
        }
    }
    public void leave(){}
    public void resign(WsMessageContext context, UserGameCommand command){
        int id = command.getGameID();
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
    }

    public void sender (String exclude, String notification, WsMessageContext context, int id, int e) throws IOException {
        if(e == -1){
            context.session.getRemote().sendString(notification);
        }
        else {
            for (String c : sessionInfo.keySet()) {
                if (!c.equals(exclude) && sessionInfo.get(c) == id) {
                    Session neededSession = sessions.get(c);
                    neededSession.getRemote().sendString(notification);
                }
            }
        }
    }

}
