package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Disc extends Circle {
	
	public final boolean red;
	
	
	public final static int KACHEL_GRÖSSE = 80;

	/**
	 * Input: Rot, wahr oder falsch?
	 * erzeugter Jeton wird somit Rot oder eben nicht
	 * @param red
	 */
	public Disc(boolean red) {
		super(KACHEL_GRÖSSE / 2, red ? Color.RED : Color.YELLOW);
		this.red = red;
		
		setCenterX(KACHEL_GRÖSSE / 2);
		setCenterY(KACHEL_GRÖSSE / 2);
	}

}
