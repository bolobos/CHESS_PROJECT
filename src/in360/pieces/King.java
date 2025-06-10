package in360.pieces;

import in360.Piece;

public class King extends Piece {

    private final int KING_WHITE = 0x2654;
    private final int KING_BLACK = 0x265A;

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

    // -1 : interdit / 1 : deplacement / 2 : mange
    @Override
    public int isValidMove(int x_next, int y_next, Piece[][] board) {
        int res = -1;

        if (((Math.abs(x_next - x)) < 2) && ((Math.abs(y_next - y)) < 2)) {
            res = 1;
            if (board[x_next][y_next] != null) {
                if (board[x_next][y_next].getColor() != this.getColor()) {
                    res = 2;
                } else {
                    res = -1;
                }
            }
        }
        return res;
    }

    public int getKING_WHITE() {
        return KING_WHITE;
    }

    public int getKING_BLACK() {
        return KING_BLACK;
    }

}
