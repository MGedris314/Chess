package handler;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import exception.*;
import model.*;
import service.UserService;
import service.GameService;


public class UserHandler {
    private DataAccess dataAccess;
    public UserHandler(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public String register(String dataAsJSON) throws UserExceptions, UserException403, DataAccessException {
        UserService userService = new UserService(dataAccess);
        UserData userdata = new Gson().fromJson(dataAsJSON, UserData.class);
        if(userdata.username() == null || userdata.password() == null || userdata.email() == null){
            throw new UserExceptions("400: Error: bad request");
        }
        RegisterResult regi = userService.getUser(userdata);
        return new Gson().toJson(regi);
    }

    public String logIn(String dataAsJson) throws UserExceptions, UserException401, DataAccessException {
        UserService userService = new UserService(dataAccess);
        UserData userdata = new Gson().fromJson(dataAsJson, UserData.class);
        if(userdata.username() == null || userdata.password() == null){
            throw new UserExceptions("400: Error: bad request");
        }
        RegisterResult regi = userService.logUser(userdata);
        return new Gson().toJson(regi);
    }

    public String logOut(String dataString) throws UserException401, DataAccessException {
        UserService userService = new UserService(dataAccess);
        String authorized = userService.logOut(dataString);
        return authorized;
    }

    public Boolean authenticate (String authToken) throws UserException401, DataAccessException {
        UserService userService = new UserService((dataAccess));
        Boolean authentic = userService.authenticate(authToken);
        if(authentic) {
            return authentic;
        }
        else{
            throw new UserException401("401: Error: unauthorized");
        }
    }

    public String addGame (String gameName) throws UserExceptions, UserException401, DataAccessException {
        GameService gameService = new GameService((dataAccess));
        if(gameName.equals("{}")){
            throw new UserExceptions("400: Error: bad request");
        }
        GameName name = new Gson().fromJson(gameName, GameName.class);
        int gameID = gameService.createGame(name.gameName());
        String words = "{game ID: ";
        String returnVal = words + gameID +"}";
        GameResult id = new GameResult(gameID);
        return new Gson().toJson(id);
    }

    public String getGames()  throws DataAccessException{
        GameService gameService = new GameService((dataAccess));
        GameRetrun games = gameService.returnGames();

        return new Gson().toJson(games);

    }

    public String clearDB() throws DataAccessException {
        UserService userService = new UserService(dataAccess);
        userService.dbclear();
        String blank = "{}";
        return blank;
    }

    public String joinGame(String dataAsJSON, String authToken) throws UserExceptions, UserException403, DataAccessException {
        GameService gameService = new GameService(dataAccess);
        JoinGameData joinData = new Gson().fromJson(dataAsJSON, JoinGameData.class);
        if(joinData.playerColor() == null){
            throw new UserExceptions("400: Error: bad request");
        }
        String join = gameService.joinByColor(joinData, authToken);
        return join;
    }
}
