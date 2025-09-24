package main;

import javafx.application.Application;
import javafx.stage.Stage;
import onboarding.Onboarding;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		new Onboarding(primaryStage);
	}

}
