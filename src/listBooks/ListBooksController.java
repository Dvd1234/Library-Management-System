package listBooks;

import java.net.URL;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dbutils.ConnectDB;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListBooksController implements Initializable{
	
	
	ObservableList<String> choice= FXCollections.observableArrayList("bookId","bookTitle","bookAuthor","bookPublisher","Tags");

	
	@FXML
	private TableView<Book> tableView;
	
	@FXML
	private TextField searchBox;
	
	
	@FXML
	private TableColumn<Book,String> idCol;
	@FXML
	private TableColumn<Book,String> titleCol;
	@FXML
	private TableColumn<Book,String> authorCol;
	@FXML
	private TableColumn<Book,String> publisherCol;
	@FXML
	private TableColumn<Book,String> tagsCol;
	
	@FXML
	private ChoiceBox<String> searchChoice;
	
	
	ObservableList<Book> list= FXCollections.observableArrayList();
	
	private ConnectDB conn;
	
	
	
	
	public void initCol() {
		
		
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));		
		authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
		publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
		tagsCol.setCellValueFactory(new PropertyValueFactory<>("tags"));
		
	}
	
	
	@FXML
	public void search() throws InstantiationException, IllegalAccessException, SQLException {
		
		String whatToSearch=searchChoice.getValue().toString();
		String search=searchBox.getText();
		
		String sql="SELECT * FROM book WHERE "+whatToSearch+" LIKE '%"+search+"%'";
		
		Connection conn=ConnectDB.getConnection();
		PreparedStatement pst=conn.prepareStatement(sql);
		
		
		ResultSet rs=pst.executeQuery();
		

		ObservableList<Book> searchList= FXCollections.observableArrayList();
		
		while(rs.next()) {
			String title=rs.getString("bookTitle");
			String id=rs.getString("bookId");
			String author=rs.getString("bookAuthor");
			String publisher=rs.getString("bookPublisher");
			String tags=rs.getString("tags");
			
			searchList.add(new Book(title, id,author,publisher,tags));
			
			
		}
		
		
		tableView.getItems().setAll(searchList);
		
		
	}
	
	
	public void loadData() throws InstantiationException, IllegalAccessException, SQLException {
		
		
		String sql="SELECT * from BOOK";
		Connection con=ConnectDB.getConnection();
		PreparedStatement pst=con.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		
		while(rs.next()) {
			String title=rs.getString("bookTitle");
			String id=rs.getString("bookId");
			String author=rs.getString("bookAuthor");
			String publisher=rs.getString("bookPublisher");
			String tags=rs.getString("tags");
			
			list.add(new Book(title, id,author,publisher,tags));
			
			
		}
		
		
		tableView.getItems().setAll(list);
		
		
		
	}
	
	
	public static class Book{
		
		private final SimpleStringProperty title;
		private final SimpleStringProperty id;
		private final SimpleStringProperty author;
		private final SimpleStringProperty publisher;
		private final SimpleStringProperty tags;
		
		public Book(String title,String id,String author,String publisher,String tags) {
			
			this.title=new SimpleStringProperty(title);
			this.id=new SimpleStringProperty(id);
			this.author=new SimpleStringProperty(author);
			this.publisher=new SimpleStringProperty(publisher);
			this.tags=new SimpleStringProperty(tags);
			
			
			
		}

		public String getTitle() {
			return title.get();
		}

		public String getTags() {
			return tags.get();
		}
		
		public String getId() {
			return id.get();
		}

		public String getAuthor() {
			return author.get();
		}

		public String getPublisher() {
			return publisher.get();
		}
		
		
	}
	
	public void checkBookLeftovers(String title) throws InstantiationException, IllegalAccessException, SQLException {
		
		//get Total Count Of Books
		String sql="SELECT count(bookTitle) FROM book WHERE bookTitle='"+title+"'";
		Connection conn=ConnectDB.getConnection();
		PreparedStatement pst=conn.prepareStatement(sql);
		
		ResultSet bookIdResult=pst.executeQuery();
		int bookCount=0;
		while(bookIdResult.next()) {
			
			bookCount=bookIdResult.getInt(1);
			
		}
		
		String sql2="SELECT count(bookId) FROM issuebook WHERE bookId IN (SELECT bookId FROM book WHERE bookTitle='"+title+"')";
		
		PreparedStatement pst2=conn.prepareStatement(sql2);
		
		ResultSet bookIdIssuedResult=pst2.executeQuery();
		int bookIssuedCount=0;
		while(bookIdIssuedResult.next()) {
			
			bookIssuedCount=bookIdIssuedResult.getInt(1);
			
		}
		
		Alert alert=new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Check Book Stock");
		alert.setHeaderText(null);
		alert.setContentText("BOOK NAME: "+title+"\nTotal Books: "+bookCount+"\nBooks Issued: "+bookIssuedCount+"\nBooks Left: "+(bookCount-bookIssuedCount));
		alert.showAndWait();
		return;
		
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		searchChoice.setValue("bookId");
		searchChoice.setItems(choice);
		
		initCol();
		conn=new ConnectDB();
		try {
			loadData();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		tableView.setRowFactory( tv -> {
		    TableRow<Book> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            Book rowData = row.getItem();
		            
		            try {
						checkBookLeftovers(rowData.getTitle());
					} catch (InstantiationException e) {
						
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						
						e.printStackTrace();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
		        }
		    });
		    return row ;
		});
		
	}

}
