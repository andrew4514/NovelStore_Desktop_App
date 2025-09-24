package menu;

import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.Connect;

public class BuyNovel {
	private Connect connect = Connect.getInstance();
	public Scene BuyNovelScene;
	private BorderPane bp = new BorderPane();
	VBox buyNovel = new VBox(25);
	VBox dataNovel = new VBox(10);
	
	TextField cariNovelTF = new TextField(); 
		
	public BuyNovel(Stage s) {
		bp.setPadding(new Insets(10));
		BuyNovelScene = new Scene(bp, 360, 600);
		BuyNovelScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
		cariNovelTF.setPromptText("Cari Judul Novel");
		
		String query = "SELECT BookID, bookDesc, quantity, CategoryName, BookName, BookPrice, BookWeight, Publisher, BookCover FROM book "
				+ "INNER JOIN bookcategory ON book.BookCategoryID = bookcategory.BookCategoryID";
		
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
		        
		        Label author = new Label(connect.rs.getString("Publisher"));
		        author.setFont(new Font("Arial", 14));
		        
		        Label description = new Label(Arrays.stream(connect.rs.getString("bookDesc").split("\\s+")).limit(30).collect(Collectors.joining(" ")) + "...");
		        description.setWrapText(true);
		        description.setFont(new Font("Arial", 10));
		        
		        Label availability = new Label("Tersedia " + connect.rs.getString("quantity") + " Buku");
		        availability.setFont(new Font("Arial", 10));
		        availability.setStyle("-fx-font-weight: bold;");
		        
		        Label price = new Label("Rp. " + connect.rs.getString("BookPrice"));
		        price.setFont(new Font("Arial", 10));
		        price.setStyle("-fx-font-weight: bold;");
		        
		        String bookName = connect.rs.getString("BookName");
		        
				
		        header.getChildren().addAll(bookImage, new VBox(8, judulNovel, author, description, new HBox(30, price, availability)));
		        header.setOnMouseClicked(e -> {
		        	detailNovel detailNovel;
					detailNovel = new detailNovel(s, bookImage, bookName, price, author, availability);
					s.setScene(detailNovel.detailNovelScene);
		        });
		        
		        dataNovel.getChildren().add(header); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("gagal ambil data!");
		}
		
		ScrollPane scrollPane = new ScrollPane(dataNovel);
		scrollPane.setFitToWidth(true); 
		scrollPane.setPannable(true);
		
		buyNovel.getChildren().addAll(cariNovelTF, dataNovel);
		bp.setBottom(new FooterMenu().footer(s));
		bp.setTop(buyNovel);
		bp.setCenter(scrollPane); 
		
		s.setTitle("Buy Novel");
		s.setScene(BuyNovelScene);
		s.show();
	}

}

