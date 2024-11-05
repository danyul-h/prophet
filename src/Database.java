import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class Database {
	private static Connection connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/scholash", "root", "");			
		} catch (Exception e) {
			return null;
		}
	}
	
	public static int login(String username, String password) {
		Connection connection = connect();
		if (connection == null) return 0; // if connection failed
		try {
			PreparedStatement statement = connection.prepareStatement("select * from users where username=? and password=?");
			statement.setString(1, username);
			statement.setString(2, password);
			if (statement.executeQuery().next()) return 1; // if user found
			return 2; // if user not found
		} catch(Exception e) {
			e.printStackTrace();
			return 0; // if mysql failed, assume user not found
		}
	}
	
	public static int signup(String username, String password) {
		Connection connection = connect();
		if (connection == null) return 0; // if connection failed
		try {
			PreparedStatement statement = connection.prepareStatement("insert into users(username, password) value(?, ?)");
			statement.setString(1, username);
			statement.setString(2, password);
			statement.executeUpdate();
			return 1; // if sign up succeeds
		} catch (Exception e) {
			if (e.toString().toLowerCase().contains("duplicate")) return 2; // if username alr exists...
			return 3; // if user/password dont meet requirements
		}
	}
}
