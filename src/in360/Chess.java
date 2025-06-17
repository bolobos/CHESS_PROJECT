package in360;

import in360.Piece.ColorP;
import in360.pieces.*;

/**
 * Main class for the Chess game.
 * Handles game state, board initialization, move validation, and game logic.
 */
public class Chess {

	// 8x8 chess board state, each cell contains a Piece or null
	protected Piece[][] chess_state = new Piece[8][8];


	protected Piece[][] initGame;

	// Graphical user interface for the board
	private Board_GUI board;

	/**
	 * Enum representing the possible states of the game.
	 */
	public enum states {
		INIT, GAME, END
	}

	protected states state_game; // Current state of the game

	protected int nTour = 0; // Move counter

	protected Piece.ColorP winner = null; // Stores the winner's color

	protected Piece.ColorP turn; // Current player's turn

	protected boolean chessBlack = false; // Is black in check?
	protected boolean chessWhite = false; // Is white in check?

	/**
	 * Gets the current move number.
	 * 
	 * @return the move number
	 */
	public int getnTour() {
		return nTour;
	}

	/**
	 * Sets the current move number.
	 * 
	 * @param nTour the move number
	 */
	public void setnTour(int nTour) {
		this.nTour = nTour;
	}

	/**
	 * Main entry point for the Chess game.
	 * Initializes the game and launches the GUI.
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {

		Chess chess = new Chess();

		chess.execute();

		chess.graphic();

		// Ajoute un shutdown hook pour détecter Ctrl+C
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Fermeture de l'interface !");
			// Ajoute ici tout nettoyage ou sauvegarde nécessaire
		}));

	}

	/**
	 * Initializes the chess board and pieces.
	 */
	private void execute() {

		this.state_game = states.GAME;
		this.turn = Piece.ColorP.WHITE;

		Piece.ColorP color = Piece.ColorP.BLACK;

		// Place all pieces for both colors
		for (int i = 0; i < 2; i++) {

			if (i == 1) {
				color = Piece.ColorP.WHITE;
			}

			chess_state[0][7 * i] = new Rook(0, 7 * i, color);
			chess_state[7][7 * i] = new Rook(7, 7 * i, color);
			chess_state[1][7 * i] = new Knight(1, 7 * i, color);
			chess_state[6][7 * i] = new Knight(6, 7 * i, color);
			chess_state[2][7 * i] = new Bishop(2, 7 * i, color);
			chess_state[5][7 * i] = new Bishop(5, 7 * i, color);
			chess_state[3][7 * i] = new Queen(3, 7 * i, color);
			chess_state[4][7 * i] = new King(4, 7 * i, color);

			// Place pawns
			for (int j = 0; j <= 7; j++) {
				chess_state[j][1 + 5 * i] = new Pawn(j, 1 + 5 * i, color);
			}
		}

		// Sauvegarde de l'état initial du plateau
		initGame = new Piece[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				initGame[i][j] = chess_state[i][j];
			}
		}

		// Place the Lion piece from JSON definition
		// Use absolute path or resource loading for the JSON file
		String lionJsonPath = System.getProperty("user.dir") + "/piecesJSON/lion.json";
		Lion lion = new Lion(lionJsonPath);
		chess_state[lion.x][lion.y] = lion;

