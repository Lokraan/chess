
package idk;

import pieces.Piece;

public class Move {

	private Position start;
	private Position end;
	private Piece piece;
	public Piece dPiece;
	public boolean castling;
	public boolean enPassant;
	public Position rStart;
	public Position rEnd;
	
	
	/**
	 * Default move
	 * @param start
	 * @param end
	 * @param movingPiece
	 */
	public Move(Position start, Position end, Piece movingPiece) {
		this.start = start;
		this.end = end;
		this.piece = movingPiece;
	}
	
	/**
	 * castling
	 * @param from
	 * @param dest
	 */
	public Move(Position kStart, Position kEnd, Position rStart, Position rEnd) {
		this.start = kStart;
		this.end = kEnd;
		this.rStart = rStart;
		this.rEnd = rEnd;
		this.castling = true;
	}
	
	/**
	 * enpassant
	 */
	public Move(Position start, Position end, Piece movingPiece, Piece deadPiece) {
		this(start, end, movingPiece);
		this.dPiece = deadPiece;
		this.enPassant = true;
	}
	
	public Position getStart() {
		return this.start;
	}

	public Position getEnd() {
		return this.end;
	}
	
	public Piece getMovingPiece() {
		return this.piece;
	}
	
	public Piece getDeadPiece() {
		return this.getDeadPiece();
	}
	
	@Override
	public String toString() {
		return start.toString() + " " + end.toString();
	}
	
}
