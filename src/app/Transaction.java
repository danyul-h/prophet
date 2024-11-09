package app;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.sql.Blob;

public class Transaction {
	private int id;
	private String username;
	private Date date;
	private BigDecimal value;
	private String category;
	private String title;
	private String description;
	private Blob image;
	
	public Transaction(int id, String username, Date date, BigDecimal value, String category, String title, String description, Blob image) {
		this.id = id;
		this.username = username;
		this.date = date;
		this.value = value;
		this.category = category;
		this.title = title;
		this.description = description;
		this.image = image;
	}
	
	public Transaction(String username, Date date, BigDecimal value, String category, String title, String description, Blob image) {
		this.username = username;
		this.date = date;
		this.value = value;
		this.category = category;
		this.title = title;
		this.description = description;
		this.image = image;
	}
	
	public Object[] toArray() {
		return new Object[]{id, username, date, value, category, title, description, image};
	}
	
	public String toString() {
		return title + ": valued at " + value + " on " + new SimpleDateFormat("EE").format(date) + " " + date;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
