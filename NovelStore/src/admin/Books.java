package admin;

import javafx.scene.image.Image;

public class Books {
	private Image BookCover;
	private String BookName;
	private int BookPrice;
	private Double BookWeight;
	private String publisher;
	private String BookCategoryID;
	private int quantity;
	private String bookDesc;
	
	public Books(Image bookCover, String bookName, int bookPrice, Double bookWeight, String publisher, String bookCategoryID, int quantity, String bookDesc) {
		super();
		BookCover = bookCover;
		BookName = bookName;
		BookPrice = bookPrice;
		BookWeight = bookWeight;
		this.publisher = publisher;
		BookCategoryID = bookCategoryID;
		this.quantity = quantity;
		this.bookDesc = bookDesc;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getBookDesc() {
		return bookDesc;
	}

	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}

	public Image getBookCover() {
		return BookCover;
	}
	
	public void setBookCover(Image bookCover) {
		BookCover = bookCover;
	}

	public String getBookName() {
		return BookName;
	}

	public void setBookName(String bookName) {
		BookName = bookName;
	}

	public int getBookPrice() {
		return BookPrice;
	}

	public void setBookPrice(int bookPrice) {
		BookPrice = bookPrice;
	}

	public Double getBookWeight() {
		return BookWeight;
	}

	public void setBookWeight(Double bookWeight) {
		BookWeight = bookWeight;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getBookCategoryID() {
		return BookCategoryID;
	}

	public void setBookCategoryID(String bookCategoryID) {
		BookCategoryID = bookCategoryID;
	}

	
	

}
