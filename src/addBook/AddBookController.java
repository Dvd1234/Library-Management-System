package addBook;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import dbutils.ConnectDB;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddBookController implements Initializable {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TextField bookTitle;
	@FXML
	private TextField bookId;
	@FXML
	private TextField bookAuthor;
	@FXML
	private TextField bookPublisher;
	
	private ConnectDB connectDB;

	// Event Listener on Button.onAction
	@FXML
	public void addBook(ActionEvent event) throws SQLException, InstantiationException, IllegalAccessException {
		
		String mtitle=bookTitle.getText();
		String mid=bookId.getText();
		String mauthor=bookAuthor.getText();
		String mpublisher=bookPublisher.getText();
		
		if(mtitle.isEmpty()||mid.isEmpty()||mauthor.isEmpty()||mpublisher.isEmpty()) {
			
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Fields must not Be empty");
			alert.showAndWait();
			return;
			
		}
		
		//here we perform Query
		String sql="INSERT INTO BOOK (bookId,bookTitle,bookAuthor,bookPublisher) VALUES (?,?,?,?)";
		Connection conn=ConnectDB.getConnection();
		PreparedStatement pst= conn.prepareStatement(sql);
		
		pst.setString(1, mid);
		pst.setString(2, mtitle);
		pst.setString(3, mauthor);
		pst.setString(4, mpublisher);
		
		pst.execute();
		Alert alert=new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("Book Added Successfully");
		alert.showAndWait();
		Stage stage=(Stage) anchorPane.getScene().getWindow();
		stage.close();
		return;
		
		
		
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void cancelBtn(ActionEvent event) {

		Stage stage=(Stage) anchorPane.getScene().getWindow();
		stage.close();

		
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		connectDB=new ConnectDB();
		
	}
}
