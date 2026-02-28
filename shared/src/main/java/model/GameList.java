package model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;

public class GameList extends ArrayList<PublicGame> {
    public GameList (){

    }

    public GameList(Collection<PublicGame> games){
        super(games);
    }

    public String toString(){
        return new Gson().toJson(this.toArray());
    }
}
