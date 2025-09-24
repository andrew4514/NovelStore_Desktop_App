package menu;


import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Home {
	private BorderPane borderPane = new BorderPane();
	public Scene homeScene;
	VBox welcomeVBox = new VBox(10);
	public static String username; 
	public static int balance; 
	
	 private int currentIndex = 0; 
	 private final Image[] imagePaths = {
			 new Image(getClass().getResourceAsStream("/images/slide1.png")),
			 new Image(getClass().getResourceAsStream("/images/slide2.jpeg")),
			 new Image(getClass().getResourceAsStream("/images/slide3.png"))
	    };

	public Home(Stage s) {
		borderPane.setPadding(new Insets(10));
		
		Label welcomeLabel = new Label("Halo, " + username);
		welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		
		int userBalance = balance; 
	    Label balanceLabel = new Label("Rp " + userBalance);
	    balanceLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2ecc71;");
		
		
	   
	    
	    ImageView imageView = new ImageView();
        imageView.setFitWidth(350); 
        imageView.setFitHeight(170); 
        imageView.setPreserveRatio(true);
        imageView.setImage(imagePaths[currentIndex]);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), imageView);
        fadeTransition.setFromValue(0); 
        fadeTransition.setToValue(1); 

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            currentIndex = (currentIndex + 1) % imagePaths.length;
            imageView.setImage(imagePaths[currentIndex]); 
            fadeTransition.play(); 
        }));
        
        timeline.setCycleCount(Timeline.INDEFINITE); 
        timeline.play(); 
        
        HBox ImageViewhb = new HBox(imageView);
        ImageViewhb.setAlignment(Pos.CENTER);    
        
        
	    Label bestSellerLabel = new Label("Best Seller");
	    bestSellerLabel.setStyle("-fx-font-size: 16px;");
	    
	    HBox bestSellerHBox1 = new HBox(10);
	    
	    ImageView imageView2 = new ImageView(new Image(getClass().getResourceAsStream("/images/novelbestseller.png")));
        imageView2.setFitWidth(80); 
        imageView2.setFitHeight(100); 
        
        ImageView imageView3 = new ImageView(new Image(getClass().getResourceAsStream("/images/novelbestseller.png")));
        imageView3.setFitWidth(80); 
        imageView3.setFitHeight(100); 
        
        ImageView imageView4 = new ImageView(new Image(getClass().getResourceAsStream("/images/novelbestseller.png")));
        imageView4.setFitWidth(80); 
        imageView4.setFitHeight(100); 
        
        bestSellerHBox1.getChildren().addAll(imageView2, imageView3, imageView4);
        bestSellerHBox1.setAlignment(Pos.CENTER);
        
        HBox bestSellerHBox2 = new HBox(10);
        
        ImageView imageView5 = new ImageView(new Image(getClass().getResourceAsStream("/images/novelbestseller.png")));
        imageView5.setFitWidth(80); 
        imageView5.setFitHeight(100); 
        
        ImageView imageView6 = new ImageView(new Image(getClass().getResourceAsStream("/images/novelbestseller.png")));
        imageView6.setFitWidth(80); 
        imageView6.setFitHeight(100); 
        
        ImageView imageView7 = new ImageView(new Image(getClass().getResourceAsStream("/images/novelbestseller.png")));
        imageView7.setFitWidth(80); 
        imageView7.setFitHeight(100); 
        
        bestSellerHBox2.getChildren().addAll(imageView5, imageView6, imageView7);
        bestSellerHBox2.setAlignment(Pos.CENTER);
    
        
		
		welcomeVBox.getChildren().addAll(welcomeLabel, balanceLabel, ImageViewhb, bestSellerLabel, bestSellerHBox1, bestSellerHBox2);
		
		borderPane.setCenter(welcomeVBox);
		borderPane.setBottom(new FooterMenu().footer(s));
		
		s.setTitle("Home");
		homeScene = new Scene(borderPane, 360, 600);
	}

}
