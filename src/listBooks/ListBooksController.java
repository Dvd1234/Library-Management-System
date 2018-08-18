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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListBooksController implements Initializable{
	
	@FXML
	private TableView<Book> tableView;
	
	@FXML
	private TableColumn<Book,String> idCol;
	@FXML
	private TableColumn<Book,String> titleCol;
	@FXML
	private TableColumn<Book,String> authorCol;
	@FXML
	private TableColumn<Book,String> publisherCol;
	
	
	
	ObservableList<Book> list= FXCollections.observableArrayList();
	
	private ConnectDB conn;
	
	
	
	
	public void initCol() {
		
		
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));		
		authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
		publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
		
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
			
			
			list.add(new Book(title, id,author,publisher));
			
			
		}
		
		
		tableView.getItems().setAll(list);
		
		
		
	}
	
	
	public static class Book{
		
		private final SimpleStringProperty title;
		private final SimpleStringProperty id;
		private final SimpleStringProperty author;
		private final SimpleStringProperty publisher;
		
		public Book(String title,String id,String author,String publisher) {
			
			this.title=new SimpleStringProperty(title);
			this.id=new SimpleStringProperty(id);
			this.author=new SimpleStringProperty(author);
			this.publisher=new SimpleStringProperty(publisher);
			
			
			
		}

		public String getTitle() {
			return title.get();
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
		initCol();
		conn=new ConnectDB();
		try {
			loadData();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
