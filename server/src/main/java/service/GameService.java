package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import exception.UserExceptions;
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
        if(games!= null) {
            return games;
        }
        else{
            return null;
        }
    }

    public String joinByColor(JoinGameData colorJoin, String authData) throws UserExceptions {
        String desiredColor = colorJoin.playerColor();
        int gameID = colorJoin.gameID();
        GameData game = dataAccess.returnSingleGame(gameID);
        if(game == null){
            throw new UserExceptions("400: Error: bad request");
        }
        PublicGame game1 = dataAccess.editPublic(gameID);
        AuthData allowed = dataAccess.findAuth(authData);
        if(desiredColor.equalsIgnoreCase("White")){
            if(game.whiteUsername().isBlank()){
                game = new GameData(game.gameID(), allowed.authToken(), game.blackUsername(), game.gameName(), game.game());
                game1 = new PublicGame(game1.gameID(), allowed.authToken(), game1.blackUsername(), game1.gameName());
                dataAccess.updateGames(game, game1, gameID);
            }
            else{
                throw new UserExceptions("403: Error: Color taken");
            }
            String empty = "{}";
            return empty;
        }
        else if(desiredColor.equalsIgnoreCase("Black")){
            if(game.blackUsername().isBlank()){
                game = new GameData(game.gameID(), game.whiteUsername(), allowed.authToken(), game.gameName(), game.game());
                game1 = new PublicGame(game.gameID(), game.whiteUsername(), allowed.authToken(), game.gameName());
                dataAccess.updateGames(game, game1, gameID);
            }
            else{
                throw new UserExceptions("403: Error: Color taken");
            }
            String empty = "{}";
            return empty;
        }
        else {
            return null;
        }
    }
}
