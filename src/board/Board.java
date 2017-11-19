
package board;

import java.util.ArrayList;

import enums.Colors;
import idk.*;
import pieces.*;

public class Board {

	private Piece[][] pieces;
	private Piece wKing;
	private Piece bKing;
	
	public Board() {}
	
	/**
	 * Unconditionally updates board. 
	 * Used for checking new instances of board to see if puts player in check etc.
	 * Hot
	 * @param move
	 */
	public void updateBoard(Move move) {
		Position start = move.getStart();
		Position end = move.getEnd();
		
		if(start.getPiece() instanceof King) {
			setKing(start.getPiece(), end);
		}
		
		int r1 = start.getRow();
		int r2 = end.getRow();
		int c1 = start.getCol();
		int c2 = end.getCol();
		
		if(pieces[r1][c1] != null) pieces[r1][c1].hasMoved = true;
		if(pieces[r2][c2] != null) pieces[r2][c2].hasMoved = true;
		
		if(!move.castling && !move.enPassant) {
			Piece p = pieces[r1][c1];
			pieces[r2][c2] = p;
			pieces[r1][c1].setPosition(null);
			pieces[r1][c1] = null;
			pieces[r2][c2].setPosition(new Position(r2, c2));
		}
		else if(move.castling) {
			Piece king = pieces[r1][c1];
			Piece rook = pieces[r2][c2];
			pieces[r1][c1] = null;
			pieces[r2][c2] = null;
			// queen side castle
			if(c1 > c2) {
				pieces[r1][c1-2] = king;
				pieces[r2][c1-1] = rook;
				rook.setPosition(new Position(r2, c1-1));
				setKing(king, new Position(r1, c1-2));
			}
			// king side castle
			else if(c2 > c1) {
				pieces[r1][c1+2] = king;
				pieces[r2][c1+1] = rook;
				rook.setPosition(new Position(r2, c1+2));
				setKing(king, new Position(r1, c1+1));
			}
		}
		if(move.getMovingPiece() instanceof Pawn) {
			if(move.enPassant) {

				// move enpassanting piece
				Piece p = pieces[r1][c1];
				pieces[r1][c1] = null;
				pieces[r2][c2] = p;
				p.setPosition(new Position(r2, c2));
				
				// set dead piece to null
				Position dpp = move.dPiece.getPosition();
				pieces[dpp.getRow()][dpp.getCol()] = null;
			}
			
			// set epable
			if(Math.abs(r2 - r1) == 2) {
				((Pawn) pieces[r2][c2]).isEpAble(true);
			}
			
			// pawn promotion
			if(r2 == 7 || r2 == 0) {
				pieces[r2][c2] = new Queen(this, move.getEnd(), move.getMovingPiece().getColor());
			}
		}
		
	}
	
	/**
	 * Checks if the move is valid
	 * @param move
	 * @return
	 */
	public boolean isValidMove(Move move) {
		Position p = move.getStart();
		if(getLegalMoves(pieces[p.getRow()][p.getCol()]).indexOf(move) > -1)
			return true;

		return false;
	}
	
