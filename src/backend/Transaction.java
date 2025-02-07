package backend;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class Transaction {

	//setting up the available categories for a transaction
	private static String[] categories = { 
			"Miscellaneous", 
			"Salary", 
			"Bills", 
			"Chores", 
			"Work", 
			"Allowance",
			"Entertainment", 
			"Dining", 
			"Education", 
			"Insurance", 
			"Health", 
			"Groceries", 
			"Transportation", 
			"Home",
			"Travel" };

	//get categories
	public static String[] getCategories() {
		return categories;
	}

	//fields of a transaction object (the input date)
	private int id;
	private String username;
	private Date date;
	private BigDecimal value;
	private String category;
	private String details;

	//constructing a transaction with username, using default values
	public Transaction(String username) {
		this.id = -1;
		this.username = username;
		this.date = Date.valueOf(LocalDate.now());
		this.value = new BigDecimal(0.00);
		this.category = "Miscellaneous";
		this.details = "Transaction";
	}

	//constructing a transaction with id and all the other input data
	public Transaction(int id, String username, Date date, BigDecimal value, String category, String details) {
		this.id = id;
		this.username = username;
		this.date = date;
		this.value = value;
		this.category = category;
		this.details = details;
	}

	//constructing a transaction with default id of -1 and all other input data
	public Transaction(String username, Date date, BigDecimal value, String category, String details) {
		this.id = -1;
		this.username = username;
		this.date = date;
		this.value = value;
		this.category = category;
		this.details = details;
	}

	//sort transactions based on date
	public static void sortTransactions(ArrayList<Transaction> transactions) {
		boolean swapped;
		for (int i = 0; i < transactions.size() - 1; i++) {
			swapped = false;
			for (int j = 0; j < transactions.size() - 1; j++) {
				if (transactions.get(j).getDate().compareTo(transactions.get(j + 1).getDate()) > 0) {
					Transaction temp = transactions.set(j, transactions.get(j + 1));
					transactions.set(j + 1, temp);
					swapped = true;
				}
			}
		}
	}

	//get all the total expenses of a list of transactions
	public static double getExpenses(ArrayList<Transaction> transactions) {
		double cost = 0;
		for (Transaction i : transactions) {
			double value = i.getValue().doubleValue();
			if (value < 0)
				cost += Math.abs(value);
		}
		return cost;
	}

	//get all the total incomes of a list of transactions
	public static double getIncomes(ArrayList<Transaction> transactions) {
		double income = 0;
		for (Transaction i : transactions) {
			double value = i.getValue().doubleValue();
			if (value > 0)
				income += value;
		}
		return income;
	}

	//get all the total expenses of a list of transactions under a specific category
	public static double getCategoryExpenses(ArrayList<Transaction> transactions, String category) {
		double cost = 0;
		for (Transaction i : transactions) {
			double value = i.getValue().doubleValue();
			if (value < 0 && i.getCategory().equals(category))
				cost += Math.abs(value);
		}
		return cost;
	}

	//get all the total incomes of a list of transactions under a specific category
	public static double getCategoryIncomes(ArrayList<Transaction> transactions, String category) {
		double income = 0;
		for (Transaction i : transactions) {
			double value = i.getValue().doubleValue();
			if (value > 0 && i.getCategory().equals(category))
				income += value;
		}
		return income;
	}

	//get the latest transaction
	public static Transaction getLatest(ArrayList<Transaction> transactions) {
		Date date = null;
		Transaction transaction = null;
		for (Transaction i : transactions) {
			if (date == null || date.compareTo(i.getDate()) < 0) {
				date = i.getDate();
				transaction = i;
			}
		}
		return transaction;
	}

	//get the oldest transaction
	public static Transaction getOldest(ArrayList<Transaction> transactions) {
		Date date = null;
		Transaction transaction = null;
		for (Transaction i : transactions) {
			if (date == null || date.compareTo(i.getDate()) > 0) {
				date = i.getDate();
				transaction = i;
			}
		}
		return transaction;
	}

	//get the total balance on a certain day
	public static double getDayValue(ArrayList<Transaction> transactions, LocalDate d) {
		java.util.Date date = Date.valueOf(d);
		double value = 0;
		for (Transaction i : transactions) {
			if (i.getDate().compareTo(date) <= 0)
				value += i.getValue().doubleValue();
		}
		return value;
	}

	//get the list of transaction filtered starting from today to the past x amount of days
	public static ArrayList<Transaction> filterDayDistance(ArrayList<Transaction> transactions, int days) {
		ArrayList<Transaction> filtered = new ArrayList<Transaction>();
		Calendar c = Calendar.getInstance();
		c.setTime(Date.valueOf(LocalDate.now()));
		c.add(Calendar.DATE, -days);
		Date compareDate = Date.valueOf(LocalDate.ofInstant(c.getTime().toInstant(), ZoneId.systemDefault()));
		Date today = Date.valueOf(LocalDate.now());
		for (Transaction i : transactions) {
			if (i.getDate().compareTo(compareDate) >= 0 && i.getDate().compareTo(today) <= 0)
				filtered.add(i);
		}
		return filtered;
	}

	//get the list of transactions filtered based on a date range
	public static ArrayList<Transaction> filterDayRange(ArrayList<Transaction> transactions, LocalDate startDate,
			LocalDate endDate) {
		ArrayList<Transaction> filtered = new ArrayList<Transaction>();
		java.util.Date start = Date.valueOf(startDate);
		java.util.Date end = Date.valueOf(endDate);
		for (Transaction i : transactions) {
			if (i.getDate().compareTo(start) >= 0 && i.getDate().compareTo(end) <= 0)
				filtered.add(i);
		}
		return filtered;
	}

	//turn the transactions into a 2d array, or a table
	public static Object[][] toTable(ArrayList<Transaction> transactions) {
		Object[][] table = new Object[transactions.size()][];
		for (int i = 0; i < transactions.size(); i++) {
			table[i] = transactions.get(i).toArray();
		}
		return table;
	}

	@Override
	//get a hashcode of the transaction
	public int hashCode() {
		return Objects.hash(category, date, details, id, username, value);
	}

	@Override
	//this function specifies how to compare transaction objects
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Transaction)) {
			return false;
		}
		Transaction other = (Transaction) obj;
		return Objects.equals(category, other.category) && Objects.equals(date, other.date)
				&& Objects.equals(details, other.details) && id == other.id && Objects.equals(username, other.username)
				&& Objects.equals(value, other.value);
	}

	//turn a transaction into an array
	public Object[] toArray() {
		return new Object[] { id, date, value, category, details, username };
	}

	//turn a transaction into a string, how they get printed out
	public String toString() {
		return id + " " + details + ": valued at " + value + " on " + new SimpleDateFormat("EE").format(date) + " "
				+ date;
	}

	//setters and getters for the fields below
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String title) {
		this.details = title;
	}
}
