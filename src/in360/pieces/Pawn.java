package in360.pieces;

import in360.Piece;

/**
 * Represents a Pawn chess piece.
 */
public class Pawn extends Piece {

    // Unicode values for white and black pawns
    private final int PAWN_WHITE = 0x2659;
    private final int PAWN_BLACK = 0x265F;

    // The turn number when the pawn moved two squares (for en passant)
    private int turn;
    private int move2cases;

    /**
     * Constructor for Pawn.
     * 
     * @param x     initial x position
     * @param y     initial y position
     * @param color piece color
     */
    public Pawn(int x, int y, ColorP color) {
        this.x = x;
        this.y = y;
        if (color == ColorP.BLACK) {
            this.piece_int = PAWN_BLACK;
            this.image = "assets/pawnB.png";
            this.color = ColorP.BLACK;
        } else {
            this.piece_int = PAWN_WHITE;
            this.image = "assets/pawnW.png";
            this.color = ColorP.WHITE;
        }
    }

    /**
     * Checks if a move is valid for the pawn.
     * 
     * @param x_next target x position
     * @param y_next target y position
     * @param board  the chess board
     * @return -1: invalid, 1: move, 2: capture, 3: en passant
     */
    @Override
    public int isValidMove(int x_next, int y_next, Piece[][] board) {
        int res = -1;

        if (this.color == Piece.ColorP.BLACK) {

            // Move two squares forward on first move
            if ((y == 1) && (y_next == 3)) {
                // Both squares must be empty
                if ((board[x][2] == null) && (board[x][3] == null) && (x == x_next)) {
                    res = 1;
                    this.move2cases = this.turn;
                }
            }
            // En passant capture
            else if ((y == 4) && ((y_next - y) == 1) && (Math.abs(x_next - x) == 1)) {
                if (board[x - 1][y] != null) {
                    if ((board[x - 1][y] instanceof Pawn) && (board[x - 1][y].getColor() != this.color)) {
                        if ((((Pawn) board[x - 1][y]).getMove2cases() - this.turn) == -1) {
                            res = 3;
                        }
                    }
                }

                if (board[x + 1][y] != null) {
                    if ((board[x + 1][y] instanceof Pawn) && (board[x + 1][y].getColor() != this.color)) {
                        if ((((Pawn) board[x + 1][y]).getMove2cases() - this.turn) == -1) {
                            res = 3;
                        }
                    }
                }
            }
            // Move one square forward or capture
            else {
                if ((((y_next - y) < 2 && (y_next - y) > 0) && (board[x][y_next] == null) && (x == x_next))) {
                    res = 1;
                } else if ((board[x_next][y_next] != null) && ((y_next - y) < 2 && (y_next - y) > 0)
                        && (Math.abs(x_next - x) == 1)) {
                    if (board[x_next][y_next].getColor() != ColorP.BLACK) {
                        res = 2;
                    }
                } else {
                    res = -1;
                }
            }

        } else // WHITE pawn

        {
            // Move two squares forward on first move
            if (y == 6 && (y_next == 4)) {
                if ((board[x][y - 1] == null) && (board[x][y - 2] == null) && (x == x_next)) {
                    res = 1;
                }
            }
            // En passant capture
            else if ((y == 3) && ((y - y_next) == 1) && (Math.abs(x_next - x) == 1)) {
                if (board[x - 1][y] != null) {
                    if ((board[x - 1][y] instanceof Pawn) && (board[x - 1][y].getColor() != this.color)) {
                        if ((((Pawn) board[x - 1][y]).getMove2cases() - this.turn) == -1) {
                            res = 3;
                        }
                    }
                }

                if (board[x + 1][y] != null) {
                    if ((board[x + 1][y] instanceof Pawn) && (board[x + 1][y].getColor() != this.color)) {
                        if ((((Pawn) board[x + 1][y]).getMove2cases() - this.turn) == -1) {
                            res = 3;
                        }
                    }
                }

            }
            // Move one square forward or capture
            else {
                if ((y - y_next) < 2 && (y - y_next) > 0 && (board[x][y_next] == null) && (x == x_next)) {
                    res = 1;
                } else if ((board[x_next][y_next] != null) && ((y - y_next) < 2 && (y - y_next) > 0)
                        && (Math.abs(x_next - x) == 1)) {
                    if (board[x_next][y_next].getColor() != ColorP.WHITE) {
                        res = 2;
                    }
                } else {
                    res = -1;
                }
            }
        }

        return res;
    }

    /**
     * Checks if this pawn threatens an opposing king.
     * 
     * @param board the chess board
     * @return the color of the threatened king, or null if none
     */
    @Override
    public Piece.ColorP threatedKing(Piece[][] board) {

        Piece.ColorP res = null;
        if (this.color == Piece.ColorP.BLACK) {
            if (((x + 1) < 8) && ((y + 1) < 8) && ((x + 1) >= 0) && ((y + 1) >= 0)) {
                if ((board[x + 1][y + 1] instanceof King) && (board[x + 1][y + 1].getColor() != this.color)) {
                    res = board[x + 1][y + 1].getColor();
                }
            }
            if (((x - 1) < 8) && ((y + 1) < 8) && ((x - 1) >= 0) && ((y + 1) >= 0)) {
                if (board[x - 1][y + 1] instanceof King && (board[x - 1][y + 1].getColor() != this.color)) {
                    res = board[x - 1][y + 1].getColor();
                }
            }

        } else {

            if (((x + 1) < 8) && ((y - 1) < 8) && ((x + 1) >= 0) && ((y - 1) >= 0)) {
                if ((board[x + 1][y - 1] instanceof King && (board[x + 1][y - 1].getColor() != this.color))) {
                    res = board[x + 1][y - 1].getColor();
                }
            }
            if (((x - 1) < 8) && ((y - 1) < 8) && ((x - 1) >= 0) && ((y - 1) >= 0)) {
                if ((board[x - 1][y - 1] instanceof King && (board[x - 1][y - 1].getColor() != this.color))) {
                    res = board[x - 1][y - 1].getColor();
                }
            }
        }
        return res;

    }

    /**
     * @return Unicode value for white pawn
     */
    public int getPAWN_WHITE() {
        return PAWN_WHITE;
    }

    /**
     * @return Unicode value for black pawn
     */
    public int getPAWN_BLACK() {
        return PAWN_BLACK;
    }

    /**
     * @return the turn number
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Sets the turn number.
     * 
     * @param tour the turn number
     */
    public void setTurn(int tour) {
        this.turn = tour;
    }

    /**
     * @return the turn number when the pawn moved two squares
     */
    public int getMove2cases() {
        return move2cases;
    }

    /**
     * Sets the turn number when the pawn moved two squares.
     * 
     * @param enPassantTurn the turn number
     */
    public void setMove2cases(int enPassantTurn) {
        this.move2cases = enPassantTurn;
    }

}