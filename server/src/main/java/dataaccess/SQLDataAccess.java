package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.UserException403;
import model.*;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;;

public class SQLDataAccess implements DataAccess {



    public SQLDataAccess() {
        try {
            DatabaseManager.createDatabase();
        } catch (DataAccessException e) {
            System.out.println("Got the error yay!");
        }
    }

    @Override
    public UserData findUser(String username) throws DataAccessException{
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "SELECT * FROM users WHERE name = username")) {
                try(var rs = statement.executeQuery()){
                    rs.next();
                    var name = rs.getString("name");
                    if(name.isEmpty()){
                        return null;
                    }
                    else {
                        UserData user = new UserData(name, null, null);
                        return user;
                    }
                }

            }
        }
        catch (DataAccessException e){
            throw new DataAccessException("e");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserData addUser(String username, UserData password) {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "INSERT INTO users (name, password) VALUES (?, ?)")) {
                try(var rs = statement.executeQuery()){
                    statement.setString(1, username);
                    statement.setString(2, password.password());
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthData addAuthToken(AuthData authToken) {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "INSERT INTO users (token) VALUES (authToken) WHERE (name = username)")){
                try(var rs = statement.executeQuery()){
                    String username = authToken.userName();
                    statement.setString(2, username);
                    statement.setString(1, authToken.authToken());
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthData findAuth(String authData) {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "SELECT name FROM users WHERE token = authData")){
                try(var rs = statement.executeQuery()){
                    statement.setString(1, authData);
                    var authenticated = rs.getString("name");
                    AuthData auth = new AuthData(authenticated, authData);
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeAuth(String authData) {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "UPDATE users SET token = 0 WHERE token = authData")){
                try(var rs = statement.executeQuery()){
                    statement.setString(1, authData);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createGame(GameData gameName) {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "INSERT INTO games (id, name, whiteTeam, blackTeam, game) VALUES(?, ?, null, null, ?")){
                try(var rs = statement.executeQuery()){
                    var state = gameName.game();
                    var serializer = new Gson();
                    var json = serializer.toJson(state);
                    statement.setString(2, gameName.gameName());
                    statement.setString(5, json);
                    var ID = rs.getInt("id");
                    return ID;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createPublic(PublicGame pub) {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "INSERT INTO public (id, name, whiteTeam, blackTeam) VALUES(?, ?, null, null, ?")){
                try(var rs = statement.executeQuery()){
                    statement.setString(2, pub.gameName());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int gameID() {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "SELECT * FROM games WHERE id = (SELECT MAX (id) FROM games)")){
                try(var rs = statement.executeQuery()){
                    var id = rs.getInt("id");
                    return id;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameRetrun gameReturn() {
        ArrayList<PublicGame> returnable = new ArrayList<PublicGame>();
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "SELECT * FROM public")){
                try(var rs = statement.executeQuery()){
                    while(rs.next()) {
                        var name = rs.getString("name");
                        var wTeam = rs.getString("whiteTeam");
                        var bTeam = rs.getString("blackTeam");
                        var id = rs.getInt("id");
                        PublicGame pub = new PublicGame(id,name, wTeam, bTeam);
                        returnable.add(pub);
                    }
                    GameRetrun games = new GameRetrun(returnable);
                    return games;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameData returnSingleGame(int gameID) throws UserException403 {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "SELECT * FROM games WHERE id = gameID")){
                try(var rs = statement.executeQuery()){
                    var name = rs.getString("name");
                    var wTeam = rs.getString("whiteTeam");
                    var bTeam = rs.getString("blackTeam");
                    var id = rs.getInt("id");
                    var json = rs.getString("game");
                    var serializer = new Gson();
                    var fromJson = serializer.fromJson(json, ChessGame.class);
                    GameData game = new GameData(id, wTeam, bTeam, name, fromJson);
                    return game;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PublicGame editPublic(int gameID) {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "SELECT * FROM public WHERE id = gameID")){
                try(var rs = statement.executeQuery()){
                    statement.setInt(1, gameID);
                    var name = rs.getString("name");
                    var wTeam = rs.getString("whiteTeam");
                    var bTeam = rs.getString("blackTeam");
                    var id = rs.getInt("id");
                    PublicGame pub = new PublicGame(id, wTeam, bTeam, name);
                    return pub;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateGames(GameData game, PublicGame pub, int gameID) {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "UPDATE games SET whiteTeam = ? blackTeam = ? WHERE id = gameID")){
                try(var rs = statement.executeQuery()){
                    statement.setInt(3, gameID);
                    statement.setString(2, game.blackUsername());
                    statement.setString(1, game.whiteUsername());
                }
            }
            try (var statement = con.prepareStatement( "UPDATE public SET whiteTeam = ? blackTeam = ? WHERE id = gameID")){
                try(var rs = statement.executeQuery()){
                    statement.setInt(3, gameID);
                    statement.setString(2, game.blackUsername());
                    statement.setString(1, game.whiteUsername());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        var statement1 = "UPDATE games SET whiteTeam = ? blackTeam = ? WHERE id = gameID";
        var statement2 = "UPDATE public SET whiteTeam = ? blackTeam = ? WHERE id = gameID";
    }

    @Override
    public void clearAuth() {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "DROP COLUMN token")){
                try(var rs = statement.executeQuery()){
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearUsers() {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "DROP TABLE users")){
                try(var rs = statement.executeQuery()){
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearGames() {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "DROP TABLE games")){
                try(var rs = statement.executeQuery()){
                }
            }
            try (var statement = con.prepareStatement( "DROP TABLE public")){
                try(var rs = statement.executeQuery()){
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final String [] createUserStatements = {
            """
            CREATE TABLE IF NOT EXISTS  users(
            `id` int NOT NULL AUTO_INCREMENT,
            `name` varchar(256) NOT NULL,
            `password` varchar(256) NOT NULL,
            `token` varchar(128),
            PRIMARY KEY (`id`)
            )
            """
    };

    private final String [] createGameStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games(
            `id` int NOT NULL AUTO_INCREMENT,
            `name` varchar(256) NOT NULL,
            `whiteTeam` TEXT DEFAULT NULL,
            `blackTeam` TEXT DEFAULT NULL,
            `game` nvarchar(max)
             PRIMARY KEY (`id`)
            )
            """
    };

    private final String [] createPublicStatements = {
            """
            CREATE TABLE IF NOT EXISTS  public(
            `id` int NOT NULL AUTO_INCREMENT,
            `name` varchar(256) NOT NULL,
            `whiteTeam` TEXT DEFAULT NULL,
            `blackTeam` TEXT DEFAULT NULL,
             PRIMARY KEY (`id`)
            )
            """
    };

}
