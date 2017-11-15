package pieces;

import board.Board;
import enums.Colors;
import idk.Move;
import idk.Position;

public class Pawn extends Piece {
	public boolean epAble; // able to en passanted
	
	/**
	 *
	 * @param board the nationality
	 * @param position row being placed
	 * @param colors col placed
	 */
	public Pawn(Board board, Position position, Colors color) {
		super(board, position, color);
	}
	
	@Override
	public void possibleMoves() {
		moves.clear();
		switch(color) {
			case White: wUp(); wAttack(); break;
			default: bUp(); bAttack(); break;
		}
	}
	
	private void wUp() {
		int row = pos.getRow();
		int col = pos.getCol();
		
		epAble = false;
		Position p = new Position(row+1, col);
		if(canMove(p)) {
			addMove(p);
			if(!hasMoved) {
				p = new Position(row+2, col);
				if(canMove(p)) addMove(p);
			}
		}
		
	}

	private void wAttack() {
		int row = pos.getRow();
		int col = pos.getCol();
		
		epAttack();
		confront(new Position(row+1, col-1));
		confront(new Position(row+1, col+1));
		
	}
	
	private void bUp() {
		int row = pos.getRow();
		int col = pos.getCol();
		
		epAble = false;
		Position p = new Position(row-1, col);
		if(canMove(p)) {
			addMove(p);
			if(!hasMoved) {
				p = new Position(row-2, col);
				if(canMove(p)) addMove(p);
			}
		}
	}
	
	private void bAttack() {
		int row = pos.getRow();
		int col = pos.getCol();
		
		epAttack();
		confront(new Position(row-1, col-1));
		confront(new Position(row-1, col+1));
	}
	
	private void epAttack() {
		int row = pos.getRow();
		int col = pos.getCol();
		
		// check left
		Position p = new Position(row, col-1);
		if(canEnPassant(p)) addEnPassant(p);
		
		// check right
		p = new Position(row, col+1);
		if(canEnPassant(p)) addEnPassant(p);
	}
	
	private boolean canMove(Position p) {
		if(board.inRange(p) && board.isClear(p)) return true;
		return false;
	}
	
	private void confront(Position p) {
		if(board.inRange(p) && board.isEnemy(this, p)) {
			addMove(p);
		}
	}
	
	private void addEnPassant(Position p) {
		int uod = -1;
		if(this.color == Colors.White) uod = 1;
		int r = p.getRow(), c = p.getCol();
		moves.add(new Move(this.pos, new Position(r+uod, c), this, board.getPieces()[r][c]));
	}
	
	private boolean canEnPassant(Position p) {
		if(board.inRange(p) && board.isEnemy(this, p)) {
			if(board.getPiece(p) instanceof Pawn) 
				return ((Pawn) board.getPiece(p)).epAble;
		}
		return false;
	}
	
	public boolean isEpAble() {
		return epAble;
	}
	
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	public void setHasMoved(boolean l) {
		hasMoved = l;
	}
	
	@Override
	public char toChar() {
		switch(this.color) {
			case Black:
				return 'P';
			default:
				return 'p';
		}
	}
	
}
