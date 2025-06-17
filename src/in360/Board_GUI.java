package in360;

import in360.Piece.ColorP;
import in360.pieces.*;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

/**
 * Graphical user interface for the chess board.
 */
public class Board_GUI {

    /**
     * Creates the main window and adds the chess board panel.
     * 
     * @param chess the Chess game object
     */
    public Board_GUI(Chess chess) {

        // Creation of a new window
        JFrame window = new JFrame("Board");
        window.setSize(900, 900);
        window.setLocation(100, 100);

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ChessBoardPanel boardPanel = new ChessBoardPanel(chess);
        window.add(boardPanel);
        window.setVisible(true);

    }

    /**
     * Inner class representing the chess board panel.
     * Handles drawing the board, pieces, and mouse events.
     */
    class ChessBoardPanel extends JPanel {

        Chess chess; // Reference to the Chess game logic
        private int x_next; // X position for dragging
        private int y_next; // Y position for dragging

        private Piece selected_piece; // Currently selected piece
        private boolean start = true; // Indicates if a drag has started
        private boolean dragged = false; // Indicates if a piece is being dragged
        Piece.ColorP winner; // Stores the winner color
        private Piece tempPrevious; // Stores the previous piece for undo
        private int xPrevious; // Previous X position
        private int yPrevious; // Previous Y position
        Chess.states state_game; // Current game state

        private BufferedImage boardImage; // Store the chessboard image

        private BufferedImage tempImage; // Image of the dragged piece

        private JButton demoButton; // Button to start demo
        private JButton restartButton; // Button to restart game

        /**
         * Constructor for the chess board panel.
         * Draws the board and loads piece images.
         * 
         * @param chess the Chess game object
         */
        public ChessBoardPanel(Chess chess) {

            // Get the chess object
            this.chess = chess;
            this.state_game = Chess.states.GAME;

            // Create and add the demo button
            demoButton = new JButton("Demo");
            this.add(demoButton);
            demoButton.addActionListener(e -> demoCode());

            // Create and add the restart button
            restartButton = new JButton("Restart");
            this.add(restartButton);
            restartButton.addActionListener(e -> restartCode(chess, this));

            // Create the chessboard image
            boardImage = new BufferedImage(900, 900, BufferedImage.TYPE_INT_ARGB);
            Graphics gBoard = boardImage.getGraphics();
            // Draw the chessboard squares
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {

                    // Alternate colors for the chessboard squares
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

            // Load images for all pieces on the board
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

            // Mouse motion listener for dragging pieces
            addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    // Start dragging the selected piece
                    if (start == true && selected_piece != null) {  // <-- Ajouté une vérification ici
                        tempPrevious = chess.chess_state[selected_piece.x][selected_piece.y];
                        xPrevious = selected_piece.x;
                        yPrevious = selected_piece.y;
                        chess.chess_state[selected_piece.x][selected_piece.y] = null;
                        dragged = true;
                        start = false;
                    }

                    // Toujours mettre à jour la position de la souris, même si rien n'est sélectionné
                    x_next = e.getX();
                    y_next = e.getY();

                    repaint();
                }

            });

