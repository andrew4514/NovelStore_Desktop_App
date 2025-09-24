package onboarding;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.History;
import menu.Home;
import menu.Pembayaran;
import util.Connect;

public class Login {
	private Connect connect = Connect.getInstance();
	private GridPane gridPane = new GridPane();
	public Scene LoginScene;
	
	 Text textSignIn = new Text("Sign In");
	 
	 Label emailLabel = new Label("Email:");
	 Label passwordLabel = new Label("Password:");
	 
	 TextField emailTF = new TextField();
	 PasswordField passwordTF = new PasswordField();
	 

	public Login(Stage primaryStage) {
		gridPane.setAlignment(Pos.TOP_LEFT);
		gridPane.setPadding(new Insets(20));
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    
	    textSignIn.setFont(Font.font(24));
	    GridPane.setMargin(textSignIn, new Insets(0, 0, 10, 0));
	    gridPane.add(textSignIn, 0, 0);
	    
	    gridPane.add(emailLabel, 0, 1);
	    gridPane.add(emailTF, 1, 1);
		
	    gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordTF, 1, 2);
	    
        Button loginButton = new Button("Log In");
        gridPane.add(loginButton, 0, 3);
        
	    loginButton.setOnAction(e -> {
	    	
	    	if (emailTF.getText().isEmpty() || passwordTF.getText().isEmpty()) {
	    		Alert alert = new Alert(AlertType.ERROR);
	        	alert.setTitle("ERROR");  
	        	alert.setContentText("Email atau password harus diisi!");
	        	alert.showAndWait();
            }
	    	
	    	if(isExist()) {
	    		Alert alert = new Alert(AlertType.INFORMATION);
	        	alert.setTitle("Success");  
	        	alert.setContentText("Berhasil Login!");
	        	alert.showAndWait();
	    		Home home = new Home(primaryStage);
				primaryStage.setScene(home.homeScene);
	    	} else {
	    		Alert alert = new Alert(AlertType.ERROR);
	        	alert.setTitle("ERROR");  
	        	alert.setContentText("Email atau Password tidak terdaftar!");
	        	alert.showAndWait();
	    	}
	    	
			
		});
	    
		LoginScene = new Scene(gridPane, 360, 600);
		primaryStage.setTitle("Log In");
	}
	
	public Boolean isExist() {
		Boolean flag;
		String query = "SELECT * FROM customer WHERE customerEmail ='" + emailTF.getText() + "' AND customerPassword ='" + passwordTF.getText() + "';";
		
		connect.execQuery(query);
		
		try {
			flag = connect.rs.next();
			Home.username = connect.rs.getString("CustomerName");
			Home.balance = connect.rs.getInt("CustomerBalance"); 
			Pembayaran.username = connect.rs.getString("CustomerName");
			History.customerID = connect.rs.getInt("CustomerID"); 
		} catch (Exception e) {
			flag = false;
		}
		
		return flag;
	}

}
