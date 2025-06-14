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

	public Piece.ColorP isEnd() {
		Piece.ColorP winner = null;
		boolean blackWin = true;
		boolean whiteWin = true;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chess_state[i][j] != null) {
					if (chess_state[i][j].color == Piece.ColorP.BLACK) {
						whiteWin = false;
					}
					if (chess_state[i][j].color == Piece.ColorP.WHITE) {
						blackWin = false;
					}
				}
			}
		}
		if (blackWin) {
			winner = Piece.ColorP.BLACK;
		}
		if (whiteWin) {
			winner = Piece.ColorP.WHITE;
		}

		return winner;
	}

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

	public Piece.ColorP isChess(Piece selected_piece) {
		Piece.ColorP res = null;

		for (int i = 0; i < 7; i++) {

		}
		return res;
	}

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

}