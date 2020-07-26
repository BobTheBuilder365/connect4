package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VierGewinnt_View {

	private Stage stage;
	private VierGewinnt_Model model;

	public final int KACHEL_GRÖSSE = 80;
	public final int SPALTEN = 7;
	public final int ZEILEN = 6;

	public boolean redMove = true;
	public Disc[][] grid = new Disc[SPALTEN][ZEILEN];

	private Pane jetonRoot = new Pane();

	public VierGewinnt_View(Stage stage, VierGewinnt_Model model) {
		this.stage = stage;
		this.model = model;

		/**
		 * Erzeuge die Szene mit unserem Layout und zeige es an
		 */
		Pane root = new Pane();
		Scene scene = new Scene(root);
		stage.setScene(new Scene(createContent()));
		stage.setTitle("Vier Gewinnt");
		// stage.setResizable(false);
	}
	
// DONE
	public Parent createContent() {
		// TODO Auto-generated method stub
		// Ausgangspunkt
		Pane root = new Pane();

		// Klasse SHape kann verschiedene Formen annehmen
		Shape gridFormen = erzeugeGrid();
		root.getChildren().add(gridFormen);
		root.getChildren().addAll(spalteHervorheben());
		return root;
	}
	
// DONE
	/**
	 * Methode erzeuge Grid Input: - Output: Methodik: 1. Rechtecke erzeugen 2.
	 * Doppelte for Schleifen
	 */
	public Shape erzeugeGrid() {
		Shape shape = new Rectangle((SPALTEN + 1) * KACHEL_GRÖSSE, (ZEILEN + 1) * KACHEL_GRÖSSE);
		// Mit einer for Schleife "Kreisel" in die Rechtecke setzen
		for (int spalte = 0; spalte < SPALTEN; spalte++) {
			for (int zeile = 0; zeile < ZEILEN; zeile++) {
				Circle circle = new Circle(KACHEL_GRÖSSE / 2);
				circle.setCenterX(KACHEL_GRÖSSE / 2);
				circle.setCenterY(KACHEL_GRÖSSE / 2);
				circle.setTranslateX(spalte * (KACHEL_GRÖSSE + 5) + KACHEL_GRÖSSE / 4);
				circle.setTranslateY(zeile * (KACHEL_GRÖSSE + 5) + KACHEL_GRÖSSE / 4);

				shape = Shape.subtract(shape, circle);
			}
		}
		Light.Distant light = new Light.Distant();
		light.setAzimuth(45.0);
		light.setElevation(30.0);

		Lighting lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(5.0);

		shape.setFill(Color.BLUE);
		shape.setEffect(lighting);

		return shape;
	}

// DONE)	
	/**
	 * Methode "spalteHervorheben" Input: -; Output: Angewählte Spalte wird als
	 * "Liste" angezeigt; Methodik: Hinweis für den User, welche Spalte er
	 * ausgewählt hat und somit der Jeton hinfallen würde
	 */
	public List<Rectangle> spalteHervorheben() {
		List<Rectangle> list = new ArrayList();

		for (int col = 0; col < SPALTEN; col++) {
			Rectangle rect = new Rectangle(KACHEL_GRÖSSE, (ZEILEN + 1) * KACHEL_GRÖSSE);
			rect.setTranslateX(col * (KACHEL_GRÖSSE + 5) + KACHEL_GRÖSSE / 4);
			rect.setFill(Color.TRANSPARENT);

			rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
			rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

			final int spalte = col;
			rect.setOnMouseClicked(e -> platziereJeton(new Disc(redMove), spalte)); // platziert Jeton wenn User drückt
			rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

			list.add(rect);
		}
		return list;
	}

	public void platziereJeton(Disc jeton, int spalte) {
		int zeile = ZEILEN - 1;
		do {
			if (!getJeton(spalte, zeile).isPresent())
				break;
			zeile--;
		} while (zeile >= 0);
		if (zeile < 0)
			return;
		grid[spalte][zeile] = jeton;
		jetonRoot.getChildren().add(jeton);
		jeton.setTranslateX(spalte * (KACHEL_GRÖSSE + 5) + KACHEL_GRÖSSE / 4);

		final int aktuelleZeile = zeile;

		TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), jeton);
		animation.setToY(zeile * (KACHEL_GRÖSSE + 5) + KACHEL_GRÖSSE / 4);
		animation.setOnFinished(e -> {
			if (spielEndet(spalte, aktuelleZeile)) {
				spielFertig();
			}
			redMove = !redMove;
		});
		animation.play();
	}

	public void spielFertig() {
		System.out.println("Sieger: " + (redMove ? "Red" : "Yellow"));

	}

	/**
	 * @Input: spalte, zeile;
	 * @Output: True/false;
	 * @Methodik: Überprüft die Siegbedingungen vertikal, horizontal und diagonal(2x)
	 * @return
	 */
	public boolean spielEndet(int spalte, int zeile) {
		List<Point2D> vertikal = IntStream.rangeClosed(zeile - 3, zeile + 3)
				// an 2. Stelle wid Inkrement angezeigt
				.mapToObj(r -> new Point2D(spalte, r)).collect(Collectors.toList());

		List<Point2D> horizontal = IntStream.rangeClosed(spalte - 3, spalte + 3).mapToObj(r -> new Point2D(spalte, r))
				.collect(Collectors.toList());

		Point2D topLeft = new Point2D(spalte - 3, zeile - 3);
		List<Point2D> diagonal1 = IntStream.rangeClosed(0, 6).mapToObj(i -> topLeft.add(i, i))
				.collect(Collectors.toList());

		Point2D bottomLeft = new Point2D(spalte - 3, zeile + 3);
		List<Point2D> diagonal2 = IntStream.rangeClosed(0, 6).mapToObj(i -> bottomLeft.add(i, -i))
				.collect(Collectors.toList());

		return checkRange(vertikal) || checkRange(horizontal) || checkRange(diagonal1) || checkRange(diagonal2);
	}

	public boolean checkRange(List<Point2D> points) {
		int kette = 0;

		for (Point2D p : points) {
			int spalte = (int) p.getX();
			int zeile = (int) p.getY();

			Disc jeton = getJeton(spalte, zeile).orElse(new Disc(!redMove));
			if (jeton.red == redMove) {
				kette++;
				if (kette == 4) {
					return true;
				} else {
					kette = 0;
				}
			}
		}
		return false;
	}

	public Optional<Disc> getJeton(int spalte, int zeile) {
		if (spalte < 0 || spalte >= SPALTEN || zeile < 0 || zeile >= ZEILEN)
			return Optional.empty();
		return Optional.ofNullable(grid[spalte][zeile]);
	}

	

	public void start() {
		stage.show();
	}
}
