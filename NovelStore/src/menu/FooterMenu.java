package menu;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FooterMenu {

	public HBox footer(Stage s) {
		HBox menuIcons = new HBox(30);	
		Image icon1 = new Image(getClass().getResourceAsStream("/images/home.png"));
		ImageView iconView1 = new ImageView(icon1);
		Text textIcon1 = new Text("Home");
		VBox iconBox1 = new VBox(-5, iconView1, textIcon1);
		iconBox1.setAlignment(Pos.CENTER);
		iconView1.setFitHeight(48);
		iconView1.setFitWidth(48);
		
		Image icon2 = new Image(getClass().getResourceAsStream("/images/purchase-books.png"));
		ImageView iconView2 = new ImageView(icon2);
		Text textIcon2 = new Text("Buy Novel");
		VBox iconBox2 = new VBox(-5, iconView2, textIcon2);
		iconBox2.setAlignment(Pos.CENTER);
		iconView2.setFitHeight(40);
		iconView2.setFitWidth(40);
		
		Image icon3 = new Image(getClass().getResourceAsStream("/images/file.png"));
		ImageView iconView3 = new ImageView(icon3);
		Text textIcon3 = new Text("History");
		VBox iconBox3 = new VBox(-5, iconView3, textIcon3);
		iconBox3.setAlignment(Pos.CENTER);
		iconView3.setFitHeight(40);
		iconView3.setFitWidth(40);
		
		Image icon4 = new Image(getClass().getResourceAsStream("/images/profile-user.png"));
		ImageView iconView4 = new ImageView(icon4);
		Text textIcon4 = new Text("Profile");
		VBox iconBox4 = new VBox(-5, iconView4, textIcon4);
		iconBox4.setAlignment(Pos.CENTER);
		iconView4.setFitHeight(40);
		iconView4.setFitWidth(40);
		
		iconView1.setOnMouseClicked(e -> s.setScene(new Home(s).homeScene));
		iconView2.setOnMouseClicked(e -> s.setScene(new BuyNovel(s).BuyNovelScene));
		iconView3.setOnMouseClicked(e -> s.setScene(new History(s).historyNovelScene));
		iconView4.setOnMouseClicked(e -> s.setScene(new Profile(s).profileScene));
		
		menuIcons.getChildren().addAll(iconBox1, iconBox2, iconBox3, iconBox4);
		menuIcons.setStyle("-fx-alignment: center;");
		menuIcons.setAlignment(Pos.CENTER);
		return menuIcons;
	}

} 
