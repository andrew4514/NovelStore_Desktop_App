package onboarding;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Onboarding {

	public Onboarding(Stage primaryStage) {
		BorderPane bp = new BorderPane();
		VBox vb = new VBox(5);
		Button signUp = new Button("Sign Up");
		Button signIn = new Button("Sign In");
		Text textOr = new Text("Or");
		
		ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/images/logo.png")));
		logo.setFitHeight(200);
		logo.setFitWidth(200); 
		
		vb.getChildren().addAll(signUp, textOr, signIn);
		vb.setAlignment(Pos.CENTER);
		
		bp.setCenter(logo);
		bp.setBottom(vb);
		BorderPane.setMargin(vb, new Insets(50));
		
		signUp.setOnAction(e -> {
			Register register = new Register(primaryStage);
			primaryStage.setScene(register.registerScene);
		});
		
		signIn.setOnAction(e -> {
			Login login = new Login(primaryStage);
			primaryStage.setScene(login.LoginScene);
		});
		
		Scene scene = new Scene(bp, 360, 600);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}


}
