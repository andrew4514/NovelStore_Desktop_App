package admin;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminHome {
	BorderPane bp = new BorderPane();
	VBox home = new VBox();
	public Scene AdminHomeScene;

	public AdminHome(Stage s) {
		AdminHomeScene = new Scene(bp, 800, 600);
		AdminHomeScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
		home.getChildren().addAll();		
		
		bp.setLeft(new sidebar().sidebarVBox(s));
		bp.setCenter(home);
		
		s.setTitle("Admin - Home");
		s.setScene(AdminHomeScene);
		s.show();
	}

}
