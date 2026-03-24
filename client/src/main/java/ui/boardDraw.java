package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class boardDraw {

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

        drawTicTacToeBoard(out, "b");

        out.print(SET_BG_COLOR_MAGENTA);
        out.print(SET_TEXT_COLOR_WHITE);
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

    public void drawTicTacToeBoard(PrintStream out, String per) {

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {

            drawRowOfSquares(out, boardRow, per);

            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                // Draw horizontal row separator.
//                drawHorizontalLine(out);
                setBlack(out);
            }
        }
    }

    private static void drawRowOfSquares(PrintStream out, int rowVal, String per) {

        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_PADDED_CHARS; ++squareRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                int color_val = 0;
                if(rowVal == 0 || rowVal == 9){
                    setGreen(out);
                    color_val = 0;
                }
                else if(boardCol == 0 || boardCol == 9){
                    setGreen(out);
                    color_val = 0;
                }
                else if(boardCol % 2 == 0 && rowVal %2 == 0) {
                    setWhite(out);
                    color_val =1;
                }
                else if((boardCol & 1) == 1 && (rowVal & 1) == 1) {
                    setWhite(out);
                    color_val = 1;
                }
                else{
                    setYellow(out);
                    color_val = 2;
                }

                if (squareRow == SQUARE_SIZE_IN_PADDED_CHARS / 2) {
                    int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
                    int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;

                    out.print(EMPTY.repeat(prefixLength));
                    String let = letters[boardCol];
                    printPlayer(out, let, rowVal, boardCol,per);
                    switch (color_val) {
                        case 0:
                            setGreen(out);
                            break;
                        case 1:
                            setWhite(out);
                            break;
                        case 2:
                            setYellow(out);
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
        out.print(SET_BG_COLOR_YELLOW);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setGreen(PrintStream out){
        out.print(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_COLOR_YELLOW);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPlayer(PrintStream out, String player, int row, int col, String per) {
        String hold = player;
        if(per == "w") {
            switch (row) {
                case 9:
                case 0:
                    setGreen(out);
                    break;
                case 2:
                case 1:
                    setWhite(out);
                    break;
                case 7:
                case 8:
                    setYellow(out);
            }
        }
        else{
            switch (row) {
                case 9:
                case 0:
                    setGreen(out);
                    break;
                case 2:
                case 1:
                    setYellow(out);
                    break;
                case 7:
                case 8:
                    setWhite(out);
            }
        }

        if(col == 0 || col == 9){
            setGreen(out);
        }

        if(row == 1 || row == 8){
            switch (col) {
                case 8:
                case 1:
                    player = " R ";
                    break;
                case 7:
                case 2:
                    player = " N ";
                    break;
                case 6:
                case 3:
                    player = " B ";
                    break;
                case 4:
                    if(per == "w"){
                        player = " K ";
                    }
                    else {
                        player = " Q ";
                    }
                    break;
                case 5:
                    if(per == "w"){
                        player = " Q ";
                    }
                    else {
                        player = " K ";
                    }
                    break;
                default:
                    player = "   ";
            }
            if(col == 0 && row == 1){
                player = " 8 ";
            }
            else if (col == 9 && row == 1){
                player = " 8 ";
            }
            else if(col == 0 && row == 8){
                player = " 1 ";
            }
            else if (col == 9 && row == 8){
                player = " 1 ";
            }
            out.print(player);
        }
        else if (row == 2 || row == 7) {
            if(col == 0 && row == 2){
                player = " 7 ";
            }
            else if (col == 9 && row == 2){
                player = " 7 ";
            }
            else if(col == 0 && row == 7){
                player = " 2 ";
            }
            else if (col == 9 && row == 7){
                player = " 2 ";
            }
            else {
                player = " p ";
            }
            out.print(player);
        }
        else {
            if(col == 0 || col == 9){
                switch(row){
                    case 3:
                        player = " 6 ";
                        break;
                    case 4:
                        player = " 5 ";
                        break;
                    case 5:
                        player = " 4 ";
                        break;
                    case 6:
                        player = " 3 ";
                        break;
                    default:
                        player = "   ";
                }
                out.print(player);
            }
            else if(row == 0 || row == 9){
                out.print(hold);
            }
            else {
                player = "   ";
                out.print(player);
            }
        }
    }
}
