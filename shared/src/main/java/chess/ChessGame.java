package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessBoard board = new ChessBoard(); //Figure out how to set up the board.
    TeamColor team_turn = TeamColor.WHITE;
    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */

//    Setting up variables that we'll probably be using later
    boolean White_check = false;
    boolean Black_check = false;
    ChessPosition King_W = find_king(board, TeamColor.WHITE);
    ChessPosition King_B = find_king(board, TeamColor.BLACK);
    ArrayList <ChessPosition> white_team = new ArrayList<ChessPosition>();
    ArrayList <ChessPosition> black_team = new ArrayList<ChessPosition>();

    public ChessPosition find_king(ChessBoard board, TeamColor color){
        for(int x = 1; x<9; x++){
            for(int y = 1; y<9; y++){
                ChessPosition finder = new ChessPosition(x, y);
                ChessPiece found = board.getPiece(finder);
                if (found != null && found.getPieceType() == ChessPiece.PieceType.KING){
                    if(found.getTeamColor() == color){
                    return finder;
                    }
                }
            }
        }
        return null;
    }

    public TeamColor getTeamTurn() {
        return team_turn;
    }

    public  void find_white_team(ChessBoard board){
        for(int x = 1; x<9; x++){
            for(int y = 1; y<9; y++){
                ChessPosition finder = new ChessPosition(x, y);
                ChessPiece found = board.getPiece(finder);
                if (found != null && found.getTeamColor() == TeamColor.WHITE){
                    white_team.add(finder);
                }
            }
        }
    }

    public void find_black_team(ChessBoard board){
        for(int x = 1; x<9; x++){
            for(int y = 1; y<9; y++){
                ChessPosition finder = new ChessPosition(x, y);
                ChessPiece found = board.getPiece(finder);
                if (found != null && found.getTeamColor() == TeamColor.BLACK){
                    black_team.add(finder);
                }
            }
        }
    }

    public Collection<ChessMove> possible_white(ChessBoard board){
        ArrayList<ChessMove> possible = new ArrayList<ChessMove>();
        for(int i = 0; i<white_team.size(); i++){
            ChessPiece hold = board.getPiece(white_team.get(i));
        }
        return possible;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        team_turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        if(teamColor == TeamColor.WHITE && White_check){
            return true;
        }
        if(teamColor == TeamColor.BLACK && Black_check){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        board.resetBoard();
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        throw new RuntimeException("Not implemented");
    }
}
