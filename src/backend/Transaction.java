package backend;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Transaction {
	private int id;
	private String username;
	private Date date;
	private BigDecimal value;
	private String category;
	private String details;
	
	public Transaction(String username) {
		this.id = -1;
		this.username = username;
		this.date = null;
		this.value = BigDecimal.valueOf(0);
		this.category = null;
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
	
	public static Object[][] toTable(ArrayList<Transaction> transactions){
		Object[][] table = new Object[transactions.size()][];
		for (int i = 0; i < transactions.size(); i++) {
			table[i] = transactions.get(i).toArray();
		}
		return table;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Transaction)) return false;
		return ((Transaction) obj).getId() == id;
	}
	
	public Object[] toArray() {
		return new Object[]{id, date, value, category, details, username};
	}
	
	public String toString() {
		return details + ": valued at " + value + " on " + new SimpleDateFormat("EE").format(date) + " " + date;
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
