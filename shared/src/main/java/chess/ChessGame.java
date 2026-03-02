package chess;

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
    boolean whiteCheck;
    boolean blackCheck;
    ChessPosition kingW;
    ChessPosition kingB;
    ArrayList <ChessPosition> whiteTeam;
    ArrayList <ChessPosition> blackTeam;

    public ChessGame() {
        this.team_turn = TeamColor.WHITE;
        this.board = new ChessBoard();
        this.board.resetBoard();
        whiteCheck = false;
        blackCheck = false;
        kingW = find_king(this.board, TeamColor.WHITE);
        kingB = find_king(this.board, TeamColor.BLACK);
        whiteTeam = new ArrayList<ChessPosition>();
        blackTeam = new ArrayList<ChessPosition>();
    }

    // @return Which team's turn it is

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
        kingW = find_king(board, color);
        ChessPiece king = board.getPiece(kingW);
        ArrayList<ChessMove> escape = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> possible_moves = new ArrayList<ChessPosition>();
        escape.addAll(king.moveCalc(kingW, board, king));
        ArrayList <ChessPosition> endings = new ArrayList<ChessPosition>();
        endings.addAll(possible_black_ends(board));
        for(int i = 0; i< escape.size(); i++){
            ChessMove current = escape.get(i);
            ChessPosition end_point = current.getEndPosition();
            possible_moves.add(end_point);
        }
        for(int x = 0; x<possible_moves.size(); x++){
            boolean open = Arrays.asList(endings).contains(possible_moves.get(x));
            if(open){
                return true;
//              This means the king has at least one escape option by moving it's self.
            }
        }
        return false;
    }
    public boolean king_escape_b(ChessBoard board, TeamColor color){
//      This checks to see if the king can escape
        kingB = find_king(board, color);
        ChessPiece king = board.getPiece(kingB);

        ArrayList<ChessMove> escape = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> possible_moves = new ArrayList<ChessPosition>();
        escape.addAll(king.moveCalc(kingB, board, king));
        ArrayList <ChessPosition> endings = new ArrayList<ChessPosition>(possible_white_ends(board));
        for(int i = 0; i< escape.size(); i++){
            ChessMove current = escape.get(i);
            ChessPosition end_point = current.getEndPosition();
            possible_moves.add(end_point);
        }
        for(int x = 0; x<possible_moves.size(); x++){
            boolean open =  Arrays.asList(endings).contains(possible_moves.get(x));
            if(open){
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
        whiteTeam.clear();
        for(int x = 1; x<9; x++){
            for(int y = 1; y<9; y++){
                ChessPosition finder = new ChessPosition(x, y);
                ChessPiece found = board.getPiece(finder);
                if (found != null && found.getTeamColor() == TeamColor.WHITE){
                    whiteTeam.add(finder);
                }
            }
        }
    }

    public void find_black_team(ChessBoard board){
        blackTeam.clear();
        for(int x = 1; x<9; x++){
            for(int y = 1; y<9; y++){
                ChessPosition finder = new ChessPosition(x, y);
                ChessPiece found = board.getPiece(finder);
                if (found != null && found.getTeamColor() == TeamColor.BLACK){
                    blackTeam.add(finder);
                }
            }
        }
    }

    public Collection<ChessPosition> possible_white_ends(ChessBoard board){
//      If full is true, we run the full thing.  Otherwise, we just get the moves.
        ArrayList<ChessMove> possible_moves = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> end_points = new ArrayList<ChessPosition>();
        for(int i = 0; i< whiteTeam.size(); i++){
            ChessPiece hold = board.getPiece(whiteTeam.get(i));
            possible_moves.addAll(hold.moveCalc(whiteTeam.get(i), board, hold));
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
        for(int i = 0; i< blackTeam.size(); i++){
            ChessPiece hold = board.getPiece(blackTeam.get(i));
            possible_moves.addAll(hold.moveCalc(blackTeam.get(i), board, hold));
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
        for(int i = 0; i< whiteTeam.size(); i++){
            ChessPiece hold = board.getPiece(whiteTeam.get(i));
            possible_moves.addAll(hold.moveCalc(whiteTeam.get(i), board, hold));
        }
        for(int i = 0; i<possible_moves.size(); i++){
            ChessMove current = possible_moves.get(i);
            ChessPosition start = current.getStartPosition();
            end_points.add(start);
        }
        return end_points;
    }

    public Collection<ChessPosition> possible_black_start(ChessBoard board){
        find_black_team(board);
        ArrayList<ChessMove> possible_moves = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> end_points = new ArrayList<ChessPosition>();
        for(int i = 0; i< blackTeam.size(); i++){
            ChessPiece hold = board.getPiece(blackTeam.get(i));
            possible_moves.addAll(hold.moveCalc(blackTeam.get(i), board, hold));
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
        whiteCheck = false;
        ArrayList <ChessPosition> endings = new ArrayList<ChessPosition>(possible_black_ends(board));
        kingW = find_king(board, TeamColor.WHITE);
        for(int x = 0; x<endings.size(); x++){
            if(endings.get(x).equals(kingW)){
                whiteCheck = true;
            }
        }
        return whiteCheck;
    }

    public boolean black_in_check(ChessBoard board){
        find_white_team(board);
        blackCheck = false;
        ArrayList <ChessPosition> endings = new ArrayList<ChessPosition>(possible_white_ends(board));
        kingB = find_king(board, TeamColor.BLACK);
        for(int x = 0; x<endings.size(); x++){
            blackCheck = false;
            if(endings.get(x).equals(kingB)){
                blackCheck = true;
                break;
            }
        }
        return blackCheck;
    }

    /* Set's which teams turn it is
     * @param team the team whose turn it is     */
    public void setTeamTurn(TeamColor team) {
        team_turn = team;
    }

// Possible teams
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /* Gets a valid moves for a piece at the given location
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        ArrayList <ChessMove> valid_moves = new ArrayList<ChessMove>();
        ArrayList <ChessMove> moves_to_check = new ArrayList<ChessMove>();
        moves_to_check.addAll(piece.moveCalc(startPosition, board, piece));
        if(piece.getTeamColor() == TeamColor.WHITE){
            for(int x = 0; x<moves_to_check.size(); x++){
                ChessBoard cloned = new ChessBoard(board);
                ChessPosition start_point = moves_to_check.get(x).getStartPosition();
                ChessPosition end_point = moves_to_check.get(x).getEndPosition();
                //Clone board here
                cloned.addPiece(end_point, board.getPiece(startPosition));
                cloned.addPiece(start_point, null);
                boolean validated = white_in_check(cloned);
                if(!validated){
                    valid_moves.add(moves_to_check.get(x));
                }
            }
            return valid_moves;
        }
        else if(piece.getTeamColor() == TeamColor.BLACK){
            for(int x = 0; x<moves_to_check.size(); x++){
                ChessBoard cloned = new ChessBoard(board);
                ChessPosition start_point = moves_to_check.get(x).getStartPosition();
                ChessPosition end_point = moves_to_check.get(x).getEndPosition();
                //Clone board here
                cloned.addPiece(end_point, board.getPiece(startPosition));
                cloned.addPiece(start_point, null);
                boolean validated = black_in_check(cloned);
                if(!validated){
                    valid_moves.add(moves_to_check.get(x));
                }
            }
           return valid_moves;
        }
        else{
            System.out.println("Something has gone wrong to get to this point, check your logic");
            return valid_moves;
        }
    }
    /** Makes a move in a chess game
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid*/
    public void makeMove(ChessMove move) throws InvalidMoveException {
        InvalidMoveException wrong = new InvalidMoveException();
        ChessPiece has_piece = board.getPiece(move.getStartPosition());
        if(has_piece == null){
            throw wrong;
        }
        if(has_piece.getTeamColor() != team_turn){
            throw wrong;
        }
        ChessPosition mover = move.getStartPosition();
        ArrayList <ChessMove> is_valid = new ArrayList<ChessMove>();
        is_valid.addAll(validMoves(mover));
        ArrayList <ChessPosition> endings = new ArrayList<ChessPosition>();
        for(int x = 0; x<is_valid.size(); x++){
            ChessMove current = is_valid.get(x);
            ChessPosition current_end = current.getEndPosition();
            endings.add(current_end);
        }

        if(is_valid.isEmpty()){

            throw wrong;
        }
        else{
            ArrayList<ChessMove> possible_moves = new ArrayList<ChessMove>();
            possible_moves.addAll(has_piece.moveCalc(move.getStartPosition(), board, has_piece));
            ArrayList<ChessPosition> possible_ends = new ArrayList<ChessPosition>();
            for (int x = 0; x < possible_moves.size(); x++) {
                ChessMove current = possible_moves.get(x);
                ChessPosition end_point = current.getEndPosition();
                possible_ends.add(end_point);
            }
            boolean can_move = false;
            for (int x = 0; x < possible_moves.size(); x++) {
                if(possible_ends.get(x).equals(move.getEndPosition())){
                    can_move = true;
                    break;
                }
            }
            if(!can_move){
                throw wrong;
            }
            ChessPosition start_point = move.getStartPosition();
            ChessPosition end_point = move.getEndPosition();
            //Clone board here
            if(move.getPromotionPiece() == null) {
                board.addPiece(end_point, board.getPiece(start_point));
                board.addPiece(start_point, null);
            }
            else if(move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT){
                board.addPiece(end_point, new ChessPiece(team_turn, ChessPiece.PieceType.KNIGHT));
                board.addPiece(start_point, null);
            }
            else if(move.getPromotionPiece() == ChessPiece.PieceType.ROOK){
                board.addPiece(end_point, new ChessPiece(team_turn, ChessPiece.PieceType.ROOK));
                board.addPiece(start_point, null);
            }
            else if(move.getPromotionPiece() == ChessPiece.PieceType.QUEEN){
                board.addPiece(end_point, new ChessPiece(team_turn, ChessPiece.PieceType.QUEEN));
                board.addPiece(start_point, null);
            }
            else if(move.getPromotionPiece() == ChessPiece.PieceType.BISHOP){
                board.addPiece(end_point, new ChessPiece(team_turn, ChessPiece.PieceType.BISHOP));
                board.addPiece(start_point, null);
            }
            if(team_turn == TeamColor.BLACK){
                team_turn = TeamColor.WHITE;
            }
            else if(team_turn == TeamColor.WHITE){
                team_turn = TeamColor.BLACK;
            }
        }
    }

    /** Determines if the given team is in check
     * @param teamColor which team to check for check
     * @return True if the specified team is in check     */
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
    /* Determines if the given team is in checkmate
     * @param teamColor which team to check for checkmate
 @return True if the specified team is in checkmate */
    public boolean isInCheckmate(TeamColor teamColor) {
//        Just call valid moves
        if(teamColor==TeamColor.WHITE){
//          Base case, if we're not in check, we're not in check mate.
            whiteCheck = white_in_check(board);
            if(!whiteCheck){
                return false;
            }
//          Last resort, move a piece to interrupt check.
            ArrayList <ChessPosition> valid_pass_in = new ArrayList<ChessPosition>();
            ArrayList <ChessMove> valid_outs = new ArrayList<ChessMove>();
            valid_pass_in.addAll(possible_white_start(board)); //This gives us all possible moves that the white team can make.
            for(int y = 0; y<valid_pass_in.size(); y++){
                ChessPosition start_point = valid_pass_in.get(y);
                valid_outs.addAll(validMoves(start_point));
            }
            if(valid_outs.isEmpty()){
                //If it is empty there are no outs, we are in checkmate.
                return true;
            }
        }
        if(teamColor==TeamColor.BLACK){
            blackCheck = black_in_check(board);
            if(!blackCheck){
                return false;
            }
            //Last resort, move a piece to interrupt check.
            ArrayList <ChessPosition> valid_pass_in = new ArrayList<ChessPosition>();
            ArrayList <ChessMove> valid_outs = new ArrayList<ChessMove>();
            valid_pass_in.addAll(possible_black_start(board)); //This gives us all possible moves that the white team can make.
            for(int y = 0; y<valid_pass_in.size(); y++){
                ChessPosition start_point = valid_pass_in.get(y);
                valid_outs.addAll(validMoves(start_point));
            }
            if(valid_outs.isEmpty()){//If it is empty there are no outs, we are in checkmate.
                return true;
            }
        }
        else{
            System.out.println("Something has gone horribly wrong to hit this statement.");
            return false;
        }
        return false;
    }
    /* Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false*/
    public boolean isInStalemate(TeamColor teamColor) {
        if(teamColor==TeamColor.WHITE){
            whiteCheck = white_in_check(board);
            if (whiteCheck){
                return false;
            }
            boolean king_move = king_escape_w(board, teamColor);
            if(!king_move && whiteCheck ==true){
                return false;
            }
//          Last resort, move a piece to interrupt check.
            ArrayList <ChessPosition> valid_pass_in = new ArrayList<ChessPosition>();
            ArrayList <ChessMove> valid_outs = new ArrayList<ChessMove>();
            valid_pass_in.addAll(possible_white_start(board)); //This gives us all possible moves that the white team can make.
            for(int y = 0; y<valid_pass_in.size(); y++){
                ChessPosition start_point = valid_pass_in.get(y);
                valid_outs.addAll(validMoves(start_point));
            }
            if(valid_outs.isEmpty() && whiteCheck == false){ //If it is empty there are no outs, we are in checkmate
                return true;
            }
        }
        if(teamColor==TeamColor.BLACK){
            blackCheck = black_in_check(board);
            if(blackCheck){
                return false;
            }
            ArrayList<ChessMove> valid_out=new ArrayList<ChessMove>();
            ChessPosition king_position = find_king(board, TeamColor.BLACK);
            valid_out.addAll(validMoves(king_position));

            if(valid_out.size()>0){
                return false;
            }
//          Last resort, move a piece to interrupt check.
            ArrayList <ChessPosition> valid_pass_in = new ArrayList<ChessPosition>();
            ArrayList <ChessMove> valid_outs = new ArrayList<ChessMove>();
            valid_pass_in.addAll(possible_black_start(board)); //This gives us all possible moves that the black team can make.
            for(int y = 0; y<valid_pass_in.size(); y++){
                ChessPosition start_point = valid_pass_in.get(y);
                valid_outs.addAll(validMoves(start_point));
            }
            if(valid_outs.isEmpty() && blackCheck == false){
                //If it is empty there are no outs, we are in checkmate.
                return true;
            }
        }
        else{
            System.out.println("Something has gone horribly wrong to hit this statement.");
            return false;
        }
        return false;
    }
    /* Sets this game's chessboard with a given board
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }
    /**Gets the current chessboard
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
        return whiteCheck == chessGame.whiteCheck && blackCheck == chessGame.blackCheck && Objects.equals(board, chessGame.board) && team_turn == chessGame.team_turn && Objects.equals(kingW, chessGame.kingW) && Objects.equals(kingB, chessGame.kingB) && Objects.equals(whiteTeam, chessGame.whiteTeam) && Objects.equals(blackTeam, chessGame.blackTeam);
    }
    @Override
    public int hashCode() {
        return Objects.hash(board, team_turn, whiteCheck, blackCheck, kingW, kingB, whiteTeam, blackTeam);
    }
}