		// Print the board to the console
		// for (int row = 7; row >= 0; row--) {
		// System.out.print((row + 1) + "|");
		// for (int col = 0; col < 8; col++) {
		// if (chess_state[col][row] == null) {
		// System.out.print((empty()) + "|");
		// } else {
		// System.out.print(show(chess_state[col][row].piece_int) + "|");
		// }
		// }
		// System.out.println();
		// }
		// System.out.println(" A B C D E F G H");
	}

	/**
	 * Launches the graphical interface for the chess game.
	 */
	private void graphic() {

		System.out.println("Lancement de l'interface graphique...");
		// Creation of new window
		board = new Board_GUI(this);
	}

	/**
	 * Returns a string representing an empty square.
	 * 
	 * @return a single space character
	 */
	private String empty() {
		return " ";
	}

	/**
	 * Converts a Unicode code point to a string for displaying a piece.
	 * 
	 * @param piece the Unicode code point
	 * @return the string representation
	 */
	private String show(int piece) {
		return new String(Character.toChars(piece));
	}

	/**
	 * Checks if the current player is checkmated.
	 * Tries all possible moves for the current player and checks if any move can
	 * escape check.
	 * 
	 * @return the color of the losing player if checkmate, or null if not
	 */
	public Piece.ColorP isEnd() {
		Piece.ColorP current = turn;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece piece = chess_state[i][j];
				if (piece != null && piece.color == current) {
					// Try all possible moves for this piece
					for (int x = 0; x < 8; x++) {
						for (int y = 0; y < 8; y++) {
							if (piece.isValid(chess_state, x, y, this.nTour) > 0) {
								// Save the current state
								Piece temp = chess_state[x][y];
								chess_state[x][y] = piece;
								chess_state[i][j] = null;
								// Check if the king is still in check after this move
								if (this.isChess() != current) {
									// Undo the move
									chess_state[i][j] = piece;
									chess_state[x][y] = temp;
									return null; // There is at least one move to escape check
								}
								// Undo the move
								chess_state[i][j] = piece;
								chess_state[x][y] = temp;
							}
						}
					}
				}
			}
		}
		// No move can escape check: the current player has lost
		return current;
	}

	/**
	 * Increments the move counter if the move is effective and updates piece state.
	 * 
	 * @param pieceMove the piece that was moved
	 */
	public void effectiveMove(Piece pieceMove) {
		this.nTour++;
		if (pieceMove instanceof King) {
			((King) pieceMove).setHasAlreadyMoved(true);
		}
		if (pieceMove instanceof Rook) {
			((Rook) pieceMove).setHasAlreadyMoved(true);
		}
	}

	/**
	 * Checks if any king is in check.
	 * 
	 * @return the color of the king in check, or null if none
	 */
	public Piece.ColorP isChess() {
		Piece.ColorP res = null;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chess_state[i][j] != null) {
					res = chess_state[i][j].isThreatedKing(chess_state);
					if (res != null) {
						return res;
					}
				}
			}
		}
		return res;
	}

	// GETTERS AND SETTERS

	/**
	 * Gets the current game state.
	 * 
	 * @return the game state
	 */
	public states getState_game() {
		return state_game;
	}

	/**
	 * Sets the current game state.
	 * 
	 * @param state_game the game state
	 */
	public void setState_game(states state_game) {
		this.state_game = state_game;
	}

	/**
	 * Checks if black is in check.
	 * 
	 * @return true if black is in check, false otherwise
	 */
	public boolean isChessBlack() {
		return chessBlack;
	}

	/**
	 * Sets the black check status.
	 * 
	 * @param chessBlack true if black is in check
	 */
	public void setChessBlack(boolean chessBlack) {
		this.chessBlack = chessBlack;
	}

	/**
	 * Checks if white is in check.
	 * 
	 * @return true if white is in check, false otherwise
	 */
	public boolean isChessWhite() {
		return chessWhite;
	}

	/**
	 * Sets the white check status.
	 * 
	 * @param chessWhite true if white is in check
	 */
	public void setChessWhite(boolean chessWhite) {
		this.chessWhite = chessWhite;
	}

	/**
	 * Gets the current chess board state.
	 * 
	 * @return the chess board
	 */
	public Piece[][] getChess_state() {
		return chess_state;
	}

	/**
	 * Sets the chess board state.
	 * 
	 * @param chess_state the chess board
	 */
	public void setChess_state(Piece[][] chess_state) {
		this.chess_state = chess_state;
	}

	/**
	 * Gets the board GUI.
	 * 
	 * @return the board GUI
	 */
	public Board_GUI getBoard() {
		return board;
	}

	/**
	 * Sets the board GUI.
	 * 
	 * @param board the board GUI
	 */
	public void setBoard(Board_GUI board) {
		this.board = board;
	}

	/**
	 * Gets the winner's color.
	 * 
	 * @return the winner's color
	 */
	public Piece.ColorP getWinner() {
		return winner;
	}

	/**
	 * Sets the winner's color.
	 * 
	 * @param winner the winner's color
	 */
	public void setWinner(Piece.ColorP winner) {
		this.winner = winner;
	}

	/**
	 * Gets the current player's turn.
	 * 
	 * @return the current player's color
	 */
	public Piece.ColorP getTurn() {
		return turn;
	}

	/**
	 * Sets the current player's turn.
	 * 
	 * @param turn the player's color
	 */
	public void setTurn(Piece.ColorP turn) {
		this.turn = turn;
	}

}