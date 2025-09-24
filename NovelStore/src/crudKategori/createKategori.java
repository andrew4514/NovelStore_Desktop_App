package crudKategori;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import admin.sidebar;
import crudbuku.readBuku;
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

public class createKategori {
	private Connect connect = Connect.getInstance();
	BorderPane bp = new BorderPane();
	VBox create = new VBox(20);
	public Scene createKategoriScene;

	public createKategori(Stage s) {
		createKategoriScene = new Scene(bp, 1000, 600);
		createKategoriScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		s.setTitle("Admin - Input Data Kategori Buku"); 
		bp.setLeft(new sidebar().sidebarVBox(s));
		bp.setCenter(create);
		create.getStyleClass().add("crud");
		
		Label TambahKategoriLabel = new Label("Tambah Kategori Buku");
		Label KategoriLabel = new Label("Nama Kategori:");
		
		TextField kategoriNameTF = new TextField();
		
		Button btnSubmit = new Button("Submit");
		
		HBox kategoriHBox = new HBox(10);
		kategoriHBox.setAlignment(Pos.CENTER_LEFT);
		kategoriHBox.getChildren().addAll(KategoriLabel, kategoriNameTF);
		
		TambahKategoriLabel.getStyleClass().add("sidebar-label");
		KategoriLabel.getStyleClass().add("HBox-label");
		
		create.getChildren().addAll(TambahKategoriLabel, kategoriHBox, btnSubmit);
		
		btnSubmit.setOnAction(e -> {
			if(kategoriNameTF.getText().isEmpty()) 
					{
						
						System.out.println("Input tidak Valid!");
						
					} else {
						
						try {
							String query = "INSERT INTO bookcategory VALUES('0', ?)";
							
							connect.prepareStatement(query);
							
							connect.ps.setString(1, kategoriNameTF.getText());
							connect.execUpdate(query);
							
							readKategori readKategori = new readKategori(s);
							s.setScene(readKategori.readKategoriScene);
						} catch (Exception e1) {
							System.out.println("Input salah!");
							e1.printStackTrace();
						}
						
						
					}
		});
	}

}
