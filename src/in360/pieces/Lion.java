package in360.pieces;

import in360.Piece;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * Represents a Lion chess piece with custom moves loaded from a JSON file.
 */
public class Lion extends Piece {

    // Unicode values for white and black lions (custom, can be changed)
    private final int LION_WHITE = 0x00;
    private final int LION_BLACK = 0x00;

    /**
     * Represents a possible move for the Lion.
     * dx and dy are relative displacements from the current position.
     * isCapture indicates if the move allows capturing an opponent's piece.
     */
    public static class Move {
        public int dx; // relative x displacement
        public int dy; // relative y displacement
        public boolean isCapture;

        public Move(int dx, int dy, boolean isCapture) {
            this.dx = dx;
            this.dy = dy;
            this.isCapture = isCapture;
        }
    }

    // List of available moves for the Lion, loaded from JSON
    private java.util.List<Move> availableMoves = new java.util.ArrayList<>();

    /**
     * Returns the list of available moves for the Lion.
     * 
     * @return list of Move objects
     */
    public java.util.List<Move> getAvailableMoves() {
        return availableMoves;
    }

    /**
     * Constructs a Lion by loading its properties and moves from a JSON file.
     * 
     * @param filePath path to the JSON file
     */
    public Lion(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            Lion temp = gson.fromJson(reader, Lion.class);
            this.x = temp.x;
            this.y = temp.y;
            this.color = temp.color;
            this.availableMoves = temp.availableMoves;

            // Set image and color based on loaded color
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

    /**
     * Checks if a move to (x_next, y_next) is valid for the Lion.
     * 
     * @param x_next target x position
     * @param y_next target y position
     * @param board  the chess board
     * @return 0: move impossible, 1: move possible, 2: capture possible
     */
    public int isValidMove(int x_next, int y_next, Piece[][] board) {
        for (Move move : availableMoves) {
            int destX = this.x + move.dx;
            int destY = this.y + move.dy;
            if (destX == x_next && destY == y_next) {
                // Vérifier si le chemin est libre (pour les mouvements de plusieurs cases)
                if (Math.abs(move.dx) > 1 || Math.abs(move.dy) > 1) {
                    if (!isPathClear(this.x, this.y, x_next, y_next, board)) {
                        return 0; // Chemin bloqué par une autre pièce
                    }
                }
                
                // Vérifier la case d'arrivée
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

    /**
     * Vérifie si le chemin entre deux positions est libre d'autres pièces.
     * 
     * @param x1 position de départ x
     * @param y1 position de départ y
     * @param x2 position d'arrivée x
     * @param y2 position d'arrivée y
     * @param board échiquier
     * @return true si le chemin est libre, false sinon
     */
    private boolean isPathClear(int x1, int y1, int x2, int y2, Piece[][] board) {
        // Déterminer le pas pour parcourir le chemin
        int stepX = Integer.compare(x2, x1); // -1, 0 ou 1
        int stepY = Integer.compare(y2, y1); // -1, 0 ou 1
        
        // Position actuelle (commencer à partir de la case APRÈS celle de départ)
        int x = x1 + stepX;
        int y = y1 + stepY;
        
        // Vérifier chaque case du chemin sauf la dernière (destination)
        while (x != x2 || y != y2) {
            if (board[x][y] != null) {
                return false; // Chemin bloqué
            }
            x += stepX;
            y += stepY;
        }
        
        return true; // Chemin libre
    }

    /**
     * Checks if this Lion threatens an opposing king.
     * 
     * @param board the chess board
     * @return the color of the threatened king, or null if none
     */
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