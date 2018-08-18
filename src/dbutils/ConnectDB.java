package dbutils;

import java.sql.*;

public class ConnectDB {
 
	 @SuppressWarnings("deprecation")
	public static Connection getConnection() throws SQLException, InstantiationException, IllegalAccessException {

		 
		 String url = "jdbc:mysql://localhost:3306/";

	        /**
	         * The MySQL user.
	         */
	        String user = "root";

	        /**
	         * Password for the above MySQL user. If no password has been 
	         * set (as is the default for the root user in XAMPP's MySQL),
	         * an empty string can be used.
	         */
	        String password = "";
		 
		 
		 try {
			//Class.forName("com.mysql.jdbc.Driver");
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stt = con.createStatement();
    		stt.execute("USE libraryManagementSystem");
			//Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1//libraryManagementSystem");	//may change to localhost
			return con;//Begin From Here
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return null;
	 }
}
