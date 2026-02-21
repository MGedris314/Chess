package model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import User.java;

public class Game(int ID, String name, User W_player, User B_player){
    public Game createGame(User.authToken authToken){
        return new Game(ID, this.name)
    }
    public Game joinGame(User.name name, String color, int GameID){
        if(color == "White"){
            W_player = name
            return(GameID, W_player, B_player)
        }
        if(color == "Black"){
            B_player = name
            return(GameID, W_player, B_player)
        }
    }
}