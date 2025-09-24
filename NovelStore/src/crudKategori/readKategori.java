package crudKategori;

import admin.KategoriNovel;
import admin.sidebar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Connect;

public class readKategori {
	private Connect connect = Connect.getInstance();
	BorderPane bp = new BorderPane();
	VBox CRUD = new VBox(20);
	public Scene readKategoriScene;

	Label DaftarKategoriLabel = new Label("Daftar Kategori Novel");
	
	Button btnTambahKategori = new Button("Tambah Kategori");
	
	TableView<KategoriNovel> KategoriTV = new TableView<KategoriNovel>();
	
	ObservableList<KategoriNovel> kategoriList = FXCollections.observableArrayList();
	
	public readKategori(Stage s) {
		readKategoriScene = new Scene(bp, 1000, 600);
	    readKategoriScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
	    
		
		TableColumn<KategoriNovel, String> namaKategoriCol = new TableColumn<>("Nama Kategori");
		namaKategoriCol.setCellValueFactory(new PropertyValueFactory<>("namaKategori"));
		
		TableColumn<KategoriNovel, Void> actionCol = new TableColumn<>("Actions");
		actionCol.setCellFactory(e -> new TableCell<>() {
			private final Button btnUpdate = new Button("Update");
		    private final Button btnDelete = new Button("Delete");
		    
		    {
		        btnUpdate.setOnAction(e -> {
		            KategoriNovel kategoriNovel = getTableView().getItems().get(getIndex());
		            updateKategori updateKategori = new updateKategori(s, kategoriNovel.getNamaKategori());
		            s.setScene(updateKategori.updateKategoriScene);
		            
		        });

		        btnDelete.setOnAction(e -> {
//		            Books book = getTableView().getItems().get(getIndex());
//		    		String query = "DELETE FROM book WHERE BookName='" + book.getBookName() + "'";
//		            connect.prepareStatement(query);
//		            connect.execUpdate(query);  
//		            bookList.remove(getIndex());
		        });
		    }
		    
		    @Override
		    protected void updateItem(Void item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty) {
		            setGraphic(null);
		        } else {
		            HBox hBox = new HBox(5, btnUpdate, btnDelete); 
		            setGraphic(hBox);
		        }
		    }
		});
		
		KategoriTV.getColumns().addAll(namaKategoriCol, actionCol);
		CRUD.getChildren().addAll(DaftarKategoriLabel, btnTambahKategori, KategoriTV);
		
		String queryKategoriList = "SELECT CategoryName FROM bookcategory";
		
		try { 
				connect.execQuery(queryKategoriList);
				
				while (connect.rs.next()) {
						kategoriList.addAll(new KategoriNovel(connect.rs.getString("CategoryName")));
				}
				
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("gagal mengambil data!");
		}
		
		KategoriTV.setItems(kategoriList);
		
		CRUD.getStyleClass().add("crud");
		DaftarKategoriLabel.getStyleClass().add("sidebar-label");
		VBox.setMargin(DaftarKategoriLabel, new Insets(0, 0, 10, 0));
		
		btnTambahKategori.setOnAction(e -> {
			createKategori createKategori = new createKategori(s);
			s.setScene(createKategori.createKategoriScene);
		});
		
		bp.setLeft(new sidebar().sidebarVBox(s));
		bp.setCenter(CRUD);
	}
		
	
}
