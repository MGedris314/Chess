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
import model.GameData;
import model.UserData;
import org.jetbrains.annotations.NotNull;
import service.GameService;
import websocket.commands.UserGameCommand;
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;

public class WebsocketHandler implements WsConnectHandler, WsMessageHandler {


    @Override
    public void handleConnect(@NotNull WsConnectContext wsConnectContext) throws Exception {
        System.out.println("Connection estabilished");
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
            LoadGameMessage load = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, trial);
            String serialzed = new Gson().toJson(load);
            context.session.getRemote().sendString(serialzed);
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

}
