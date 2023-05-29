package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
private static Connection connection;
public static Connection dbconnect() {
	try {
		 Class.forName("com.mysql.cj.jdbc.Driver");
		   connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root","");
	} catch (Exception e) {
		System.out.println(e);
	}
	return connection;
}
public Connection dis() {
	try {
		connection.close();
	} catch (SQLException e) {
		System.out.println(e);
		e.printStackTrace();
	}
	return connection;
	
}
}
