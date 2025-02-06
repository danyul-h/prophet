package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Database {

	// status codes to return instead of ints, easier to rememeber their meaning
	public enum Status {
		UNAVAILABLE, // if mysql connection error / missed exception occurs
		SUCCESSFUL, // if action was successful
		INVALID, // if input was invalid
		DUPLICATE, // if action attempted to add a duplicate username, etc.
	}

	public static Properties getProps() {
		// initialize properties
		Properties props = new Properties();
		// load .env path
		var envFile = Paths.get(".env");
		try {
			// load data from .env into props
			props.load(Files.newInputStream(envFile));
			return props; // return once done
		} catch (Exception e) {
			return null; // return null if error occurred
		}
	}

	public static Transaction getTransaction(int id) {
		// getting properties from .env file
		Properties props = getProps();
		if (props == null)
			return null; // if getting props didn't work, return null
		Connection connection; // initializing connection
		try {
			// finds the class to connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			// connects to the database described by the properties in the .env file
			connection = DriverManager.getConnection((String) props.get("DB_URL"), (String) props.get("DB_USER"),
					(String) props.get("DB_PASSWORD"));
		} catch (Exception e) {
			// if connection didn't work, return null
			return null;
		}
		try {
			// look for transactions in the database based on its id
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions WHERE id=?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				String username = rs.getString("username");
				Date date = rs.getDate("date");
				BigDecimal value = rs.getBigDecimal("value");
				String category = rs.getString("category");
				String title = rs.getString("details");
				connection.close();
				return new Transaction(id, username, date, value, category, title);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Transaction> getTransactions(String username) {
		// getting properties from .env file
		Properties props = getProps();
		if (props == null)
			return null; // if getting props didn't work, return null
		Connection connection; // initializing connection
		try {
			// finds the class to connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			// connects to the database described by the properties in the .env file
			connection = DriverManager.getConnection((String) props.get("DB_URL"), (String) props.get("DB_USER"),
					(String) props.get("DB_PASSWORD"));
		} catch (Exception e) {
			// if connection didn't work, return null
			return null;
		}
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		if (connection == null)
			return null;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM transactions WHERE username=?");
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				Date date = rs.getDate("date");
				BigDecimal value = rs.getBigDecimal("value");
				String category = rs.getString("category");
				String title = rs.getString("details");
				transactions.add(new Transaction(id, username, date, value, category, title));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public static int addTransaction(Transaction transaction) {
		// getting properties from .env file
		Properties props = getProps();
		if (props == null)
			return -1; // if getting props didn't work, return -1
		Connection connection; // initializing connection
		try {
			// finds the class to connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			// connects to the database described by the properties in the .env file
			connection = DriverManager.getConnection((String) props.get("DB_URL"), (String) props.get("DB_USER"),
					(String) props.get("DB_PASSWORD"));
		} catch (Exception e) {
			// if connection didn't work, return -1
			return -1;
		}
		try {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO transactions(username, date, value, category, details) values(?, ?, ?, ?, ?)");
			statement.setString(1, transaction.getUsername());
			statement.setDate(2, transaction.getDate());
			statement.setBigDecimal(3, transaction.getValue());
			statement.setString(4, transaction.getCategory());
			statement.setString(5, transaction.getDetails());
			statement.executeUpdate();
			statement = connection.prepareStatement("SELECT LAST_INSERT_ID() FROM transactions");
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // assume connection failure
	}

	public static Status editTransaction(Transaction transaction) {
		// getting properties from .env file
		Properties props = getProps();
		if (props == null)
			return Status.UNAVAILABLE; // if getting props didn't work, return unavailable status
		Connection connection; // initializing connection
		try {
			// finds the class to connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			// connects to the database described by the properties in the .env file
			connection = DriverManager.getConnection((String) props.get("DB_URL"), (String) props.get("DB_USER"),
					(String) props.get("DB_PASSWORD"));
		} catch (Exception e) {
			// if connection didn't work, return unavailable
			return Status.UNAVAILABLE;
		}
		try {
			PreparedStatement statement = connection
					.prepareStatement("UPDATE transactions SET date=?, value=?, category=?, details=? WHERE id=?");
			statement.setDate(1, transaction.getDate());
			statement.setBigDecimal(2, transaction.getValue());
			statement.setString(3, transaction.getCategory());
			statement.setString(4, transaction.getDetails());
			statement.setInt(5, transaction.getId());
			statement.executeUpdate();
			return Status.SUCCESSFUL; // success
		} catch (SQLException e) {
			return Status.INVALID; // fields exceeded
		} catch (Exception e) {
			e.printStackTrace();
			return Status.UNAVAILABLE; // assume connection failure
		}
	}

	public static Status deleteTransaction(int id) {
		// getting properties from .env file
		Properties props = getProps();
		if (props == null)
			return Status.UNAVAILABLE; // if getting props didn't work, return null
		Connection connection; // initializing connection
		try {
			// finds the class to connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			// connects to the database described by the properties in the .env file
			connection = DriverManager.getConnection((String) props.get("DB_URL"), (String) props.get("DB_USER"),
					(String) props.get("DB_PASSWORD"));
		} catch (Exception e) {
			// if connection didn't work, return null
			return Status.UNAVAILABLE;
		}
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM transactions WHERE id=?");
			statement.setInt(1, id);
			statement.executeUpdate();
			return Status.SUCCESSFUL;
		} catch (Exception e) {
			e.printStackTrace();
			return Status.UNAVAILABLE;
		}
	}

	// signs up with given username and password, essentially inserts a row into the
	// users table in prophet database in mysql
	public static Status signup(String username, String password) {
		// getting properties from .env file
		Properties props = getProps();
		if (props == null)
			return Status.UNAVAILABLE; // if getting props didn't work, return unavailable
		Connection connection; // initializing connection
		try {
			// finds the class to connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			// connects to the database described by the properties in the .env file
			connection = DriverManager.getConnection((String) props.get("DB_URL"), (String) props.get("DB_USER"),
					(String) props.get("DB_PASSWORD"));
		} catch (Exception e) {
			// if connection didn't work, return unavailable
			return Status.UNAVAILABLE;
		}
		try {
			/*
			 * this statement says... insert a row into the users table with a value
			 * describing the username and password we have to say
			 * "users(username, password)" to specify vars note that "users" is equal to
			 * "users(id, username, password)" id is auto generated by mysql
			 */
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO users(username, password) VALUES(?, ?)");
			statement.setString(1, username);
			statement.setString(2, password);
			statement.executeUpdate();
			connection.close(); // close connection after execution
			return Status.SUCCESSFUL; // if sign up succeeds
		} catch (SQLIntegrityConstraintViolationException e) {
			return Status.DUPLICATE; // if username alr exists
		} catch (SQLException e) {
			return Status.INVALID; // if username doesn't meet constraints, return invalid
		} catch (Exception e) {
			e.printStackTrace();
			return Status.UNAVAILABLE; // catch all other exceptions, return unavailable
		}
	}

	// log in, verifies login with provided username and password by comparing it to
	// rows in the users table in prophet database in mysql
	public static Status login(String username, String password) {
		// getting properties from .env file
		Properties props = getProps();
		if (props == null)
			return Status.UNAVAILABLE; // if getting props didn't work, return unavailable
		Connection connection; // initializing connection
		try {
			// finds the class to connect to the database
			Class.forName("com.mysql.cj.jdbc.Driver");
			// connects to the database described by the properties in the .env file
			connection = DriverManager.getConnection((String) props.get("DB_URL"), (String) props.get("DB_USER"),
					(String) props.get("DB_PASSWORD"));
		} catch (Exception e) {
			// if connection didn't work, return unavailable
			return Status.UNAVAILABLE;
		}
		// try to login
		try {
			// prepare a statement to execute in mysql, looking for a user based off of
			// username and password
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
			statement.setString(1, username); // input the user's username
			statement.setString(2, password); // input the user's password
			// now we execute the statement
			boolean foundUser = statement.executeQuery().next();
			// close connection after execution
			connection.close();
			if (foundUser) {
				// if the statement found results, close the connection and return successful
				// status
				return Status.SUCCESSFUL;
			}
			return Status.INVALID; // if user not found, return invalid status
		} catch (Exception e) {
			e.printStackTrace();
			return Status.UNAVAILABLE; // if another error occurred, connection failed and return unavailable
		}
	}

}
