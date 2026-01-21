package chess;

import java.util.ArrayList;
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

    public Collection<ChessMove> move_king(ChessPosition myPosition){
        ArrayList<ChessMove> moves =  new ArrayList<ChessMove>();
//      What we'll need to do for each of these is creates an array of objects of type chess moves.  Those appear as follows:
//      public ChessMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece)
//      With the exception of pawns set promotion piece to null.
//      Keep myPosition as the start position we only need to edit the end position.  Do that by using the position and then .get row/column

        if (myPosition.getRow() == 1){
//            Left edge
            if (myPosition.getColumn() == 1){
//              Bottom left corner
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),null));
            } else if (myPosition.getColumn() == 8) {
//                Bottom right corner
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1),null));
            }
            else{
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1),null));
            }

        }
        else if (myPosition.getRow() == 8){
//            Right edge
            if (myPosition.getColumn() == 1){
//              Top left corner
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1),null));
            } else if (myPosition.getColumn() == 8) {
//              Top right corner
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()),null));
            }
            else{
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()),null));
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1),null));
            }

        }
        else if (myPosition.getColumn() == 1){
//          Bottom Row
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1),null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()),null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1),null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1),null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),null));

        }
        else if (myPosition.getColumn() == 8){
//          Top Row
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1),null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()),null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1),null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1),null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),null));

        }
        else {
//      Default, one space in all directions.
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - 1), null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 1), null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1), null));
        }
        return moves;
    }

    public Collection<ChessMove> move_rook(ChessPosition myPosition){
//      A rook is capable of moving up, down, left, and right any amount of spaces available to a max of 7
    ArrayList<ChessMove> moves =  new ArrayList<ChessMove>();
//   Get starting values
     int start_col = myPosition.getColumn();
     int start_row = myPosition.getRow();
//           Find total movement abilities
     int to_left = start_col-1;
     int to_right = 8-start_col;
     int up = 8-start_row;
     int down = start_row-1;
     System.out.println(to_left);
     System.out.println(to_right);
     System.out.println(up);
     System.out.println(down);
     for (int x = to_left; x > 0; x--){
         moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-x), null));
     }
     for (int x = to_right; x > 0; x--){
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+x), null));
     }
     for (int x = up; x > 0; x--){
        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+x, myPosition.getColumn()), null));
     }
     for (int x = down; x > 0; x--){
        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-x, myPosition.getColumn()), null));
     }
        return moves;
    }

    public Collection<ChessMove> move_bishop(ChessPosition myPosition){
        ArrayList<ChessMove>moves =  new ArrayList<>();
/*  for reference, row first column second
*   NE: +x +x
*   SE: -x +x
*   SW: -x -x
*   NW: +x -x
* */
        int start_row = myPosition.getRow();
        int start_col = myPosition.getColumn();
//      NE
        int x = start_row; int y = start_col; int loop_count = 0;
        while( x<8 && y<8){
            loop_count++;
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+loop_count, myPosition.getColumn()+loop_count), null));
            x++;
            y++;
        }
//      SE
        x = start_row; y = start_col;  loop_count = 0;
        while( x<=8 && y<=8){
            loop_count++;
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-loop_count, myPosition.getColumn()+loop_count), null));
            x++;
            y++;
        }
//      SW
        x = start_row; y = start_col; loop_count = 0;
        while( x<8 && y<8){
            loop_count++;
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-loop_count, myPosition.getColumn()-loop_count), null));
            x++;
            y++;
        }
//      NW
        x = start_row; y = start_col; loop_count = 0;
        while( x<8 && y<8){
            loop_count++;
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+loop_count, myPosition.getColumn()-loop_count), null));
            x++;
            y++;
        }
        return moves;
    }

    public Collection<ChessMove> move_queen(ChessPosition myPosition){
        ArrayList<ChessMove> moves =  new ArrayList<ChessMove>();
//   Get starting values
        int start_col = myPosition.getColumn();
        int start_row = myPosition.getRow();
//           Find total movement abilities
        int to_left = start_col-1;
        int to_right = 8-start_col;
        int up = 8-start_row;
        int down = start_row-1;
        for (int x = to_left; x > 0; x--){
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-x), null));
        }
        for (int x = to_right; x > 0; x--){
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+x), null));
        }
        for (int x = up; x > 0; x--){
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+x, myPosition.getColumn()), null));
        }
        for (int x = down; x > 0; x--){
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-x, myPosition.getColumn()), null));
        }
//      NE
        int x = start_row; int y = start_col; int loop_count = 0;
        while( x<8 && y<8){
            loop_count++;
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+loop_count, myPosition.getColumn()+loop_count), null));
            x++;
            y++;
        }
//      SE
        x = start_row; y = start_col;  loop_count = 0;
        while( x<=8 && y<=8){
            loop_count++;
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-loop_count, myPosition.getColumn()+loop_count), null));
            x++;
            y++;
        }
//      SW
        x = start_row; y = start_col; loop_count = 0;
        while( x<8 && y<8){
            loop_count++;
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-loop_count, myPosition.getColumn()-loop_count), null));
            x++;
            y++;
        }
