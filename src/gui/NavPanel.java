package gui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class NavPanel extends JPanel {
	/**
	 * ?
	 */
	private static final long serialVersionUID = 1L;

	public NavPanel() {
		JButton gameBtn = new JButton("New Game");
		JButton takebackBtn = new JButton("Takeback");
		add(gameBtn);
		add(takebackBtn);
	}
}
