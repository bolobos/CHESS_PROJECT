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
            this.image="assets/kingB.png";
        } else {
            this.piece_int = KNIGHT_WHITE;
            this.image="assets/kingW.png";
        }
    }

    public int getKNIGHT_WHITE() {
        return KNIGHT_WHITE;
    }

    public int getKNIGHT_BLACK() {
        return KNIGHT_BLACK;
    }

}
