package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static ui.EscapeSequences.*;

public class BoardDraw {

    // Board dimensions.
    private static final int BOARD_SIZE_IN_SQUARES = 10;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 3;
    private static final int LINE_WIDTH_IN_PADDED_CHARS = 1;

    // Padded characters.
    private static final String EMPTY = "   ";
    private static String [] letters = {"   "," a "," b "," c "," d "," e "," f "," g "," h ","   ",};

    public void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);
        ChessGame game = new ChessGame();
        drawTicTacToeBoard(out, "b", game);

        out.print(SET_BG_COLOR_MAGENTA);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    public void doodle(PrintStream out, ChessBoard board){
        for(int x = 1;x<=8; x++){
            for(int y = 1;y<=8; y++){
                int row = 9-x;
                int col = y;
                ChessPosition point = new ChessPosition(row, col);
                ChessPiece hold = board.getPiece(point);
                if(hold != null){
                    if(hold.getTeamColor()== ChessGame.TeamColor.WHITE){
                        setRed(out);
                    }else{
                        setGreen(out);
                    }
                    if (hold.getPieceType() == ChessPiece.PieceType.BISHOP) {out.print(" B ");
                    } else if (hold.getPieceType() == ChessPiece.PieceType.ROOK) {out.print(" R ");
                    } else if (hold.getPieceType() == ChessPiece.PieceType.KNIGHT) {out.print(" N ");
                    } else if (hold.getPieceType() == ChessPiece.PieceType.PAWN) {out.print(" P ");
                    } else if (hold.getPieceType() == ChessPiece.PieceType.QUEEN) {out.print(" Q ");
                    } else if (hold.getPieceType() == ChessPiece.PieceType.KING) {out.print(" K ");
                    }
                }
            }
            out.println();
        }
    }

    private static void drawHeaders(PrintStream out) {

        setBlack(out);

        String[] headers = { "", "", "", "", "", "", "", "", "", "" };
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);

            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                out.print(EMPTY.repeat(LINE_WIDTH_IN_PADDED_CHARS));
            }
        }

        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_MAGENTA);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setBlack(out);
    }

    public void drawTicTacToeBoard(PrintStream out, String per, ChessGame game) {

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            drawRowOfSquares(out, boardRow, per, game);

            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                // Draw horizontal row separator.
//                drawHorizontalLine(out);
                setBlack(out);
            }
        }
    }

    public void drawHighlight(PrintStream out, String per, ChessGame game, ArrayList<ChessPosition> points){
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            drawHighligtSquares(out, boardRow, per, game, points);

            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                // Draw horizontal row separator.
//                drawHorizontalLine(out);
                setBlack(out);
            }
        }
    }

    private static void drawHighligtSquares(PrintStream out, int rowVals, String per, ChessGame game, ArrayList<ChessPosition> points){
        for (int squaredRow = 0; squaredRow < SQUARE_SIZE_IN_PADDED_CHARS; ++squaredRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                int colorVal = 0;
                if(rowVals == 0 || rowVals == 9){
                    setGreen(out);
                    colorVal = 0;
                }
                else if(boardCol == 0 || boardCol == 9){
                    setGreen(out);
                    colorVal = 0;
                }
                else if(boardCol % 2 == 0 && rowVals %2 == 0) {
                    setYellow(out);
                    colorVal =1;
                }
                else if((boardCol & 1) == 1 && (rowVals & 1) == 1) {
                    setYellow(out);
                    colorVal = 1;
                }
                else{
                    setWhite(out);
                    colorVal = 2;
                }
                for(int x = 0; x< points.size(); x++){
                    ChessPosition end = points.get(x);
                    int eR = end.getRow();
                    int eC = end.getColumn();
                    if(eR == rowVals && eC == boardCol){
                        setRed(out);
                        colorVal = 3;
                    }
                }

                if (squaredRow == SQUARE_SIZE_IN_PADDED_CHARS / 2) {
                    int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
                    int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;

                    out.print(EMPTY.repeat(prefixLength));
                    String let = letters[boardCol];
                    printPlayer(out, let, rowVals, boardCol,per, game);
                    switch (colorVal) {
                        case 0:
                            setGreen(out);
                            break;
                        case 1:
                            setYellow(out);
                            break;
                        case 2:
                            setWhite(out);
                            break;
                        case 3:
                            setRed(out);
                            break;
                        default:
                            setBlack(out);
                    }
                    out.print(EMPTY.repeat(suffixLength));
                }
                else {
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_PADDED_CHARS));
                }

                setBlack(out);
            }

            out.println();
        }
    }

    private static void drawRowOfSquares(PrintStream out, int rowVal, String per, ChessGame game) {

        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_PADDED_CHARS; ++squareRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                int colorVal = 0;
                if(rowVal == 0 || rowVal == 9){
                    setGreen(out);
                    colorVal = 0;
                }
                else if(boardCol == 0 || boardCol == 9){
                    setGreen(out);
                    colorVal = 0;
                }
                else if(boardCol % 2 == 0 && rowVal %2 == 0) {
                    setYellow(out);
                    colorVal =1;
                }
                else if((boardCol & 1) == 1 && (rowVal & 1) == 1) {
                    setYellow(out);
                    colorVal = 1;
                }
                else{
                    setWhite(out);
                    colorVal = 2;
                }
                if (squareRow == SQUARE_SIZE_IN_PADDED_CHARS / 2) {
                    int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
                    int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;

                    out.print(EMPTY.repeat(prefixLength));
                    String let = letters[boardCol];
                    printPlayer(out, let, rowVal, boardCol,per, game);
                    switch (colorVal) {
                        case 0:
                            setGreen(out);
                            break;
                        case 1:
                            setYellow(out);
                            break;
                        case 2:
                            setWhite(out);
                            break;
                        default:
                            setBlack(out);
                    }
                    out.print(EMPTY.repeat(suffixLength));
                }
                else {
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_PADDED_CHARS));
                }

                setBlack(out);
            }

            out.println();
        }
    }

    public static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setYellow(PrintStream out) {
        out.print(SET_BG_COLOR_BLUE);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setGreen(PrintStream out){
        out.print(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_COLOR_YELLOW);
    }

    private static void setRed(PrintStream out){
        out.print(SET_BG_COLOR_MAGENTA);
        out.print(SET_TEXT_COLOR_YELLOW);
    }


    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPlayer(PrintStream out, String player, int row, int col, String per, ChessGame tester) {
        String hold = player;
        if(per.equals("w")) {
            switch (row) {
                case 9:case 0:setGreen(out);break;
                case 2:case 1:setWhite(out);break;
                case 7:case 8:setYellow(out);
            }
        }else{
            switch (row) {
                case 9:case 0:setGreen(out);break;
                case 2:case 1:setYellow(out);break;
                case 7:case 8:setWhite(out);
            }
        }if(col == 0 || col == 9){setGreen(out);
        }ChessBoard board = tester.getBoard();
        if(row != 0 && row!= 9 && col !=0 && col != 9) {
            if(per.equals("w")) {
                ChessPosition pos = new ChessPosition(row, col);
                if (board.getPiece(pos) != null) {
                    ChessPiece piece = board.getPiece(pos);
                    if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {player = " B ";
                    } else if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {player = " R ";
                    } else if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {player = " N ";
                    } else if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {player = " P ";
                    } else if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {player = " Q ";
                    } else if (piece.getPieceType() == ChessPiece.PieceType.KING) {player = " K ";
                    }if(piece.getTeamColor()== ChessGame.TeamColor.WHITE){
                        setRed(out);
                    }else{
                        setGreen(out);
                    }
                } else {player = "   ";
                }
            }else{
                ChessPosition pos = new ChessPosition(row, col);
                if (board.getPiece(pos) != null) {
                    ChessPiece piece = board.getPiece(pos);
                    if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {player = " B ";
                    } else if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {player = " R ";
                    } else if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {player = " N ";
                    } else if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {player = " P ";
                    } else if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {player = " Q ";
                    } else if (piece.getPieceType() == ChessPiece.PieceType.KING) {player = " K ";
                    }if(piece.getTeamColor()== ChessGame.TeamColor.WHITE){
                        setRed(out);
                    }else{
                        setGreen(out);
                    }
                } else {player = "   ";
                }
            }
        }
        if(row == 1 || row == 8){
            if(col == 0 && row == 1){
                if(per.equals("b")) {player = " 8 ";
                }else{player = " 1 ";}
            }else if (col == 9 && row == 1){
                if(per.equals("b")) {player = " 8 ";
                }else{player = " 1 ";}
            }else if(col == 0 && row == 8){
                if(per.equals("b")) {player = " 1 ";
                }else{player = " 8 ";}
            }else if (col == 9 && row == 8){
                if(per.equals("b")) {player = " 1 ";
                }else{player = " 8 ";}
            }out.print(player);
        }else if (row == 2 || row == 7) {if(col == 0 && row == 2){
                if(per.equals("b")) {player = " 7 ";
                }else{player = " 2 ";}
            }else if (col == 9 && row == 2){if(per.equals("b")) {
                    player = " 7 ";
                }else{player = " 2 ";}
            }else if(col == 0 && row == 7){if(per.equals("b")) {
                    player = " 2 ";
                }else{player = " 7 ";}
            }else if (col == 9 && row == 7){if(per.equals("b")) {
                    player = " 2 ";
                }else{player = " 7 ";}
            }out.print(player);
        }else {
            if(col == 0 || col == 9){switch(row){
                    case 3:if(per.equals("b")) {player = " 6 ";
                        }
                        else{player = " 3 ";}break;
                    case 4:if(per.equals("b")) {player = " 5 ";
                        }
                        else{player = " 4 ";}break;
                    case 5:if(per.equals("b")) {player = " 4 ";
                        }
                        else{player = " 5 ";}break;
                    case 6:if(per.equals("b")) {player = " 3 ";
                        }
                        else{player = " 6 ";}break;
                    default:player = "   ";
                }out.print(player);
            }else if(row == 0 || row == 9){out.print(hold);
            }else {player = "   ";
                out.print(player);
            }
        }
    }
}
