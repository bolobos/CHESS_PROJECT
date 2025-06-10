package in360.pieces;

import in360.Piece;

public class Pawn extends Piece {

    private final int PAWN_WHITE = 0x2659;
    private final int PAWN_BLACK = 0x265F;

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

    // -1 : interdit / 1 : deplacement / 2 : mange
    @Override
    public int isValidMove(int x_next, int y_next, Piece[][] board) {
        int res = -1;

        if (this.color == Piece.ColorP.BLACK) {
            if ((y == 1) && (y_next == 3)) {
                if ((board[x][2] == null) && (board[x][3] == null) && (x == x_next)) {
                    res = 1;
                }
            } else {
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

        } else {
            if (y == 6 && (y_next == 4)) {
                if ((board[x][y - 1] == null) && (board[x][y - 2] == null) && (x == x_next)) {
                    res = 1;
                }
            } else {
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

    public int getPAWN_WHITE() {
        return PAWN_WHITE;
    }

    public int getPAWN_BLACK() {
        return PAWN_BLACK;
    }

}
