package in360.pieces;

import in360.Piece;

/**
 * Represents a Knight chess piece.
 */
public class Knight extends Piece {

    // Unicode values for white and black knights
    private final int KNIGHT_WHITE = 0x2658;
    private final int KNIGHT_BLACK = 0x265E;

    /**
     * Constructor for Knight.
     * 
     * @param x     initial x position
     * @param y     initial y position
     * @param color piece color
     */
    public Knight(int x, int y, ColorP color) {
        this.x = x;
        this.y = y;
        if (color == ColorP.BLACK) {
            this.piece_int = KNIGHT_BLACK;
            this.image = "assets/knightB.png";
            this.color = ColorP.BLACK;
        } else {
            this.piece_int = KNIGHT_WHITE;
            this.image = "assets/knightW.png";
            this.color = ColorP.WHITE;
        }
    }

    /**
     * Checks if a move is valid for the knight.
     * 
     * @param x_next target x position
     * @param y_next target y position
     * @param board  the chess board
     * @return -1: invalid, 1: move, 2: capture
     */
    @Override
    public int isValidMove(int x_next, int y_next, Piece[][] board) {
        int res = -1;

        int x_temp = Math.abs(x_next - x);
        int y_temp = Math.abs(y_next - y);
        // Knight moves in an "L" shape: 2 by 1 or 1 by 2
        if ((x_temp < 3) && (y_temp < 3) && (Math.abs(x_temp - y_temp) == 1) && ((x_temp == 2) || (y_temp == 2))) {
            res = 1;

            // If the target square is occupied, it's a capture
            if (board[x_next][y_next] != null) {
                res = 2;
            }

            // If the target square is occupied by an allied piece, move is invalid
            if ((res == 2) && (board[x_next][y_next].getColor() == this.getColor())) {
                res = -1;
            }
        }

        return res;
    }

    /**
     * Checks if this knight threatens an opposing king.
     * 
     * @param board the chess board
     * @return the color of the threatened king, or null if none
     */
    @Override
    public Piece.ColorP threatedKing(Piece[][] board) {

        int x_temp;
        int y_temp;
        Piece.ColorP res = null;

        // Loop through all possible knight moves
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                // Check if the target square is within the board
                if ((x + i) < 0 || (x + i) > 7 || (y + j) < 0 || (y + j) > 7) {
                    continue;
                } else {
                    x_temp = Math.abs((x + i) - x);
                    y_temp = Math.abs((y + j) - y);

                    // Check if the move is a valid knight move
                    if (((x_temp == 2) || (y_temp == 2)) && (Math.abs(x_temp - y_temp) == 1)
                            && ((x_temp == 2) || (y_temp == 2))) {
                        if (board[x + i][y + j] != null) {
                            // If the target is an opposing king, return its color
                            if (board[x + i][y + j] instanceof King && (board[x + i][y + j].getColor() != this.color)) {
                                res = board[x + i][y + j].getColor();
                            }
                        }
                    }
                }
            }
        }

        return res;
    }

    /**
     * @return Unicode value for white knight
     */
    public int getKNIGHT_WHITE() {
        return KNIGHT_WHITE;
    }

    /**
     * @return Unicode value for black knight
     */
    public int getKNIGHT_BLACK() {
        return KNIGHT_BLACK;
    }

}