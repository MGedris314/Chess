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

    public Collection<ChessMove> move_king(ChessPosition myPosition, ChessBoard board, ChessPiece piece){
        ArrayList<ChessMove> moves =  new ArrayList<ChessMove>();

//      What we'll need to do for each of these is creates an array of objects of type chess moves.  Those appear as follows:
//      public ChessMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece)
//      With the exception of pawns set promotion piece to null.
//      Keep myPosition as the start position we only need to edit the end position.  Do that by using the position and then .get row/column
        int row =  myPosition.getRow();
        int col =  myPosition.getColumn();
        if (myPosition.getRow() == 1){
//            Left edge
            if (myPosition.getColumn() == 1){
                ChessPiece check2 = board.getPiece(new ChessPosition(row+1, col));
                ChessPiece check3 = board.getPiece(new ChessPosition(row+1, col+1));
                ChessPiece check4 = board.getPiece(new ChessPosition(row, col+1));
//              Bottom left corner 2 3 4
                if(check2 == null || !(check2.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
                }
                if(check3 == null || !(check3.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()+1), null));
                }
                if(check4 == null || !(check4.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),null));
                }
            }
            else if (myPosition.getColumn() == 8) {
                ChessPiece check1 = board.getPiece(new ChessPosition(row+1, col-1));
                ChessPiece check2 = board.getPiece(new ChessPosition(row+1, col));
                ChessPiece check5 = board.getPiece(new ChessPosition(row, col-1));
//                Bottom right corner 1 2 5
                if(check1 == null || !(check1.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1),null));
                }
                if(check2 == null || !(check2.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()), null));
                }
                if(check5 == null || !(check5.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1), null));
                }
            }
            else{
                ChessPiece check1 = board.getPiece(new ChessPosition(row+1, col-1));
                ChessPiece check2 = board.getPiece(new ChessPosition(row+1, col));
                ChessPiece check3 = board.getPiece(new ChessPosition(row+1, col+1));
                ChessPiece check4 = board.getPiece(new ChessPosition(row, col+1));
                ChessPiece check5 = board.getPiece(new ChessPosition(row, col-1));
                if(check1 == null || !(check1.getTeamColor().equals(piece.getTeamColor()))){
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
                }
                if(check2 == null || !(check2.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
                }
                if(check3 == null || !(check3.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()+1), null));
                }
                if(check4 == null || !(check4.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),null));
                }
                if(check5 == null || !(check5.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1), null));
                }
            }

        }
        else if (myPosition.getRow() == 8){
//          Right edge
            if (myPosition.getColumn() == 1){
//              Top left corner
                ChessPiece check4 = board.getPiece(new ChessPosition(row, col+1));
                ChessPiece check7 = board.getPiece(new ChessPosition(row-1, col));
                ChessPiece check8 = board.getPiece(new ChessPosition(row-1, col+1));
                if(check4 == null || !(check4.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),null));
                }
                if(check7 == null || !(check7.getTeamColor().equals(piece.getTeamColor()))){
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
                }
                if(check8 == null || !(check8.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()+8), null));
                }
            }
            else if (myPosition.getColumn() == 8) {
//              Top right corner
                ChessPiece check5 = board.getPiece(new ChessPosition(row, col-1));
                ChessPiece check6 = board.getPiece(new ChessPosition(row-1, col-1));
                ChessPiece check7 = board.getPiece(new ChessPosition(row-1, col));
                if(check5 == null || !(check6.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1),null));
                }
                if(check6 == null || !(check6.getTeamColor().equals(piece.getTeamColor()))){
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
                }
                if(check7 == null || !(check7.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
                }
            }
            else{
                ChessPiece check4 = board.getPiece(new ChessPosition(row, col+1));
                ChessPiece check5 = board.getPiece(new ChessPosition(row, col-1));
                ChessPiece check6 = board.getPiece(new ChessPosition(row-1, col-1));
                ChessPiece check7 = board.getPiece(new ChessPosition(row-1, col));
                ChessPiece check8 = board.getPiece(new ChessPosition(row-1, col-1));
                if(check4 == null || !(check4.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),null));
                }
                if(check5 == null || !(check5.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1),null));
                }
                if(check6 == null || !(check6.getTeamColor().equals(piece.getTeamColor()))){
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
                }
                if(check7 == null || !(check7.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
                }
                if(check8 == null || !(check8.getTeamColor().equals(piece.getTeamColor()))) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()-1), null));
                }
            }

        }
        else if (myPosition.getColumn() == 1){
            ChessPiece check2 = board.getPiece(new ChessPosition(row+1, col));
            ChessPiece check3 = board.getPiece(new ChessPosition(row+1, col+1));
            ChessPiece check4 = board.getPiece(new ChessPosition(row, col+1));
            ChessPiece check7 = board.getPiece(new ChessPosition(row-1, col));
            ChessPiece check8 = board.getPiece(new ChessPosition(row-1, col+1));
//          Left column
            if(check2 == null || !(check2.getTeamColor().equals(piece.getTeamColor()))) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
            }
            if(check3 == null || !(check3.getTeamColor().equals(piece.getTeamColor()))) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()+1), null));
            }
            if(check4 == null || !(check4.getTeamColor().equals(piece.getTeamColor()))) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),null));
            }
            if(check7 == null || !(check7.getTeamColor().equals(piece.getTeamColor()))){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
            }
            if(check8 == null || !(check8.getTeamColor().equals(piece.getTeamColor()))) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()-1), null));
            }

        }
        else if (myPosition.getColumn() == 8){
            ChessPiece check1 = board.getPiece(new ChessPosition(row+1, col-1));
            ChessPiece check2 = board.getPiece(new ChessPosition(row+1, col));
            ChessPiece check5 = board.getPiece(new ChessPosition(row, col-1));
            ChessPiece check6 = board.getPiece(new ChessPosition(row-1, col-1));
            ChessPiece check7 = board.getPiece(new ChessPosition(row-1, col));

//          Right column
            if(check1 == null || !(check1.getTeamColor().equals(piece.getTeamColor()))){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
            }
            if(check2 == null || !(check2.getTeamColor().equals(piece.getTeamColor()))) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
            }
            if(check5 == null || !(check5.getTeamColor().equals(piece.getTeamColor()))) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1),null));
            }
            if(check6 == null || !(check6.getTeamColor().equals(piece.getTeamColor()))){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
            }
            if(check7 == null || !(check7.getTeamColor().equals(piece.getTeamColor()))) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
            }

        }
        else {
//      Default, one space in all directions.
            ChessPiece check1 = board.getPiece(new ChessPosition(row+1, col-1));
            ChessPiece check2 = board.getPiece(new ChessPosition(row+1, col));
            ChessPiece check3 = board.getPiece(new ChessPosition(row+1, col+1));
            ChessPiece check4 = board.getPiece(new ChessPosition(row, col+1));
            ChessPiece check5 = board.getPiece(new ChessPosition(row, col-1));
            ChessPiece check6 = board.getPiece(new ChessPosition(row-1, col-1));
            ChessPiece check7 = board.getPiece(new ChessPosition(row-1, col));
            ChessPiece check8 = board.getPiece(new ChessPosition(row-1, col+1));
            if(check1 == null || !(check1.getTeamColor().equals(piece.getTeamColor()))){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
            }
            if(check2 == null || !(check2.getTeamColor().equals(piece.getTeamColor()))) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
            }
            if(check3 == null || !(check3.getTeamColor().equals(piece.getTeamColor()))) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()+1), null));
            }
            if(check4 == null || !(check4.getTeamColor().equals(piece.getTeamColor()))) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1),null));
            }
            if(check5 == null || !(check5.getTeamColor().equals(piece.getTeamColor()))) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1),null));
            }
            if(check6 == null || !(check6.getTeamColor().equals(piece.getTeamColor()))){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
            }
            if(check7 == null || !(check7.getTeamColor().equals(piece.getTeamColor()))) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
            }
            if(check8 == null || !(check8.getTeamColor().equals(piece.getTeamColor()))) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()+1), null));
            }
        }
        return moves;
    }

    public Collection<ChessMove> move_rook(ChessPosition myPosition, ChessBoard board, ChessPiece piece){
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
     for (int x = 1; x <=to_left; x++){
         ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow(), myPosition.getColumn()-x));
         if (check == null) {
             moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - x), null));
         }
         else if(!check.getTeamColor().equals(piece.getTeamColor())){
             moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - x), null));
             break;
         }
         else{
             break;
         }
     }
     for (int x = 1; x <= to_right; x++){
         ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow(), myPosition.getColumn()+x));
         if (check == null) {
             moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+x), null));
         }
         else if(!check.getTeamColor().equals(piece.getTeamColor())){
             moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+x), null));
             break;
         }
         else{
             break;
         }

     }
     for (int x = 1; x <= up; x++){
         ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow()+x, myPosition.getColumn()));
         if (check == null) {
             moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+x, myPosition.getColumn()), null));
         }
         else if(!check.getTeamColor().equals(piece.getTeamColor())){
             moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+x, myPosition.getColumn()), null));
             break;
         }
         else{
             break;
         }

     }
     for (int x = 1; x <= down; x++){
         ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow()-x, myPosition.getColumn()));
         if (check == null) {
             moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-x, myPosition.getColumn()), null));
         }
         else if(!check.getTeamColor().equals(piece.getTeamColor())){
             moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-x, myPosition.getColumn()), null));
             break;
         }
         else{
             break;
         }
     }
        return moves;
    }

    public Collection<ChessMove> move_bishop(ChessPosition myPosition, ChessBoard board, ChessPiece piece){
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
            if (myPosition.getColumn()+loop_count >= 9 || myPosition.getRow() + loop_count >= 9){
                break;
            }
            ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow()+loop_count, myPosition.getColumn()+loop_count));
            if (check == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+loop_count, myPosition.getColumn()+loop_count), null));
            }
            else if(!check.getTeamColor().equals(piece.getTeamColor())){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+loop_count, myPosition.getColumn()+loop_count), null));
                break;
            }
            else{
                break;
            }
            x++;
            y++;
        }
