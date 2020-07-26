package application;

public class Player {
	

	private String name;
	private boolean isNext;

	/**
	 * Erzeugt einen neuen Spieler mit dem übergebenen Namen. Der Spieler hält im
	 * Spiel die Farbe des Spielsteins anhand der übergebenen Grafik.
	 * 
	 * @param name
	 * @param pieceImage Die visualisierende Grafik für den Spielstein.
	 */
	public Player(String name) {
		this.name = name;

	}

	/**
	 * Gibt den Namen des Spielers zurück.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt zurück, ob ein Spieler am Zug ist, oder nicht.
	 * 
	 * @return true, wenn der Spieler am Zug ist, sonst false.
	 */
	public boolean isNext() {
		isNext = !isNext;
		return isNext;
	}
	
	
}