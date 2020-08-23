package ordersystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection 
{
	public Connection connectionCreation()
	{
		Connection conn = null;
		try {
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
		    conn = DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:orcl", "hr", "hr");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
