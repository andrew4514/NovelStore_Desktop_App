package menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.Connect;

public class History {
	private Connect connect = Connect.getInstance();
	public Scene historyNovelScene;
	private BorderPane bp = new BorderPane();
	VBox history = new VBox(15);
	
	public static int customerID;

	public History(Stage s) {
		bp.setPadding(new Insets(15));
	    historyNovelScene = new Scene(bp, 360, 600);
		historyNovelScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
		Label historyLabel = new Label("History");
		HBox historyHBox = new HBox(historyLabel); 
	    historyHBox.setAlignment(Pos.CENTER); 
	    historyLabel.setFont(new Font("Arial", 18));
	    historyHBox.setStyle("-fx-font-weight: bold;");
	    
	    history.getChildren().addAll(historyHBox);
	    
	    String query = "SELECT * FROM sale INNER JOIN book ON sale.BookID = book.BookID WHERE CustomerID=" + customerID;
	    
	    try {
			connect.execQuery(query);
			
			while(connect.rs.next()) {
				HBox header = new HBox(10);
				header.getStyleClass().add("card-novel");
				
				ImageView bookImage = new ImageView(new Image(getClass().getResource("/uploads/" + connect.rs.getString("BookCover")).toExternalForm()));
		        bookImage.setFitWidth(100);
		        bookImage.setFitHeight(150);
		        
		        Label judulNovel = new Label(connect.rs.getString("BookName"));
		        judulNovel.setFont(new Font("Arial", 16));
		        judulNovel.setStyle("-fx-font-weight: bold;");
		        
		        Label statusNovel = new Label(connect.rs.getString("status"));
		        statusNovel.setFont(new Font("Arial", 14));
		        statusNovel.setStyle("-fx-font-weight: bold;");
		        
		        Label price = new Label("Rp. " + connect.rs.getString("BookPrice"));
		        price.setFont(new Font("Arial", 10));
		        price.setStyle("-fx-font-weight: bold;"); 
		        
		        HBox priceHBox = new HBox(price);
		        priceHBox.setAlignment(Pos.BOTTOM_RIGHT);
		        
		        String bookName = connect.rs.getString("BookName");
		        
		        Button btnDetail = new Button("Detail");
		        btnDetail.setOnAction(e -> {
		        	DetailHistory detailHistory;
					detailHistory = new DetailHistory(s, bookName);
					s.setScene(detailHistory.detailHistoryNovelScene);
		        });
				HBox btnHBox = new HBox(btnDetail); 
				btnHBox.setAlignment(Pos.BOTTOM_RIGHT); 
				
				Region spacer = new Region();
				VBox.setVgrow(spacer, Priority.ALWAYS); 
		        
		        header.getChildren().addAll(bookImage, new VBox(8, judulNovel, statusNovel, spacer, price, btnHBox));
		         
		        history.getChildren().add(header);
			}
	    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		ScrollPane scrollPane = new ScrollPane(history);
		scrollPane.setFitToWidth(true);  
		scrollPane.setPannable(true);
		scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 10px;");
		
		
		bp.setCenter(scrollPane);
		bp.setBottom(new FooterMenu().footer(s));
		bp.setTop(history); 
		s.setTitle("History");
		s.setScene(historyNovelScene);
		s.show();
	}

}