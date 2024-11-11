package backend;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

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
		this.date = Date.valueOf(LocalDate.now());
		this.value = BigDecimal.valueOf(0.00);
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
