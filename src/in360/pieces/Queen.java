package in360.pieces;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import in360.Piece;

public class Queen extends Piece {

    private final int QUEEN_WHITE = 0x2655;
    private final int QUEEN_BLACK = 0x265B;

    public Queen(int x, int y, ColorP color) {
        this.x = x;
        this.y = y;
        if (color == ColorP.BLACK) {
            this.piece_int = QUEEN_BLACK;
            this.image = "assets/queenB.png";
            this.color = ColorP.BLACK;
        } else {
            this.piece_int = QUEEN_WHITE;
            this.image = "assets/queenW.png";
            this.color = ColorP.WHITE;
        }
    }

    public Queen(int x, int y, ColorP color, boolean promote) {
        this.x = x;
        this.y = y;
        if (color == ColorP.BLACK) {
            this.piece_int = QUEEN_BLACK;
            this.image = "assets/queenB.png";
            this.color = ColorP.BLACK;
        } else {
            this.piece_int = QUEEN_WHITE;
            this.image = "assets/queenW.png";
            this.color = ColorP.WHITE;
        }
        if (promote) {
            try {
                this.pieceImage = ImageIO.read(new File(this.image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // -1 : interdit / 1 : deplacement / 2 : mange
    // TODO : ENLEVER LE KING
    @Override
    public int isValidMove(int x_next, int y_next, Piece[][] board) {

        int res = -1;
        Bishop bishop = new Bishop(this.x, this.y, this.color);
        Rook rook = new Rook(this.x, this.y, this.color);
        King king = new King(this.x, this.y, this.color);

        if (((bishop.isValidMove(x_next, y_next, board)) == -1) && (rook.isValidMove(x_next, y_next, board) == -1)
                && (king.isValidMove(x_next, y_next, board) == -1)) {
            res = -1;
        } else if ((((bishop.isValidMove(x_next, y_next, board)) == 2)
                || (rook.isValidMove(x_next, y_next, board) == 2)
                || (king.isValidMove(x_next, y_next, board) == 2))) {
            res = 2;
        } else {
            res = 1;
        }

        return res;
    }

    @Override
    public Piece.ColorP threatedKing(Piece[][] board) {

        Piece.ColorP res = null;
        Bishop bishop = new Bishop(this.x, this.y, this.color);
        Rook rook = new Rook(this.x, this.y, this.color);

        if (bishop.threatedKing(board) != null) {
            res = bishop.threatedKing(board);
        }
        if (rook.threatedKing(board) != null) {
            res = rook.threatedKing(board);
        }

        return res;

    }

    public int getQUEEN_WHITE() {
        return QUEEN_WHITE;
    }

    public int getQUEEN_BLACK() {
        return QUEEN_BLACK;
    }

}
