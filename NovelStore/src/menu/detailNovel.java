package menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.Connect;

public class detailNovel {
	private Connect connect = Connect.getInstance();
	public Scene detailNovelScene;
	private BorderPane bp = new BorderPane();
	VBox detailNovel = new VBox(10);
	
	public detailNovel(Stage s, ImageView bookImage, String bookName, Label price, Label author, Label availability) {
		bp.setPadding(new Insets(10));
	    detailNovelScene = new Scene(bp, 360, 600);
		detailNovelScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
		ImageView iconBack = new ImageView(new Image(getClass().getResourceAsStream("/images/back.png")));
		iconBack.setFitWidth(30); 
        iconBack.setFitHeight(30); 
        
        ImageView novelImage = bookImage;
        novelImage.setFitWidth(140);
        novelImage.setFitHeight(200);
        
        HBox novelImageHBox = new HBox(novelImage);
        novelImageHBox.setAlignment(Pos.CENTER); 
        
        Label novelName = new Label(bookName); 
        novelName.setFont(new Font("Arial", 14)); 
        novelName.setStyle("-fx-font-weight: bold;");
        HBox judulNovelHBox = new HBox(novelName);
        judulNovelHBox.setAlignment(Pos.CENTER);
        
        HBox priceNovelHBox = new HBox(price);
        price.setFont(new Font("Arial", 14));
        priceNovelHBox.setStyle("-fx-font-weight: bold;");
        
        HBox authorNovelHBox = new HBox(author);
        author.setFont(new Font("Arial", 14));
        authorNovelHBox.setStyle("-fx-font-weight: bold;");
        
        HBox availabilityNovelHBox = new HBox(availability);
        availabilityNovelHBox.setAlignment(Pos.CENTER_LEFT); 
        availability.setFont(new Font("Arial", 14));
        availabilityNovelHBox.setStyle("-fx-font-weight: bold;");
        
        String query = "SELECT * FROM book WHERE BookName='" + bookName + "'";   
        
        Text description = new Text("tes");
        
        
        try {
			connect.execQuery(query);
			 
			if(connect.rs.next()) {
				Pembayaran.BookID = connect.rs.getInt("BookID"); 
				description = new Text(connect.rs.getString("bookDesc"));
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
          
        description.setWrappingWidth(340);
        description.setFont(new Font("Arial", 10));
        HBox descriptionNovelHBox = new HBox(description);
        
        BorderPane bpLabel = new BorderPane(); 
        bpLabel.setLeft(authorNovelHBox);
        bpLabel.setRight(priceNovelHBox);
        
        Label quantityLabel = new Label("Quantity:");
        Spinner<Integer> quantitySpinner = new Spinner<>(1, 100, 1); 
        quantitySpinner.setEditable(true);
        quantitySpinner.setFocusTraversable(false); 

        HBox quantityBox = new HBox(10, quantityLabel, quantitySpinner);
        quantityBox.setAlignment(Pos.CENTER_LEFT);
        
        Button btnBuy = new Button("Buy");
        HBox btnHBox = new HBox(btnBuy);
        btnHBox.setAlignment(Pos.CENTER);
        btnBuy.setFocusTraversable(false);
        
        btnBuy.setOnAction(e -> { 
        	Pembayaran pembayaran = new Pembayaran(s, quantitySpinner.getValue());
        	s.setScene(pembayaran.pembayaranNovelScene);
        });
        
        detailNovel.getChildren().addAll(iconBack, novelImageHBox, judulNovelHBox, bpLabel, descriptionNovelHBox, quantityBox, availabilityNovelHBox, btnHBox);
        
		bp.setBottom(new FooterMenu().footer(s));
		bp.setTop(detailNovel);
		s.setTitle("Detail Novel");
		s.setScene(detailNovelScene);
		s.show();
	}
	
}