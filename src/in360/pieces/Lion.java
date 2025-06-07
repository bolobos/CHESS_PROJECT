package in360.pieces;

import in360.Piece;

public class Lion extends Piece {

    private final int LION_WHITE = 0x00;
    private final int LION_BLACK = 0x00;

    public Lion(int x, int y, ColorP color) {
        this.x = x;
        this.y = y;
        if (color == ColorP.BLACK) {
            this.piece_int = LION_BLACK;
            this.image="";
        } else {
            this.piece_int = LION_WHITE;
            this.image="";
        }
    }

}
