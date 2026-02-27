package handler;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import exception.UserExceptions;
import model.AuthData;
import model.UserData;
import model.GameData;
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
        RegisterResult regi = userService.GetUser(userdata);
        return new Gson().toJson(regi);
    }

    public String log_in (String data_asJson) throws UserExceptions{
        UserService userService = new UserService(dataAccess);
        UserData userdata = new Gson().fromJson(data_asJson, UserData.class);
        if(userdata.username().isBlank() || userdata.password().isBlank()){
            throw new UserExceptions("400: Error: bad request");
        }
        RegisterResult regi = userService.LogUser(userdata);
        return new Gson().toJson(regi);
    }

    public String log_out (String data_string) throws UserExceptions{
        UserService userService = new UserService(dataAccess);
        String authorized = userService.logOut(data_string);
        return new Gson().toJson(authorized);
    }

    public Boolean authenticate (String auth_token) throws UserExceptions{
        UserService userService = new UserService((dataAccess));
        Boolean authentic = userService.authenticate(auth_token);
        if(authentic) {
            return authentic;
        }
        else{
            throw new UserExceptions("401: Unauthorized");
        }
    }

    public String addGame (String gameName) throws UserExceptions{
        GameService gameService = new GameService((dataAccess));
        if(gameName.isBlank()){
            throw new UserExceptions("400: Error: bad request");
        }
        int gameID = gameService.createGame(gameName);
        String words = "game ID: ";
        String return_val = words + gameID;
        return new Gson().toJson(return_val);
    }

    public String getGames(){
        GameService gameService = new GameService((dataAccess));
        Collection<PublicGame> games = gameService.returnGames();
        return new Gson().toJson(games);
    }

    public String ClearDB(){
        UserService userService = new UserService(dataAccess);
        userService.DBClear();
        String blank = "";
        return new Gson().toJson(blank);
    }

    public String JoinGame(String data_asJSON, String authToken){
        GameService gameService = new GameService(dataAccess);
        JoinGameData joinData = new Gson().fromJson(data_asJSON, JoinGameData.class);
        String join = gameService.joinByColor(joinData, authToken);
        return new Gson().toJson(join);
    }
}
