package menu;

import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.Connect;

public class DetailHistory {
	private Connect connect = Connect.getInstance();
	public Scene detailHistoryNovelScene;
	private BorderPane bp = new BorderPane();
	VBox detailHistory = new VBox(15);

	public DetailHistory(Stage s, String bookName) {
		bp.setPadding(new Insets(15));
	    detailHistoryNovelScene = new Scene(bp, 360, 600);
		detailHistoryNovelScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
		try {
			String query = "SELECT * FROM book WHERE BookName='" + bookName + "'";
			connect.execQuery(query);
			
			connect.rs.next();
			
			ImageView bookImage = new ImageView(new Image(getClass().getResource("/uploads/" + connect.rs.getString("BookCover")).toExternalForm()));
	        bookImage.setFitWidth(100);
	        bookImage.setFitHeight(150);
	        
	        HBox bookImageHB = new HBox(bookImage);
	        bookImageHB.setAlignment(Pos.CENTER);
	        
	        Label judulNovel = new Label("Judul Novel : " + connect.rs.getString("BookName"));
	        judulNovel.setFont(new Font("Arial", 16));
	        judulNovel.setStyle("-fx-font-weight: bold;");
	        
	        Label author = new Label("Author : " + connect.rs.getString("Publisher"));
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
			
	        detailHistory.getChildren().addAll(bookImageHB, judulNovel, author, price);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		bp.setBottom(new FooterMenu().footer(s));
		bp.setTop(detailHistory); 
		s.setTitle("Detail History");
		s.setScene(detailHistoryNovelScene);
		s.show();
	}

}
