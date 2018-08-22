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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import settings.Settings;

public class MainController implements Initializable {

	@FXML
	private TextField memberInputId;
	
	@FXML
	private Label memberName;
	
	@FXML
	private Label memberContactNumber;
	
	@FXML
	private Label booksBorrowed;
	

	private String memberType;
	
	@FXML
	private TextField bookInputId;
	
	@FXML
	private TextField bookSubmitId;
	
	
	@FXML
	private Label bookName;
	
	@FXML
	private Label bookAuthor;
	
	@FXML
	private ListView<String> listView;
	
	private ConnectDB connectDb;
	
	Settings set;
	boolean bookflag=false;
	boolean memberflag=false;
	boolean submitflag=false;
	
	
	String submitRequiredMemId="";
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		connectDb=new ConnectDB();
		set=new Settings();
		
		
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
			memberType=rs.getString("type");
			
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
	public void loadSettings(ActionEvent event) throws IOException {
		
		loadWindow("/settings/Settings.fxml","Settings","/img/5edaf57721d0a36f313bb84bec6c7a68-screwdriver.png");
		
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
		
		int maxBook=2;
		
		System.out.println(memberType);
		if(memberType.equalsIgnoreCase("student")) {
			maxBook=set.getMaxBooksStudent();
		}
		else if(memberType.equalsIgnoreCase("faculty")) {
			maxBook=set.getMaxBookFaculty();
		}
		
		if(Integer.parseInt(borrowed)>=maxBook) {
			
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
	
	
	
	
	public void submitBook(ActionEvent event) throws InstantiationException, IllegalAccessException, SQLException {
		
		if(!submitflag) {
			
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("The Book has not been Issued. Please reCheck");
			alert.showAndWait();
			return;
			
		}
		String id=bookSubmitId.getText();
		String sql="DELETE FROM issueBook WHERE bookId='"+id+"'";
		Connection conn=ConnectDB.getConnection();
		PreparedStatement pst=conn.prepareStatement(sql);
		pst.execute();
		Alert alert=new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("The Book with BookId "+bookSubmitId.getText()+" has been Submitted");
		alert.showAndWait();
		
		PreparedStatement pst2=conn.prepareStatement("UPDATE MEMBER SET booksBorrowed=booksBorrowed-1 WHERE memberId='"+submitRequiredMemId+"'");
		pst2.execute();
		
		
		bookSubmitId.setText("");
		submitflag=false;
		listView.getItems().clear();
		
		
	}
	
	
	
	
	public void loadInfo(ActionEvent event) throws InstantiationException, IllegalAccessException, SQLException {
		
		ObservableList<String> issueData=FXCollections.observableArrayList();
		
		String id=bookSubmitId.getText();
		String sql="SELECT * FROM issueBook WHERE bookId='"+id+"'";
		Connection conn=ConnectDB.getConnection();
		PreparedStatement pst=conn.prepareStatement(sql);
		ResultSet rst=pst.executeQuery();
		
		submitflag=false;
		
		while(rst.next()) {
			
			String bookId=id;
			String memId=rst.getString("memberId");
			String issueTime=rst.getString("timeStamp");
			
			submitRequiredMemId=memId;
			
			issueData.add("Issued Information:");
			issueData.add("Issue Date and Time : "+issueTime);
			
			submitflag=true;
			
			String sql1="SELECT * FROM book WHERE bookId='"+id+"'";
			
			PreparedStatement pst1=conn.prepareStatement(sql1);
			ResultSet rst1=pst1.executeQuery();
			
			issueData.add("");
			while(rst1.next()) {
				
				issueData.add("Book Name: "+rst1.getString("bookTitle"));
				issueData.add("Author Name: "+rst1.getString("bookAuthor"));
				issueData.add("Publisher Name: "+rst1.getString("bookPublisher"));
				
				
			}
			
			String sql2="SELECT * FROM member WHERE memberId='"+memId+"'";
			
			PreparedStatement pst2=conn.prepareStatement(sql2);
			ResultSet rst2=pst2.executeQuery();
			issueData.add("");
			issueData.add("Issued To:");
			while(rst2.next()) {
				
				issueData.add("Member Name: "+rst2.getString("Name"));
				issueData.add("Member Contact Number: "+rst2.getString("contactNumber"));
				issueData.add("Type: "+rst2.getString("type"));
				issueData.add("Department: "+rst2.getString("department"));
				
				
			}
			
			
			
			
		}
		
		if(!submitflag){
			issueData.add("No Book Of This Id Issued");
			issueData.add("Please Recheck the Id");
		}
		
		listView.getItems().setAll(issueData);		
		
		
		
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
