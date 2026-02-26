package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import model.AuthData;
import model.GameData;
import model.JoinGameData;
import model.PublicGame;

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
        PublicGame publicGame = new PublicGame(ID, "", "", game_name);
        dataAccess.createGame(gameData);
        dataAccess.createPublic(publicGame);
        return ID;
    }

    public Collection<PublicGame> returnGames() {
        ArrayList<PublicGame> games = dataAccess.gameReturn();
        return games;
    }

    public String joinByColor(JoinGameData colorJoin, String authData){
        String desiredColor = colorJoin.playerColor();
        int gameID = colorJoin.gameID();
        GameData game = dataAccess.returnSingleGame(gameID);
        PublicGame game1 = dataAccess.editPublic(gameID);
        AuthData allowed = dataAccess.findAuth(authData);
        if(desiredColor.equalsIgnoreCase("White")){
            if(game.whiteUsername().isBlank()){
                game.whiteUsername() = allowed.userName();
                game1.whiteUsername() = allowed.userName();
                dataAccess.updateGames(game, game1, gameID);
            }

            return null;
        }
        else if(desiredColor.equalsIgnoreCase("Black")){
            if(game.whiteUsername().isBlank()){
                game.blackUsername() = allowed.userName();
                game1.blackUsername() = allowed.userName();
                dataAccess.updateGames(game, game1, gameID);
            }
            return null;
        }
        else {
            return null;
        }
    }
}
