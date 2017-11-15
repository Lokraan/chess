
package idk;

import pieces.Piece;

public class Move {

	private Position start;
	private Position end;
	private Piece piece;
	public Piece dPiece;
	public boolean castling;
	public boolean enPassant;
	
	/**
	 * everything except castling
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
	public Move(Position start, Position end) {
		this.start = start;
		this.end = end;
		this.castling = true;
	}
	
	/**
	 * enpassant
	 * @return
	 */
	public Move(Position start, Position end, Piece movingPiece, Piece deadPiece) {
		this(start, end, movingPiece);
		this.dPiece = deadPiece;
		
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
