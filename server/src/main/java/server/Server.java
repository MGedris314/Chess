package server;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import exception.UserExceptions;
import handler.UserHandler;
import io.javalin.*;
import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.Javalin;
import org.eclipse.jetty.server.Authentication;

import java.util.Map;


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
//        400 and 403
        try {
            String addin = handler.register(ctx.body());
            ctx.result(addin);
        }
        catch(UserExceptions e ){
            ctx.status(403);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 403)));
        }

    }

    private void logIn(Context ctx){
//        400 and 401
        try {
            String logged = handler.log_in(ctx.body());
            ctx.result(logged);
        }
        catch (UserExceptions e){
            ctx.status(400);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 400)));
        }
    }

    private void createGame(Context ctx){
//        400 and 401
        try {
            boolean authentic = handler.authenticate(ctx.header("authorization"));
            if (authentic) {
                try {
                    String ID = handler.addGame(ctx.body());
                    ctx.result(ID);
                }
                catch (UserExceptions e){
                    ctx.status(400);
                    ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 400)));
                }
            }
        }
        catch (UserExceptions e){
            ctx.status(401);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 401)));
        }
    }

    private void joinGame(Context ctx){
//        400, 401, and 403
        try{
        boolean authentic = handler.authenticate(ctx.header("authorization"));
            if (authentic) {
                try {
                    String token = ctx.header("authorization");
                    String joined = handler.JoinGame(ctx.body(), token);
                    ctx.result(joined);
                }
                catch (UserExceptions e){
                    ctx.status(401);
                    ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 401)));
                }
            }
        }
        catch (UserExceptions e){
            ctx.status(401);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 401)));
        }
    }

    private void listGames(Context ctx){
        try {
            boolean authentic = handler.authenticate(ctx.header("authorization"));
            if (authentic) {
                String games = handler.getGames();
                ctx.result(games);
            }
        }
        catch (UserExceptions e){
                ctx.status(401);
                ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 401)));
        }
    }

    private void logOut(Context ctx){
        try {
            String logout = handler.log_out(ctx.header("authorization"));
            ctx.result(logout);
        }
        catch(UserExceptions e){
            ctx.status(401);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage(), "status", 401)));
        }
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
