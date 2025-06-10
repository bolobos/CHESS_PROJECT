package in360.pieces;

import in360.Piece;

public class Knight extends Piece {

    private final int KNIGHT_WHITE = 0x2658;
    private final int KNIGHT_BLACK = 0x265E;

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

    // -1 : interdit / 1 : deplacement / 2 : mange
    @Override
    public int isValidMove(int x_next, int y_next, Piece[][] board) {
        int res = -1;

        int x_temp = Math.abs(x_next - x);
        int y_temp = Math.abs(y_next - y);
        if ((x_temp < 3) && (y_temp < 3) && (Math.abs(x_temp - y_temp) == 1)&&((x_temp==2)||(y_temp==2))) {

            res = 1;

            if (board[x_next][y_next] != null) {
                res = 2;
            }

            if ((res == 2) && (board[x_next][y_next].getColor() == this.getColor())) {
                res = -1;
            }

        }

        return res;
    }

    public int getKNIGHT_WHITE() {
        return KNIGHT_WHITE;
    }

    public int getKNIGHT_BLACK() {
        return KNIGHT_BLACK;
    }

}
