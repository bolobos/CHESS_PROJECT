package in360.pieces;

import in360.Piece;

/**
 * Represents a Bishop chess piece.
 */
public class Bishop extends Piece {

    // Unicode values for white and black bishops
    private final int BISHOP_WHITE = 0x2657;
    private final int BISHOP_BLACK = 0x265D;

    /**
     * Constructor for Bishop.
     * 
     * @param x     initial x position
     * @param y     initial y position
     * @param color piece color
     */
    public Bishop(int x, int y, ColorP color) {
        this.x = x;
        this.y = y;
        if (color == ColorP.BLACK) {
            this.piece_int = BISHOP_BLACK;
            this.image = "assets/bishopB.png";
            this.color = ColorP.BLACK;
        } else {
            this.piece_int = BISHOP_WHITE;
            this.image = "assets/bishopW.png";
            this.color = ColorP.WHITE;
        }
    }

    /**
     * Checks if a move is valid for the bishop.
     * 
     * @param x_next target x position
     * @param y_next target y position
     * @param board  the chess board
     * @return -1: invalid, 1: move, 2: capture
     */
    @Override
    public int isValidMove(int x_next, int y_next, Piece[][] board) {
        int res = -1;

        // Check if the move is diagonal
        if ((Math.abs(x_next - x) == Math.abs(y_next - y))) {
            int x_temp = x_next - x;
            int y_temp = y_next - y;
            res = 1;

            // If the target square is occupied, it's a capture
            if (board[x_next][y_next] != null) {
                res = 2;
            }

            // Check for obstacles along the diagonal
            if ((x_temp > 0) && (y_temp > 0)) {
                for (int m = 1; m < Math.abs(x_temp); m++) {
                    if (board[x + m][y + m] != null) {
                        res = -1;
                    }
                }
            } else if ((x_temp < 0) && (y_temp > 0)) {
                for (int m = 1; m < Math.abs(x_temp); m++) {
                    if (board[x - m][y + m] != null) {
                        res = -1;
                    }
                }
            } else if ((x_temp > 0) && (y_temp < 0)) {
                for (int m = 1; m < Math.abs(x_temp); m++) {
                    if (board[x + m][y - m] != null) {
                        res = -1;
                    }
                }
            } else {
                for (int m = 1; m < Math.abs(x_temp); m++) {
                    if (board[x - m][y - m] != null) {
                        res = -1;
                    }
                }
            }

            // If the target square is occupied by an allied piece, move is invalid
            if ((res == 2) && (board[x_next][y_next].getColor() == this.getColor())) {
                res = -1;
            }
        }

        return res;
    }

    /**
     * Checks if this bishop threatens an opposing king.
     * 
     * @param board the chess board
     * @return the color of the threatened king, or null if none
     */
    @Override
    public Piece.ColorP threatedKing(Piece[][] board) {
        Piece.ColorP res = null;
        boolean a = false, b = false, c = false, d = false;

        // Scan all four diagonals from the bishop's position
        for (int m = 1; m < 8; m++) {
            // Top-right diagonal
            if ((x + m < 8) && (y + m < 8) && !a) {
                if (board[x + m][y + m] instanceof King && (board[x + m][y + m].getColor() != this.color)) {
                    res = board[x + m][y + m].getColor();
                }
                if (board[x + m][y + m] != null) {
                    a = true;
                }
            }
            // Top-left diagonal
            if ((x - m >= 0) && (y + m < 8) && !b) {
                if (board[x - m][y + m] instanceof King && (board[x - m][y + m].getColor() != this.color)) {
                    res = board[x - m][y + m].getColor();
                }
                if (board[x - m][y + m] != null) {
                    b = true;
                }
            }
            // Bottom-right diagonal
            if ((x + m < 8) && (y - m >= 0) && !c) {
                if (board[x + m][y - m] instanceof King && (board[x + m][y - m].getColor() != this.color)) {
                    res = board[x + m][y - m].getColor();
                }
                if (board[x + m][y - m] != null) {
                    c = true;
                }
            }
            // Bottom-left diagonal
            if ((x - m >= 0) && (y - m >= 0) && !d) {
                if (board[x - m][y - m] instanceof King && (board[x - m][y - m].getColor() != this.color)) {
                    res = board[x - m][y - m].getColor();
                }
                if (board[x - m][y - m] != null) {
                    d = true;
                }
            }
        }
        return res;
    }

    /**
     * @return Unicode value for white bishop
     */
    public int getBISHOP_WHITE() {
        return BISHOP_WHITE;
    }

    /**
     * @return Unicode value for black bishop
     */
    public int getBISHOP_BLACK() {
        return BISHOP_BLACK;
    }

}