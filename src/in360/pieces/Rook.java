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
            this.image="assets/rookB.png";
        } else {
            this.piece_int = ROOK_WHITE;
            this.image="assets/rookW.png";
        }
    }

    public int getROOK_WHITE() {
        return ROOK_WHITE;
    }

    public int getROOK_BLACK() {
        return ROOK_BLACK;
    }

}