            // Mouse listener for selecting and releasing pieces
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // Select the piece at the clicked position
                    int x = (int) ((e.getX() - 50) / 100);
                    int y = (int) ((e.getY() - 50) / 100);
                    if (x >= 0 && x < 8 && y >= 0 && y < 8) {
                        selected_piece = chess.chess_state[x][y];
                        if (selected_piece != null) {
                            try {
                                tempImage = ImageIO.read(new File(selected_piece.image));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                tempImage = null;
                            }
                        } else {
                            tempImage = null;
                        }
                        start = true;
                    }
                }

                public void mouseReleased(MouseEvent e) {

                    int x = (int) ((e.getX() - 50) / 100);
                    int y = (int) ((e.getY() - 50) / 100);

                    handleMouseRelease(x, y);
                }
            });
        }

        /**
         * Paints the chess board and pieces.
         * 
         * @param g the Graphics object
         */
        @Override
        protected void paintComponent(Graphics g) {

            // Call the original paintComponent method to reset the background
            super.paintComponent(g);

            switch (state_game) {
                case Chess.states.INIT:
                    // Initial state, nothing to draw

                    break;
                case Chess.states.GAME:

                    // Draw the chessboard
                    g.drawImage(boardImage, 0, 0, this);

                    // Draw the turn indicator (white or black)
                    if (chess.turn == Piece.ColorP.BLACK) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.WHITE);
                    }

                    g.fillRect(50 + 800, 50 + 350, 50, 100);

                    // Draw all pieces on the board
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {

                            if (chess.chess_state[i][j] != null) {

                                g.drawImage(chess.chess_state[i][j].pieceImage, 62 + (100 * i), 60 + (100 * j), 75,
                                        75,
                                        this);

                            }
                        }
                    }

                    // Draw the dragged piece if any
                    if (dragged == true) {
                        g.drawImage(tempImage, x_next - 50, y_next - 50, 75, 75, this);
                    }

                    break;

                case Chess.states.END:

                    // Draw the end game message and winner
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

        /**
         * Handles the logic when a piece is released (either by mouse or demo).
         * Validates the move, updates the board, manages special moves, and checks for
         * endgame.
         * 
         * @param x destination x
         * @param y destination y
         */
        public void handleMouseRelease(int x, int y) {
            if (selected_piece != null) {
                int valid = selected_piece.isValid(chess.chess_state, x, y, chess.getnTour());

                // Only allow moving pieces of the current turn color
                if (selected_piece.color != chess.turn) {
                    valid = 0;
                }

                // Handle en passant capture
                if (valid == 3) {
                    if (chess.turn == Piece.ColorP.BLACK) {
                        chess.chess_state[x][y - 1] = null;
                    } else {
                        chess.chess_state[x][y + 1] = null;
                    }
                }

                // Handle kingside castling
                if (valid == 4) {
                    chess.chess_state[5][y] = new Rook(5, y, chess.turn, true);
                    chess.chess_state[7][y] = null;
                }
                // Handle queenside castling
                if (valid == 5) {
                    chess.chess_state[3][y] = new Rook(3, y, chess.turn, true);
                    chess.chess_state[0][y] = null;
                }

                if (valid > 0) {

                    selected_piece.x = x;
                    selected_piece.y = y;

                    // Handle pawn promotion
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

                // Place the moved piece on the board
                Piece temp = chess.chess_state[selected_piece.x][selected_piece.y];
                chess.chess_state[selected_piece.x][selected_piece.y] = selected_piece;

                // Check if a king is in check after the move
                Piece.ColorP whoChess = chess.isChess();
                if (whoChess == Piece.ColorP.BLACK) {
                    chess.setChessBlack(true);
                    if (chess.turn == Piece.ColorP.BLACK) {

                    } else {
                        // Undo the move if it puts own king in check
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
                        // Undo the move if it puts own king in check
                        chess.chess_state[selected_piece.x][selected_piece.y] = temp;
                        chess.chess_state[xPrevious][yPrevious] = tempPrevious;
                        chess.chess_state[xPrevious][yPrevious].x = xPrevious;
                        chess.chess_state[xPrevious][yPrevious].y = yPrevious;
                        chess.turn = Piece.ColorP.WHITE;
                    }
                } else {
                    chess.setChessWhite(false);
                }

                // If the move is valid and the game is not over, increment the turn and apply
                // the move
                if ((valid > 0) && (whoChess == null) || chess.chessBlack || chess.chessWhite) {
                    chess.setnTour(chess.getnTour() + 1);

                    chess.effectiveMove(selected_piece);

                }
            }
            // Check for end of game and set winner
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

        /**
         * Runs a demo sequence of moves to showcase different chess rules and piece
         * movements.
         * Uses a timer to animate each move step by step.
         */
        public void demoCode() {
            Timer timer = new Timer(700, null); // 700 ms between each move

            // Sequence of moves to demonstrate various chess rules and piece movements
            final int[][] moves = {
                    // Pawns to free bishops
                    { 2, 1, 2, 3 }, // White c2->c4
                    { 5, 6, 5, 4 }, // Black f7->f5

                    // Central pawns
                    { 4, 1, 4, 3 }, // White e2->e4
                    { 4, 6, 4, 4 }, // Black e7->e5
                    { 3, 1, 3, 3 }, // White d2->d4
                    { 3, 6, 3, 4 }, // Black d7->d5

                    // En passant
                    { 4, 3, 3, 4 }, // White e4 takes d5 en passant

                    // Knights
                    { 6, 0, 5, 2 }, // White g1->f3
                    { 1, 7, 2, 5 }, // Black b8->c6

                    // Bishops (now they can move)
                    { 5, 0, 2, 3 }, // White f1->c4
                    { 2, 7, 5, 4 }, // Black c8->f5

                    // Rooks
                    { 7, 0, 7, 3 }, // White h1->h4
                    { 0, 7, 0, 4 }, // Black a8->a5

                    // Queens (after freeing the h-file)
                    { 7, 1, 7, 3 }, // White h2->h4
                    { 7, 6, 7, 4 }, // Black h7->h5
                    { 3, 0, 7, 4 }, // White d1->h5 (attacks black king)
                    { 3, 7, 7, 3 }, // Black d8->h4

                    // Check
                    { 7, 4, 4, 7 }, // White h5->e8 (check to black king)

                    // Kingside castling
                    { 4, 0, 6, 0 }, // White e1->g1 (castling)
                    { 4, 7, 6, 7 }, // Black e8->g8 (castling)
            };
            final int[] step = { 0 };
            timer.addActionListener(e -> {
                if (step[0] < moves.length) {
                    int x1 = moves[step[0]][0];
                    int y1 = moves[step[0]][1];
                    int x2 = moves[step[0]][2];
                    int y2 = moves[step[0]][3];
                    selected_piece = chess.chess_state[x1][y1];
                    if (selected_piece != null) {
                        // Simulate drag & drop
                        tempPrevious = chess.chess_state[selected_piece.x][selected_piece.y];
                        xPrevious = selected_piece.x;
                        yPrevious = selected_piece.y;
                        chess.chess_state[selected_piece.x][selected_piece.y] = null;
                        dragged = true;
                        start = false;
                        handleMouseRelease(x2, y2); // Move the piece
                    }
                    step[0]++;
                } else {
                    timer.stop();
                }
            });
            timer.start();
        }
    }

    /**
     * Resets the chess board and game state to the initial configuration.
     * 
     * @param chess the Chess game object
     * @param board the ChessBoardPanel to repaint and reset
     */
    public void restartCode(Chess chess, ChessBoardPanel board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chess.chess_state[i][j] = chess.initGame[i][j];
            }
        }
        // Reset all important values (color,...)
        chess.turn = Piece.ColorP.WHITE;
        chess.setnTour(0);
        chess.setChessBlack(false);
        chess.setChessWhite(false);
        board.state_game = Chess.states.GAME;
        board.winner = null;
        board.selected_piece = null;
        board.dragged = false;
        board.repaint();
    }
}