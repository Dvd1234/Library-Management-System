package settings;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsController {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Button saveBtn;
	
	@FXML
	private TextField studentMax;

	@FXML
	private TextField facultyMax;
	
	
	// Event Listener on Button[#saveBtn].onAction
	@FXML
	public void saveSettings(ActionEvent event) {
		if(studentMax.equals("")||facultyMax.equals("")) {
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Fields must not Be empty. Please fill BOTH fields.");
			alert.showAndWait();
			return;
		}
		
		Settings set=new Settings();
		set.setMaxBooksStudent(Integer.parseInt(studentMax.getText()));
		set.setMaxBookFaculty(Integer.parseInt(facultyMax.getText()));
		
		Alert alert=new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("Settings Changed Succesfully.");
		alert.showAndWait();
		Stage stage=(Stage) anchorPane.getScene().getWindow();
		stage.close();
		return;
		
		
		
	}
	
	
	// Event Listener on Button.onAction
	@FXML
	public void cancelSettings(ActionEvent event) {
		
		Stage stage=(Stage) anchorPane.getScene().getWindow();
		stage.close();
	}
}
