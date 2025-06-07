package in360;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.image.BufferedImage;
import java.util.List;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

public class Board_GUI {

    public Board_GUI(Chess chess) {

        // Creation of a new window
        JFrame window = new JFrame("Board");
        //window.add(new JLabel("Hello world!"));
        window.setSize(900, 900);
        window.setLocation(100, 100);

        // Add a label to the window
        // JLabel label = new JLabel("New board !", JLabel.CENTER);
        // window.add(label);

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);

        ChessBoardPanel boardPanel = new ChessBoardPanel(chess);
        window.add(boardPanel);

    }

    // JPanel group of graphical component
    class ChessBoardPanel extends JPanel {

        Chess chess;

        public ChessBoardPanel(Chess chess){
            this.chess = chess;
        }

        // override paintComponent method on JComponents class
        @Override
        protected void paintComponent(Graphics g) {

            // call the original paintComponent method, specific to JavaSwing, "reset the
            // backgroud"
            super.paintComponent(g);

            // Draw the chessboard
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {

                    if ((col % 2 == 0) && (row % 2 == 0)) {
                        g.setColor(Color.WHITE);
                    } else if ((col % 2 == 1) && (row % 2 == 0)) {
                        g.setColor(Color.BLACK);
                    } else if ((col % 2 == 0) && (row % 2 == 1)) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.WHITE);
                    }

                    g.fillRect(50 + (100 * col), 50 + (100 * row), 100, 100);

                }
            }

            List<Piece> pieces = chess.getPieces();

            // Draw all pieces
            for (Piece piece : pieces) {
                try {
                    BufferedImage image = ImageIO.read(new File(piece.image));
                    g.drawImage(image, 62 + (100 * piece.getX()), 60 + (100 * piece.getY()), 75, 75, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
