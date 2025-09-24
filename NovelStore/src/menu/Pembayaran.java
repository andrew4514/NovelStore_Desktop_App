package menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.Connect;

public class Pembayaran {
	private Connect connect = Connect.getInstance();
	public Scene pembayaranNovelScene;
	private BorderPane bp = new BorderPane();
	VBox pembayaran = new VBox(15);
	
	public static String username;
	public static int BookID;
	public int customerID;

	public Pembayaran(Stage s, Integer quantity) { 
		bp.setPadding(new Insets(10));
	    pembayaranNovelScene = new Scene(bp, 360, 600);
		pembayaranNovelScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
		ImageView iconBack = new ImageView(new Image(getClass().getResourceAsStream("/images/back.png")));
		iconBack.setFitWidth(30); 
        iconBack.setFitHeight(30); 
        
        Label metodeLabel = new Label("Metode Pembayaran");
        HBox metodeHBox = new HBox(metodeLabel);
        metodeHBox.setAlignment(Pos.CENTER); 
        metodeLabel.setFont(new Font("Arial", 18));
        metodeHBox.setStyle("-fx-font-weight: bold;");
        
        ToggleGroup paymentGroup = new ToggleGroup();

        RadioButton eWallet = new RadioButton("E-Wallet");
        eWallet.setTextFill(Color.BLACK);
        eWallet.setToggleGroup(paymentGroup);
        eWallet.setSelected(true); // Default selected

        RadioButton transferBank = new RadioButton("Transfer Bank");
        transferBank.setTextFill(Color.BLACK);
        transferBank.setToggleGroup(paymentGroup);

        RadioButton kartuKredit = new RadioButton("Kartu Kredit");
        kartuKredit.setTextFill(Color.BLACK);
        kartuKredit.setToggleGroup(paymentGroup);

        VBox paymentBox = new VBox(10, eWallet, transferBank, kartuKredit);
        paymentBox.setPadding(new Insets(10));
        paymentBox.setAlignment(Pos.CENTER);
        
        Label addressLabel = new Label("Alamat pembeli:");
        addressLabel.setFont(new Font("Arial", 12));
        addressLabel.setTextFill(Color.BLACK);

        TextField addressField = new TextField();
        addressField.setPrefWidth(250);
        addressField.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: black;");
        
        String query = "SELECT * FROM customer WHERE CustomerName='" + username + "'"; 
        
        VBox addressVBox = new VBox();
        Button btnAddress = new Button("Save");
        
        try {
			connect.execQuery(query);
			
			connect.rs.next();
			
			customerID = connect.rs.getInt("CustomerID"); 
			
			if(connect.rs.getString("CustomerAddress").equals("")) {
				addressVBox.getChildren().addAll(addressLabel, addressField, btnAddress);
				
				
			} else {
				Label addressLabel2 = new Label(connect.rs.getString("CustomerAddress"));
				addressVBox.getChildren().add(addressLabel2);  
			}
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
        
        
        btnAddress.setOnAction(e -> {
        	String queryUpdate = "UPDATE customer SET CustomerAddress='" + addressField.getText() + "' WHERE CustomerID=" + customerID;
        	
        	try {
				connect.prepareStatement(queryUpdate);
				connect.execUpdate(queryUpdate);
				
				Alert alert = new Alert(AlertType.INFORMATION);
	        	alert.setTitle("Success");  
	        	alert.setContentText("Alamat Berhasil disimpan!.");
	        	alert.showAndWait();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
        });
        
        Label priceLabel = new Label("Harga buku: 85.000");
        priceLabel.setFont(new Font("Arial", 12));
        priceLabel.setTextFill(Color.BLACK);

        
        Button payButton = new Button("Pay");
        payButton.setFont(new Font("Arial", 14));
        payButton.setStyle("-fx-background-color: #60434A; -fx-text-fill: white; -fx-background-radius: 10;");
        payButton.setPrefWidth(80);
        
        payButton.setOnAction(e -> {
        	String query2 = "INSERT INTO sale VALUES('0', '" + customerID + "', '" + BookID + "', '" + quantity + "', '" + "Menunggu Konfirmasi" + "' )";
        	
        	try { 
        		connect.prepareStatement(query2);
				connect.execUpdate(query2);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
        	
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("Success"); 
        	alert.setContentText("Pembayaran Berhasil.");
        	alert.showAndWait();
        	
        	History history = new History(s);
        	s.setScene(history.historyNovelScene);
        });
         
        pembayaran.getChildren().addAll(iconBack, metodeHBox, paymentBox, addressVBox, priceLabel, payButton); 
		
		bp.setBottom(new FooterMenu().footer(s));
		bp.setTop(pembayaran);
		s.setTitle("Detail Novel");
		s.setScene(pembayaranNovelScene);
		s.show();
	}

}
