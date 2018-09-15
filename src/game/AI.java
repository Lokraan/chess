package game;

import java.util.ArrayList;

import board.Board;
import board.BoardBuilder;
import enums.Colors;
import idk.Move;
import pieces.Bishop;
import pieces.King;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class AI {

	private Colors color;
	
	public AI(Colors color) {
		this.color = color;
	}
	
	private double getPieceVal(Piece p) {
		if(p instanceof King) return 1000;
		else if(p instanceof Queen) return 9.0;
		else if(p instanceof Rook) return 5.0;
		else if(p instanceof Bishop) return 3.5;
		else if(p instanceof Knight) return 3.0;
		else if(p instanceof Pawn) return 1.5;
		return 0.0;
	}
	
	private double evaluateBoard(Board b) {
		double score = 0.0;
		
		Piece[][] pieces = b.getPieces();
		for(Piece[] row : pieces) {
			for(Piece p : row) {
				if(p != null) {
					double pieceVal = getPieceVal(p);
					if(p.getColor() == color) {
						score -= pieceVal;
					} else {
						score += pieceVal;
					}
				}
			}
		}
		
		return score;
	}
	
	private Double minimax(Board b, int depth, Colors color) {
		
		ArrayList<Move> moves = b.getLegalMoves(color);
		
		if(depth == 0) {
			return -evaluateBoard(b);
		}
		
		double bestMove = 9999;
		if(this.color == color) {
			bestMove = -9999;
		}
		
		for(Move m : moves) {
			Board testBoard = BoardBuilder.cloneBoard(b);
			testBoard.updateBoard(m);
			bestMove = Math.max(bestMove, minimax(testBoard, depth-1, b.flipColor(color)));
		}
		
		return bestMove;

	}
	
	private Move minimaxRoot(Board b, int depth, Colors color) {
		ArrayList<Move> moves = b.getLegalMoves(color);
		double bestMove = -9999;
		Move bestMoveFound = null;
		
		for(Move m : moves) {
			Board testBoard = BoardBuilder.cloneBoard(b);
			testBoard.updateBoard(m);
			double val = minimax(testBoard, depth-1, b.flipColor(color));
			if(val >= bestMove) {
				bestMove = val;
				bestMoveFound = m;
			}
		}
		
		return bestMoveFound;
		
	}
	
	
	public Move getMove(Board b) {
		return minimaxRoot(b, 4, color);
	}
	
}
