package in360.pieces;

import in360.Piece;

public class Queen extends Piece {

    private final int QUEEN_WHITE = 0x2655;
    private final int QUEEN_BLACK = 0x265B;

    public Queen(int x, int y, ColorP color) {
        this.x = x;
        this.y = y;
        if (color == ColorP.BLACK) {
            this.piece_int = QUEEN_BLACK;
            this.image="assets/queenB.png";
        } else {
            this.piece_int = QUEEN_WHITE;
            this.image="assets/queenW.png";
        }
    }

    public int getQUEEN_WHITE() {
        return QUEEN_WHITE;
    }

    public int getQUEEN_BLACK() {
        return QUEEN_BLACK;
    }

}
