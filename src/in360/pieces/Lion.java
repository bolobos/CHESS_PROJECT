package in360.pieces;

import in360.Piece;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Lion extends Piece {

    private final int LION_WHITE = 0x00;
    private final int LION_BLACK = 0x00;

    public static class Move {
        public int dx; // déplacement relatif en x
        public int dy; // déplacement relatif en y
        public boolean isCapture;

        public Move(int dx, int dy, boolean isCapture) {
            this.dx = dx;
            this.dy = dy;
            this.isCapture = isCapture;
        }
    }

    private java.util.List<Move> availableMoves = new java.util.ArrayList<>();

    public java.util.List<Move> getAvailableMoves() {
        return availableMoves;
    }

    public Lion(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            Lion temp = gson.fromJson(reader, Lion.class);
            this.x = temp.x;
            this.y = temp.y;
            this.color = temp.color;
            this.availableMoves = temp.availableMoves;

            if (color == ColorP.BLACK) {
                this.image = "assets/lionB.png";
                this.color = ColorP.BLACK;
            } else {
                this.image = "assets/lionW.png";
                this.color = ColorP.WHITE;
            }
        } catch (IOException | JsonSyntaxException | JsonIOException e) {
            e.printStackTrace();
        }
    }

    public int isValidMove(int x_next, int y_next, Piece[][] board) {
        for (Move move : availableMoves) {
            int destX = this.x + move.dx;
            int destY = this.y + move.dy;
            if (destX == x_next && destY == y_next) {
                if (board[x_next][y_next] == null) {
                    return 1; // Move possible, empty square
                } else if (move.isCapture && board[x_next][y_next].getColor() != this.color) {
                    return 2; // Move possible, capture
                } else {
                    return 0; // Move not allowed
                }
            }
        }
        return 0; // Move impossible
    }

    @Override
    public Piece.ColorP threatedKing(Piece[][] board) {
        for (Move move : availableMoves) {
            int destX = this.x + move.dx;
            int destY = this.y + move.dy;
            if (destX >= 0 && destX < 8 && destY >= 0 && destY < 8) {
                Piece target = board[destX][destY];
                if (target instanceof King && target.getColor() != this.color) {
                    return target.getColor();
                }
            }
        }
        return null;
    }

}