package server;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import handler.UserHandler;
import io.javalin.*;
import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.Javalin;
import org.eclipse.jetty.server.Authentication;


public class Server {
    private final Javalin javalin;
    private final UserHandler handler;

    public Server() {
        this(new UserHandler(new MemoryDataAccess()));

        // Register your endpoints and exception handlers here.
    }
    public Server(UserHandler handler){
        this.handler = handler;
        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", this::addUser)
                .post("/session", this::logIn)
                .post("/game", this::createGame)
                .put("/game", this::joinGame)
                .get("game", this::listGames)
                .delete("/session", this::logOut)
                .delete("/db", this::fullClear);
    }

    private void addUser(Context ctx){
        String addin = handler.register(ctx.body());
        ctx.result(addin);
    }

    private void logIn(Context ctx){
        String logged = handler.log_in(ctx.body());
        ctx.result(logged);
    }

    private void createGame(Context ctx){
//        There are two things we need to pass in auth and game data
        boolean authentic = handler.authenticate(ctx.header("authorization"));
        if(authentic){
            String ID = handler.addGame(ctx.body());
            ctx.result(ID);
        }
        else{
            String ID = "-1";
            ctx.result(ID);
        }
    }

    private void joinGame(Context ctx){
        boolean authentic = handler.authenticate(ctx.header("authorization"));
        if(authentic){
//        Returns a string, we need to pass in the body to be used as a jgd type.
            String token = ctx.header("authorization");
        }
        else{
            String ID = "-1";
            ctx.result(ID);
        }
    }

    private void listGames(Context ctx){
        boolean authentic = handler.authenticate(ctx.header("authorization"));
        if(authentic){
            String games = handler.getGames();
            ctx.result(games);
        }
        else{
            String ID = "-1";
            ctx.result(ID);
        }
    }

    private void logOut(Context ctx){
        String logout = handler.log_out(ctx.header("authorization"));
        ctx.result(logout);
    }

    private void fullClear(Context ctx){
        String clear_out = handler.ClearDB();
        ctx.result(clear_out);
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