//      SE
        x = start_row; y = start_col;  loop_count = 0;
        while( x<=8 && y<=8){
            loop_count++;
            if (start_row == 1 || start_col == 8){
                break;
            }
            if (myPosition.getColumn()+loop_count > 8 || myPosition.getRow() - loop_count <=0){
                break;
            }
            ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow()-loop_count, myPosition.getColumn()+loop_count));
            if (check == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-loop_count, myPosition.getColumn()+loop_count), null));
            }
            else if(!check.getTeamColor().equals(piece.getTeamColor())){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-loop_count, myPosition.getColumn()+loop_count), null));
                break;
            }
            else{
                break;
            }
            x++;
            y++;
        }
//      SW
        x = start_row; y = start_col; loop_count = 0;
        while( x>=0 && y>=0){
            loop_count++;
            if (start_row == 1 || start_col == 1){
                break;
            }
            if (myPosition.getColumn()-loop_count <= 0 || myPosition.getRow() - loop_count <=0){
                break;
            }
            ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow()-loop_count, myPosition.getColumn()-loop_count));
            if (check == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-loop_count, myPosition.getColumn()-loop_count), null));
            }
            else if(!check.getTeamColor().equals(piece.getTeamColor())){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-loop_count, myPosition.getColumn()-loop_count), null));
                break;
            }
            else{
                break;
            }
            x--;
            y--;
        }
