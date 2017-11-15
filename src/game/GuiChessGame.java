package game;

public class GuiChessGame extends ChessGame {
	private GuiController controller;
	
	/**
	 * Create guichessgame and new gui controller
	 */
	public GuiChessGame() {
		super();
		controller = new GuiController(this);
		controller.createUI();
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		GuiChessGame game = new GuiChessGame();
		
	}
	
}
