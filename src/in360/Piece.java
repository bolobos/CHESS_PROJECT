package in360;

import java.awt.image.BufferedImage;
import in360.pieces.Bishop;
import in360.pieces.King;
import in360.pieces.Knight;
import in360.pieces.Pawn;
import in360.pieces.Queen;
import in360.pieces.Rook;

public class Piece {

    protected int x;
    protected int y;

    public enum ColorP {
        WHITE, BLACK
    }

    protected ColorP color;
    protected int piece_int;
    protected String image;

    protected BufferedImage pieceImage;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ColorP getColor() {
        return color;
    }

    public void setColor(ColorP color) {
        this.color = color;
    }

    public int getPiece_int() {
        return piece_int;
    }

    public void setPiece_int(int piece_int) {
        this.piece_int = piece_int;
    }

    public int isValid(Piece selected_piece, Piece[][] arrayCol, int x_next, int y_next) {
        int res = -1;

        if (((x_next == x) && (y_next == y)) || (x_next > 7) || (y_next > 7)) {
        } else {
            if (selected_piece instanceof King) {
                res = ((King) selected_piece).isValidMove(x_next, y_next, arrayCol);
            } else if (selected_piece instanceof Queen) {
                res = ((Queen) selected_piece).isValidMove(x_next, y_next, arrayCol);
            } else if (selected_piece instanceof Rook) {
                res = ((Rook) selected_piece).isValidMove(x_next, y_next, arrayCol);
            } else if (selected_piece instanceof Bishop) {
                res = ((Bishop) selected_piece).isValidMove(x_next, y_next, arrayCol);
            } else if (selected_piece instanceof Knight) {
                res = ((Knight) selected_piece).isValidMove(x_next, y_next, arrayCol);
            } else if (selected_piece instanceof Pawn) {
                res = ((Pawn) selected_piece).isValidMove(x_next, y_next, arrayCol);
            } else {
                System.out.println("Type de pièce inconnu.");
            }
        }
        return res;
    }

    public int isValidMove(int x_next, int y_next, Piece[][] board) {

        return -1;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
