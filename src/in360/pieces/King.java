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
            this.image="assets/kingB.png";
        } else {
            this.piece_int = KING_WHITE;
            this.image="assets/kingW.png";
        }
    }

    public int getKING_WHITE() {
        return KING_WHITE;
    }

    public int getKING_BLACK() {
        return KING_BLACK;
    }

}
