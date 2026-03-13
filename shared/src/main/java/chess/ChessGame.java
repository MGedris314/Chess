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
    TeamColor teamTurn;
    boolean whiteCheck;
    boolean blackCheck;
    ChessPosition kingW;
    ChessPosition kingB;
    ArrayList <ChessPosition> whiteTeam;
    ArrayList <ChessPosition> blackTeam;

    public ChessGame() {
        this.teamTurn = TeamColor.WHITE;
        this.board = new ChessBoard();
        this.board.resetBoard();
        whiteCheck = false;
        blackCheck = false;
        kingW = findKing(this.board, TeamColor.WHITE);
        kingB = findKing(this.board, TeamColor.BLACK);
        whiteTeam = new ArrayList<ChessPosition>();
        blackTeam = new ArrayList<ChessPosition>();
    }

    // @return Which team's turn it is

    public ChessPosition findKing(ChessBoard board, TeamColor color){
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

    public boolean kingEscapeW(ChessBoard board, TeamColor color){
//      This checks to see if the king can escape
        kingW = findKing(board, color);
        ChessPiece king = board.getPiece(kingW);
        ArrayList<ChessMove> escape = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> possibleMoves = new ArrayList<ChessPosition>();
        escape.addAll(king.moveCalc(kingW, board, king));
        ArrayList <ChessPosition> endings = new ArrayList<ChessPosition>();
        endings.addAll(possibleBlackEnds(board));
        for(int i = 0; i< escape.size(); i++){
            ChessMove current = escape.get(i);
            ChessPosition endPosition = current.getEndPosition();
            possibleMoves.add(endPosition);
        }
        for(int x = 0; x<possibleMoves.size(); x++){
            boolean open = Arrays.asList(endings).contains(possibleMoves.get(x));
            if(open){
                return true;
//              This means the king has at least one escape option by moving it's self.
            }
        }
        return false;
    }

    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    public void findWhiteTeam(ChessBoard board){
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

    public void findBlackTeam(ChessBoard board){
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

    public Collection<ChessPosition> possibleWhiteEnds(ChessBoard board){
//      If full is true, we run the full thing.  Otherwise, we just get the moves.
        ArrayList<ChessMove> possibleMoves = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> endPoints = new ArrayList<ChessPosition>();
        for(int i = 0; i< whiteTeam.size(); i++){
            ChessPiece hold = board.getPiece(whiteTeam.get(i));
            possibleMoves.addAll(hold.moveCalc(whiteTeam.get(i), board, hold));
        }
        
        for(int i = 0; i<possibleMoves.size(); i++){
            ChessMove current = possibleMoves.get(i);
            ChessPosition end = current.getEndPosition();
            endPoints.add(end);
        }
        return endPoints;
    }

    public Collection<ChessPosition> possibleBlackEnds(ChessBoard board){
        ArrayList<ChessMove> possibleMoves = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> endPoints = new ArrayList<ChessPosition>();
        for(int i = 0; i< blackTeam.size(); i++){
            ChessPiece hold = board.getPiece(blackTeam.get(i));
            possibleMoves.addAll(hold.moveCalc(blackTeam.get(i), board, hold));
        }
        for(int i = 0; i<possibleMoves.size(); i++){
            ChessMove current = possibleMoves.get(i);
            ChessPosition end = current.getEndPosition();
            endPoints.add(end);
        }
        return endPoints;
    }

    public Collection<ChessPosition> possibleWhiteStart(ChessBoard board){
//      If full is true, we run the full thing.  Otherwise, we just get the moves.
        ArrayList<ChessMove> possibleMoves = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> endPoints = new ArrayList<ChessPosition>();
        for(int i = 0; i< whiteTeam.size(); i++){
            ChessPiece hold = board.getPiece(whiteTeam.get(i));
            possibleMoves.addAll(hold.moveCalc(whiteTeam.get(i), board, hold));
        }
        for(int i = 0; i<possibleMoves.size(); i++){
            ChessMove current = possibleMoves.get(i);
            ChessPosition start = current.getStartPosition();
            endPoints.add(start);
        }
        return endPoints;
    }

    public Collection<ChessPosition> possibleBlackStart(ChessBoard board){
        findBlackTeam(board);
        ArrayList<ChessMove> possibleMoves = new ArrayList<ChessMove>();
        ArrayList<ChessPosition> endPoints = new ArrayList<ChessPosition>();
        for(int i = 0; i< blackTeam.size(); i++){
            ChessPiece hold = board.getPiece(blackTeam.get(i));
            possibleMoves.addAll(hold.moveCalc(blackTeam.get(i), board, hold));
        }
        for(int i = 0; i<possibleMoves.size(); i++){
            ChessMove current = possibleMoves.get(i);
            ChessPosition start = current.getStartPosition();
            endPoints.add(start);
        }
        return endPoints;
    }

    public boolean whiteInCheck(ChessBoard board){
        findBlackTeam(board);
        whiteCheck = false;
        ArrayList <ChessPosition> endings = new ArrayList<ChessPosition>(possibleBlackEnds(board));
        kingW = findKing(board, TeamColor.WHITE);
        for(int x = 0; x<endings.size(); x++){
            if(endings.get(x).equals(kingW)){
                whiteCheck = true;
            }
        }
        return whiteCheck;
    }

    public boolean blackInCheck(ChessBoard board){
        findWhiteTeam(board);
        blackCheck = false;
        ArrayList <ChessPosition> endings = new ArrayList<ChessPosition>(possibleWhiteEnds(board));
        kingB = findKing(board, TeamColor.BLACK);
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
        teamTurn = team;
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
        ArrayList <ChessMove> validMoves = new ArrayList<ChessMove>();
        ArrayList <ChessMove> movesToCheck = new ArrayList<ChessMove>();
        movesToCheck.addAll(piece.moveCalc(startPosition, board, piece));
        if(piece.getTeamColor() == TeamColor.WHITE){
            for(int x = 0; x<movesToCheck.size(); x++){
                ChessBoard cloned = new ChessBoard(board);
                ChessPosition startPoint = movesToCheck.get(x).getStartPosition();
                ChessPosition endPoint = movesToCheck.get(x).getEndPosition();
                //Clone board here
                cloned.addPiece(endPoint, board.getPiece(startPosition));
                cloned.addPiece(startPoint, null);
                boolean validated = whiteInCheck(cloned);
                if(!validated){
                    validMoves.add(movesToCheck.get(x));
                }
            }
            return validMoves;
        }
        else if(piece.getTeamColor() == TeamColor.BLACK){
            for(int x = 0; x<movesToCheck.size(); x++){
                ChessBoard cloned = new ChessBoard(board);
                ChessPosition startPoint = movesToCheck.get(x).getStartPosition();
                ChessPosition endPoint = movesToCheck.get(x).getEndPosition();
                //Clone board here
                cloned.addPiece(endPoint, board.getPiece(startPosition));
                cloned.addPiece(startPoint, null);
                boolean validated = blackInCheck(cloned);
                if(!validated){
                    validMoves.add(movesToCheck.get(x));
                }
            }
           return validMoves;
        }
        else{
            System.out.println("Something has gone wrong to get to this point, check your logic");
            return validMoves;
        }
    }
    /** Makes a move in a chess game
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid*/
    public void makeMove(ChessMove move) throws InvalidMoveException {
        InvalidMoveException wrong = new InvalidMoveException();
        ChessPiece hasPiece = board.getPiece(move.getStartPosition());
        if(hasPiece == null){
            throw wrong;
        }
        if(hasPiece.getTeamColor() != teamTurn){
            throw wrong;
        }
        ChessPosition mover = move.getStartPosition();
        ArrayList <ChessMove> isValid = new ArrayList<ChessMove>();
        isValid.addAll(validMoves(mover));
        ArrayList <ChessPosition> endings = new ArrayList<ChessPosition>();
        for(int x = 0; x<isValid.size(); x++){
            ChessMove current = isValid.get(x);
            ChessPosition currentEnd = current.getEndPosition();
            endings.add(currentEnd);
        }

        if(isValid.isEmpty()){

            throw wrong;
        }
        else{
            ArrayList<ChessMove> possibleMoves = new ArrayList<ChessMove>();
            possibleMoves.addAll(hasPiece.moveCalc(move.getStartPosition(), board, hasPiece));
            ArrayList<ChessPosition> possibleEnds = new ArrayList<ChessPosition>();
            for (int x = 0; x < possibleMoves.size(); x++) {
                ChessMove current = possibleMoves.get(x);
                ChessPosition endPoint = current.getEndPosition();
                possibleEnds.add(endPoint);
            }
            boolean canMove = false;
            for (int x = 0; x < possibleMoves.size(); x++) {
                if(possibleEnds.get(x).equals(move.getEndPosition())){
                    canMove = true;
                    break;
                }
            }
            if(!canMove){
                throw wrong;
            }
            ChessPosition startPoint = move.getStartPosition();
            ChessPosition endPoint = move.getEndPosition();
            //Clone board here
            if(move.getPromotionPiece() == null) {
                board.addPiece(endPoint, board.getPiece(startPoint));
                board.addPiece(startPoint, null);
            }
            else if(move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT){
                board.addPiece(endPoint, new ChessPiece(teamTurn, ChessPiece.PieceType.KNIGHT));
                board.addPiece(startPoint, null);
            }
            else if(move.getPromotionPiece() == ChessPiece.PieceType.ROOK){
                board.addPiece(endPoint, new ChessPiece(teamTurn, ChessPiece.PieceType.ROOK));
                board.addPiece(startPoint, null);
            }
            else if(move.getPromotionPiece() == ChessPiece.PieceType.QUEEN){
                board.addPiece(endPoint, new ChessPiece(teamTurn, ChessPiece.PieceType.QUEEN));
                board.addPiece(startPoint, null);
            }
            else if(move.getPromotionPiece() == ChessPiece.PieceType.BISHOP){
                board.addPiece(endPoint, new ChessPiece(teamTurn, ChessPiece.PieceType.BISHOP));
                board.addPiece(startPoint, null);
            }
            if(teamTurn == TeamColor.BLACK){
                teamTurn = TeamColor.WHITE;
            }
            else if(teamTurn == TeamColor.WHITE){
                teamTurn = TeamColor.BLACK;
            }
        }
    }

    /** Determines if the given team is in check
     * @param teamColor which team to check for check
     * @return True if the specified team is in check     */
    public boolean isInCheck(TeamColor teamColor) {
        if(teamColor == TeamColor.WHITE){
            return whiteInCheck(board);
        }
        if(teamColor == TeamColor.BLACK){
            return blackInCheck(board);
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
            whiteCheck = whiteInCheck(board);
            if(!whiteCheck){
                return false;
            }
//          Last resort, move a piece to interrupt check.
            ArrayList <ChessPosition> validPassIn = new ArrayList<ChessPosition>();
            ArrayList <ChessMove> validOuts = new ArrayList<ChessMove>();
            validPassIn.addAll(possibleWhiteStart(board)); //This gives us all possible moves that the white team can make.
            for(int y = 0; y<validPassIn.size(); y++){
                ChessPosition startPoint = validPassIn.get(y);
                validOuts.addAll(validMoves(startPoint));
            }
            if(validOuts.isEmpty()){
                //If it is empty there are no outs, we are in checkmate.
                return true;
            }
        }
        if(teamColor==TeamColor.BLACK){
            blackCheck = blackInCheck(board);
            if(!blackCheck){
                return false;
            }
            //Last resort, move a piece to interrupt check.
            ArrayList <ChessPosition> validPassIn = new ArrayList<ChessPosition>();
            ArrayList <ChessMove> validOuts = new ArrayList<ChessMove>();
            validPassIn.addAll(possibleBlackStart(board)); //This gives us all possible moves that the white team can make.
            for(int y = 0; y<validPassIn.size(); y++){
                ChessPosition startPoint = validPassIn.get(y);
                validOuts.addAll(validMoves(startPoint));
            }
            if(validOuts.isEmpty()){//If it is empty there are no outs, we are in checkmate.
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
            whiteCheck = whiteInCheck(board);
            if (whiteCheck){
                return false;
            }
            boolean kingMove = kingEscapeW(board, teamColor);
            if(!kingMove && whiteCheck ==true){
                return false;
            }
//          Last resort, move a piece to interrupt check.
            ArrayList <ChessPosition> validPassIn = new ArrayList<ChessPosition>();
            ArrayList <ChessMove> validOuts = new ArrayList<ChessMove>();
            validPassIn.addAll(possibleWhiteStart(board)); //This gives us all possible moves that the white team can make.
            for(int y = 0; y<validPassIn.size(); y++){
                ChessPosition startPoint = validPassIn.get(y);
                validOuts.addAll(validMoves(startPoint));
            }
            if(validOuts.isEmpty() && whiteCheck == false){ //If it is empty there are no outs, we are in checkmate
                return true;
            }
        }
        if(teamColor==TeamColor.BLACK){
            blackCheck = blackInCheck(board);
            if(blackCheck){
                return false;
            }
            ArrayList<ChessMove> validOut=new ArrayList<ChessMove>();
            ChessPosition kingPosition = findKing(board, TeamColor.BLACK);
            validOut.addAll(validMoves(kingPosition));

            if(validOut.size()>0){
                return false;
            }
//          Last resort, move a piece to interrupt check.
            ArrayList <ChessPosition> validPassIn = new ArrayList<ChessPosition>();
            ArrayList <ChessMove> validOuts = new ArrayList<ChessMove>();
            validPassIn.addAll(possibleBlackStart(board)); //This gives us all possible moves that the black team can make.
            for(int y = 0; y<validPassIn.size(); y++){
                ChessPosition startPoint = validPassIn.get(y);
                validOuts.addAll(validMoves(startPoint));
            }
            if(validOuts.isEmpty() && blackCheck == false){
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
        return whiteCheck == chessGame.whiteCheck && blackCheck == chessGame.blackCheck &&
                Objects.equals(board, chessGame.board) && teamTurn == chessGame.teamTurn &&
                Objects.equals(kingW, chessGame.kingW) && Objects.equals(kingB, chessGame.kingB) &&
                Objects.equals(whiteTeam, chessGame.whiteTeam) && Objects.equals(blackTeam, chessGame.blackTeam);
    }
    @Override
    public int hashCode() {
        return Objects.hash(board, teamTurn, whiteCheck, blackCheck, kingW, kingB, whiteTeam, blackTeam);
    }
}