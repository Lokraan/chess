package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import game.GuiController;
import idk.Move;
import idk.Position;
import pieces.Piece;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Color white = Color.WHITE;
	private Color black = Color.BLACK;
	private Color selectColor = Color.GREEN;
	private Color enemySelectColor = Color.RED;
	
	private GuiController controller;
	private Square[][] squares;
	
	private Square selectedSquare;
	private ArrayList<Square> movableSquares;
	
	/**
	 * Intialize BoardPanel
	 * @param controller
	 */
	public BoardPanel(GuiController controller) {
		this.movableSquares = new ArrayList<Square>();
		this.controller = controller;
		setPreferredSize(new Dimension(8*Square.size, 8*Square.size));
		buildSquares();
		mouseListenerFun();
	}
	
	public Square[][] getSquares() {
		return squares;
	}
	
	private void mouseListenerFun() {
		this.addMouseListener( new MouseAdapter() {
			
			/**
			 * Gets current square clicked on, sets it as selected square or clicked square. If clicked checks if valid move and does the move.
			 */
			public void mouseReleased(MouseEvent e) {
				int row = Math.abs(e.getY() / Square.size - 7);
				int col = e.getX() / Square.size;
				
				Piece[][] pieces = controller.getBoard().getPieces();
				Piece p = pieces[row][col];
				
				// check for new selection
				if(selectedSquare == null) {
					if(p != null && p.getColor() == controller.currentPlayer()) {
						selectSquare(squares[Math.abs(row-7)][col]);
						return;
					}
					return;
				}
				
				// check for move
				Piece p2 = pieces[selectedSquare.getRow()][selectedSquare.getCol()];
				// check reselection and selecting of same team
				if(p2 != null) {
					if(p == p2) {
						System.out.println("unselecting square");
						unselectSquare();
						return;
					} else if(p != null && p.getColor() == p2.getColor()) return;
				}
				
				// do the dab
				Square clickedSquare = squares[Math.abs(row-7)][col];
				if(movableSquares != null && movableSquares.indexOf(clickedSquare) > -1) {
					Position start = new Position(selectedSquare.getRow(), selectedSquare.getCol());
					Position des = new Position(row, col);
					controller.move(start, des);
					unselectSquare();
					// System.out.println(start + " " + des);
				}
				
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		updateUI(g);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Builds the squares.
	 */
	private void buildSquares() {
		squares = new Square[8][8];
		Color color = white;
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				squares[i][j] = new Square(color, Math.abs(i-7), j, controller);
				color = flipFlopColor(color);
			}
			color = flipFlopColor(color);
		}
	}

	/**
	 * Flip flops color
	 * @param color
	 * @return Color
	 */
	private Color flipFlopColor(Color color) {
		if(color == black) return white;
		return black;
	}
	
	/**
	 * Sets all movable squares to not movable.
	 */
	private void clearMovableSquares() {
		if(movableSquares != null) {
			for(Square square : movableSquares) {
				square.notPossibleMove();
				square.notHighlighted();
			}
			movableSquares.clear();
		}
	}
	
	/**
	 * Selects square, gets all possible moves for current piece on square and updates UI
	 * @param square
	 */
	private void selectSquare(Square square) {
		square.isHighlighted(selectColor);
		selectedSquare = square;
		Piece p = square.getPiece();
		System.out.println("Selecting: " + p.toChar());
		for(Move m : controller.getMoves(p)) {
			Square sq = squares[Math.abs(m.getEnd().getRow()-7)][m.getEnd().getCol()];
			movableSquares.add(sq);
			if(sq.getPiece() != null && sq.getPiece().getColor() != controller.currentPlayer()) {
				sq.isHighlighted(enemySelectColor);
			} else sq.isPossibleMove();
		}
		paintComponent(this.getGraphics());
	}
	
	/**
	 * Unselects the square, removes all movable squares, and updates UI
	 */
	private void unselectSquare() {
		clearMovableSquares();
		selectedSquare.notHighlighted();
		selectedSquare = null;
		paintComponent(this.getGraphics());
	}
	
	/**
	 * update the UI?
	 */
	private void updateUI(Graphics g) {
		for(int i = 0; i < squares.length; ++i) {
			for(int j = 0; j < squares.length; ++j) {
				squares[i][j].draw(g);
			}
		}
	}
	
}
