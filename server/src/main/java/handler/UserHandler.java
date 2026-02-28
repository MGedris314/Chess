package handler;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import exception.UserExceptions;
import model.AuthData;
import model.UserData;
import model.GameData;
import model.GameResult;
import model.PublicGame;
import model.JoinGameData;
import service.UserService;
import model.RegisterResult;
import service.GameService;

import java.util.Collection;


public class UserHandler {
    private DataAccess dataAccess;
    public UserHandler(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    public String register(String data_asJSON) throws UserExceptions{
        UserService userService = new UserService(dataAccess);
        UserData userdata = new Gson().fromJson(data_asJSON, UserData.class);
        if(userdata.username() == null || userdata.password() == null || userdata.email() == null){
            throw new UserExceptions("400: Error: bad request");
        }
        RegisterResult regi = userService.GetUser(userdata);
        return new Gson().toJson(regi);
    }

    public String log_in (String data_asJson) throws UserExceptions{
        UserService userService = new UserService(dataAccess);
        UserData userdata = new Gson().fromJson(data_asJson, UserData.class);
        if(userdata.username() == null || userdata.password() == null){
            throw new UserExceptions("400: Error: bad request");
        }
        RegisterResult regi = userService.LogUser(userdata);
        return new Gson().toJson(regi);
    }

    public String log_out (String data_string) throws UserExceptions{
        UserService userService = new UserService(dataAccess);
        String authorized = userService.logOut(data_string);
        return authorized;
    }

    public Boolean authenticate (String auth_token) throws UserExceptions{
        UserService userService = new UserService((dataAccess));
        Boolean authentic = userService.authenticate(auth_token);
        if(authentic) {
            return authentic;
        }
        else{
            throw new UserExceptions("401: Error: unauthorized");
        }
    }

    public String addGame (String gameName) throws UserExceptions{
        GameService gameService = new GameService((dataAccess));
        if(gameName.isBlank()){
            throw new UserExceptions("400: Error: bad request");
        }
        int gameID = gameService.createGame(gameName);
        String words = "{game ID: ";
        String return_val = words + gameID +"}";
        GameResult ID = new GameResult(gameID);
        return new Gson().toJson(ID);
    }

    public String getGames(){
        GameService gameService = new GameService((dataAccess));
        Collection<PublicGame> games = gameService.returnGames();
        if (games.size() > 0) {
            return new Gson().toJson(games);
        }
        else {
            String empty = "{}";
            return empty;
        }
    }

    public String ClearDB(){
        UserService userService = new UserService(dataAccess);
        userService.DBClear();
        String blank = "{}";
        return blank;
    }

    public String JoinGame(String data_asJSON, String authToken) throws UserExceptions{
        GameService gameService = new GameService(dataAccess);
        JoinGameData joinData = new Gson().fromJson(data_asJSON, JoinGameData.class);
        if(joinData.playerColor() == null){
            throw new UserExceptions("400: Error: bad request");
        }
        String join = gameService.joinByColor(joinData, authToken);
        return join;
    }
}
