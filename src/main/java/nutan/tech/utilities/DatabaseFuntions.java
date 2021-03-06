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
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/palm_business","nutan","tech");
			//return DriverManager.getConnection("jdbc:mysql://localhost:3306/palm_business","","");
		}
			catch(Exception e) {
				e.printStackTrace();
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
