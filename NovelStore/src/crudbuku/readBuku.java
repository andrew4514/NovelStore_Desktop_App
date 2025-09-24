package crudbuku;


import admin.Books;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Connect;

public class readBuku {
	
	private Connect connect = Connect.getInstance();
	BorderPane bp = new BorderPane();
	VBox CRUD = new VBox(20);
	public Scene ReadBukuScene;
	
	Label DaftarBukuLabel = new Label("Daftar Buku Novel");
	
	Button btnTambahBuku = new Button("Tambah Buku");
	
	TextField CariBukuTF = new TextField("Cari Data Buku");
	
	TableView<Books> BookTV = new TableView<Books>();
	
	ObservableList<Books> bookList = FXCollections.observableArrayList();

	public readBuku(Stage s) {
		ReadBukuScene = new Scene(bp, 1020, 600);
	    ReadBukuScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
		
		TableColumn<Books, Image> BookImageCol = new TableColumn<>("Cover Novel");
		BookImageCol.setCellValueFactory(new PropertyValueFactory<>("BookCover"));
		
		 BookImageCol.setCellFactory(param -> new TableCell<Books, Image>() {
	            @Override
	            protected void updateItem(Image item, boolean empty) {
	                super.updateItem(item, empty);
	                if (empty || item == null) {
	                    setGraphic(null);
	                } else {
	                    ImageView imageView = new ImageView(item);
	                    imageView.setFitWidth(50); 
	                    imageView.setFitHeight(50); 
	                    setGraphic(imageView);
	                }
	            }
	        });
		
		TableColumn<Books, String> BookNameCol = new TableColumn<>("Judul Novel");
		BookNameCol.setCellValueFactory(new PropertyValueFactory<>("BookName"));
		
		TableColumn<Books, Integer> BookPriceCol = new TableColumn<>("Harga");
		BookPriceCol.setCellValueFactory(new PropertyValueFactory<>("BookPrice"));
		
		TableColumn<Books, Double> BookWeightCol = new TableColumn<>("Berat Buku");
		BookWeightCol.setCellValueFactory(new PropertyValueFactory<>("BookWeight"));
		
		TableColumn<Books, String> BookCategoryCol = new TableColumn<>("Genre Novel");
		BookCategoryCol.setCellValueFactory(new PropertyValueFactory<>("BookCategoryID"));
		
		TableColumn<Books, Double> BookPublisherCol = new TableColumn<>("Penerbit");
		BookPublisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
		
		TableColumn<Books, String> BookDescCol = new TableColumn<>("Deskripsi");
		BookDescCol.setCellValueFactory(new PropertyValueFactory<>("bookDesc"));
		BookDescCol.setMaxWidth(100);
		
		TableColumn<Books, Integer> BookQuantityCol = new TableColumn<>("Jumlah");
		BookQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		
		TableColumn<Books, Void> ActionCol = new TableColumn<>("Actions");
		ActionCol.setCellFactory(e -> new TableCell<>() {
			private final Button btnUpdate = new Button("Update");
		    private final Button btnDelete = new Button("Delete");
		    
		    {
		        btnUpdate.setOnAction(e -> {
		            Books book = getTableView().getItems().get(getIndex());
		            updateBuku updateBuku = new updateBuku(s, book.getBookName(), book.getBookCategoryID(), book.getBookPrice(), book.getBookWeight(), book.getPublisher(), book.getBookCover(), book.getQuantity(), book.getBookDesc());
		            s.setScene(updateBuku.updateBukuScene);
		            
		        });

		        btnDelete.setOnAction(e -> {
		            Books book = getTableView().getItems().get(getIndex());
		    		String query = "DELETE FROM book WHERE BookName='" + book.getBookName() + "'";
		            connect.prepareStatement(query);
		            connect.execUpdate(query);  
		            bookList.remove(getIndex());
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
		
		
		
		BookTV.getColumns().addAll(BookImageCol, BookNameCol, BookPriceCol, BookWeightCol, BookCategoryCol, BookPublisherCol, BookDescCol, BookQuantityCol, ActionCol);
		
		
		
		
		String queryBookList = "SELECT BookCover, BookName, BookPrice, BookWeight, Publisher, CategoryName, quantity, bookDesc " 
				                + "FROM book "
				                + "INNER JOIN bookcategory ON book.BookCategoryID = bookcategory.BookCategoryID";
		
		try { 
				connect.execQuery(queryBookList);
				
				while (connect.rs.next()) {
					
						bookList.add(new Books(
								new Image(getClass().getResource("/uploads/" + connect.rs.getString("BookCover")).toExternalForm()),
								connect.rs.getString("BookName"),  
								connect.rs.getInt("BookPrice"), 
								connect.rs.getDouble("BookWeight"),  
								connect.rs.getString("publisher"), 
								connect.rs.getString("CategoryName"), 
								connect.rs.getInt("quantity"),
								connect.rs.getString("bookDesc")));
					
				}
				
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("gagal mengambil data!");
		}
		
		BookTV.setItems(bookList);
		CRUD.getChildren().addAll(DaftarBukuLabel, btnTambahBuku, CariBukuTF, BookTV);
		// Daftar Buku Novel
		
		
		bp.setLeft(new sidebar().sidebarVBox(s));
		bp.setCenter(CRUD);
		
		
		CRUD.getStyleClass().add("crud");
		DaftarBukuLabel.getStyleClass().add("sidebar-label");
		VBox.setMargin(DaftarBukuLabel, new Insets(0, 0, 10, 0));
		
		
		btnTambahBuku.setOnAction(e -> {
			createBuku createBuku = new createBuku(s);
			s.setScene(createBuku.createBukuScene);
		});
		
		
		
		s.setTitle("Admin - Kelola Buku");
		s.setScene(ReadBukuScene);
		s.show();
	}

	
}