	/**
	 * Returns false if the move isn't completed successfully,
	 * True if move.
	 * @param move
	 * @return
	 */
	public boolean move(Move move) {
		if(isValidMove(move)) {
			updateBoard(move);
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if anyone can move
	 * @param color nationality
	 * @return yes/no
	 */
	public boolean canAnyoneMove(Colors color) {
		ArrayList<Piece> pieceList;
		switch(color) {
			case Black: pieceList = getPieceList(Colors.Black);
			default: pieceList = getPieceList(Colors.White);
		}
		
		for(Piece p : pieceList) {
			if(getLegalMoves(p) != null) return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if move will put player into check, true if so, false if not.
	 * @param color
	 * @param move
	 * @return
	 */
	private boolean putsPlayerInCheck(Colors color, Move move) { 
		Board newBoard = BoardBuilder.cloneBoard(this);
		newBoard.updateBoard(move);
		if(newBoard.inCheck(color)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets list of pieces for specified color
	 * @param color
	 * @return
	 */
	private ArrayList<Piece> getPieceList(Colors color) {
		ArrayList<Piece> pieceList = new ArrayList<Piece>();
		for(int x = 0; x < pieces.length; x++) {
			for(int y = 0; y < pieces.length; y++) {
				Position p = new Position(x, y);
				if(getPiece(p) != null && getPiece(p).getColor() == color) {
					pieceList.add(getPiece(p));
				}
			}
		}
		return pieceList;
	}
	
	public void setBoard(Piece[][] pieces) {
		this.pieces = pieces;
	}
	
	public void setKing(Piece king, Position p) {
		king.setPosition(p);
		switch(king.getColor()) {
			case Black: bKing = king; break;
			default: wKing = king; break;
		}
	}
	
	/**
	 * check if player in check or not
	 * @param color
	 * @return true if check, false if not
	 */
	public boolean inCheck(Colors color) {
		Position pos = getKing(color).getPosition();
		King k = (King) pieces[pos.getRow()][pos.getCol()];
		ArrayList<Piece> pieceList = getPieceList(flipColor(color));
		
		// check all enemy pieces' moves, if one of them equals king pos in check
		for(Piece p : pieceList) {
			for(Move m : p.getPossibleMoves()) {
				if(m.getEnd() != null) {
					int r = m.getEnd().getRow(), c = m.getEnd().getCol();
					Piece targetP = pieces[r][c];
					if(targetP instanceof King && targetP == k) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public Colors flipColor(Colors c) {
		switch(c) {
			case Black: return Colors.White;
			default: return Colors.Black;
		}
	}
	
	/**
	 * Check if position is in range of the board
	 * @param pos
	 * @return
	 */
	public boolean inRange(Position pos) {
		int row = pos.getRow();
		int col = pos.getCol();
		return -1 < row && row < pieces.length && -1 < col && col < pieces.length;
	}
	
	/**
	 * Check if position is clear
	 * @param pos
	 * @return
	 */
	public boolean isClear(Position pos) {
		int row = pos.getRow();
		int col = pos.getCol();
		
		if(!inRange(pos) || pieces[row][col] != null) return false;
		
		return true;
		
	}
	
	/**
	 * Check is position is enemy to piece
	 * @param piece
	 * @param pos
	 * @return gucci gang
	 */
	public boolean isEnemy(Piece piece, Position pos) {
		int row = pos.getRow();
		int col = pos.getCol();
		return (pieces[row][col] == null) ? false : !(piece.getColor() == pieces[row][col].getColor());
	}
	
	/**
	 * Get piece at current pos
	 * @param pos
	 * @return
	 */
	public Piece getPiece(Position pos) {
		int row = pos.getRow();
		int col = pos.getCol();
		return pieces[row][col];
	}
	
	public Piece[][] getPieces() {
		return pieces;
	}
	
	public Piece getKing(Colors color) {
		Position p;
		switch(color) {
			case Black: 
				p = bKing.getPosition();
				return pieces[p.getRow()][p.getCol()];
			default: 
				p = wKing.getPosition();
				return pieces[p.getRow()][p.getCol()];
		}
	}
	
	/**
	 * Gets legal moves for piece :)
	 * @param piece
	 * @return
	 */
	public ArrayList<Move> getLegalMoves(Piece piece) {
		ArrayList<Move> moves = new ArrayList<Move>();
		if(piece == null) return moves;
		ArrayList<Move> possibleMoves = piece.getPossibleMoves();
		for(Move m : possibleMoves) {
			if(!putsPlayerInCheck(piece.getColor(), m)) {
				moves.add(m);
			}
		}
		return moves;
	}
	
	/**
	 * Default white oriented toString
	 */
	public String toString() {
		String ret = "";
		for(Piece[] pRow : pieces) {
			for(Piece piece : pRow) {
				if(piece == null) {
					ret += " ";
				} else {
					ret += piece.toChar();
				}
				ret += " ";
			}
			ret += "\n";
		}
		
		String rev = "";
		
		rev += " a b c d e f g h \n";
		String[] split = ret.split("\n");
		for(int x = split.length -1; x > -1; x--) rev += x+1 + " " + split[x] + "\n";
		
		return rev;
		
	}
	
	/**
	 * View oriented toString :)
	 * @param color
	 * @return
	 */
	public String toString(Colors color) {
		switch(color) {
			case Black:
				String ret = "";
				for(Piece[] pRow : pieces) {
					for(Piece piece : pRow) {
						if(piece == null) {
							ret += " ";
						} else {
							ret += piece.toChar();
						}
						ret += " ";
					}
					ret += "\n";
				}
				
				String rev = "";
				
				rev += " a b c d e f g h \n";
				String[] split = ret.split("\n");
				for(int x = 0; x < split.length; x++) rev += x+1 + " " + split[x] + "\n";
				
				return rev;
				
			default:
				return toString();
		}
		
	}
	
}
