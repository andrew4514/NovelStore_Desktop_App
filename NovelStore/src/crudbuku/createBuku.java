package crudbuku;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import admin.sidebar;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.Connect;

public class createBuku {
	private Connect connect = Connect.getInstance();
	BorderPane bp = new BorderPane();
	VBox create = new VBox(20);
	public Scene createBukuScene;

	public createBuku(Stage s) {
		createBukuScene = new Scene(bp, 1000, 600);
		createBukuScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		s.setTitle("Admin - Input Data Buku"); 
		bp.setLeft(new sidebar().sidebarVBox(s));
		bp.setCenter(create);
		create.getStyleClass().add("crud");
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
		
		Label TambahBukuLabel = new Label("Tambah Buku Novel");
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
		BookDescTA.setPromptText("Deskripsi Singkat Novel");
        BookDescTA.setWrapText(true);
		TextField BookQuantityTF = new TextField();
		
		ComboBox<String> genreNovel = new ComboBox<>();
		
		try {
			String query = "Select CategoryName FROM bookcategory";
			connect.execQuery(query);
			
			while(connect.rs.next()) {
				genreNovel.getItems().add(connect.rs.getString("CategoryName"));
			} 
			
		} catch (Exception e) {
			System.out.println("gagal mengambil data");
		}
		
		BookFilePathTF.setPromptText("File path will be displayed here.");
		BookFilePathTF.setEditable(false);
		BookFilePathTF.setPrefWidth(350);
		BookFilePathTF.setFocusTraversable(false);
		
		Button uploadImage = new Button("Upload Cover Novel");
		Button btnUploadDataNovel = new Button("Submit");
		
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
		
		
	
		
		
		uploadImage.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(null);
			
			if (selectedFile != null) {
				BookFilePathTF.setText(selectedFile.getAbsolutePath());
            } else {
                BookFilePathTF.setText("No file selected.");
            }
			
			
		});
		

		
		TambahBukuLabel.getStyleClass().add("sidebar-label");
		BookImageLabel.getStyleClass().add("HBox-label");
		BookGenreLabel.getStyleClass().add("HBox-label");
		
		
		create.getChildren().addAll(TambahBukuLabel, BookNameHBox, BookGenreNovelHBox, BookPriceHBox, BookWeightHBox, BookPublisherHBox, BookUploadImgHBox, bookDescHBox, bookQuantityHBox, btnUploadDataNovel);
		
		btnUploadDataNovel.setOnAction(e -> {
			
			if(BookNameTF.getText().isEmpty() || 
			   BookPriceTF.getText().isEmpty() || 
			   BookWeightTF.getText().isEmpty() || 
			   BookPublisherTF.getText().isEmpty() || 
			   BookFilePathTF.getText().isEmpty()) 
			{
				
				System.out.println("Input Data Novel tidak Valid!");
				
			} else {
				
				try {
					String searchGenreId = "Select BookCategoryID FROM bookcategory WHERE CategoryName='" + genreNovel.getValue() + "'";
					
					connect.execQuery(searchGenreId);
					connect.rs.next();
					
					String query = "INSERT INTO book VALUES('0', '" + connect.rs.getInt("BookCategoryID") + "', '"+ BookNameTF.getText() + "', '" + Integer.parseInt(BookPriceTF.getText()) + "', '" + Double.parseDouble(BookWeightTF.getText()) + "', '" + BookPublisherTF.getText() + "', ?, ?, ?)";
					
					connect.prepareStatement(query);
				
					File sourceFile = new File(BookFilePathTF.getText());
					File destFile = new File("resources/uploads/" + sourceFile.getName());
					
					Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

					String filePath = sourceFile.getName();
					
					connect.ps.setString(1, filePath);
					connect.ps.setInt(2, Integer.parseInt(BookQuantityTF.getText()));
					connect.ps.setString(3, BookDescTA.getText());
					connect.execUpdate(query);
					
					try {
					    Thread.sleep(3000); 
					} catch (InterruptedException e1) {
					    e1.printStackTrace();
					}
					
					readBuku readBuku = new readBuku(s);
					s.setScene(readBuku.ReadBukuScene);
				} catch (Exception e1) {
					System.out.println("Input buku salah!");
					e1.printStackTrace();
				}
				
				
			}
			
		});
	}
	
	public HBox createHBox(Label label, TextField textField) {
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER_LEFT);
		hb.getChildren().addAll(label, textField);
		label.getStyleClass().add("HBox-label");
		return hb;
	}

}
