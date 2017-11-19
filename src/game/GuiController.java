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
import pieces.Pawn;
import pieces.Piece;

public class GuiController {

	private BoardFrame bFrame;
	private BoardPanel bPanel;
	
	private GuiChessGame game;
	private Colors cPlayer = Colors.White;
	
	/**
	 * Instantiates guicontroller, creates new board frame, panel, and nav panel, then maps them to the frame.
	 * @param game
	 */
	public GuiController(GuiChessGame game) {
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
				cPlayer = game.flipFlopColor(cPlayer);
				// add marker on king to indicate in check
				if(game.getBoard().inCheck(cPlayer)) {
					System.out.println("in check");
					Piece k = game.getBoard().getKing(game.flipFlopColor(cPlayer));
					bPanel.getSquares()[k.getPosition().getRow()][k.getPosition().getCol()].isHighlighted(Color.RED);
				}
				bPanel.updateUI();
			}
		}
	}
	
	/**
	 * Gets legal moves for piece from current game
	 * @param p
	 * @return
	 */
	public ArrayList<Move> getMoves(Piece p) {
		ArrayList<Move> moves = game.getBoard().getLegalMoves(p);
//		System.out.println(p.toChar() + " " + moves);
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