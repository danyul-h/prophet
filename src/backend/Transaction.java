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
	
	private static String[] categories = {"Miscellaneous", "Salary", "Bills", "Chores", "Work", "Allowance", "Entertainment", "Dining", "Education", "Insurance", "Health", "Groceries", "Transportation", "Home", "Travel"};
	
	public static String[] getCategories() {
		return categories;
	}
	
	private int id;
	private String username;
	private Date date;
	private BigDecimal value;
	private String category;
	private String details;
	
	public Transaction(String username) {
		this.id = -1;
		this.username = username;
		this.date = Date.valueOf(LocalDate.now());
		this.value = new BigDecimal(0.00);
		this.category = "Miscellaneous";
		this.details = "Transaction";
	}
	
	public Transaction(int id, String username, Date date, BigDecimal value, String category, String details) {
		this.id = id;
		this.username = username;
		this.date = date;
		this.value = value;
		this.category = category;
		this.details = details;
	}
	
	public Transaction(String username, Date date, BigDecimal value, String category, String details) {
		this.id = -1;
		this.username = username;
		this.date = date;
		this.value = value;
		this.category = category;
		this.details = details;
	}
	
	public static double getCategoryExpenses(ArrayList<Transaction> transactions, String category) {
		double cost = 0;
		for (Transaction i : transactions) {
			double value = i.getValue().doubleValue();
			if(value < 0 && i.getCategory().equals(category)) cost += Math.abs(value);
		}
		return cost;
	}
	
	public static double getCategoryIncomes(ArrayList<Transaction> transactions, String category) {
		double income = 0;
		for (Transaction i : transactions) {
			double value = i.getValue().doubleValue();
			if(value>0 && i.getCategory().equals(category)) income += value;
		}
		return income;
	}
	
	public static double getDayValue(ArrayList<Transaction> transactions, java.util.Date date) {
		double value = 0;
		for (Transaction i : transactions) {
			if (i.getDate().compareTo(date) <= 0) value+=i.getValue().doubleValue();
		}
		return value;
	}
	
	public static ArrayList<Transaction> filterDayDistance(ArrayList<Transaction> transactions, int days){
		ArrayList<Transaction> filtered =  new ArrayList<Transaction>();
		Calendar c = Calendar.getInstance();
		c.setTime(Date.valueOf(LocalDate.now()));
		c.add(Calendar.DATE, -days);
		Date compareDate = Date.valueOf(LocalDate.ofInstant(c.getTime().toInstant(), ZoneId.systemDefault()));
		Date today = Date.valueOf(LocalDate.now());
		for (Transaction i : transactions) {
			if (i.getDate().compareTo(compareDate) >= 0 && i.getDate().compareTo(today) <= 0) filtered.add(i);
		}
		return filtered;
	}
	
	public static ArrayList<Transaction> filterDayRange(ArrayList<Transaction> transactions, java.util.Date startDate, java.util.Date endDate){
		ArrayList<Transaction> filtered = new ArrayList<Transaction>();
		for (Transaction i : transactions) {
			if (i.getDate().compareTo(startDate) >= 0 && i.getDate().compareTo(endDate) <= 0) filtered.add(i); 
		}
		return filtered;
	}
	
	public static Object[][] toTable(ArrayList<Transaction> transactions){
		Object[][] table = new Object[transactions.size()][];
		for (int i = 0; i < transactions.size(); i++) {
			table[i] = transactions.get(i).toArray();
		}
		return table;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(category, date, details, id, username, value);
	}

	@Override
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
	
	public Object[] toArray() {
		return new Object[]{id, date, value, category, details, username};
	}
	
	public String toString() {
		return id + " "+ details + ": valued at " + value + " on " + new SimpleDateFormat("EE").format(date) + " " + date;
	}
	
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
