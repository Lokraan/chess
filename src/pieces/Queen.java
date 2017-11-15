package pieces;

import board.Board;
import enums.Colors;
import idk.*;

public class Queen extends Piece {
	public Queen(Board board, Position pos, Colors color) {
		super(board, pos, color);
	}
	
	public String toString() {
		switch(this.color) {
			case Black:
				return "Q";
			default:
				break;
		}
		return "q";
	}

	@Override
	public void possibleMoves() {
		moves.clear();
		Rook r = new Rook(this.board, this.pos, this.color);
		r.possibleMoves();
		for(Move move : r.getPossibleMoves())
			moves.add(move);
		
		Bishop b = new Bishop(this.board, this.pos, this.color);
		b.possibleMoves();
		for(Move move: b.getPossibleMoves())
			moves.add(move);
	}

	@Override
	public char toChar() {
		switch(this.color) {
			case White:
				return 'q';
			default:
				return 'Q';
		}
	}
	
}
