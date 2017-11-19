package pieces;

import board.Board;
import enums.Colors;
import idk.Move;
import idk.Position;

public class King extends Piece {
	public King(Board board, Position pos, Colors color) {
		super(board, pos, color);
	}

	@Override
	public void possibleMoves() {
		moves.clear();
		dab();
		castleKingSide();
		castleQueenSide();
	}
	
	/**
	 *   \o> <o/
	 *   <o/   \o>
	 *   dab squad yeet
	 */
	public void dab() {
		Piece[][] pieces = board.getPieces();
		int row = pos.getRow();
		int col = pos.getCol();
		
		int i = row;
		if(row != 0)
			i = row -1;
		
		int j = col;
		if(col != 0)
			j = col -1;
		
		for(; i < pieces.length && i < row+2; ++i) {
			for(; j < pieces.length && j < col+2; ++j) {
				Position p = new Position(i, j);
				if(board.isClear(p)) addMove(p);
				else {
					if(!board.isEnemy(this, p)) break;
					else {
						addMove(p);
						break;
					}
				}
			}
		}
		
	}
	
	private void castleKingSide() {
		Position p;
		if(hasMoved) return;
		for(int j = 2; j > 0; --j) {
			p = new Position(pos.getRow(), j);
			if(!board.isClear(p)) return;
		}
		
		p = new Position(pos.getRow(), 0);
		
		if(board.isEnemy(this, p) || board.getPiece(p) == null || !(board.getPiece(p) instanceof Rook) || ((Rook)board.getPiece(p)).hasMoved) return;
		
		moves.add(new Move(this.pos, p));
	}
	
	private void castleQueenSide() {
		Position p;
		if(hasMoved) return;
		for(int j = 4; j < 7; ++j) {
			p = new Position(pos.getRow(), j);
			if(!board.isClear(p)) return;
		}
		
		p = new Position(pos.getRow(), 7);
		
		if(board.isEnemy(this, p) || board.getPiece(p) == null || !(board.getPiece(p) instanceof Rook) || ((Rook)board.getPiece(p)).hasMoved) return;
		
		moves.add(new Move(this.pos, p));
	}

	@Override
	public char toChar() {
		switch(this.color) {
			case Black:
				return 'K';
			default:
				return 'k';
		}
	}
	
}
