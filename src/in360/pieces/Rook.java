package in360.pieces;

import in360.Piece;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Represents a Rook chess piece.
 */
public class Rook extends Piece {

    // Unicode values for white and black rooks
    private final int ROOK_WHITE = 0x2656;
    private final int ROOK_BLACK = 0x265C;

    // Indicates if the rook has already moved (used for castling)
    private boolean hasAlreadyMoved = false;

    /**
     * Constructor for Rook.
     * 
     * @param x     initial x position
     * @param y     initial y position
     * @param color piece color
     */
    public Rook(int x, int y, ColorP color) {
        this.x = x;
        this.y = y;
        if (color == ColorP.BLACK) {
            this.piece_int = ROOK_BLACK;
            this.image = "assets/rookB.png";
            this.color = ColorP.BLACK;
        } else {
            this.piece_int = ROOK_WHITE;
            this.image = "assets/rookW.png";
            this.color = ColorP.WHITE;
        }
    }

    /**
     * Constructor for Rook with image loading option.
     * 
     * @param x         initial x position
     * @param y         initial y position
     * @param color     piece color
     * @param loadImage true to load the image for the rook
     */
    public Rook(int x, int y, ColorP color, boolean loadImage) {
        this(x, y, color);
        if (loadImage) {
            try {
                this.pieceImage = ImageIO.read(new File(this.image));
            } catch (IOException e) {
                e.printStackTrace();
                this.pieceImage = null;
            }
        }
    }

    /**
     * Checks if a move is valid for the rook.
     * The rook moves in straight lines (horizontal or vertical).
     * 
     * @param x_next target x position
     * @param y_next target y position
     * @param board  the chess board
     * @return -1: invalid, 1: move, 2: capture
     */
    @Override
    public int isValidMove(int x_next, int y_next, Piece[][] board) {
        int res = -1;
        int x_temp = x_next - x;
        int y_temp = y_next - y;

        // Rook moves only in straight lines
        if ((x_next == x) || (y_next == y)) {

            res = 1;

            // If the target square is occupied, it's a capture
            if (board[x_next][y_next] != null) {
                res = 2;
            }

            // Check for obstacles along the path
            if ((x_temp == 0) && (y_temp > 0)) {
                for (int m = 1; m < Math.abs(y_temp); m++) {
                    if (board[x][y + m] != null) {
                        res = -1;
                    }
                }
            } else if ((x_temp == 0) && (y_temp < 0)) {
                for (int m = 1; m < Math.abs(y_temp); m++) {
                    if (board[x][y - m] != null) {
                        res = -1;
                    }
                }
            } else if ((x_temp > 0) && (y_temp == 0)) {
                for (int m = 1; m < Math.abs(x_temp); m++) {
                    if (board[x + m][y] != null) {
                        res = -1;
                    }
                }
            } else {
                for (int m = 1; m < Math.abs(x_temp); m++) {
                    if (board[x - m][y] != null) {
                        res = -1;
                    }
                }
            }

            // If the target square is occupied by an allied piece, move is invalid
            if ((res == 2) && (board[x_next][y_next].getColor() == this.getColor())) {
                res = -1;
            }
        }
        return res;
    }

    /**
     * Checks if this rook threatens an opposing king.
     * 
     * @param board the chess board
     * @return the color of the threatened king, or null if none
     */
    @Override
    public Piece.ColorP threatedKing(Piece[][] board) {
        Piece.ColorP res = null;
        boolean a = false, b = false, c = false, d = false;

        // Scan all four straight directions from the rook's position
        for (int m = 1; m < 8; m++) {
            // Up
            if ((y + m < 8) && !a) {
                if (board[x][y + m] instanceof King && (board[x][y + m].getColor() != this.color)) {
                    res = board[x][y + m].getColor();
                }
                if (board[x][y + m] != null) {
                    a = true;
                }
            }
            // Down
            if ((y - m >= 0) && !b) {
                if (board[x][y - m] instanceof King && (board[x][y - m].getColor() != this.color)) {
                    res = board[x][y - m].getColor();
                }
                if (board[x][y - m] != null) {
                    b = true;
                }
            }
            // Right
            if ((x + m < 8) && !c) {
                if (board[x + m][y] instanceof King && (board[x + m][y].getColor() != this.color)) {
                    res = board[x + m][y].getColor();
                }
                if (board[x + m][y] != null) {
                    c = true;
                }
            }
            // Left
            if ((x - m >= 0) && !d) {
                if (board[x - m][y] instanceof King && (board[x - m][y].getColor() != this.color)) {
                    res = board[x - m][y].getColor();
                }
                if (board[x - m][y] != null) {
                    d = true;
                }
            }
        }
        return res;
    }

    /**
     * @return Unicode value for white rook
     */
    public int getROOK_WHITE() {
        return ROOK_WHITE;
    }

    /**
     * @return Unicode value for black rook
     */
    public int getROOK_BLACK() {
        return ROOK_BLACK;
    }

    /**
     * Checks if the rook has already moved (for castling).
     * 
     * @return true if the rook has moved, false otherwise
     */
    public boolean isHasAlreadyMoved() {
        return hasAlreadyMoved;
    }

    /**
     * Sets the flag indicating if the rook has already moved.
     * 
     * @param isAlreadyMove true if the rook has moved, false otherwise
     */
    public void setHasAlreadyMoved(boolean isAlreadyMove) {
        this.hasAlreadyMoved = isAlreadyMove;
    }

}