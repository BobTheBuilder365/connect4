package application;

public class VierGewinnt_Model {

	// Konstante Spielfeldgrösse
	public static final int BOARD_SIZE = 7;

	// 2 Spieler
	private Player player1, player2;
	private JetonBoard jetonBoard;
	private Disc jeton;

	public VierGewinnt_Model() {
		this.jetonBoard = new JetonBoard();
		this.player1 = new Player(null);
		this.player2 = new Player(null);
		this.jeton = new Disc(false);
		
		
		

	}
	
}
