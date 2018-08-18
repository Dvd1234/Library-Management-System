package addMember;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dbutils.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.RadioButton;

public class AddMemberController implements Initializable{
	
	
	
	ObservableList<String> dept= FXCollections.observableArrayList("Bio-Technology","Chemical Engineering","Chemistry","Civil","Computer Science And Engineering","Electrical","Electronics And Communication","Humanities and Management","Industrial &amp; Production Engg.","Information & Technology","Instrumentation &amp; Control Engg.","Mathematics","Mechanical Engineering","Mining","Physics","Textile","Central Facilities");
	
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private TextField memberId;
	@FXML
	private TextField memberName;
	@FXML
	private TextField memberContact;
	@FXML
	private TextField memberEmail;
	@FXML
	private RadioButton memberTypeStudent;
	@FXML
	private ToggleGroup memberType;
	@FXML
	private RadioButton memberTypeFaculty;
	@FXML
	private Button saveBtn;
	@FXML
	private Button cancelBtn;

	
	@FXML
	private ChoiceBox<String> department;
	
	// Event Listener on Button[#saveBtn].onAction
	@FXML
	public void addMember(ActionEvent event) throws SQLException, InstantiationException, IllegalAccessException {
		
		String memId=memberId.getText();
		String memName=memberName.getText();
		String memContact=memberContact.getText();
		String memEmail=memberEmail.getText();
		RadioButton selectedButton=(RadioButton) memberType.getSelectedToggle();
		String memType=selectedButton.getText();
		String dept=department.getValue().toString();
		
		if(memId.isEmpty()||memName.isEmpty()||memContact.isEmpty()||memEmail.isEmpty()||memType.isEmpty()||dept.isEmpty()) {
			
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Fields must not Be empty");
			alert.showAndWait();
			return;
			
		}
		
		String sql="INSERT INTO MEMBER (memberId,name,contactNumber,email,booksBorrowed,type,department) VALUES (?,?,?,?,?,?,?)";
		Connection conn=ConnectDB.getConnection();
		PreparedStatement pst= conn.prepareStatement(sql);
		
		pst.setString(1, memId);
		pst.setString(2, memName);
		pst.setString(3, memContact);
		pst.setString(4, memEmail);
		pst.setString(5, String.valueOf(0));
		pst.setString(6, memType);
		pst.setString(7, dept);
		
		pst.execute();
		Alert alert=new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("Member Added Successfully");
		alert.showAndWait();
		Stage stage=(Stage) anchorPane.getScene().getWindow();
		stage.close();
		return;
		
		
	}
	
	
	// Event Listener on Button[#cancelBtn].onAction
	@FXML
	public void cancelBtn(ActionEvent event) {
		
		Stage stage=(Stage) anchorPane.getScene().getWindow();
		stage.close();

		
	}
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		department.setValue("Bio-Technology");
		department.setItems(dept);
		
	}
}
