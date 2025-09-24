package crudbuku;

import java.sql.SQLException;

import admin.sidebar;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.Connect;

public class updateBuku {
	private Connect connect = Connect.getInstance();
	public Scene updateBukuScene;
	BorderPane bp = new BorderPane();
	VBox update = new VBox(20);

	public updateBuku(Stage s, String bookName, String bookGenre, int bookPrice, Double bookWeight, String bookPublihser, Image bookCover, int bookQuantity, String bookDesc) {
		updateBukuScene = new Scene(bp, 1000, 600);
		updateBukuScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		update.getStyleClass().add("crud");
		s.setTitle("Admin - Input Data Buku"); 
		bp.setLeft(new sidebar().sidebarVBox(s));
		bp.setCenter(update);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
		
		Label UpdateBukuLabel = new Label("Update Buku Novel");
		Label BookNameLabel = new Label("Judul Novel:");
		Label BookGenreLabel = new Label("Genre Novel:");
		Label BookPriceLabel = new Label("Harga Novel:");
		Label BookWeightLabel = new Label("Berat Novel:");
		Label BookPublisherLabel = new Label("Penerbit:");
		Label BookImageLabel = new Label("Cover Novel:");
		Label BookDescLabel = new Label("Deskripsi Novel:");
		Label BookQuantityLabel = new Label("Jumlah Novel:");
		
		TextField BookNameTF = new TextField();
		TextField BookPriceTF = new TextField();
		TextField BookWeightTF = new TextField();
		TextField BookPublisherTF = new TextField();
		TextField BookFilePathTF = new TextField();
		TextArea BookDescTA = new TextArea();
        BookDescTA.setWrapText(true);
		TextField BookQuantityTF = new TextField();
		
		ComboBox<String> genreNovel = new ComboBox<>();
		genreNovel.setValue(bookGenre);	
		
		try {
			String query1 = "Select CategoryName FROM bookcategory";
			connect.execQuery(query1);
			
			while(connect.rs.next()) {
				genreNovel.getItems().add(connect.rs.getString("CategoryName"));
			} 
			
		} catch (Exception e) {
			System.out.println("gagal mengambil data");
		}
		
		BookFilePathTF.setText("File path will be displayed here.");
		BookFilePathTF.setEditable(false);
		BookFilePathTF.setPrefWidth(350);
		BookFilePathTF.setFocusTraversable(false);
		BookNameTF.setText(bookName);
		BookPriceTF.setText(String.valueOf(bookPrice));
		BookWeightTF.setText(String.valueOf(bookWeight));
		BookPublisherTF.setText(bookPublihser);
		BookDescTA.setText(bookDesc);
		BookQuantityTF.setText(String.valueOf(bookQuantity));
		
		Button uploadImage = new Button("Upload Cover Novel");
		Button btnUpdateDataNovel = new Button("Save");
		
		HBox BookNameHBox = createHBox(BookNameLabel, BookNameTF);
		HBox BookPriceHBox = createHBox(BookPriceLabel, BookPriceTF);
		HBox BookWeightHBox = createHBox(BookWeightLabel, BookWeightTF);
		HBox BookPublisherHBox = createHBox(BookPublisherLabel, BookPublisherTF);
		HBox BookUploadImgHBox = new HBox(10);
		BookUploadImgHBox.setAlignment(Pos.CENTER_LEFT);
		BookUploadImgHBox.getChildren().addAll(BookImageLabel, uploadImage, BookFilePathTF);
		HBox BookGenreNovelHBox = new HBox(10);
		BookGenreNovelHBox.setAlignment(Pos.CENTER_LEFT);
		BookGenreNovelHBox.getChildren().addAll(BookGenreLabel, genreNovel);
		HBox bookDescHBox = new HBox(10);
		bookDescHBox.setAlignment(Pos.CENTER_LEFT);
		bookDescHBox.getChildren().addAll(BookDescLabel, BookDescTA);
		HBox bookQuantityHBox = new HBox(10);
		bookQuantityHBox.setAlignment(Pos.CENTER_LEFT);
		bookQuantityHBox.getChildren().addAll(BookQuantityLabel, BookQuantityTF);
		
		 
		btnUpdateDataNovel.setOnAction(e -> {
			
			if(BookFilePathTF.getText().equals("File path will be displayed here.")) {
				String searchGenreId = "Select BookCategoryID FROM bookcategory WHERE CategoryName='" + genreNovel.getValue() + "'";
				
				connect.execQuery(searchGenreId);
				
				try {
					connect.rs.next();
					int bookCategoryID = connect.rs.getInt("BookCategoryID");
					
					String searchBookId = "Select BookID FROM book WHERE BookName='" + BookNameTF.getText() + "'";
					
					connect.execQuery(searchBookId);
					connect.rs.next();
					int bookID = connect.rs.getInt("BookID");
					
					
					String updateQuery = "UPDATE book SET BookName='" + BookNameTF.getText() + "', BookPrice='" + BookPriceTF.getText() + "', BookWeight='" + BookWeightTF.getText() + "', Publisher='" + BookPublisherTF.getText() + "', BookCategoryID='" + bookCategoryID + "', quantity='" + BookQuantityTF.getText() + "', bookDesc='" + BookDescTA.getText() + "' WHERE BookID ='" + bookID + "'";   
					 
					connect.prepareStatement(updateQuery);
					connect.execUpdate(updateQuery);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
				
				readBuku readBuku = new readBuku(s);
				s.setScene(readBuku.ReadBukuScene);
			} else {
				System.out.println("gagal");
			}
		});
		
		
		update.getChildren().addAll(UpdateBukuLabel, BookNameHBox, BookGenreNovelHBox, BookPriceHBox, BookWeightHBox, BookPublisherHBox, BookUploadImgHBox, bookDescHBox, bookQuantityHBox, btnUpdateDataNovel);
		
		UpdateBukuLabel.getStyleClass().add("sidebar-label");
		BookImageLabel.getStyleClass().add("HBox-label");
		BookGenreLabel.getStyleClass().add("HBox-label");
		BookDescLabel.getStyleClass().add("HBox-label");
		BookQuantityLabel.getStyleClass().add("HBox-label");
		
		
	}

	public HBox createHBox(Label label, TextField textField) {
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER_LEFT);
		hb.getChildren().addAll(label, textField);
		label.getStyleClass().add("HBox-label");
		return hb;
	}
 
}
