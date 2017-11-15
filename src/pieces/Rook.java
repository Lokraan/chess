package pieces;

import board.Board;
import enums.Colors;
import idk.Position;

public class Rook extends Piece {
	/**
	 *
	 * @param color the nationality
	 * @param row row being placed
	 * @param col col placed
	 */
	public Rook(Board board, Position pos, Colors color) {
		super(board, pos, color);
	}
	
	public void setHasMoved(boolean dad) {
		this.hasMoved = dad;
	}

	@Override
	public void possibleMoves() {
		moves.clear();
		this.upDown();
		this.leftRight();
	}

	private void upDown() {
		int row = pos.getRow();
		int col = pos.getCol();
		
		for(int j = col+1; j < board.getPieces().length; ++j) {
			Position p = new Position(row, j);
			if(!board.isClear(p)) {
				if(!board.isEnemy(this, p))
					break;
				else if(board.isEnemy(this, p)) {
					addMove(p);
					break;
				}
			} else addMove(p);
		}
		
		for(int j = col-1; j >= 0; --j) {
			Position p = new Position(row, j);
			if(!board.isClear(p)) {
				if(!board.isEnemy(this, p))
					break;
				else if(board.isEnemy(this, p)) {
					addMove(p);
					break;
				}
			} else addMove(p);
		}
		
	}
	
	private void leftRight() {
		int row = pos.getRow();
		int col = pos.getCol();
		
		for(int i = row+1; i < board.getPieces().length; ++i) {
			Position p = new Position(i, col);
			if(!board.isClear(p)) {
				if(!board.isEnemy(this, p))
					break;
				else if(board.isEnemy(this, p)) {
					addMove(p);
					break;
				}
			} else addMove(p);
		}
		
		for(int i = row-1; i >= 0; --i) {
			Position p = new Position(i, col);
			if(!board.isClear(p)) {
				if(!board.isEnemy(this, p))
					break;
				else if(board.isEnemy(this, p)) {
					addMove(p);
					break;
				}
			} else addMove(p);
		}
	}
	
	
	@Override
	public char toChar() {
		switch(color) {
			case Black:
				return 'R';
			default:
				return 'r';
		}
	}

}
