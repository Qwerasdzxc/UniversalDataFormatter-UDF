package app;

import app.views.MainStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class UDFApplication extends Application {
	
	public static void start() {
		launch();
	}

	@Override
    public void start(Stage primaryStage) {
		MainStage mainStage = new MainStage();
		mainStage.show();
    }
}
