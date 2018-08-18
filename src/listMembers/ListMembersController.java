package listMembers;

import java.net.URL;
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

public class ListMembersController implements Initializable{
	@FXML
	private TableColumn<Member,String> memberId;
	@FXML
	private TableColumn<Member,String> name;
	@FXML
	private TableColumn<Member,String> contactNumber;
	@FXML
	private TableColumn<Member,String> email;
	@FXML
	private TableColumn<Member,String> booksBorrowed;
	@FXML
	private TableColumn<Member,String> type;
	@FXML
	private TableColumn<Member,String> department;
	
	
	@FXML
	private TableView<Member> tableView;
	
	
	
	
	ObservableList<Member> list= FXCollections.observableArrayList();
	
	private ConnectDB conn;
	
	
	
	
	public void initCol() {
		
		System.out.println("Inside this");
		memberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));		
		contactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		booksBorrowed.setCellValueFactory(new PropertyValueFactory<>("booksBorrowed"));		
		type.setCellValueFactory(new PropertyValueFactory<>("type"));
		department.setCellValueFactory(new PropertyValueFactory<>("department"));
		
	}
	
	
public void loadData() throws InstantiationException, IllegalAccessException, SQLException {
		
	
		String sql="SELECT * from MEMBER";
		Connection con=ConnectDB.getConnection();
		PreparedStatement pst=con.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		
		while(rs.next()) {
			String memberId=rs.getString("memberId");
			String name=rs.getString("name");
			String contactNumber=rs.getString("contactNumber");
			String email=rs.getString("email");
			String booksBorrowed=rs.getString("booksBorrowed");
			String type=rs.getString("type");
			String department=rs.getString("department");
			
			
			list.add(new Member(memberId,name,contactNumber,email,booksBorrowed,type,department));
			
			
		}
		
		
		tableView.getItems().setAll(list);
		
		
		
	}
	

		public static class Member{
			
			private final SimpleStringProperty memberId;
			private final SimpleStringProperty name;
			private final SimpleStringProperty contactNumber;
			private final SimpleStringProperty email;
			private final SimpleStringProperty booksBorrowed;
			private final SimpleStringProperty type;
			private final SimpleStringProperty department;
			
			public Member(String memberId,String name,String contactNumber,String email,String booksBorrowed,String type, String department) {
				
				
				this.memberId=new SimpleStringProperty(memberId);
				this.name=new SimpleStringProperty(name);
				this.contactNumber=new SimpleStringProperty(contactNumber);
				this.email=new SimpleStringProperty(email);
				this.booksBorrowed=new SimpleStringProperty(booksBorrowed);
				this.type=new SimpleStringProperty(type);
				this.department=new SimpleStringProperty(department);
				
				
			}
			
			
			public String getMemberId() {
				return memberId.get();
			}



			public String getName() {
				return name.get();
			}



			public String getContactNumber() {
				return contactNumber.get();
			}



			public String getEmail() {
				return email.get();
			}



			public String getBooksBorrowed() {
				return booksBorrowed.get();
			}



			public String getType() {
				return type.get();
			}



			public String getDepartment() {
				return department.get();
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
