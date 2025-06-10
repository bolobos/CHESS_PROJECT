package in360.pieces;

import in360.Piece;

public class Rook extends Piece {

    private final int ROOK_WHITE = 0x2656;
    private final int ROOK_BLACK = 0x265C;

    public Rook(int x, int y, ColorP color) {
        this.x = x;
        this.y = y;
        if (color == ColorP.BLACK) {
            this.piece_int = ROOK_BLACK;
            this.image = "assets/rookB.png";
            this.color = ColorP.BLACK;
        } else {
            this.piece_int = ROOK_WHITE;
            this.image = "assets/rookW.png";
            this.color = ColorP.WHITE;
        }
    }

    // -1 : interdit / 1 : deplacement / 2 : mange
    @Override
    public int isValidMove(int x_next, int y_next, Piece[][] board) {
        int res = -1;
        int x_temp = x_next - x;
        int y_temp = y_next - y;

        if ((x_next == x) || (y_next == y)) {

            res = 1;

            if (board[x_next][y_next] != null) {
                res = 2;
            }

            if ((x_temp == 0) && (y_temp > 0)) {
                for (int m = 1; m < Math.abs(y_temp); m++) {
                    if (board[x][y + m] != null) {
                        res = -1;
                    }
                }
            } else if ((x_temp == 0) && (y_temp < 0)) {
                for (int m = 1; m < Math.abs(y_temp); m++) {
                    if (board[x][y - m] != null) {
                        res = -1;
                    }
                }
            } else if ((x_temp > 0) && (y_temp == 0)) {
                for (int m = 1; m < Math.abs(x_temp); m++) {
                    if (board[x + m][y] != null) {
                        res = -1;
                    }
                }
            } else {
                for (int m = 1; m < Math.abs(x_temp); m++) {
                    if (board[x - m][y] != null) {
                        res = -1;
                    }
                }
            }

            if ((res == 2) && (board[x_next][y_next].getColor() == this.getColor())) {
                res = -1;
            }
        }
        return res;
    }

    public int getROOK_WHITE() {
        return ROOK_WHITE;
    }

    public int getROOK_BLACK() {
        return ROOK_BLACK;
    }

}
