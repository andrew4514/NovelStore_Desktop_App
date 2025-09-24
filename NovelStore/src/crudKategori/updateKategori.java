package crudKategori;

import admin.sidebar;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Connect;

public class updateKategori {
	private Connect connect = Connect.getInstance();
	BorderPane bp = new BorderPane();
	VBox update = new VBox(20);
	public Scene updateKategoriScene;
	
	Label updateKategoriLabel = new Label("Update Kategori Novel");

	public updateKategori(Stage s, String namaKategori) {
		updateKategoriScene = new Scene(bp, 1000, 600);
	    updateKategoriScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
	    update.getStyleClass().add("crud");
	    s.setTitle("Admin - Update Kategori Novel"); 
	    
	    Label namaKategoriLabel = new Label("Nama Kategori");
	    TextField namaKategoriTF = new TextField();
	    
	    Button btnUpdateKategoriNovel = new Button("Save");
	    
	    namaKategoriTF.setText(namaKategori);
	    
	    HBox namaKategoriHBox = new HBox(10);
		namaKategoriHBox.setAlignment(Pos.CENTER_LEFT);
		namaKategoriHBox.getChildren().addAll(namaKategoriLabel, namaKategoriTF, btnUpdateKategoriNovel);
		
		btnUpdateKategoriNovel.setOnAction(e -> {
			if(namaKategoriTF.getText().isEmpty()) 
			{
				
				System.out.println("Input tidak Valid!");
				
			} else {
				String searchCategoryID = "SELECT BookCategoryID FROM bookcategory WHERE CategoryName='" + namaKategori + "'";
				 
				connect.execQuery(searchCategoryID);
				
				try {
					connect.rs.next();
					
					String query = "UPDATE bookcategory SET CategoryName=? WHERE BookCategoryID='" + connect.rs.getInt("BookCategoryID") + "'";  
					
					connect.prepareStatement(query);
					
					connect.ps.setString(1, namaKategoriTF.getText());
					connect.execUpdate(query);
					
					readKategori readKategori = new readKategori(s);
					s.setScene(readKategori.readKategoriScene);
				} catch (Exception e1) {
					System.out.println("Input salah!");
					e1.printStackTrace();
				}
				
				
			}
		});
	    
	    update.getChildren().addAll(updateKategoriLabel, namaKategoriHBox);
	    bp.setLeft(new sidebar().sidebarVBox(s));
		bp.setCenter(update);
	}


}
