package pieces;

import board.Board;
import enums.Colors;
import idk.*;

public class Knight extends Piece {
	/**
	 *
	 * @param color the nationality
	 * @param row row being placed
	 * @param col col placed
	 */
	public Knight(Board board, Position position, Colors color) {
		super(board, position, color);
		this.color = color;
		this.pos = position;
		this.color = color;
	}
	
	public String toString() {
		
		switch(color) {
			case Black:
				return "N";
			default:
				return "n";
		}
		
	}

	@Override
	public void possibleMoves() {
		int row = pos.getRow();
		int col = pos.getCol();
		moves.clear();
		
		confrontation(new Position(row + 2, col - 1));
		confrontation(new Position(row + 2, col + 1));
		confrontation(new Position(row + 1, col - 2));
		confrontation(new Position(row + 1, col + 2));
		confrontation(new Position(row - 1, col - 2));
		confrontation(new Position(row - 1, col + 2));
		confrontation(new Position(row - 2, col - 1));
		confrontation(new Position(row - 2, col + 1));
		
	}
	
	private void confrontation(Position pos) {
		if(!board.inRange(pos)) return;
		
		if(board.isClear(pos) || board.isEnemy(this, pos))
			addMove(pos);
	}

	@Override
	public char toChar() {
		switch(color) {
			case Black:
				return 'N';
			default:
				return 'n';
		}
	}
	
}
