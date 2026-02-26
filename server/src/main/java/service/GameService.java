package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import model.GameData;
import model.JoinGameData;
import java.util.ArrayList;
import java.util.Collection;

public class GameService {

    private final DataAccess dataAccess;

    public GameService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public int createGame(String game_name){
        int ID = dataAccess.gameID();
        GameData gameData = new GameData(ID, "","",game_name, new ChessGame());
        dataAccess.createGame(gameData);
        return ID;
    }

    public Collection<GameData> returnGames() {
        ArrayList<GameData> games = dataAccess.gameReturn();
        return games;
    }

    public String joinByColor(JoinGameData colorJoin){
        String desiredColor = colorJoin.playerColor();
        if(desiredColor.equalsIgnoreCase("White")){
            return null;
        }
        else if(desiredColor.equalsIgnoreCase("Black")){
            return null;
        }
        else {
            return null;
        }
    }
}
