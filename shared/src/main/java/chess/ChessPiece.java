package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }
    

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }
//    Use block bellow:
//    piece.getTeamColor().equals(ChessGame.TeamColor.WHITE
    public Collection<ChessMove> move_pawn(ChessPosition myposiiton, ChessBoard board, ChessPiece piece){
        int row = myposiiton.getRow();
        int col = myposiiton.getColumn();
        int home_r = 7;
        int end_r = 2;
        int direction = -1;
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
            home_r = 2;
            end_r = 7;
            direction = 1;
        }
        if(row == end_r){
            ChessPosition check_r = new ChessPosition(row + direction, col+1);
            ChessPosition check_l = new ChessPosition(row + direction, col-1);
            ChessPosition check_f = new ChessPosition(row + direction, col);
            if(col < 8) {
                ChessPiece piece_r = board.getPiece(check_r);
                if(piece_r != null && piece_r.getTeamColor() != piece.getTeamColor()){
                    moves.add(new ChessMove(myposiiton, check_r, PieceType.BISHOP));
                    moves.add(new ChessMove(myposiiton, check_r, PieceType.ROOK));
                    moves.add(new ChessMove(myposiiton, check_r, PieceType.QUEEN));
                    moves.add(new ChessMove(myposiiton, check_r, PieceType.KNIGHT));
                }
            }
            if(col > 1) {
                ChessPiece piece_l = board.getPiece(check_l);
                if(piece_l != null && piece_l.getTeamColor() != piece.getTeamColor()){
                    moves.add(new ChessMove(myposiiton, check_l, PieceType.BISHOP));
                    moves.add(new ChessMove(myposiiton, check_l, PieceType.ROOK));
                    moves.add(new ChessMove(myposiiton, check_l, PieceType.QUEEN));
                    moves.add(new ChessMove(myposiiton, check_l, PieceType.KNIGHT));
                }
            }
            ChessPiece piece_f = board.getPiece(check_f);
            if(piece_f == null){
                moves.add(new ChessMove(myposiiton, check_f, PieceType.BISHOP));
                moves.add(new ChessMove(myposiiton, check_f, PieceType.ROOK));
                moves.add(new ChessMove(myposiiton, check_f, PieceType.QUEEN));
                moves.add(new ChessMove(myposiiton, check_f, PieceType.KNIGHT));
            }
            return moves;
        }

        ChessPosition check_r = new ChessPosition(row + direction, col+1);
        ChessPosition check_l = new ChessPosition(row + direction, col-1);
        ChessPosition check_f = new ChessPosition(row + direction, col);
        if(col < 8) {
            ChessPiece piece_r = board.getPiece(check_r);
            if(piece_r != null && piece_r.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myposiiton, check_r, null));
            }
        }
        if(col > 1) {
            ChessPiece piece_l = board.getPiece(check_l);
            if(piece_l != null && piece_l.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myposiiton, check_l, null));
            }
        }
        ChessPiece piece_f = board.getPiece(check_f);
        if(piece_f == null){
            moves.add(new ChessMove(myposiiton, check_f, null));
        }
        if(row == home_r){
            ChessPosition check_d = new ChessPosition(row + (2*direction), col);
            ChessPiece piece_d = board.getPiece(check_d);
            if(piece_f == null && piece_d == null){
                moves.add(new ChessMove(myposiiton, check_d, null));
            }
        }
        return moves;
    }

    public Collection<ChessMove> move_knight(ChessPosition myPosition, ChessBoard board, ChessPiece piece){
        int row  = myPosition.getRow();
        int col = myPosition.getColumn();
        ArrayList<ChessMove>moves = new ArrayList<ChessMove>();
//      up:  Doesn't work rows 7 or >
        if (row < 7){
            if (col < 8){
                ChessPiece check1 = board.getPiece(new ChessPosition(row+2, col+1));
                if(check1 == null || !(check1.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col + 1), null));
                }
            }
            if (col > 1){
                ChessPiece check2 = board.getPiece(new ChessPosition(row+2, col-1));
                if(check2 == null || !(check2.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col - 1), null));
                }
            }
        }
//      down:
        if (row > 2) {
            if (col < 8) {
                ChessPiece check1 = board.getPiece(new ChessPosition(row-2, col+1));
                if(check1 == null || !(check1.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col + 1), null));
                }
            }
            if (col > 1) {
                ChessPiece check2 = board.getPiece(new ChessPosition(row-2, col-1));
                if(check2 == null || !(check2.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col - 1), null));
                }
            }
        }
//      Left:
        if (col > 2) {
            if (row < 8) {
                ChessPiece check1 = board.getPiece(new ChessPosition(row+1, col-2));
                if(check1 == null || !(check1.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 2), null));
                }
            }
            if (row > 1) {
                ChessPiece check2 = board.getPiece(new ChessPosition(row-1, col-2));
                if(check2 == null || !(check2.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 2), null));
                }
            }
        }
