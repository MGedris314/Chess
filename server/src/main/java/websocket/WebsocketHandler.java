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
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessages;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.HashMap;

public class WebsocketHandler implements WsConnectHandler, WsMessageHandler {
    HashMap<String, Integer> sessionInfo = new HashMap<>();
    HashMap<String, Session> sessions = new HashMap<>();

    @Override
    public void handleConnect(@NotNull WsConnectContext wsConnectContext) throws Exception {
        System.out.println("Connection established");
    }


    @Override
    public void handleMessage(@NotNull WsMessageContext wsMessageContext) throws Exception {
        UserGameCommand command = new Gson().fromJson(wsMessageContext.message(), UserGameCommand.class);
        switch (command.getCommandType()){
            case CONNECT -> connect(wsMessageContext, command);
            case MAKE_MOVE -> move();
            case LEAVE -> leave();
            case RESIGN -> resign();
        }
    }
    public int connect(WsMessageContext context, UserGameCommand command){
        int id = command.getGameID();
        DataAccess access = new SQLDataAccess();
        GameService service = new GameService(access);
        try{
            GameData trial = service.observe(id);
            AuthData name = service.findUser(command.getAuthToken());
            LoadGameMessage load = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, trial);
            String serialzed = new Gson().toJson(load);
            context.session.getRemote().sendString(serialzed);
            sessionInfo.put(name.userName(), id);
            sessions.put(name.userName(), context.session);
            NotificationMessages note = new NotificationMessages(ServerMessage.ServerMessageType.NOTIFICATION, "Has joined the game");
            String msg = new Gson().toJson(note);
            sender(name.userName(), msg, context, id);
            return 0;
        } catch (UserExceptions e) {
            return 1;
        } catch (IOException e) {
            return 2;
        }
    }
    public void move(){}
    public void leave(){}
    public void resign(){}

    public void sender (String exclude, String notification, WsMessageContext context, int id) throws IOException {
        for (String c : sessionInfo.keySet()){
            if(!c.equals(exclude) && sessionInfo.get(c) == id){
                Session neededSession = sessions.get(c);
                neededSession.getRemote().sendString(notification);
//                Note to self add an additional data structure to find the sessions.
            }
        }
    }

}
