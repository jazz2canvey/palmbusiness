package nutan.tech.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseFuntions {

	public Connection connect2DB() {
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/palm_business","root","");  
			return con;			
		}
			catch(Exception e) { 
				System.out.println(e);
			}  
	
		return null;		
	}
	
	public int closeDBOperations(Connection connection, Statement statement, ResultSet resultSet) {
		
		try {
			statement.close();
			connection.close();
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
}
