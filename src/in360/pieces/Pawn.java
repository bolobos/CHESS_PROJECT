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
        } else {
            this.piece_int = PAWN_WHITE;
            this.image = "assets/pawnW.png";
        }
    }

    public boolean isMoveRight(int x_next, int y_next) {
        boolean res = false;

        if ((x_next - x) < 3 && (x_next - x) > 0) {
            res = true;
        } else {
            res = false;
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
