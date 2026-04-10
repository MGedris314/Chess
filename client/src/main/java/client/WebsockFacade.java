package client;

import chess.ChessGame;
import com.google.gson.Gson;
import jakarta.websocket.*;
import websocket.commands.MoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessages;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

public class WebsockFacade extends Endpoint {
    Session session;
    Notifications notifications;
    public WebsockFacade(String url, Notifications notifications){
        try {
            url = "http://localhost:" + url;
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.notifications = notifications;
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {

                @Override
                public void onMessage(String message) {
                    System.out.println("Message recieved");
                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
                    if(notification.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
                        System.out.println("We at least get here");
                        NotificationMessages notes = new Gson().fromJson(message, NotificationMessages.class);
                        notifications.notify(notification);
                        System.out.println(notes.message);
                    }
                    if(notification.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
                        System.out.println("Maybe we get here");
                        LoadGameMessage game = new Gson().fromJson(message, LoadGameMessage.class);
                        notifications.notify(game);
                        System.out.println(game.game.game().getBoard());
                    }
                    if(notification.getServerMessageType() == ServerMessage.ServerMessageType.ERROR){
                        System.out.println("This is the point we're hitting");
                        ErrorMessage errored = new Gson().fromJson(message, ErrorMessage.class);
                        notifications.notify(errored);
                    }
                }
            });

        } catch (URISyntaxException | DeploymentException | IOException e) {
            System.out.println("Houston we have a problem");
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
    public void connect(UserGameCommand context){
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(context));
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }

    public void makeMove(MoveCommand context){
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(context));
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }

    public void leave(UserGameCommand context){
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(context));
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }

    public void resign(){}

}