//      Right:
        if (col < 7) {
            if (row < 8) {
                ChessPiece check1 = board.getPiece(new ChessPosition(row+1, col+2));
                if(check1 == null || !(check1.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 2), null));
                }
            }
            if (row > 1) {
                ChessPiece check2 = board.getPiece(new ChessPosition(row-1, col+2));
                if(check2 == null || !(check2.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 2), null));
                }
            }
        }
        return moves;
    }

    public Collection<ChessMove> move_calc(ChessPosition myposiiton, ChessBoard board, ChessPiece piece){
        int row = myposiiton.getRow();
        int col = myposiiton.getColumn();
        int up = 8-row;
        int down = row-1;
        int right = 8-col;
        int left = col-1;
        boolean king = false;
        if(piece.type == PieceType.KING){king = true;}
        if(piece.type == PieceType.KNIGHT){return move_knight(myposiiton, board, piece);}
        if(piece.type == PieceType.PAWN){return move_pawn(myposiiton, board, piece);}

        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

//      N+ S- E+ W-
        for(int x = 1; x<8; x++){
            if(up == 0 || row+x>8|| piece.type == PieceType.BISHOP){break;}
            ChessPosition check = new ChessPosition(row + x, col);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){
                moves.add(new ChessMove(myposiiton, check, null));
            }
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myposiiton, check, null));
                break;
            }
            else{break;}
            if(king == true){break;}
        }
//      S
        for(int x = 1; x<8; x++){
            if(down == 0 || row-x<1|| piece.type == PieceType.BISHOP){break;}
            ChessPosition check = new ChessPosition(row - x, col);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){
                moves.add(new ChessMove(myposiiton, check, null));
            }
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myposiiton, check, null));
                break;
            }
            else{break;}
            if(king == true){break;}
        }
//      E
        for(int x = 1; x<8; x++){
            if(right == 0 || col+x>8 || piece.type == PieceType.BISHOP){break;}
            ChessPosition check = new ChessPosition(row, col + x);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){
                moves.add(new ChessMove(myposiiton, check, null));
            }
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myposiiton, check, null));
                break;
            }
            else{break;}
            if(king == true){break;}
        }
//      W
        for(int x = 1; x<8; x++){
            if(left == 0 || col-x<1 || piece.type == PieceType.BISHOP){break;}
            ChessPosition check = new ChessPosition(row, col-x);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){
                moves.add(new ChessMove(myposiiton, check, null));
            }
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myposiiton, check, null));
                break;
            }
            else{break;}
            if(king == true){break;}
        }
//      NE ++
        for(int x = 1; x<8; x++){
            if(up == 0 || right==0 || col+x>8 || row+x>8 || piece.type == PieceType.ROOK){break;}
            ChessPosition check = new ChessPosition(row + x, col+x);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){
                moves.add(new ChessMove(myposiiton, check, null));
            }
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myposiiton, check, null));
                break;
            }
            else{break;}
            if(king == true){break;}
        }
//      SE -+
        for(int x = 1; x<8; x++){
            if(down == 0 || right==0 || col+x>8 || row-x<1|| piece.type == PieceType.ROOK){break;}
            ChessPosition check = new ChessPosition(row - x, col +x);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){
                moves.add(new ChessMove(myposiiton, check, null));
            }
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myposiiton, check, null));
                break;
            }
            else{break;}
            if(king == true){break;}
        }
//      SW --
        for(int x = 1; x<8; x++){
            if(down == 0 || left==0 || row-x<1 || col-x<1 || piece.type == PieceType.ROOK){break;}
            ChessPosition check = new ChessPosition(row - x, col - x);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){
                moves.add(new ChessMove(myposiiton, check, null));
            }
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myposiiton, check, null));
                break;
            }
            else{break;}
            if(king == true){break;}
        }
//      NW +-
        for(int x = 1; x<8; x++){
            if(left == 0 || up==0 || row+x>8 || col-x<1 || piece.type == PieceType.ROOK){break;}
            ChessPosition check = new ChessPosition(row+x, col-x);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){
                moves.add(new ChessMove(myposiiton, check, null));
            }
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myposiiton, check, null));
                break;
            }
            else{break;}
            if(king == true){break;}
        }
        return moves;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // Returns all known locations that a piece can end up given a piece and a starting position.
        ChessPiece piece = board.getPiece(myPosition);
        if (piece.getPieceType() == PieceType.BISHOP){
            System.out.println(myPosition);
            return move_calc(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.KNIGHT){
            System.out.println(myPosition);
            return move_calc(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.ROOK){
            System.out.println(myPosition);
            return move_calc(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.PAWN){
            System.out.println(myPosition);
            return move_calc(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.QUEEN){
            System.out.println(myPosition);
            return move_calc(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.KING){
            System.out.println(myPosition);
            return move_calc(myPosition, board, piece);
        }
        return List.of();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}
