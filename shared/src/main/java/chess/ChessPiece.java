package chess;

import java.util.Collection;
import java.util.List;

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

    public Collection<ChessMove> piece_movement(String piece, ChessPosition myPosition){
        // I want this to take the piece and use an if statement to see where the piece can move.  From there I want it to return the possible options of that
        // that pieces movement.
        // possible_moves = []
        /*
        if (piece == "pawn"){
            if (myPosition == 2){ //the piece is on home row and can move two
                possible_moves.append(myPosition.row+1, myPosition.col)
                possible_moves.append(myPosition.row+2, myPosition.col)
                return possible_moves
            } 
            else{
            possible_moves.append(myPosition.row+1)
            retrun possible_moves
            }
        }

        if (piece == "rook"){ // can move any number of spaces up, down, left or right
            start_row = myPosition.row
            start_col = myPosition.col
            if (start_row == 1){
                for x in range 7{
                   possible_moves.append(myPosition.row+x, myPosiiton.col)
                }
                
            }
            else{
                while start_row != 1{
                    possible_moves.append(myPosition.row-1, myPosiiton.col);
                    start_row -=1;
                }
                start_row = myPosition.row
                while start_row != 8{
                    possible_moves.append(myPosition.row+1, myPosiiton.col)
                    start_row+=1
                }    
            }
            if (start_col == 1){
                for x in range 7{
                   possible_moves.append(myPosition.row, myPosiiton.col+x)
                }
            }
            else{
                while start_col != 1{
                    possible_moves.append(myPosition.row, myPosiiton.col-1);
                    start_col -=1;
                }
                start_col = myPosition.col
                while start_col != 8{
                    possible_moves.append(myPosition.row, myPosiiton.col+1)
                    start_col+=1
                }    
            }

        }
        */
                
            return null;
        
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
            return List.of();
        }
        return List.of();
    }
}
