package in360;

import java.awt.image.BufferedImage;
import in360.pieces.Bishop;
import in360.pieces.King;
import in360.pieces.Knight;
import in360.pieces.Pawn;
import in360.pieces.Queen;
import in360.pieces.Rook;
import in360.pieces.Lion;

/**
 * Abstract representation of a chess piece.
 * Contains common properties and methods for all chess pieces.
 */
public class Piece {

    // Current x position on the board (0-7)
    protected int x;
    // Current y position on the board (0-7)
    protected int y;

    /**
     * Enum representing the color of a piece.
     */
    public enum ColorP {
        WHITE, BLACK
    }

    // Color of the piece
    protected ColorP color;
    // Unicode or integer representation of the piece
    protected int piece_int;
    // Path to the image file for the piece
    protected String image;

    // Indicates if the piece is currently in check (for kings)
    protected boolean isChess = false;

    // Image object for GUI display (not serialized)
    protected transient BufferedImage pieceImage;

    /**
     * Gets the x position of the piece.
     * 
     * @return x position
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x position of the piece.
     * 
     * @param x new x position
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y position of the piece.
     * 
     * @return y position
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y position of the piece.
     * 
     * @param y new y position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the color of the piece.
     * 
     * @return color of the piece
     */
    public ColorP getColor() {
        return color;
    }

    /**
     * Sets the color of the piece.
     * 
     * @param color new color
     */
    public void setColor(ColorP color) {
        this.color = color;
    }

    /**
     * Gets the integer representation of the piece.
     * 
     * @return piece integer
     */
    public int getPiece_int() {
        return piece_int;
    }

    /**
     * Sets the integer representation of the piece.
     * 
     * @param piece_int new piece integer
     */
    public void setPiece_int(int piece_int) {
        this.piece_int = piece_int;
    }

    /**
     * General method to check if a move is valid for the specific piece type.
     * Delegates to the correct subclass based on the piece type.
     * 
     * @param arrayCol the chess board
     * @param x_next   target x position
     * @param y_next   target y position
     * @param nbTurn   current turn number (for pawns and special moves)
     * @return result code depending on the piece's move logic
     */
    // maybe rename isVlid with a more general form actionSpecificPiece
    public int isValid(Piece[][] arrayCol, int x_next, int y_next, int nbTurn) {
        int res = -1;

        // Check for out-of-bounds or no movement
        if (((x_next == x) && (y_next == y)) || (x_next > 7) || (y_next > 7) || (x_next < 0) || (y_next < 0)) {
            // Invalid move
        } else {
            // Delegate to the correct piece subclass
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
                ((Pawn) this).setTurn(nbTurn);
                res = ((Pawn) this).isValidMove(x_next, y_next, arrayCol);
            } else if (this instanceof Lion) {
                res = ((Lion) this).isValidMove(x_next, y_next, arrayCol);
            } else {
                System.out.println("Type de pièce inconnu.");
            }
        }
        return res;
    }

    /**
     * Default implementation for move validation.
     * Should be overridden by subclasses.
     * 
     * @param x_next target x position
     * @param y_next target y position
     * @param board  the chess board
     * @return -1 (invalid by default)
     */
    public int isValidMove(int x_next, int y_next, Piece[][] board) {
        return -1;
    }

    /**
     * Checks if this piece threatens an opposing king.
     * Delegates to the correct subclass based on the piece type.
     * 
     * @param board the chess board
     * @return the color of the threatened king, or null if none
     */
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
        } else if (this instanceof Lion) {
            res = ((Lion) this).threatedKing(board);
        } else {
            System.out.println("Type de pièce inconnu.");
        }
        return res;
    }

    /**
     * Default implementation for checking if this piece threatens a king.
     * Should be overridden by subclasses.
     * 
     * @param board the chess board
     * @return null (no threat by default)
     */
    public Piece.ColorP threatedKing(Piece[][] board) {
        return null;
    }

    /**
     * Gets the image path for this piece.
     * 
     * @return image path
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image path for this piece.
     * 
     * @param image new image path
     */
    public void setImage(String image) {
        this.image = image;
    }

}