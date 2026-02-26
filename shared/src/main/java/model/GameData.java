package model;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
//    Only return the first four values for the record.
}
