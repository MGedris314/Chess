package websocket.messages;

public class NotificationMessages extends ServerMessage{
    public String message;
    public NotificationMessages(ServerMessageType type, String message) {
        super(type);
        this.message= message;
    }
}
