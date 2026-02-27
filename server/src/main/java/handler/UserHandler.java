package handler;

import com.google.gson.Gson;
import dataaccess.DataAccess;
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

    public String register(String data_asJSON) {
        UserService userService = new UserService(dataAccess);
        UserData userdata = new Gson().fromJson(data_asJSON, UserData.class);
        RegisterResult regi = userService.GetUser(userdata);
        return new Gson().toJson(regi);
    }

    public String log_in (String data_asJson){
        UserService userService = new UserService(dataAccess);
        UserData userdata = new Gson().fromJson(data_asJson, UserData.class);
        RegisterResult regi = userService.LogUser(userdata);
        return new Gson().toJson(regi);
    }

    public String log_out (String data_string){
        UserService userService = new UserService(dataAccess);
        String authorized = userService.logOut(data_string);
        return new Gson().toJson(authorized);
    }

    public Boolean authenticate (String auth_token){
        UserService userService = new UserService((dataAccess));
        Boolean authentic = userService.authenticate(auth_token);
        return  authentic;
    }

    public String addGame (String gameName){
        GameService gameService = new GameService((dataAccess));
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