//      NW
        x = start_row; y = start_col; loop_count = 0;
        while( x<8 && y<8){
            loop_count++;
            if (myPosition.getColumn()-loop_count <= 0 || myPosition.getRow() + loop_count >=9){
                break;
            }
            ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow()+loop_count, myPosition.getColumn()-loop_count));
            if (check == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+loop_count, myPosition.getColumn()-loop_count), null));
            }
            else if(!check.getTeamColor().equals(piece.getTeamColor())){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+loop_count, myPosition.getColumn()-loop_count), null));
                break;
            }
            else{
                break;
            }
            x++;
            y++;
        }
        return moves;
    }

    public Collection<ChessMove> move_queen(ChessPosition myPosition, ChessBoard board, ChessPiece piece){
        ArrayList<ChessMove> moves =  new ArrayList<ChessMove>();

//   Get starting values
        int start_col = myPosition.getColumn();
        int start_row = myPosition.getRow();
//           Find total movement abilities
        int to_left = start_col-1;
        int to_right = 8-start_col;
        int up = 8-start_row;
        int down = start_row-1;
        for (int x = 1; x <=to_left; x++){
            ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow(), myPosition.getColumn()-x));
            if (check == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - x), null));
            }
            else if(!check.getTeamColor().equals(piece.getTeamColor())){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - x), null));
                break;
            }
            else{
                break;
            }
        }
        for (int x = 1; x <= to_right; x++){
            ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow(), myPosition.getColumn()+x));
            if (check == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+x), null));
            }
            else if(!check.getTeamColor().equals(piece.getTeamColor())){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+x), null));
                break;
            }
            else{
                break;
            }

        }
        for (int x = 1; x <= up; x++){
            ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow()+x, myPosition.getColumn()));
            if (check == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+x, myPosition.getColumn()), null));
            }
            else if(!check.getTeamColor().equals(piece.getTeamColor())){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+x, myPosition.getColumn()), null));
                break;
            }
            else{
                break;
            }

        }
        for (int x = 1; x <= down; x++){
            ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow()-x, myPosition.getColumn()));
            if (check == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-x, myPosition.getColumn()), null));
            }
            else if(!check.getTeamColor().equals(piece.getTeamColor())){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-x, myPosition.getColumn()), null));
                break;
            }
            else{
                break;
            }
        }
