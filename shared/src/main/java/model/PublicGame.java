package model;

import chess.ChessGame;

public record PublicGame(int gameID, String whiteUsername, String blackUsername, String gameName) {
}
