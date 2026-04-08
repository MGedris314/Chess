package client;


import websocket.messages.ServerMessage;

public interface Notifications {
    void notify(ServerMessage message);
}
