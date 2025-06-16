package in360;

import in360.Piece.ColorP;
import in360.pieces.*;

public class Chess {

	// Create 8x8
	protected Piece[][] chess_state = new Piece[8][8];

	private Board_GUI board;

	public enum states {
		INIT, GAME, END
	}

	protected states state_game;

	protected Piece.ColorP winner = null;

	protected Piece.ColorP turn;

	protected boolean chessBlack = false;
	protected boolean chessWhite = false;

	public static void main(String[] args) {

		Chess chess = new Chess();

		chess.execute();

		chess.graphic();

	}

	private void execute() {

		// for (int i = 0; i < 2; i++) {
		// for (int j = 0; j < 2; j++) {
		// pieces.add(new Rook(j,j));
		// pieces.add(new Knight());
		// pieces.add(new Bishop());
		// pieces.add(new Queen());
		// pieces.add(new King());
		// }
		// for (int row = 7; row >= 0; row--) {
		// pieces.add(new Pawn());
		// }
		// }

		state_game = states.GAME;
		turn = Piece.ColorP.WHITE;

		Piece.ColorP color = Piece.ColorP.BLACK;

		for (int i = 0; i < 2; i++) {

			if (i == 1) {
				color = Piece.ColorP.WHITE;
			} else {
			}

			chess_state[0][7 * i] = new Rook(0, 7 * i, color);
			chess_state[7][7 * i] = new Rook(7, 7 * i, color);
			chess_state[1][7 * i] = new Knight(1, 7 * i, color);
			chess_state[6][7 * i] = new Knight(6, 7 * i, color);
			chess_state[2][7 * i] = new Bishop(2, 7 * i, color);
			chess_state[5][7 * i] = new Bishop(5, 7 * i, color);
			chess_state[4][7 * i] = new Queen(4, 7 * i, color);
			chess_state[3][7 * i] = new King(3, 7 * i, color);

			for (int j = 0; j <= 7; j++) {
				chess_state[j][1 + 5 * i] = new Pawn(j, 1 + 5 * i, color);
			}
		}

		Lion lion = new Lion("lion.json");
		chess_state[lion.x][lion.y] = lion;

		for (int row = 7; row >= 0; row--) {
			System.out.print((row + 1) + "|");
			for (int col = 0; col < 8; col++) {
				if (chess_state[col][row] == null) {
					System.out.print((empty()) + "|");
				} else {
					System.out.print(show(chess_state[col][row].piece_int) + "|");
				}

			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");
	}

	private void graphic() {

		// Creation of new window
		board = new Board_GUI(this);

	}

	private String empty() {
		return " ";
	}

	private String show(int piece) {
		return new String(Character.toChars(piece));
	}

	// Fonction testant toutes les déplacements de pièce en regardant : 
	//	- Si le déplacement est valide
	//	- Si le jeu dest encore en situation d'échec
	public Piece.ColorP isEnd() {
		Piece.ColorP current = turn;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece piece = chess_state[i][j];
				if (piece != null && piece.color == current) {
					// Parcourt tous les mouvements possibles de la pièce
					for (int x = 0; x < 8; x++) {
						for (int y = 0; y < 8; y++) {
							if (piece.isValid(chess_state, x, y) > 0) {
								// Sauvegarde l'état actuel
								Piece temp = chess_state[x][y];
								chess_state[x][y] = piece;
								chess_state[i][j] = null;
								// Vérifie si le roi n'est plus en échec après ce coup
								if (this.isChess() != current) {
									// Annule le coup
									chess_state[i][j] = piece;
									chess_state[x][y] = temp;
									return null; // Il existe au moins un coup pour sortir d'échec
								}
								// Annule le coup
								chess_state[i][j] = piece;
								chess_state[x][y] = temp;
							}
						}
					}
				}
			}
		}
		// Aucun coup ne permet de sortir d'échec : le joueur courant a perdu
		return current;
	}

	// Function to know if the king is in situation of chess
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

	public states getState_game() {
		return state_game;
	}

	public void setState_game(states state_game) {
		this.state_game = state_game;
	}

	public boolean isChessBlack() {
		return chessBlack;
	}

	public void setChessBlack(boolean chessBlack) {
		this.chessBlack = chessBlack;
	}

	public boolean isChessWhite() {
		return chessWhite;
	}

	public void setChessWhite(boolean chessWhite) {
		this.chessWhite = chessWhite;
	}

	public Piece[][] getChess_state() {
		return chess_state;
	}

	public void setChess_state(Piece[][] chess_state) {
		this.chess_state = chess_state;
	}

	public Board_GUI getBoard() {
		return board;
	}

	public void setBoard(Board_GUI board) {
		this.board = board;
	}

	public Piece.ColorP getWinner() {
		return winner;
	}

	public void setWinner(Piece.ColorP winner) {
		this.winner = winner;
	}

	public Piece.ColorP getTurn() {
		return turn;
	}

	public void setTurn(Piece.ColorP turn) {
		this.turn = turn;
	}

	
}