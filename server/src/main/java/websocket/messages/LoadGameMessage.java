package websocket.messages;
import model.GameData;
import model.GameRetrun;


public class LoadGameMessage extends ServerMessage{
    public GameData game;

    public LoadGameMessage(ServerMessageType type, GameData game) {
        super(type);
        this.game = game;
    }

    public GameData returning(){
        return game;
    }
}
