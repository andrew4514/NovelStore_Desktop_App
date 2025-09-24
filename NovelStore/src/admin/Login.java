package admin;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.Connect;

public class Login extends Application{
	private Connect connect = Connect.getInstance();
	Scene loginAdminScene;
	GridPane gp;
	VBox card;
	
	Text cardTitle = new Text("Admin Sign In");
	
	Label emailLabel = new Label("Email:");
	Label passwordLabel = new Label("Password:");
	
	TextField emailTF = new TextField();
	PasswordField passwordTF = new PasswordField();
	
	Button btnSignIn = new Button("Sign In");
	
	
	public static void main(String args[]) {
		launch(args);
	}
	
	@Override
	public void start(Stage s) throws Exception {
		gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		loginAdminScene = new Scene(gp, 800, 600);
		loginAdminScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
		HBox hb = new HBox();
	    hb.getChildren().add(cardTitle);
	    hb.setAlignment(Pos.CENTER);   
		
		card = new VBox(15, hb, emailLabel, emailTF, passwordLabel, passwordTF, btnSignIn);
		card.getStyleClass().add("card");
		cardTitle.getStyleClass().add("card-title");
		emailTF.getStyleClass().add("text-field");
		passwordTF.getStyleClass().add("text-field");
		btnSignIn.getStyleClass().add("login-button");
        card.setPrefWidth(300);
        
        gp.add(card, 0, 0);
        
        s.setResizable(false);
        s.setScene(loginAdminScene);
		s.setTitle("Admin - Sign In"); 
		s.show();
        
        btnSignIn.setOnAction(e -> {
        	if(emailTF.getText().isEmpty() || passwordTF.getText().isEmpty()) {
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("ERROR");  
	        	alert.setContentText("Email atau Password tidak boleh kosong!");
	        	alert.showAndWait();
        	} else if(isExist()) {
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Success");  
	        	alert.setContentText("Berhasil Login!");
	        	alert.showAndWait();
        		AdminHome adminHome = new AdminHome(s);
        		s.setScene(adminHome.AdminHomeScene);
        	} else {
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("ERROR");
	        	alert.setContentText("Email atau Password tidak terdaftar!");
	        	alert.showAndWait();
        	}
        });
		
		
	}
	
	public Boolean isExist() {
		Boolean flag;
		String query = "SELECT * FROM staff WHERE StaffEmail ='" + emailTF.getText() + "' AND StaffPassword ='" + passwordTF.getText() + "';";
		
		connect.execQuery(query);
		
		try {
			flag = connect.rs.next();
		} catch (Exception e) {
			flag = false;
		}
		
		return flag;
	}

}
