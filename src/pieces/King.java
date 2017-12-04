package pieces;

import java.util.ArrayList;

import board.Board;
import enums.Colors;
import idk.Move;
import idk.Position;

public class King extends Piece {
	public King(Board board, Position pos, Colors color) {
		super(board, pos, color);
	}

	@Override
	/**
	 * Gets possible moves
	 */
	public void possibleMoves() {
		moves.clear();
		dab();
	}
	
	/**
	 * Gets castling moves
	 * @return arraylist of moves for castling
	 */
	public ArrayList<Move> getCastlingMoves() {
		castleKingSide();
		castleQueenSide();
		return moves;
	}
	
	/**
	 *   \o> <o/
	 *   <o/   \o>
	 *   dab squad yeet
	 */
	private void dab() {
		Piece[][] pieces = board.getPieces();
		int row = pos.getRow();
		int col = pos.getCol();

		
		/*
		 * M M M 
		 * M K M
		 * M M M
		 */
		for(int i = row-1; i < pieces.length && i < row+2; ++i) {
			if(i < 0) i = 0;
			for(int j = col - 1; j < pieces.length && j < col+2; ++j) {
				if(j < 0) j = 0;
				Position p = new Position(i, j);
				if(board.isClear(p) || board.isEnemy(this, p)) {
					addMove(p);
				}
			}
		}
		
	}
	
	/**
	 * Get castling moves for king side
	 */
	private void castleKingSide() {
		Position p;
		if(hasMoved) return;
		// starting at start king pos to right end, if any aren't clear get out of there
		for(int j = 5; j < 7; ++j) {
			p = new Position(pos.getRow(), j);
			if(!board.isClear(p) || board.isUnderFire(color, p)) return;
		}
		
		p = new Position(pos.getRow(), 7);
		
		// check to make sure piece at row 7 is a rook that hasn't moved
		Piece piece = board.getPiece(p);
		System.out.println("piece: " + p);
		if(piece == null || !(piece instanceof Rook) || ((Rook) piece).hasMoved) return;
		
		// if hasn't moved, add castling move yeet
		Position rookStart = p;
		Position rookEnd = new Position(pos.getRow(), 5);
		Position kingEnd = new Position(pos.getRow(), pos.getCol() + 2);
		moves.add( new Move(pos, kingEnd, rookStart, rookEnd) );
	}
	
	/**
	 * Get castling moves for queen side
	 */
	private void castleQueenSide() {
		Position p;
		if(hasMoved) return;
		// starting at start king pos to right end, if any aren't clear get out of there
		for(int j = 2; j > 0; --j) {
			p = new Position(pos.getRow(), j);
			if(!board.isClear(p) || board.isUnderFire(color, p)) return;
		}
		
		p = new Position(pos.getRow(), 0);
		
		// check to make sure piece at row 7 is a rook that hasn't moved
		Piece piece = board.getPiece(p);
		if(piece == null || !(piece instanceof Rook) || ((Rook) piece).hasMoved) return;
		
		// if hasn't moved, add castling move yeet
		Position rookStart = p;
		Position rookEnd = new Position(pos.getRow(), 3);
		Position kingEnd = new Position(pos.getRow(), pos.getCol() - 2);
		moves.add( new Move(pos, kingEnd, rookStart, rookEnd) );
		
	}

	@Override
	/**
	 * Gets char rep of piece
	 * @return Char rep of piece
	 */
	public char toChar() {
		switch(this.color) {
			case Black:
				return 'K';
			default:
				return 'k';
		}
	}
	
}
