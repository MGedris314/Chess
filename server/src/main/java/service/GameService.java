package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import model.GameData;

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

}
