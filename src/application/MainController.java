package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import dbutils.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {

	@FXML
	private TextField memberInputId;
	
	@FXML
	private Label memberName;
	
	@FXML
	private Label memberContactNumber;
	
	@FXML
	private Label booksBorrowed;
	
	@FXML
	private TextField bookInputId;
	
	@FXML
	private Label bookName;
	
	@FXML
	private Label bookAuthor;
	
	private ConnectDB connectDb;
	
	
	boolean bookflag=false;
	boolean memberflag=false;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		connectDb=new ConnectDB();
		
		
	}
	
	@FXML
	public void bookIdEnter(ActionEvent event) throws InstantiationException, IllegalAccessException, SQLException {
		
		String id=bookInputId.getText();
		String sql="SELECT * FROM BOOK WHERE bookId= '"+id+"'";
		Connection conn=ConnectDB.getConnection();
		PreparedStatement pst=conn.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		
		bookflag=false;
		while(rs.next()) {
			String bname=rs.getString("bookTitle");
			String aname=rs.getString("bookAuthor");
			
			bookName.setText(bname);
			bookAuthor.setText(aname);
			bookflag=true;
			
		}
		
		if(!bookflag) {
			bookName.setText("No Such Book Available");
			bookAuthor.setText("No Such Author");
		}
		
	}
	
	@FXML
	public void memberIdEnter(ActionEvent event) throws InstantiationException, IllegalAccessException, SQLException {
		
		String id=memberInputId.getText();
		String sql="SELECT * FROM MEMBER WHERE memberId= '"+id+"'";
		Connection conn=ConnectDB.getConnection();
		PreparedStatement pst=conn.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		
		memberflag=false;
		while(rs.next()) {
			String bname=rs.getString("name");
			String aname=rs.getString("contactNumber");
			String bBorrowed=rs.getString("booksBorrowed");
			
			memberName.setText(bname);
			memberContactNumber.setText(aname);
			booksBorrowed.setText(bBorrowed);
			memberflag=true;
			
		}
		
		if(!memberflag) {
			memberName.setText("No Such Book Available");
			memberContactNumber.setText("No Such Author");
			booksBorrowed.setText("");
		}
		
	}
	
	
	
	@FXML
	public void addBook(ActionEvent event) throws IOException {
		
		loadWindow("/addBook/AddBook.fxml","Add Book","/img/add-book.png");
	}
	
	
	@FXML
	public void addMember(ActionEvent event) throws IOException {
		
		loadWindow("/addMember/AddMember.fxml","Add Member","/img/user-add-icon---shine-set-add-new-user-add-user-30.png");
	
	}
	
	
	@FXML
	public void listBooks(ActionEvent event) throws IOException {
		
		loadWindow("/listBooks/listBooks.fxml","Book List","/img/search_book-512.png");
	
	}
	
	
	@FXML
	public void listMembers(ActionEvent event) throws IOException {
		
		loadWindow("/listMembers/ListMembers.fxml","Book List","/img/image_1_orig.png");
	
	}
	
	
	@FXML
	public void issueBook(ActionEvent event) throws InstantiationException, IllegalAccessException, SQLException {
		
		String memberId=memberInputId.getText();
		String bookId=bookInputId.getText();
		String borrowed=booksBorrowed.getText();
		
		if(!bookflag||!memberflag) {
			
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Either Book Id or Member Id is Invalid. Please ReCheck.");
			alert.showAndWait();
			return;
			
		}
		
		if(Integer.parseInt(borrowed)==2) {
			
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("The Member Exceeds the Max Book Borrowing Limit.");
			alert.showAndWait();
			return;
			
			
		}
		
		
		Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Book Issue Confirmation");
		alert.setHeaderText(null);
		alert.setContentText("Are You Sure You want to issue\nBook: "+bookName.getText()+" To Member: "+memberName.getText());
		Optional<ButtonType> response=alert.showAndWait();
		
		
		if(response.get()==ButtonType.OK) {
			
			String sql="INSERT INTO issueBook (bookId,memberId) Values (?,?)";
			Connection conn=ConnectDB.getConnection();
			PreparedStatement pst=conn.prepareStatement(sql);
			
			pst.setString(1, bookId);
			pst.setString(2, memberId);
			
			pst.execute();
			
			
			memberInputId.setText("");
			bookInputId.setText("");
			bookName.setText("Book Name");
			memberName.setText("Member Name");
			bookAuthor.setText("Book Author");
			booksBorrowed.setText("Books Borrowed");
			memberContactNumber.setText("Member Contact Number");
			
			bookflag=false;
			memberflag=false;
			
			Alert alert1=new Alert(Alert.AlertType.INFORMATION);
			alert1.setTitle("Book Issue Success");
			alert1.setHeaderText(null);
			alert1.setContentText("Book Issued Successfully");
			alert1.showAndWait();
			
			String sql1="UPDATE member SET booksBorrowed=(?) WHERE memberID=(?)";
			PreparedStatement pst1=conn.prepareStatement(sql1);
			
			pst1.setString(1, String.valueOf(Integer.parseInt(borrowed)+1));
			pst1.setString(2, memberId);
			
			pst1.execute();
			
			
			
			
			
			
		}
		
		
		
		
		
	}
	
	
	
	
	
	public void loadWindow(String location, String title,String iconPath) throws IOException {
		Parent root=FXMLLoader.load(Main.class.getResource(location));
		Stage stage=new Stage(StageStyle.DECORATED);
		stage.setTitle(title);
		Image icon=new Image(Main.class.getResourceAsStream(iconPath));
		stage.getIcons().add(icon);
		stage.setScene(new Scene(root));
		stage.show();
		
	}

	
	
}
