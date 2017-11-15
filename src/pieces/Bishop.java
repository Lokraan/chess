package pieces;

import board.Board;
import enums.Colors;
import idk.Position;

public class Bishop extends Piece{
	public Bishop(Board board, Position pos, Colors color) {
		super(board, pos, color);
	}

	@Override
	/**
	 * Add up all possible moves for
	 * North east/west & south east/west
	 */
	public void possibleMoves() {
		moves.clear();
		this.northEast();
		this.northWest();
		this.southEast();
		this.southWest();
	}
	
	private void northWest() {
		Piece[][] pieces = board.getPieces();
		int i = pos.getRow()+1;
		int j = pos.getCol()-1;
		
		while(i < pieces.length && j > -1) {
			Position p = new Position(i, j);
			if(!board.isClear(p)) {
				if(!board.isEnemy(this, p)) break;
				else {
					addMove(p);
					break;
				}
			} else addMove(p);
			i++; j--;
		}
	}
	
	private void northEast() {
		Piece[][] pieces = board.getPieces();
		int i = pos.getRow()+1;
		int j = pos.getCol()+1;
		
		while(i < pieces.length && j < pieces.length) {
			Position p = new Position(i, j);
			if(!board.isClear(p)) {
				if(!board.isEnemy(this, p)) {
					break;
				}
				else {
					addMove(p);
					break;
				}
					
			} else addMove(p);
			i++; j++;
		}
	}
	
	private void southEast() {
		Piece[][] pieces = board.getPieces();
		int i = pos.getRow()-1;
		int j = pos.getCol()+1;
		
		while(i > -1 && j < pieces.length) {
			Position p = new Position(i, j);
			if(!board.isClear(p)) {
				if(!board.isEnemy(this, p)) break;
				else {
					addMove(p);
					break;
				} 
			} else addMove(p);
			i--; j++;
		}
	}
	
	private void southWest() {
		int i = pos.getRow()-1;
		int j = pos.getCol()-1;
		
		while(i > -1 && j > -1) {
			Position p = new Position(i, j);
			if(!board.isClear(p)) {
				if(!board.isEnemy(this, p)) break;
				else {
					addMove(p);
					break;
				} 
			} else addMove(p);
			i--; j--;
		}
	}

	@Override
	public char toChar() {
		switch(this.color) {
			case Black:
				return 'B';
			default:
				return 'b';
		}
	}
	
}
