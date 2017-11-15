package pieces;

import java.util.ArrayList;
import board.Board;
import enums.Colors;
import idk.Move;
import idk.Position;

public abstract class Piece {
	protected ArrayList<Move> moves = new ArrayList<Move>();
	protected Colors color;
	protected Position pos;
	protected Board board;
	public boolean hasMoved;
	
	public Piece(Board board, Position position, Colors color) {
		this.hasMoved = false;
		this.color = color;
		this.pos = position;
		this.board = board;
		this.pos.setPiece(this);
	}
	
	/**
	 * get possible moves and store it into moves array
	 */
	public abstract void possibleMoves();
	
	public abstract char toChar();
	
	public boolean hasMoved() {
		return this.hasMoved;
	}
	
	public Colors getColor() {
		return this.color;
	}
	
	public Position getPosition() {
		return pos;
	}
	
	public ArrayList<Move> getPossibleMoves() {
		possibleMoves();
		return this.moves;
	}
	
	/**
	 * =p
	 * @param p
	 */
	public void setPosition(Position p) {
		pos =p;
	}
	
	protected void addMove(Position dest) {
		moves.add(new Move(this.pos, dest, this));
	}
	
}
