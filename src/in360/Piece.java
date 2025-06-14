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

    protected boolean isChess = false;

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

    // maybe rename isVlid with a more general form actionSpecificPiece
    public int isValid(Piece[][] arrayCol, int x_next, int y_next) {
        int res = -1;

        if (((x_next == x) && (y_next == y)) || (x_next > 7) || (y_next > 7)) {
        } else {
            if (this instanceof King) {
                res = ((King) this).isValidMove(x_next, y_next, arrayCol);
            } else if (this instanceof Queen) {
                res = ((Queen) this).isValidMove(x_next, y_next, arrayCol);
            } else if (this instanceof Rook) {
                res = ((Rook) this).isValidMove(x_next, y_next, arrayCol);
            } else if (this instanceof Bishop) {
                res = ((Bishop) this).isValidMove(x_next, y_next, arrayCol);
            } else if (this instanceof Knight) {
                res = ((Knight) this).isValidMove(x_next, y_next, arrayCol);
            } else if (this instanceof Pawn) {
                res = ((Pawn) this).isValidMove(x_next, y_next, arrayCol);
            } else {
                System.out.println("Type de pièce inconnu.");
            }
        }
        return res;

    }

    public int isValidMove(int x_next, int y_next, Piece[][] board) {

        return -1;
    }

    public Piece.ColorP isThreatedKing(Piece[][] board) {

        Piece.ColorP res = null;
        
        if (this instanceof Queen) {
            res = ((Queen) this).threatedKing(board);
        } else if (this instanceof Rook) {
            res = ((Rook) this).threatedKing(board);
        } else if (this instanceof Bishop) {
            res = ((Bishop) this).threatedKing(board);
        } else if (this instanceof Knight) {
            res = ((Knight) this).threatedKing(board);
        } else if (this instanceof Pawn) {
            res = ((Pawn) this).threatedKing(board);
        } else if (this instanceof King) {
            res = ((King) this).threatedKing(board);
        } else {
            System.out.println("Type de pièce inconnu.");
        }
        return res;
    }

    public Piece.ColorP threatedKing(Piece[][] board) {
        return null;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
