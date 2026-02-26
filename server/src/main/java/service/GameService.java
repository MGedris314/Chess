package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import model.GameData;

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
}
