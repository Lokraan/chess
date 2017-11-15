package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import game.GuiController;
import pieces.Piece;

public class Square {

	public static int size = 100;
	private int circleDiameter = size/10;
	
	private Color possibleMoveColor = Color.GREEN;
	private Color highlightColor;
	private Color color;
	
	private GuiController controller;
	private Piece piece;
	private int row;
	private int col;
	
	private boolean highlighted;
	private boolean isPossibleMove;
	
	/**
	 * Creates new square
	 * @param color
	 * @param row
	 * @param col
	 * @param controller
	 */
	public Square(Color color, int row, int col, GuiController controller) {
		this.color = color;
		this.row = row;
		this.col = col;
		this.controller = controller;
		piece = controller.getPieces()[row][col];
	}
	
	/**
	 * Sets highlight
	 * @param highlightType
	 */
	public void isHighlighted(Color highlightType) {
		this.highlightColor = highlightType;
		this.highlighted = true;
	}
	
	/**
	 * Removes highlight
	 */
	public void notHighlighted() {
		this.highlighted = false;
	}
	
	/**
	 * Lets it know that it's a candidate for a possible move
	 */
	public void isPossibleMove() {
		this.isPossibleMove = true;
	}
	
	/**
	 * Lets it know that it's no longer a candidate for a possible move
	 */
	public void notPossibleMove() {
		this.isPossibleMove = false;
	}
	
	/**
	 * get row
	 * @return
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * get col
	 * @return
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * get piece
	 * @return
	 */
	public Piece getPiece() {
		return controller.getBoard().getPieces()[row][col];
	}
	
	/**
	 * set piece
	 * @param p
	 */
	public void setPiece(Piece p) {
		this.piece = p;
	}
	
	/** 
	 * Draws the square onto the board panel :DDD
	 * @param g
	 */
	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		int r = col;
		int c = Math.abs(row-7);
		
		Rectangle2D rect = new Rectangle2D.Double(r*size, c*size, size, size);
		g2D.setColor(color);
		g2D.fill(rect);
		
		piece = controller.getPieces()[Math.abs(c-7)][r];
		String p  = PieceImages.getImagePath(piece);
		if(p != null) {
			Image img = Toolkit.getDefaultToolkit().getImage(p);
			g.drawImage(img, r*size, c*size, null);
		}
		
		if(isPossibleMove) {
			int d = circleDiameter;
			Ellipse2D e = new Ellipse2D.Double(r*size + size/2 - d, c*size + size/2 - d, d, d);
			g2D.setColor(possibleMoveColor);
			g2D.fill(e);
			g2D.draw(e);
		}
		
		if(highlighted) {
			Rectangle2D outline = new Rectangle2D.Double(r*size, c*size, size, size);
			g2D.setColor(highlightColor);
			g2D.setStroke(new BasicStroke(2));
			g2D.draw(outline);
		}
		
	}
	
	public String toString() {
		return row + " " + col;
	}
	
}
