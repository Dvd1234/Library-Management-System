package dbutils;

import java.sql.*;

public class ConnectDB {
 
	 public static Connection getConnection() {
		 try {
			Class.forName("org.sqlite.JDBC");
			Connection conn=DriverManager.getConnection("jdbc:sqlite:""");		//Begin From Here
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
}
