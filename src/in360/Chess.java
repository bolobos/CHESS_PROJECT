package in360;

import in360.pieces.*;
import java.util.ArrayList;
import java.util.List;


public class Chess {

	// Create 8x8
	private int[][] chess_state = new int[8][8];

	// Create array list whith all the pieces
	private List<Piece> pieces = new ArrayList<>();

	private Board_GUI board;

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

		Piece.ColorP color = Piece.ColorP.BLACK;

		for (int i = 0; i < 2; i++) {

			if (i == 1) {
				color = Piece.ColorP.WHITE;
			}

			pieces.add(new Rook(0, 7 * i, color));
			pieces.add(new Rook(7, 7 * i, color));
			pieces.add(new Knight(1, 7 * i, color));
			pieces.add(new Knight(6, 7 * i, color));
			pieces.add(new Bishop(2, 7 * i, color));
			pieces.add(new Bishop(5, 7 * i, color));
			pieces.add(new Queen(4, 7 * i, color));
			pieces.add(new King(3, 7 * i, color));

			for (int j = 0; j <= 7; j++) {
				pieces.add(new Pawn(j, 1 + 5 * i, color));
			}
		}

		for (Piece piece : pieces) {
			int x = piece.getX();
			int y = piece.getY();

			if ((x <= 7) && (x >= 0) && (y <= 7) && (y >= 0)) {

				// if (piece instanceof Bishop) {
				// Bishop bishop = (Bishop) piece;
				// piece_int=piece.getPiece_int();

				// } else if (piece instanceof Rook) {
				// Rook rook = (Rook) piece;
				// piece_int=piece.getPiece_int();

				// } else if (piece instanceof Knight) {
				// Knight knight = (Knight) piece;
				// piece_int=piece.getPiece_int();

				// } else if (piece instanceof Queen) {
				// Queen queen = (Queen) piece;
				// piece_int=piece.getPiece_int();

				// } else if (piece instanceof King) {
				// King king = (King) piece;
				// piece_int=piece.getPiece_int();

				// } else if (piece instanceof Pawn) {
				// Pawn pawn = (Pawn) piece;
				// piece_int=piece.getPiece_int();

				// } else {
				// chess_state[x][y] = ' ';
				// }

				chess_state[x][y] = piece.getPiece_int();
			}

		}

		for (int row = 7; row >= 0; row--) {
			System.out.print((row + 1) + "|");
			for (int col = 0; col < 8; col++) {
				if (chess_state[col][row] == 0) {
					System.out.print((empty()) + "|");
				} else {
					System.out.print(show(chess_state[col][row]) + "|");
				}

			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");

		/*
		 * System.out.println("8|"+show(ROOK_BLACK)+"|"+show(KNIGHT_BLACK)+"|"+show(
		 * BISHOP_BLACK)+"|"+show(QUEEN_BLACK)+"|"+show(KING_BLACK)+"|"+show(
		 * BISHOP_BLACK)+"|"+show(KNIGHT_BLACK)+"|"+show(ROOK_BLACK)+"|");
		 * System.out.println("7|"+show(PAWN_BLACK)+"|"+show(PAWN_BLACK)+"|"+show(
		 * PAWN_BLACK)+"|"+show(PAWN_BLACK)+"|"+show(PAWN_BLACK)+"|"+show(PAWN_BLACK)+
		 * "|"+show(PAWN_BLACK)+"|"+show(PAWN_BLACK)+"|");
		 * System.out.println("6|"+empty()+"|"+empty()+"|"+empty()+"|"+empty()+"|"+empty
		 * ()+"|"+empty()+"|"+empty()+"|"+empty()+"|");
		 * System.out.println("5|"+empty()+"|"+empty()+"|"+empty()+"|"+empty()+"|"+empty
		 * ()+"|"+empty()+"|"+empty()+"|"+empty()+"|");
		 * System.out.println("4|"+empty()+"|"+empty()+"|"+empty()+"|"+empty()+"|"+empty
		 * ()+"|"+empty()+"|"+empty()+"|"+empty()+"|");
		 * System.out.println("3|"+empty()+"|"+empty()+"|"+empty()+"|"+empty()+"|"+empty
		 * ()+"|"+empty()+"|"+empty()+"|"+empty()+"|");
		 * System.out.println("2|"+show(PAWN_WHITE)+"|"+show(PAWN_WHITE)+"|"+show(
		 * PAWN_WHITE)+"|"+show(PAWN_WHITE)+"|"+show(PAWN_WHITE)+"|"+show(PAWN_WHITE)+
		 * "|"+show(PAWN_WHITE)+"|"+show(PAWN_WHITE)+"|");
		 * System.out.println("1|"+show(ROOK_WHITE)+"|"+show(KNIGHT_WHITE)+"|"+show(
		 * BISHOP_WHITE)+"|"+show(QUEEN_WHITE)+"|"+show(KING_WHITE)+"|"+show(
		 * BISHOP_WHITE)+"|"+show(KNIGHT_WHITE)+"|"+show(ROOK_WHITE)+"|");
		 * System.out.println("  A B C D E F G H");
		 */

	}

	private void graphic(){
		
		// Creation of new window
		board = new Board_GUI(this);


	}

	private String empty() {
		return " ";
	}

	private String show(int piece) {
		return new String(Character.toChars(piece));
	}

	public List<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
	}

	

}