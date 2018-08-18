package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
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
