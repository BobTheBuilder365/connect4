package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class VierGewinnt_MVC extends Application {

	private VierGewinnt_Model model; // model
	private VierGewinnt_View view; // view
	private VierGewinnt_Controller controller; // controller
	
	
	public static void main(String[] args) {
		launch();
	}

	public void start(Stage stage) throws Exception {
		this.model = new VierGewinnt_Model();
		this.view = new VierGewinnt_View(stage, model);
		this.controller = new VierGewinnt_Controller(model, view);
		view.start();
	}

}
