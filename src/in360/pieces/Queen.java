package in360.pieces;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import in360.Piece;

/**
 * Represents a Queen chess piece.
 */
public class Queen extends Piece {

    // Unicode values for white and black queens
    private final int QUEEN_WHITE = 0x2655;
    private final int QUEEN_BLACK = 0x265B;

    /**
     * Constructor for Queen.
     * 
     * @param x     initial x position
     * @param y     initial y position
     * @param color piece color
     */
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

    /**
     * Constructor for Queen with promotion option.
     * 
     * @param x       initial x position
     * @param y       initial y position
     * @param color   piece color
     * @param promote true if the queen is created by pawn promotion
     */
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

    /**
     * Checks if a move is valid for the queen.
     * The queen can move like a bishop or a rook.
     * 
     * @param x_next target x position
     * @param y_next target y position
     * @param board  the chess board
     * @return -1: invalid, 1: move, 2: capture
     */
    @Override
    public int isValidMove(int x_next, int y_next, Piece[][] board) {

        int res = -1;
        // Create temporary bishop and rook to reuse their movement logic
        Bishop bishop = new Bishop(this.x, this.y, this.color);
        Rook rook = new Rook(this.x, this.y, this.color);

        // If neither bishop nor rook move is valid, queen move is invalid
        if (((bishop.isValidMove(x_next, y_next, board)) == -1) && (rook.isValidMove(x_next, y_next, board) == -1)) {
            res = -1;
        }
        // If either bishop or rook move is a capture, queen move is a capture
        else if ((((bishop.isValidMove(x_next, y_next, board)) == 2)
                || (rook.isValidMove(x_next, y_next, board) == 2))) {
            res = 2;
        } else {
            res = 1;
        }

        return res;
    }

    /**
     * Checks if this queen threatens an opposing king.
     * Uses bishop and rook logic to determine if a king is threatened.
     * 
     * @param board the chess board
     * @return the color of the threatened king, or null if none
     */
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

    /**
     * @return Unicode value for white queen
     */
    public int getQUEEN_WHITE() {
        return QUEEN_WHITE;
    }

    /**
     * @return Unicode value for black queen
     */
    public int getQUEEN_BLACK() {
        return QUEEN_BLACK;
    }

}