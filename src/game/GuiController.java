package game;

import java.awt.Color;
import java.util.ArrayList;

import board.Board;
import enums.Colors;
import gui.BoardFrame;
import gui.BoardPanel;
import gui.NavPanel;
import idk.Move;
import idk.Position;
import pieces.*;

public class GuiController {

	private BoardFrame bFrame;
	private BoardPanel bPanel;
	
	private AI ai;
	private GuiChessGame game;
	private Colors cPlayer = Colors.White;
	
	/**
	 * Instantiates guicontroller, creates new board frame, panel, and nav panel, then maps them to the frame.
	 * @param game
	 */
	public GuiController(GuiChessGame game) {
		this.ai = new AI(Colors.Black);
		this.game = game;
	}
	
	public void createUI() {
		bFrame = new BoardFrame();
		NavPanel navPanel = new NavPanel();
		bPanel = new BoardPanel(this);
		bFrame.addPanelSouth(navPanel);
		bFrame.addPanelCenter(bPanel);
	}
	
	/**
	 * Return game board
	 * @return
	 */
	public Board getBoard() {
		return game.getBoard();
	}
	
	/**
	 * Return game pieces
	 * @return
	 */
	public Piece[][] getPieces() {
		return game.getBoard().getPieces();
	}
	
	/**
	 * Update move for board
	 * @param move
	 */
	public void move(Position start, Position des) {
		Board b = game.getBoard();
		for(Move m : getMoves(b.getPieces()[start.getRow()][start.getCol()])) {
			if(m.getEnd().equals(des)) {
				b.updateBoard(m);
				if(m.castling) {
				}
				cPlayer = game.flipFlopColor(cPlayer);
				// add marker on king to indicate in check
				if(game.getBoard().inCheck(cPlayer)) {
					Piece k = game.getBoard().getKing(game.flipFlopColor(cPlayer));
					bPanel.getSquares()[k.getPosition().getRow()][k.getPosition().getCol()].isHighlighted(Color.RED);
				}
				bPanel.updateUI();
			}
		}
		
		if(b.isCheckmate()) {
			System.out.println("CHECKMATE!");
		} else if (b.isStalemate()) {
			System.out.println("STALEMATE!");
		}
		
		b.updateBoard(ai.getMove(b));
		cPlayer = game.flipFlopColor(cPlayer);
		
		if(b.isCheckmate()) {
			System.out.println("CHECKMATE!");
		} else if (b.isStalemate()) {
			System.out.println("STALEMATE!");
		}
		
		
	}
	
	/**
	 * Gets legal moves for piece from current game
	 * @param p
	 * @return
	 */
	public ArrayList<Move> getMoves(Piece p) {
		ArrayList<Move> moves = game.getBoard().getLegalMoves(p);
		return moves;
	}
	
	public Colors flipFlopColor(Colors color) {
		return this.game.flipFlopColor(color);
	}
	
	/**
	 * Returns current player's color
	 * @return
	 */
	public Colors currentPlayer() {
		return cPlayer;
	}
	
}