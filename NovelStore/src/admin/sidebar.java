package admin;

import crudKategori.createKategori;
import crudKategori.readKategori;
import crudbuku.readBuku;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class sidebar {
	
	VBox sidebar = new VBox(10);
	VBox profileBox = new VBox(5);
	
	Label username = new Label("Budi");
	
	Button btnEditProfile = new Button("Edit Profile");
	
	Circle profilePicture = new Circle(40, Color.LIGHTGRAY);
	
	Image profileImg = new Image(getClass().getResourceAsStream("/images/home.png"));
	ImageView profileImgView = new ImageView(profileImg);
	Label profileLabel = new Label("Profile");
	
	
	Image DataBukuImg = new Image(getClass().getResourceAsStream("/images/home.png"));
	ImageView DataBukuImgView = new ImageView(DataBukuImg);
	Label DataBukuLabel = new Label("Kelola Buku");
	
	
	Image DataCategoryImg = new Image(getClass().getResourceAsStream("/images/home.png"));
	ImageView DataCategoryImgView = new ImageView(DataCategoryImg);
	Label DataCategoryLabel = new Label("Kategory Buku");
	
	Image DataPenjualanImg = new Image(getClass().getResourceAsStream("/images/home.png"));
	ImageView DataPenjualanImgView = new ImageView(DataBukuImg);
	Label DataPenjualanLabel = new Label("Penjualan");

	public VBox sidebarVBox(Stage s) {
		
        profileBox.getChildren().addAll(profilePicture, username, btnEditProfile);
		
		HBox dataBukuHBox = createHBoxLabel(DataBukuImgView, DataBukuLabel);
		HBox dataCategoryBox = createHBoxLabel(DataCategoryImgView, DataCategoryLabel);
		
		
		dataBukuHBox.setOnMouseClicked(e -> s.setScene(new readBuku(s).ReadBukuScene));
		dataCategoryBox.setOnMouseClicked(e -> s.setScene(new readKategori(s).readKategoriScene));
		
		sidebar.getChildren().addAll(profileBox, dataBukuHBox, dataCategoryBox);
		
		sidebar.getStyleClass().add("sidebar");
		username.getStyleClass().add("sidebar-label");
		profileBox.getStyleClass().add("profile-box");
		btnEditProfile.getStyleClass().add("edit-profile-button");
		VBox.setMargin(profileBox, new Insets(0, 0, 20, 0));
		
		return sidebar;
	}
	
	public HBox createHBoxLabel(ImageView img, Label label) {
		HBox hb = new HBox(10);
		hb.setAlignment(Pos.CENTER_LEFT);
		hb.getChildren().addAll(img, label);
		label.getStyleClass().add("HBox-label");
		return hb;
	}

}