//      NE
        int x = start_row; int y = start_col; int loop_count = 0;
        while( x<8 && y<8){
            loop_count++;
            if (myPosition.getColumn()+loop_count >= 9 || myPosition.getRow() + loop_count >= 9){
                break;
            }
            ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow()+loop_count, myPosition.getColumn()+loop_count));
            if (check == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+loop_count, myPosition.getColumn()+loop_count), null));
            }
            else if(!check.getTeamColor().equals(piece.getTeamColor())){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+loop_count, myPosition.getColumn()+loop_count), null));
                break;
            }
            else{
                break;
            }
            x++;
            y++;
        }
//      SE
        x = start_row; y = start_col;  loop_count = 0;
        while( x<=8 && y<=8){
            loop_count++;
            if (start_row == 1 || start_col == 8){
                break;
            }
            if (myPosition.getColumn()+loop_count > 8 || myPosition.getRow() - loop_count <=0){
                break;
            }
            ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow()-loop_count, myPosition.getColumn()+loop_count));
            if (check == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-loop_count, myPosition.getColumn()+loop_count), null));
            }
            else if(!check.getTeamColor().equals(piece.getTeamColor())){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-loop_count, myPosition.getColumn()+loop_count), null));
                break;
            }
            else{
                break;
            }
            x++;
            y++;
        }
//      SW
        x = start_row; y = start_col; loop_count = 0;
        while( x>=0 && y>=0){
            loop_count++;
            if (start_row == 1 || start_col == 1){
                break;
            }
            if (myPosition.getColumn()-loop_count <= 0 || myPosition.getRow() - loop_count <=0){
                break;
            }
            ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow()-loop_count, myPosition.getColumn()-loop_count));
            if (check == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-loop_count, myPosition.getColumn()-loop_count), null));
            }
            else if(!check.getTeamColor().equals(piece.getTeamColor())){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-loop_count, myPosition.getColumn()-loop_count), null));
                break;
            }
            else{
                break;
            }
            x--;
            y--;
        }
//      NW
        x = start_row; y = start_col; loop_count = 0;
        while( x<8 && y<8){
            loop_count++;
            if (myPosition.getColumn()-loop_count <= 0 || myPosition.getRow() + loop_count >=9){
                break;
            }
            ChessPiece check = board.getPiece(new ChessPosition( myPosition.getRow()+loop_count, myPosition.getColumn()-loop_count));
            if (check == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+loop_count, myPosition.getColumn()-loop_count), null));
            }
            else if(!check.getTeamColor().equals(piece.getTeamColor())){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+loop_count, myPosition.getColumn()-loop_count), null));
                break;
            }
            else{
                break;
            }
            x++;
            y++;
        }
        return moves;
    }

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
            return move_bishop(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.KNIGHT){
            System.out.println(myPosition);
            return move_knight(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.ROOK){
            System.out.println(myPosition);
            return move_rook(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.PAWN){
            System.out.println(myPosition);
            return move_pawn(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.QUEEN){
            System.out.println(myPosition);
            return move_queen(myPosition, board, piece);
        }
        if (piece.getPieceType() == PieceType.KING){
            System.out.println(myPosition);
            return move_king(myPosition, board, piece);
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
