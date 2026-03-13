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
    public Collection<ChessMove> movePawn(ChessPosition myposiiton, ChessBoard board, ChessPiece piece){
        int row = myposiiton.getRow();
        int col = myposiiton.getColumn();
        int homeR = 7;
        int endR = 2;
        int direction = -1;
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
            homeR = 2;
            endR = 7;
            direction = 1;
        }
        if(row == endR){
            ChessPosition checkR = new ChessPosition(row + direction, col+1);
            ChessPosition checkL = new ChessPosition(row + direction, col-1);
            ChessPosition checkF = new ChessPosition(row + direction, col);
            if(col < 8) {
                ChessPiece pieceR = board.getPiece(checkR);
                if(pieceR != null && pieceR.getTeamColor() != piece.getTeamColor()){
                    moves.add(new ChessMove(myposiiton, checkR, PieceType.BISHOP));
                    moves.add(new ChessMove(myposiiton, checkR, PieceType.ROOK));
                    moves.add(new ChessMove(myposiiton, checkR, PieceType.QUEEN));
                    moves.add(new ChessMove(myposiiton, checkR, PieceType.KNIGHT));
                }
            }
            if(col > 1) {
                ChessPiece pieceL = board.getPiece(checkL);
                if(pieceL != null && pieceL.getTeamColor() != piece.getTeamColor()){
                    moves.add(new ChessMove(myposiiton, checkL, PieceType.BISHOP));
                    moves.add(new ChessMove(myposiiton, checkL, PieceType.ROOK));
                    moves.add(new ChessMove(myposiiton, checkL, PieceType.QUEEN));
                    moves.add(new ChessMove(myposiiton, checkL, PieceType.KNIGHT));
                }
            }
            ChessPiece pieceF = board.getPiece(checkF);
            if(pieceF == null){
                moves.add(new ChessMove(myposiiton, checkF, PieceType.BISHOP));
                moves.add(new ChessMove(myposiiton, checkF, PieceType.ROOK));
                moves.add(new ChessMove(myposiiton, checkF, PieceType.QUEEN));
                moves.add(new ChessMove(myposiiton, checkF, PieceType.KNIGHT));
            }
            return moves;
        }

        ChessPosition checkR = new ChessPosition(row + direction, col+1);
        ChessPosition checkL = new ChessPosition(row + direction, col-1);
        ChessPosition checkF = new ChessPosition(row + direction, col);
        if(col < 8) {
            ChessPiece pieceR = board.getPiece(checkR);
            if(pieceR != null && pieceR.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myposiiton, checkR, null));
            }
        }
        if(col > 1) {
            ChessPiece pieceL = board.getPiece(checkL);
            if(pieceL != null && pieceL.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myposiiton, checkL, null));
            }
        }
        ChessPiece pieceF = board.getPiece(checkF);
        if(pieceF == null){
            moves.add(new ChessMove(myposiiton, checkF, null));
        }
        if(row == homeR){
            ChessPosition checkD = new ChessPosition(row + (2*direction), col);
            ChessPiece pieceD = board.getPiece(checkD);
            if(pieceF == null && pieceD == null){
                moves.add(new ChessMove(myposiiton, checkD, null));
            }
        }
        return moves;
    }

    public Collection<ChessMove> moveKnight(ChessPosition myPosition, ChessBoard board, ChessPiece piece){
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

    public Collection<ChessMove>moveCalc(ChessPosition myPositon,ChessBoard board,ChessPiece piece){
        int row = myPositon.getRow();
        int col = myPositon.getColumn(); int up = 8-row;
        int down = row-1;
        int right = 8-col;
        int left = col-1;
        boolean king = false;
        if(piece.type == PieceType.KING){king = true;}
        if(piece.type == PieceType.KNIGHT){return moveKnight(myPositon, board, piece);}
        if(piece.type == PieceType.PAWN){return movePawn(myPositon, board, piece);}
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        for(int x = 1; x<8; x++){ //      N+ S- E+ W-
            if(up == 0 || row+x>8|| piece.type == PieceType.BISHOP){break;}
            ChessPosition check = new ChessPosition(row + x, col);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){moves.add(new ChessMove(myPositon, check, null));}
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myPositon, check, null));break;
            }
            else{break;}
            if(king == true){break;}
        }
        for(int x = 1; x<8; x++){ //      S
            if(down == 0 || row-x<1|| piece.type == PieceType.BISHOP){break;}
            ChessPosition check = new ChessPosition(row - x, col);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){moves.add(new ChessMove(myPositon, check, null));}
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myPositon, check, null));break;
            }
            else{break;}
            if(king == true){break;}
        }
        for(int x = 1; x<8; x++){ //      E
            if(right == 0 || col+x>8 || piece.type == PieceType.BISHOP){break;}
            ChessPosition check = new ChessPosition(row, col + x);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){moves.add(new ChessMove(myPositon, check, null));}
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myPositon, check, null));break;
            }
            else{break;}
            if(king == true){break;}
        }
        for(int x = 1; x<8; x++){ //      W
            if(left == 0 || col-x<1 || piece.type == PieceType.BISHOP){break;}
            ChessPosition check = new ChessPosition(row, col-x);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){moves.add(new ChessMove(myPositon, check, null));}
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myPositon, check, null));break;
            }
            else{break;}
            if(king == true){break;}
        }
        for(int x = 1; x<8; x++){ //      NE ++
            if(up == 0 || right==0 || col+x>8 || row+x>8 || piece.type == PieceType.ROOK){break;}
            ChessPosition check = new ChessPosition(row + x, col+x);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){moves.add(new ChessMove(myPositon, check, null));}
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myPositon, check, null));break;
            }
            else{break;}
            if(king == true){break;}
        }
        for(int x = 1; x<8; x++){ //      SE -+
            if(down == 0 || right==0 || col+x>8 || row-x<1|| piece.type == PieceType.ROOK){break;}
            ChessPosition check = new ChessPosition(row - x, col +x);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){moves.add(new ChessMove(myPositon, check, null));}
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myPositon, check, null));break;
            }
            else{break;}
            if(king == true){break;}
        }
        for(int x = 1; x<8; x++){ //      SW --
            if(down == 0 || left==0 || row-x<1 || col-x<1 || piece.type == PieceType.ROOK){break;}
            ChessPosition check = new ChessPosition(row - x, col - x);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){moves.add(new ChessMove(myPositon, check, null));}
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myPositon, check, null));break;
            }
            else{break;}
            if(king == true){break;}
        }
        for(int x = 1; x<8; x++){ //      NW +-
            if(left == 0 || up==0 || row+x>8 || col-x<1 || piece.type == PieceType.ROOK){break;}
            ChessPosition check = new ChessPosition(row+x, col-x);
            ChessPiece check1 = board.getPiece(check);
            if(check1 == null){moves.add(new ChessMove(myPositon, check, null));}
            else if(check1 != null && check1.getTeamColor() != piece.getTeamColor()){
                moves.add(new ChessMove(myPositon, check, null));break;
            }
            else{break;}
            if(king == true){break;}
        }
        return moves;}

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
            return moveCalc(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.KNIGHT){
            System.out.println(myPosition);
            return moveCalc(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.ROOK){
            System.out.println(myPosition);
            return moveCalc(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.PAWN){
            System.out.println(myPosition);
            return moveCalc(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.QUEEN){
            System.out.println(myPosition);
            return moveCalc(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.KING){
            System.out.println(myPosition);
            return moveCalc(myPosition, board, piece);
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
