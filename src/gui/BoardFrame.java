package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoardFrame extends JFrame {

	/**
	 * ?
	 */
	private static final long serialVersionUID = 1L;

	public BoardFrame() {
		setTitle("CHess?");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new BorderLayout());
	}
	
	public void addPanelNorth(JPanel panel) {
		add(panel, BorderLayout.NORTH);
		pack();
	}
	
	public void addPanelCenter(JPanel panel) {
		getContentPane().add(panel, BorderLayout.CENTER);
		pack();
	}
	
	public void addPanelWest(JPanel panel) {
		getContentPane().add(panel, BorderLayout.WEST);
		pack();
	}
	
	public void addPanelSouth(JPanel panel) {
		getContentPane().add(panel, BorderLayout.SOUTH);
		pack();
	}
	
	public void addPanelEast(JPanel panel) {
		getContentPane().add(panel, BorderLayout.EAST);
		pack();
	}
	
}
