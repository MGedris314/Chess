package client;

import com.google.gson.Gson;
import jakarta.websocket.*;
import websocket.commands.UserGameCommand;
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
                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
                    notifications.notify(notification);
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

    public void makeMove(){}

    public void leave(){}

    public void resign(){}

}
