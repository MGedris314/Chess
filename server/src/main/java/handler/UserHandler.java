package handler;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import exception.*;
import model.*;
import service.UserService;
import service.GameService;

import java.util.Collection;


public class UserHandler {
    private DataAccess dataAccess;
    public UserHandler(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public String register(String data_asJSON) throws UserExceptions, UserException403{
        UserService userService = new UserService(dataAccess);
        UserData userdata = new Gson().fromJson(data_asJSON, UserData.class);
        if(userdata.username() == null || userdata.password() == null || userdata.email() == null){
            throw new UserExceptions("400: Error: bad request");
        }
        RegisterResult regi = userService.GetUser(userdata);
        return new Gson().toJson(regi);
    }

    public String log_in (String data_asJson) throws UserExceptions, UserException401{
        UserService userService = new UserService(dataAccess);
        UserData userdata = new Gson().fromJson(data_asJson, UserData.class);
        if(userdata.username() == null || userdata.password() == null){
            throw new UserExceptions("400: Error: bad request");
        }
        RegisterResult regi = userService.LogUser(userdata);
        return new Gson().toJson(regi);
    }

    public String log_out (String data_string) throws UserException401{
        UserService userService = new UserService(dataAccess);
        String authorized = userService.logOut(data_string);
        return authorized;
    }

    public Boolean authenticate (String auth_token) throws UserException401{
        UserService userService = new UserService((dataAccess));
        Boolean authentic = userService.authenticate(auth_token);
        if(authentic) {
            return authentic;
        }
        else{
            throw new UserException401("401: Error: unauthorized");
        }
    }

    public String addGame (String gameName) throws UserExceptions, UserException401{
        GameService gameService = new GameService((dataAccess));
        if(gameName.equals("{}")){
            throw new UserExceptions("400: Error: bad request");
        }
        GameName name = new Gson().fromJson(gameName, GameName.class);
        int gameID = gameService.createGame(name.gameName());
        String words = "{game ID: ";
        String return_val = words + gameID +"}";
        GameResult ID = new GameResult(gameID);
        return new Gson().toJson(ID);
    }

    public String getGames(){
        GameService gameService = new GameService((dataAccess));
        GameList games = gameService.returnGames();

        return new Gson().toJson(games);

    }

    public String ClearDB(){
        UserService userService = new UserService(dataAccess);
        userService.DBClear();
        String blank = "{}";
        return blank;
    }

    public String JoinGame(String data_asJSON, String authToken) throws UserExceptions, UserException403{
        GameService gameService = new GameService(dataAccess);
        JoinGameData joinData = new Gson().fromJson(data_asJSON, JoinGameData.class);
        if(joinData.playerColor() == null){
            throw new UserExceptions("400: Error: bad request");
        }
        String join = gameService.joinByColor(joinData, authToken);
        return join;
    }
}
