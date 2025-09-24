package menu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Profile {
	public Scene profileScene;
	private BorderPane borderPane;

	public Profile(Stage s) {
		borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10));
		profileScene = new Scene(borderPane, 360, 600);
		
		borderPane.setBottom(new FooterMenu().footer(s));
		
		s.setScene(profileScene);
		s.setTitle("Profile");
		s.show();
	}

}
