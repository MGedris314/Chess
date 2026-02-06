package chess;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessBoard board; //Figure out how to set up the board.
    TeamColor team_turn;
    boolean White_check;
    boolean Black_check;
    ChessPosition King_W;
    ChessPosition King_B;
    ArrayList <ChessPosition> white_team;
    ArrayList <ChessPosition> black_team;
    public ChessGame() {
        this.team_turn = TeamColor.WHITE;
        this.board = new ChessBoard();
        this.board.resetBoard();
        White_check = false;
        Black_check = false;
        King_W = find_king(this.board, TeamColor.WHITE);
        King_B = find_king(this.board, TeamColor.BLACK);
        white_team = new ArrayList<ChessPosition>();
        black_team = new ArrayList<ChessPosition>();
    }

    /**
     * @return Which team's turn it is
     */

//    Setting up variables that we'll probably be using later

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

    public boolean king_escape_w(ChessBoard board, TeamColor color){
//      This checks to see if the king can escape
        King_W = find_king(board, color);
        ChessPiece king = board.getPiece(King_W);
        ArrayList<ChessMove> escape = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> possible_moves = new ArrayList<ChessPosition>();
        escape.addAll(king.move_calc(King_W, board, king));
        ArrayList <ChessPosition> endings = new ArrayList<ChessPosition>(possible_black_ends(board));
        for(int i = 0; i< escape.size(); i++){
            ChessMove current = escape.get(i);
            ChessPosition end_point = current.getEndPosition();
            possible_moves.add(end_point);
        }
        for(int x = 0; x<possible_moves.size(); x++){
            boolean open = Arrays.asList(endings).contains(possible_moves.get(x));
            if(!open){
                return true;
//              This means the king has at least one escape option by moving it's self.
            }
        }
        return false;
    }
    public boolean king_escape_b(ChessBoard board, TeamColor color){
//      This checks to see if the king can escape
        King_B = find_king(board, color);
        ChessPiece king = board.getPiece(King_B);
        ArrayList<ChessMove> escape = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> possible_moves = new ArrayList<ChessPosition>();
        escape.addAll(king.move_calc(King_B, board, king));
        ArrayList <ChessPosition> endings = new ArrayList<ChessPosition>(possible_white_ends(board));
        for(int i = 0; i< escape.size(); i++){
            ChessMove current = escape.get(i);
            ChessPosition end_point = current.getEndPosition();
            possible_moves.add(end_point);
        }
        for(int x = 0; x<possible_moves.size(); x++){
            boolean open = Arrays.asList(endings).contains(possible_moves.get(x));
            if(!open){
                return true;
//              This means the king has at least one escape option by moving it's self.
            }
        }
        return false;
    }

    public TeamColor getTeamTurn() {
        return team_turn;
    }

    public void find_white_team(ChessBoard board){
        white_team.clear();
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
        black_team.clear();
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

    public Collection<ChessPosition> possible_white_ends(ChessBoard board){
//      If full is true, we run the full thing.  Otherwise, we just get the moves.
        ArrayList<ChessMove> possible_moves = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> end_points = new ArrayList<ChessPosition>();
        for(int i = 0; i<white_team.size(); i++){
            ChessPiece hold = board.getPiece(white_team.get(i));
            possible_moves.addAll(hold.move_calc(white_team.get(i), board, hold));
        }
        
        for(int i = 0; i<possible_moves.size(); i++){
            ChessMove current = possible_moves.get(i);
            ChessPosition end = current.getEndPosition();
            end_points.add(end);
        }
        return end_points;
    }

    public Collection<ChessPosition> possible_black_ends(ChessBoard board){
        ArrayList<ChessMove> possible_moves = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> end_points = new ArrayList<ChessPosition>();
        for(int i = 0; i<black_team.size(); i++){
            ChessPiece hold = board.getPiece(black_team.get(i));
            possible_moves.addAll(hold.move_calc(black_team.get(i), board, hold));
        }
        for(int i = 0; i<possible_moves.size(); i++){
            ChessMove current = possible_moves.get(i);
            ChessPosition end = current.getEndPosition();
            end_points.add(end);
        }
        return end_points;
    }

    public Collection<ChessPosition> possible_white_start(ChessBoard board){
//      If full is true, we run the full thing.  Otherwise, we just get the moves.
        ArrayList<ChessMove> possible_moves = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> end_points = new ArrayList<ChessPosition>();
        for(int i = 0; i<white_team.size(); i++){
            ChessPiece hold = board.getPiece(white_team.get(i));
            possible_moves.addAll(hold.move_calc(white_team.get(i), board, hold));
        }
        for(int i = 0; i<possible_moves.size(); i++){
            ChessMove current = possible_moves.get(i);
            ChessPosition start = current.getStartPosition();
            end_points.add(start);
        }
        return end_points;
    }

    public Collection<ChessPosition> possible_black_start(ChessBoard board){
        ArrayList<ChessMove> possible_moves = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> end_points = new ArrayList<ChessPosition>();
        for(int i = 0; i<black_team.size(); i++){
            ChessPiece hold = board.getPiece(black_team.get(i));
            possible_moves.addAll(hold.move_calc(black_team.get(i), board, hold));
        }
        for(int i = 0; i<possible_moves.size(); i++){
            ChessMove current = possible_moves.get(i);
            ChessPosition start = current.getStartPosition();
            end_points.add(start);
        }
        return end_points;
    }

    public boolean white_in_check(ChessBoard board){
        find_black_team(board);
        ArrayList <ChessPosition> endings = new ArrayList<ChessPosition>(possible_black_ends(board));
        King_W = find_king(board, TeamColor.WHITE);
        for(int x = 0; x<endings.size(); x++){
            if(endings.get(x).equals(King_W)){
                White_check = true;
            }
        }
        return White_check;
    }

    public boolean black_in_check(ChessBoard board){
        find_white_team(board);
        ArrayList <ChessPosition> endings = new ArrayList<ChessPosition>(possible_white_ends(board));
        King_B = find_king(board, TeamColor.BLACK);
        for(int x = 0; x<endings.size(); x++){
            System.out.println(endings.get(x));
            if(endings.get(x).equals(King_B)){
                Black_check = true;
            }
        }
        return Black_check;
    }

    public Collection<ChessMove> valid_white(){
        return null;
    }
    public Collection<ChessMove> valid_black(){
        return null;
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
        ChessPiece piece = board.getPiece(startPosition);
        ArrayList <ChessMove> valid_moves = new ArrayList<ChessMove>();
        ArrayList <ChessMove> moves_to_check = new ArrayList<ChessMove>();
        moves_to_check.addAll(piece.move_calc(startPosition, board, piece));
        if(piece.getTeamColor() == TeamColor.WHITE){
            return valid_moves;
            /*
            * Okay, I know I'm close, here's what I need to do.
            * I need to figure out how to clone the board.  Ideally using the constructor method.
            * I need to move pieces around and then call the is in check method on it
            * If the piece results in not being in check, add it to the valid moves array and return it.
            * Reset the cloned board and repeat.
            * */
        }
        else if(piece.getTeamColor() == TeamColor.BLACK){
            return valid_moves;
        }
        else{
            System.out.println("Something has gone wrong to get to this point, check your logic");
            return valid_moves;
        }
        /*
        for loop calling make move for all possible moves?

        return all valid moves after adding them to an array.
        * */
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
        /*
        Calls valid moves
        * */
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        if(teamColor == TeamColor.WHITE){
            return white_in_check(board);
        }
        if(teamColor == TeamColor.BLACK){
            return black_in_check(board);
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
//        Just call valid moves
        if(teamColor==TeamColor.WHITE){
//          Base case, if we're not in check, we're not in check mate.
            if(!White_check){
                return false;
            }
//          Second case, if the king can move, it's not check mate.
            boolean king_move = king_escape_w(board, teamColor);
            if(king_move){
                return false;
            }
//          Last resort, move a piece to interrupt check.
            ArrayList <ChessPosition> valid_pass_in = new ArrayList<ChessPosition>();
            valid_pass_in.addAll(possible_white_start(board)); //This gives us all possible moves that the white team can make.
        }
        if(teamColor==TeamColor.BLACK){
            if(!Black_check){
                return false;
            }
            boolean king_move = king_escape_b(board, teamColor);
            if(king_move){
                return false;
            }
        }
        else{
            System.out.println("Something has gone horribly wrong to hit this statement.");
            return false;
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if(teamColor==TeamColor.WHITE){
            if (White_check){
                return false;
            }
        }
        if(teamColor==TeamColor.BLACK){
            if(Black_check){
                return false;
            }
        }
        else{
            System.out.println("Something has gone horribly wrong to hit this statement.");
            return false;
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return White_check == chessGame.White_check && Black_check == chessGame.Black_check && Objects.equals(board, chessGame.board) && team_turn == chessGame.team_turn && Objects.equals(King_W, chessGame.King_W) && Objects.equals(King_B, chessGame.King_B) && Objects.equals(white_team, chessGame.white_team) && Objects.equals(black_team, chessGame.black_team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, team_turn, White_check, Black_check, King_W, King_B, white_team, black_team);
    }
}
