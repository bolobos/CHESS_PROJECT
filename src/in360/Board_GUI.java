package in360;

import in360.Piece.ColorP;
import in360.pieces.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

public class Board_GUI {

    public Board_GUI(Chess chess) {

        // Creation of a new window
        JFrame window = new JFrame("Board");
        // window.add(new JLabel("Hello world!"));
        window.setSize(900, 900);
        window.setLocation(100, 100);

        // Add a label to the window
        // JLabel label = new JLabel("New board !", JLabel.CENTER);
        // window.add(label);

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ChessBoardPanel boardPanel = new ChessBoardPanel(chess);
        window.add(boardPanel);
        window.setVisible(true);

    }

    // JPanel group of graphical component
    class ChessBoardPanel extends JPanel {

        Chess chess;
        private int x_next;
        private int y_next;

        private Piece selected_piece;
        private boolean start = true;
        private boolean dragged = false;
        Piece.ColorP winner;
        private Piece tempPrevious;
        private int xPrevious;
        private int yPrevious;
        Chess.states state_game;

        private BufferedImage boardImage; // Store the chessboard image

        private BufferedImage tempImage;

        public ChessBoardPanel(Chess chess) {

            // Get the chess object where 
            this.chess = chess;
            this.state_game = Chess.states.GAME;

            boardImage = new BufferedImage(900, 900, BufferedImage.TYPE_INT_ARGB);
            Graphics gBoard = boardImage.getGraphics();
            // Draw the chessboard
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {

                    if ((col % 2 == 0) && (row % 2 == 0)) {
                        gBoard.setColor(Color.WHITE);
                    } else if ((col % 2 == 1) && (row % 2 == 0)) {
                        gBoard.setColor(Color.BLACK);
                    } else if ((col % 2 == 0) && (row % 2 == 1)) {
                        gBoard.setColor(Color.BLACK);
                    } else {
                        gBoard.setColor(Color.WHITE);
                    }

                    gBoard.fillRect(50 + (100 * col), 50 + (100 * row), 100, 100);

                }
            }

            // Fill all the bufferedimage
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {

                    try {
                        if (chess.chess_state[i][j] != null) {
                            chess.chess_state[i][j].pieceImage = ImageIO.read(new File(chess.chess_state[i][j].image));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (start == true) {
                        tempPrevious = chess.chess_state[selected_piece.x][selected_piece.y];
                        xPrevious = selected_piece.x;
                        yPrevious = selected_piece.y;
                        chess.chess_state[selected_piece.x][selected_piece.y] = null;
                        dragged = true;
                        start = false;
                    }

                    x_next = e.getX();
                    y_next = e.getY();

                    // chess.chess_state[(x_prev-50)/100][(y_prev-50)/100]=
                    repaint();
                }

            });

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    selected_piece = chess.chess_state[(int) ((e.getX() - 50) / 100)][((int) (e.getY() - 50)
                            / 100)];
                    try {
                        tempImage = ImageIO.read(new File(selected_piece.image));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        tempImage = null;
                    }
                    start = true;

                }

                public void mouseReleased(MouseEvent e) {

                    int x = (int) ((e.getX() - 50) / 100);
                    int y = (int) ((e.getY() - 50) / 100);

                    if (selected_piece != null) {
                        int valid = selected_piece.isValid(chess.chess_state, x, y);

                        if (selected_piece.color != chess.turn) {
                            valid = 0;
                        }

                        if (valid > 0) {

                            selected_piece.x = x;
                            selected_piece.y = y;

                            if (chess.turn == Piece.ColorP.BLACK) {
                                chess.turn = Piece.ColorP.WHITE;

                                if ((selected_piece instanceof Pawn) && (selected_piece.y == 7)) {
                                    selected_piece = null;
                                    selected_piece = new Queen(x, y, Piece.ColorP.BLACK, true);
                                }
                            } else {
                                chess.turn = Piece.ColorP.BLACK;

                                if ((selected_piece instanceof Pawn) && (selected_piece.y == 0)) {
                                    selected_piece = null;
                                    selected_piece = new Queen(x, y, Piece.ColorP.WHITE, true);
                                }
                            }

                        }

                        Piece temp = chess.chess_state[selected_piece.x][selected_piece.y];
                        chess.chess_state[selected_piece.x][selected_piece.y] = selected_piece;

                        // Couleur du roi menacé
                        Piece.ColorP whoChess = chess.isChess();
                        if (whoChess == Piece.ColorP.BLACK) {
                            chess.setChessBlack(true);
                            if (chess.turn == Piece.ColorP.BLACK) {

                            } else {
                                
                                chess.chess_state[selected_piece.x][selected_piece.y] = temp;
                                chess.chess_state[xPrevious][yPrevious] = tempPrevious;
                                chess.chess_state[xPrevious][yPrevious].x = xPrevious;
                                chess.chess_state[xPrevious][yPrevious].y = yPrevious;
                                chess.turn = Piece.ColorP.BLACK;
                            }

                        } else {
                            chess.setChessBlack(false);
                        }
                        if (whoChess == Piece.ColorP.WHITE) {
                            chess.setChessBlack(true);
                            if (chess.turn == Piece.ColorP.WHITE) {

                            } else {
                                
                                chess.chess_state[selected_piece.x][selected_piece.y] = temp;
                                chess.chess_state[xPrevious][yPrevious] = tempPrevious;
                                chess.turn = Piece.ColorP.WHITE;
                            }
                        } else {
                            chess.setChessWhite(false);
                        }
                    }
                    if ((chess.chessBlack || chess.chessWhite) == true) {
                        winner = chess.isEnd();
                        if (winner != null) {
                            state_game = Chess.states.END;
                        }
                    }

                    selected_piece = null;
                    dragged = false;
                    repaint();

                }
            });
        }

        // override paintComponent method on JComponents class
        @Override
        protected void paintComponent(Graphics g) {

            // call the original paintComponent method, specific to JavaSwing, "reset the
            // backgroud"
            super.paintComponent(g);

            switch (state_game) {
                case Chess.states.INIT:

                    break;
                case Chess.states.GAME:

                    g.drawImage(boardImage, 0, 0, this);

                    if (chess.turn == Piece.ColorP.BLACK) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.WHITE);
                    }

                    g.fillRect(50 + 800, 50 + 350, 50, 100);

                    // Draw all pieces
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {

                            if (chess.chess_state[i][j] != null) {

                                g.drawImage(chess.chess_state[i][j].pieceImage, 62 + (100 * i), 60 + (100 * j), 75,
                                        75,
                                        this);

                            }
                        }
                    }

                    if (dragged == true) {
                        g.drawImage(tempImage, x_next - 50, y_next - 50, 75, 75, this);
                    }

                    break;

                case Chess.states.END:

                    g.setColor(Color.WHITE);
                    g.fillRect(100, 100, 100, 100);
                    g.setColor(Color.BLACK);
                    g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 32)); // Choose your font and size
                    g.drawString("The winner are ", 100, 50); // (x, y) position on the panel
                    if (winner == ColorP.BLACK) {
                        g.drawString("the whites ! ", 400, 50);
                    }
                    if (winner == ColorP.WHITE) {
                        g.drawString("the blacks ! ", 400, 50);
                    }
                    break;

                default:
                    break;
            }

        }
    }
}
