package board;

import enums.Colors;
import idk.Position;
import pieces.*;

public abstract class BoardBuilder {

	private static Board board;
	
	public static Board build() {
		Piece[][] pieces = new Piece[8][8];
		board = new Board();
		
		addWhite(pieces);
		addBlack(pieces);
		
		board.setBoard(pieces);
		
		return board;
	}
	
	public static Board cloneBoard(Board oldBoard) {
		Board newBoard = new Board();
		Piece[][] dad = new Piece[8][8];
		Piece[][] pieces = oldBoard.getPieces();
		
		for(int i = 0; i < pieces.length; ++i) {
			for(int j = 0; j < pieces.length; ++j) {
				dad[i][j] = clonePiece(newBoard, pieces[i][j]);
				if(dad[i][j] instanceof King) newBoard.setKing(dad[i][j], new Position(i, j));
			}
		}
		
		newBoard.setBoard(dad);
		
		return newBoard;
	}
	
	private static Piece clonePiece(Board b, Piece p) {
		if(p instanceof Pawn) return new Pawn(b, p.getPosition(), p.getColor());
		else if(p instanceof Rook)  return new Rook(b, p.getPosition(), p.getColor());
		else if(p instanceof Knight)  return new Knight(b, p.getPosition(), p.getColor());
		else if(p instanceof Bishop)  return new Bishop(b, p.getPosition(), p.getColor());
		else if(p instanceof King)  return new King(b, p.getPosition(), p.getColor());
		else if(p instanceof Queen)  return new Queen(b, p.getPosition(), p.getColor());
		else return null;
	}
	
	public static void addWhite(Piece[][] pieces) {
		for(int col = 0; col < 8; col ++) {
			pieces[1][col] = new Pawn(board, new Position(1, col), Colors.White);
		}
		
        pieces[0][0] = new Rook(board, new Position(0, 0), Colors.White);
        pieces[0][7] = new Rook(board, new Position(0, 7), Colors.White);

        pieces[0][1] = new Knight(board, new Position(0, 1), Colors.White);
        pieces[0][6] = new Knight(board, new Position(0, 6), Colors.White);

        pieces[0][2] = new Bishop(board, new Position(0, 2), Colors.White);
        pieces[0][5] = new Bishop(board, new Position(0, 5), Colors.White);

        pieces[0][3] = new Queen(board, new Position(0, 3), Colors.White);
        pieces[0][4] = new King(board, new Position(0, 4), Colors.White);
        board.setKing(pieces[0][4], new Position(0, 4));
	}
	
	public static void addBlack(Piece[][] pieces) {
		for(int col = 0; col < 8; col ++) {
			pieces[6][col] = new Pawn(board, new Position(6, col), Colors.Black);
		}
		
		pieces[7][7] = new Rook(board, new Position(7, 7), Colors.Black);
		pieces[7][0] = new Rook(board, new Position(7, 0), Colors.Black);
		 
		pieces[7][6] = new Knight(board, new Position(7, 6), Colors.Black);
	    pieces[7][1] = new Knight(board, new Position(7, 1), Colors.Black);
	     
	    pieces[7][2] = new Bishop(board, new Position(7, 2), Colors.Black);
	    pieces[7][5] = new Bishop(board, new Position(7, 5), Colors.Black);
	     
	     
	    pieces[7][3] = new Queen(board, new Position(7, 3), Colors.Black);
	    pieces[7][4] = new King(board, new Position(7, 4), Colors.Black);
	    board.setKing(pieces[7][4], new Position(7, 4));
	}
	
}
