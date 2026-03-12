package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.UserException403;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;;

public class SQLDataAccess implements DataAccess {



    public SQLDataAccess() {
        try {
            DatabaseManager.createDatabase();
            configureDatabase();
        } catch (DataAccessException e) {
            System.out.println("Got the error yay!");
        }
    }

    private void configureDatabase()  {
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createUserStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
            for (String statement : createGameStatements) {
                try (var preparedStatement1 = conn.prepareStatement(statement)) {
                    preparedStatement1.executeUpdate();
                }
            }
            for (String statement : createPublicStatements) {
                try (var preparedStatement2 = conn.prepareStatement(statement)) {
                    preparedStatement2.executeUpdate();
                }
            }
        } catch (SQLException | DataAccessException ex) {
            System.out.print("Something went wrong");
        }
    }

    @Override
    public UserData findUser(String username) throws DataAccessException{
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "SELECT * FROM users WHERE name = ?")) {
                statement.setString(1, username);
                try(var rs = statement.executeQuery()){
                    if(rs.next() == false){
                        return null;
                    }
                    var name = rs.getString("name");
                    var pass = rs.getString("password");
                    if(name.isEmpty()){
                        return null;
                    }
                    else {
                        UserData user = new UserData(name, pass, null);
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
            try (var statement = con.prepareStatement( "INSERT INTO users (name, password, token) VALUES (?, ?, ?)")) {
                String pass = BCrypt.hashpw(password.password(), BCrypt.gensalt());
                statement.setString(1, username);
                statement.setString(2, pass);
                statement.setString(3, "0");
                var rs = statement.executeUpdate();
                return null;
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
            try (var statement = con.prepareStatement( "UPDATE users SET token = ? WHERE name = ?")){
                String username = authToken.authToken();
                statement.setString(2, username);
                statement.setString(1, authToken.userName());
                var rs = statement.executeUpdate();
                return authToken;
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
            try (var statement = con.prepareStatement( "SELECT * FROM users WHERE token = ? ")){
                statement.setString(1, authData);
                try(var rs = statement.executeQuery()){
                    System.out.println(rs);
                    rs.next();
                    String authenticated = rs.getString("name");
                    System.out.println("Get's there.");
                    AuthData auth = new AuthData(authenticated, authData);;
                    return auth;
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
            try (var statement = con.prepareStatement( "UPDATE users SET token = 0 WHERE token = ? ")){
                statement.setString(1, authData);
                var rs = statement.executeUpdate();
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
            try (var statement = con.prepareStatement( "INSERT INTO games (name, whiteTeam, blackTeam, game) VALUES(?, ?, ?, ?)")){
                var state = gameName.game();
                var serializer = new Gson();
                var json = serializer.toJson(state);
                statement.setString(1, gameName.gameName());
                statement.setString(2, "");
                statement.setString(3, "");
                statement.setString(4, json);
                var rs = statement.executeUpdate();
//                    var ID = rs.getInt("id");
                    return rs;
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
            try (var statement = con.prepareStatement( "INSERT INTO public (name, whiteTeam, blackTeam) VALUES(?, ?, ?)")){
                statement.setString(1, pub.gameName());
                statement.setString(2, "");
                statement.setString(3, "");
                var rs = statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int gameID() {
        return 1;
//        try(var con = DatabaseManager.getConnection()) {
//            try (var statement = con.prepareStatement( "SELECT * FROM games WHERE id = (SELECT MAX (id) FROM games)")){
//                try(var rs = statement.executeQuery()){
//                    var id = rs.getInt("id");
//                    return id;
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (DataAccessException e) {
//            throw new RuntimeException(e);
//        }
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
            try (var statement = con.prepareStatement( "SELECT * FROM games WHERE id = ?")){
                statement.setInt(1, gameID);
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
                statement.setInt(3, gameID);
                statement.setString(2, game.blackUsername());
                statement.setString(1, game.whiteUsername());
                var rs = statement.executeUpdate();
            }
            try (var statement = con.prepareStatement( "UPDATE public SET whiteTeam = ? blackTeam = ? WHERE id = gameID")){
                statement.setInt(3, gameID);
                statement.setString(2, game.blackUsername());
                statement.setString(1, game.whiteUsername());
                var rs = statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearAuth() {
        return;
//        try(var con = DatabaseManager.getConnection()) {
//            try (var statement = con.prepareStatement( "ALTER users TRUNCATE COLUMN token")){
//                int rs = statement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (DataAccessException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void clearUsers() {
        try(var con = DatabaseManager.getConnection()) {
            try (var statement = con.prepareStatement( "TRUNCATE users")){
                int rs = statement.executeUpdate();
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
            try (var statement1 = con.prepareStatement( "TRUNCATE games")){
                int rs = statement1.executeUpdate();
            }
            try (var statement2 = con.prepareStatement( "TRUNCATE public")){
                int rs = statement2.executeUpdate();
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
            `token` varchar(128) NOT NULL,
            PRIMARY KEY (`id`)
            )
            """
    };

    private final String [] createGameStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games(
            `id` int NOT NULL AUTO_INCREMENT,
            `name` varchar(256) NOT NULL,
            `whiteTeam` varchar(256) NOT NULL,
            `blackTeam` varchar(256) NOT NULL,
            `game` text NOT NULL,
             PRIMARY KEY (`id`)
            )
            """
    };

    private final String [] createPublicStatements = {
            """
            CREATE TABLE IF NOT EXISTS  public(
            `id` int NOT NULL AUTO_INCREMENT,
            `name` varchar(256) NOT NULL,
            `whiteTeam` varchar(256) ,
            `blackTeam` varchar(256) ,
             PRIMARY KEY (`id`)
            )
            """
    };

}
