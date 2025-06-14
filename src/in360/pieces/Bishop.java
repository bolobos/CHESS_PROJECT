package in360.pieces;

import in360.Piece;

public class Bishop extends Piece {

    private final int BISHOP_WHITE = 0x2657;
    private final int BISHOP_BLACK = 0x265D;

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

    // -1 : interdit / 1 : deplacement / 2 : mange
    @Override
    public int isValidMove(int x_next, int y_next, Piece[][] board) {
        int res = -1;

        if ((Math.abs(x_next - x) == Math.abs(y_next - y))) {
            int x_temp = x_next - x;
            int y_temp = y_next - y;
            res = 1;

            if (board[x_next][y_next] != null) {
                res = 2;
            }

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

            if ((res == 2) && (board[x_next][y_next].getColor() == this.getColor())) {
                res = -1;
            }

        }

        return res;
    }

    @Override
    public Piece.ColorP threatedKing(Piece[][] board) {
        Piece.ColorP res = null;
        boolean a = false, b = false, c = false, d = false;

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

    public int getBISHOP_WHITE() {
        return BISHOP_WHITE;
    }

    public int getBISHOP_BLACK() {
        return BISHOP_BLACK;
    }

}
