
package idk;
import pieces.Piece;

public class Position {

	private int row;
	private int col;
	private Piece piece;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public Position(int row, int col, Piece piece) {
		this(row, col);
		this.piece = piece;
	}
	
	public Position(Position other) {
		this.row = other.getRow();
		this.col = other.getCol();
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public boolean equals(Position other) {
		return (this.row == other.getRow()) && (this.col == other.getCol());
	}
	
	@Override
	public String toString() {
		String s = "";
		if(this.piece != null) s += this.piece.toChar();
		return s + (char) (65 + this.col) + (this.row + 1);
	}
	
}
