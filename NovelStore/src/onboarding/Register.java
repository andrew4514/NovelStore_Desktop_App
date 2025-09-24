package onboarding;

import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.Connect;

public class Register {
	private Connect connect = Connect.getInstance();
	private GridPane gridPane = new GridPane();
	
	HBox hb = new HBox(30);
	Scene registerScene;
	
	Text textSignUp = new Text("Sign Up");
	
	Label usernameLabel = new Label("Username:");
	Label emailLabel = new Label("Email:");
	Label genderLabel = new Label("Gender:");
	Label tanggalLahirLabel = new Label("Tanggal Lahir:");
	Label passwordLabel = new Label("Password:");
	Label RePasswordLabel = new Label("Re-Password:");
    
	TextField usernameTF = new TextField();
	TextField emailTF = new TextField();
	PasswordField passwordTF = new PasswordField();
	PasswordField RePasswordTF = new PasswordField();
	
	ToggleGroup genderGroup = new ToggleGroup();
    RadioButton maleRadio = new RadioButton("Male");
    RadioButton femaleRadio = new RadioButton("Female");
    
    DatePicker tanggalLahir = new DatePicker();
    
    LocalDate localDate;
	

	public Register(Stage primaryStage) {
		gridPane.setAlignment(Pos.TOP_LEFT);
		gridPane.setPadding(new Insets(20));
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    
	    textSignUp.setFont(Font.font(24));
	    GridPane.setMargin(textSignUp, new Insets(0, 0, 10, 0));
	    gridPane.add(textSignUp, 0, 0);
	    
		gridPane.add(usernameLabel, 0, 1);
		gridPane.add(usernameTF, 0, 2);
		
	    gridPane.add(emailLabel, 0, 3);
	    gridPane.add(emailTF, 0, 4);
	    
	    maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);
        hb.getChildren().addAll(maleRadio, femaleRadio);
	    gridPane.add(genderLabel, 0, 5);
	    gridPane.add(hb, 0, 6);
	    
	    gridPane.add(tanggalLahirLabel, 0, 7);
	    gridPane.add(tanggalLahir, 0, 8);
	    
	    gridPane.add(passwordLabel, 0, 9);
        gridPane.add(passwordTF, 0, 10);
        
        gridPane.add(RePasswordLabel, 0, 11);
        gridPane.add(RePasswordTF, 0, 12);
	    
		Button registerButton = new Button("Register");
	    gridPane.add(registerButton, 0, 13);
	        
	   
		registerButton.setOnAction(e -> {
			
			if(validation()) {			
				Alert alert = new Alert(AlertType.INFORMATION);
	        	alert.setTitle("Success");  
	        	alert.setContentText("Register Berhasil!");
	        	alert.showAndWait();
				Login login = new Login(primaryStage);
				primaryStage.setScene(login.LoginScene);
			} else {
				System.out.println("input tidak valid");
			}
			
		});
		
		registerScene = new Scene(gridPane, 360, 600);
		primaryStage.setTitle("Registrasi");
	}

	public Boolean validation() {
		RadioButton selectedRadio = (RadioButton) genderGroup.getSelectedToggle();
		if (usernameTF.getText().isEmpty() || 
			emailTF.getText().isEmpty() || 
			passwordTF.getText().isEmpty() || 
			selectedRadio.getText().isEmpty() || 
			String.valueOf(tanggalLahir.getValue()).isEmpty() || 
			!(passwordTF.getText().equals(RePasswordTF.getText()))) {
			
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("ERROR");  
        	alert.setContentText("Email atau password harus diisi!");
        	alert.showAndWait();
			return false;
		} else {
			localDate = tanggalLahir.getValue();
			addData(usernameTF.getText(), emailTF.getText(), passwordTF.getText(), 
					selectedRadio.getText(), String.valueOf(localDate));
			return true;
		}
	}
	
	public void addData(String username, String email, String password, String gender, String dob) {
		String query = "INSERT INTO customer VALUES ('0', '" + username + "', '" + email +"', '', '" + 
	dob + "', '" + gender + "', '" + password + "', '1500000', '')";
		connect.prepareStatement(query);
		connect.execUpdate(query);
	}
}