//      NW
        x = start_row; y = start_col; loop_count = 0;
        while( x<8 && y<8){
            loop_count++;
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+loop_count, myPosition.getColumn()-loop_count), null));
            x++;
            y++;
        }
        return moves;
    }

    public Collection<ChessMove> move_pawn(ChessPosition myPosition, ChessPiece piece, ChessBoard board){
//   I know the syntax for this isn't fully correct.  Run with it until we can get help tomorrow.
//   White moves up, black moves down.
        ArrayList<ChessMove> moves =  new ArrayList<ChessMove>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
            ChessPosition left_check = new ChessPosition(row+1, col-1);
            ChessPosition right_check = new ChessPosition(row+1, col+1);
            ChessPosition forward_check = new ChessPosition(row+1, col);
            ChessPosition double_forward = new ChessPosition(row+2, col);
            ChessPiece left_capture = board.getPiece(left_check);
            ChessPiece right_capture = board.getPiece(right_check);
            ChessPiece forward = board.getPiece((forward_check));
            ChessPiece double_check = board.getPiece((double_forward));
            if (left_capture != null){
                ChessGame.TeamColor left_color=left_capture.getTeamColor();
                if (left_color == ChessGame.TeamColor.BLACK && row+1 == 8){
                    moves.add(new ChessMove(myPosition, left_check, PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, left_check, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, left_check, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, left_check, PieceType.QUEEN));
                }
                else if (left_color == ChessGame.TeamColor.BLACK){
                    moves.add(new ChessMove(myPosition, left_check, null));
                }
            }
            if (right_capture != null){
                ChessGame.TeamColor right_color=right_capture.getTeamColor();
                if (right_color == ChessGame.TeamColor.BLACK && row+1 == 8){
                    moves.add(new ChessMove(myPosition, right_check, PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, right_check, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, right_check, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, right_check, PieceType.QUEEN));
                }
                else if (right_color == ChessGame.TeamColor.BLACK){
                    moves.add(new ChessMove(myPosition, right_check, null));
                }
            }
            if (forward == null && row+1 == 8) {
                moves.add(new ChessMove(myPosition, forward_check, PieceType.KNIGHT));
                moves.add(new ChessMove(myPosition, forward_check, PieceType.ROOK));
                moves.add(new ChessMove(myPosition, forward_check, PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, forward_check, PieceType.QUEEN));
            }
            else if(forward == null){
                moves.add(new ChessMove(myPosition, forward_check, null));
            }
            if (row == 2 && double_check == null){
                moves.add(new ChessMove(myPosition, forward_check, null));
                moves.add(new ChessMove(myPosition, double_forward, null));
            }

        }
        else if(piece.getTeamColor() == ChessGame.TeamColor.BLACK){
            ChessPosition left_check = new ChessPosition(row-1, col-1);
            ChessPosition right_check = new ChessPosition(row-1, col+1);
            ChessPosition forward_check = new ChessPosition(row-1, col);
            ChessPosition double_forward = new ChessPosition(row-2, col);
            ChessPiece left_capture = board.getPiece(left_check);
            ChessPiece right_capture = board.getPiece(right_check);
            ChessPiece forward = board.getPiece((forward_check));
            ChessPiece double_check = board.getPiece((double_forward));
            if (left_capture != null){
                ChessGame.TeamColor left_color=left_capture.getTeamColor();
                if (left_color == ChessGame.TeamColor.WHITE && row-1 ==1){
                    moves.add(new ChessMove(myPosition, left_check, PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, left_check, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, left_check, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, left_check, PieceType.QUEEN));
                }
                else if (left_color == ChessGame.TeamColor.WHITE){
                    moves.add(new ChessMove(myPosition, left_check, null));
                }
            }
            if (right_capture != null){
                ChessGame.TeamColor right_color=right_capture.getTeamColor();
                if (right_color == ChessGame.TeamColor.WHITE && row-1 == 1){
                    moves.add(new ChessMove(myPosition, right_check, PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, right_check, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, right_check, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, right_check, PieceType.QUEEN));
                }
                else if (right_color == ChessGame.TeamColor.WHITE){
                    moves.add(new ChessMove(myPosition, right_check, null));
                }
            }
            if (forward == null && row-1 == 1) {
                moves.add(new ChessMove(myPosition, forward_check, PieceType.KNIGHT));
                moves.add(new ChessMove(myPosition, forward_check, PieceType.ROOK));
                moves.add(new ChessMove(myPosition, forward_check, PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, forward_check, PieceType.QUEEN));
            }
            else if(forward == null){
                moves.add(new ChessMove(myPosition, forward_check, null));
            }
            if (row == 2 && double_check == null){
                moves.add(new ChessMove(myPosition, forward_check, null));
                moves.add(new ChessMove(myPosition, double_forward, null));
            }

        }
        return moves;
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
            return possible_moves
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
            System.out.println(myPosition);
            return move_bishop(myPosition);
        }
        if (piece.getPieceType() == PieceType.KNIGHT){
            System.out.println(myPosition);
            return List.of();
        }
        if (piece.getPieceType() == PieceType.ROOK){
            System.out.println(myPosition);
            return move_rook(myPosition);
        }
        if (piece.getPieceType() == PieceType.PAWN){
            System.out.println(myPosition);
            return move_pawn(myPosition, piece, board);
        }
        if (piece.getPieceType() == PieceType.QUEEN){
            System.out.println(myPosition);
            return move_queen(myPosition);
        }
        if (piece.getPieceType() == PieceType.KING){
            System.out.println(myPosition);
            return move_king(myPosition);
        }
        return List.of();
    }
}
