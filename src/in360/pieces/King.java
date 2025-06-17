package in360.pieces;

import in360.Piece;

/**
 * Represents a King chess piece.
 */
public class King extends Piece {

    // Unicode values for white and black kings
    private final int KING_WHITE = 0x2654;
    private final int KING_BLACK = 0x265A;

    // Indicates if the king has already moved (used for castling)
    private boolean hasAlreadyMoved = false;

    /**
     * Constructor for King.
     * 
     * @param x     initial x position
     * @param y     initial y position
     * @param color piece color
     */
    public King(int x, int y, ColorP color) {
        this.x = x;
        this.y = y;
        if (color == ColorP.BLACK) {
            this.piece_int = KING_BLACK;
            this.image = "assets/kingB.png";
            this.color = ColorP.BLACK;
        } else {
            this.piece_int = KING_WHITE;
            this.image = "assets/kingW.png";
            this.color = ColorP.WHITE;
        }
    }

    /**
     * Checks if a move is valid for the king.
     * 
     * @param x_next target x position
     * @param y_next target y position
     * @param board  the chess board
     * @return -1: invalid, 1: move, 2: capture, 4: kingside castling, 5: queenside
     *         castling
     */
    @Override
    public int isValidMove(int x_next, int y_next, Piece[][] board) {
        int res = -1;

        // Standard king move (one square in any direction)
        if (((Math.abs(x_next - x)) < 2) && ((Math.abs(y_next - y)) < 2)) {
            res = 1;
            if (board[x_next][y_next] != null) {
                if (board[x_next][y_next].getColor() != this.getColor()) {
                    res = 2; // Capture
                } else {
                    res = -1; // Can't capture own piece
                }
            }
        }
        // Castling logic
        if (!this.hasAlreadyMoved) {
            // Kingside castling
            if ((x_next == 6) && (y_next == y) && (board[5][y] == null) && (board[6][y] == null)
                    && (board[7][y] instanceof Rook)) {
                if (!((Rook) board[7][y]).isHasAlreadyMoved()) {
                    res = 4;
                }
            }
            // Queenside castling
            if ((x_next == 2) && (y_next == y) && (board[1][y] == null) && (board[2][y] == null)
                    && (board[3][y] == null)
                    && (board[0][y] instanceof Rook)) {
                if (!((Rook) board[0][y]).isHasAlreadyMoved()) {
                    res = 5;
                }
            }
        }

        return res;
    }

    /**
     * @return Unicode value for white king
     */
    public int getKING_WHITE() {
        return KING_WHITE;
    }

    /**
     * @return Unicode value for black king
     */
    public int getKING_BLACK() {
        return KING_BLACK;
    }

    /**
     * Checks if the king has already moved (for castling).
     * 
     * @return true if the king has moved, false otherwise
     */
    public boolean isHasAlreadyMoved() {
        return hasAlreadyMoved;
    }

    /**
     * Sets the flag indicating if the king has already moved.
     * 
     * @param isAlreadyMove true if the king has moved, false otherwise
     */
    public void setHasAlreadyMoved(boolean isAlreadyMove) {
        this.hasAlreadyMoved = isAlreadyMove;
    }

}