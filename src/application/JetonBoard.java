package application;

public class JetonBoard {

	// Boardgrösse als Konstanten
	public final int KACHEL_GRÖSSE = 80;
	public final int SPALTEN = 7;
	public final int ZEILEN = 6;

	// 2D Array als Board
	private Disc[][] jetonBoard = new Disc[ZEILEN][SPALTEN];
	private Disc jeton = new Disc(false);

	public JetonBoard() {
		this.jetonBoard = jetonBoard;
		this.jeton = jeton;
		
		for (int row = 1; row < jetonBoard.length; row++) {
			for (int col = 0; col < jetonBoard.length; col++) {
				Disc jeton = new Disc(false);
				jetonBoard[row][col] = jeton;
			}	
		}
	}

}
