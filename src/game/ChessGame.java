package game;

import board.*;
import enums.Colors;
import idk.*;

public class ChessGame {

	private Board board;
	private Colors currentPlayer;
	
	/**
	 * Instantiate chess game
	 */
	public ChessGame() {
		this.board = BoardBuilder.build();
		this.currentPlayer = Colors.White;
	}
	
	/** 
	 * Moves
	 * @param move
	 * @return true if valid, false if not
	 */
	public boolean move(Move move) {
		int r = move.getStart().getRow(), c = move.getStart().getCol();
		
		if(board.getPieces()[r][c] != null)
			if(board.getPieces()[r][c].getColor() != currentPlayer) return false;
		
		boolean result = board.move(move);
		if(result) currentPlayer = flipFlopColor(currentPlayer); 
		
		return result;
	}
	
	/**
	 * Converts string to Move class for board
	 * @param move
	 */
	public void move(String move) {
		move(parseInput(move));
	}
	
	/**
	 * Gets current board
	 * @return
	 */
	public Board getBoard() {
		return this.board;
	}
	
	/**
	 * Flip flops colors like my tree
	 * @param color
	 * @return inverse color
	 */
	public Colors flipFlopColor(Colors color) {
		switch(color) {
			case Black: return Colors.White;
			default: return Colors.Black;
		}
	}

	
	/** 
	 * Converts string input into Move class
	 * @param move
	 * @return
	 */
	private Move parseInput(String move) {
		String[] moveSplit = move.split(" ");
		
		// start pos
		int r1 = charToRow(moveSplit[0].charAt(0));
		int c1 = 0 + moveSplit[0].charAt(1);
		
		// end pos
		int r2 = charToRow(moveSplit[1].charAt(0));
		int c2 = 0 + moveSplit[1].charAt(1);
		
		if(r1 < 0 || r2 < 0) throw new IndexOutOfBoundsException();
		
		return new Move(new Position(r1, c1), new Position(r2, c2), board.getPieces()[r1][c1]);
	}
	
	/**
	 * Converts char to int for 8x8 array
	 * @param a
	 * @return
	 */
	private int charToRow(char a) {
		switch(a) {
			case 'a': return 0;
			case 'b': return 1;
			case 'c': return 2;
			case 'd': return 3;
			case 'e': return 4;
			case 'f': return 5;
			case 'g': return 6;
			case 'h': return 7;
		}
		return -1;
	}
	
}
