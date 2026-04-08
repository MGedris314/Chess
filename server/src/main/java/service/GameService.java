package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import exception.UserException403;
import exception.UserExceptions;
import model.*;

import java.util.ArrayList;
import java.util.Collection;

public class GameService {

    private final DataAccess dataAccess;

    public GameService(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public int createGame(String gameName) throws DataAccessException {
//        Failsafe double check to make sure the game_name isn't null
        if(gameName.isEmpty()){
            return -1;
        }
        int id = dataAccess.gameID();
        GameData gameData = new GameData(id, "","",gameName, new ChessGame());
        PublicGame publicGame = new PublicGame(id, "", "", gameName);
        dataAccess.createGame(gameData);
        dataAccess.createPublic(publicGame);
        return id;
    }

    public GameRetrun returnGames()  throws DataAccessException {
        GameRetrun games = dataAccess.gameReturn();
        if(games!= null) {
            return games;
        }
        else{
            return null;
        }
    }

    public GameData observe (int gameId) throws UserExceptions{
        try {
            GameData game = dataAccess.returnSingleGame(gameId);
            return game;
        } catch (DataAccessException e) {
            throw new UserExceptions("400: Error bad request.");
        } catch (UserException403 e) {
            throw new UserExceptions("403: Error bad request.");
        }
    }

    public AuthData findUser (String token) throws UserExceptions{
        try {
            AuthData game = dataAccess.findAuth(token);
            return game;
        } catch (DataAccessException e) {
            throw new UserExceptions("400: Error bad request.");
        }
    }

    public String joinByColor(JoinGameData colorJoin, String authData) throws UserExceptions, UserException403, DataAccessException {
        String desiredColor = colorJoin.playerColor();
        if(!desiredColor.equalsIgnoreCase("White")){
            if(!desiredColor.equalsIgnoreCase("Black")) {
                throw new UserExceptions("400: Error: bad request");
            }
        }
        int gameID = colorJoin.gameID();
        GameData game = dataAccess.returnSingleGame(gameID);
//      the above line is what I want.  What I don't know is how to get it.
        if(game == null){
            throw new UserExceptions("400: Error: bad request");
        }
        PublicGame game1 = dataAccess.editPublic(gameID);
        AuthData allowed = dataAccess.findAuth(authData);
        if(desiredColor.equalsIgnoreCase("White")){
            if(game.whiteUsername().isEmpty() || game.whiteUsername().equals("null")){
                if(game.blackUsername().isEmpty() || game.blackUsername().equals("null")) {
                    game = new GameData(game.gameID(), allowed.userName(), game.blackUsername(), game.gameName(), game.game());
                    game1 = new PublicGame(game1.gameID(), allowed.userName(), null, game1.gameName());
                    dataAccess.updateGames(game, game1, gameID);
                }
                else{
                    game = new GameData(game.gameID(), allowed.userName(), game.blackUsername(), game.gameName(), game.game());
                    game1 = new PublicGame(game1.gameID(), allowed.userName(), game1.blackUsername(), game1.gameName());
                    dataAccess.updateGames(game, game1, gameID);
                }
            }
            else{
                throw new UserException403("403: Error: Color taken");
            }
            String empty = "{}";
            return empty;
        }
        else if(desiredColor.equalsIgnoreCase("Black")){
            if(game.blackUsername().isEmpty() || game.blackUsername().equals("null")){
                if(game.whiteUsername().isEmpty()) {
                    game = new GameData(game.gameID(), game.whiteUsername(), allowed.userName(), game.gameName(), game.game());
                    game1 = new PublicGame(game.gameID(), null, allowed.userName(), game.gameName());
                    dataAccess.updateGames(game, game1, gameID);
                }
                else{
                    game = new GameData(game.gameID(), game.whiteUsername(), allowed.userName(), game.gameName(), game.game());
                    game1 = new PublicGame(game.gameID(), game.whiteUsername(), allowed.userName(), game.gameName());
                    dataAccess.updateGames(game, game1, gameID);
                }
            }
            else{
                throw new UserException403("403: Error: Color taken");
            }
            String empty = "{}";
            return empty;
        }
        else {
            return null;
        }
    }
}
