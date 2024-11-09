package app;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Blob;

public class Transaction {
	private int id;
	private String username;
	private Date date;
	private BigDecimal value;
	private String category;
	private String details;
	private String description;
	private Blob image;
	
	public Transaction(int id, String username, Date date, BigDecimal value, String category, String details) {
		this.id = id;
		this.username = username;
		this.date = date;
		this.value = value;
		this.category = category;
		this.details = details;
	}
	
	public Transaction(String username, Date date, BigDecimal value, String category, String details) {
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
	
	public Object[] toArray() {
		return new Object[]{date, value, category, details, description, image, id, username};
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}	
}
