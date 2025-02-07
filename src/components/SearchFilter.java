package components;

import java.math.BigDecimal;

import javax.swing.RowFilter;

public class SearchFilter extends RowFilter {
	private String details;
	private String category;

	//search filter takes in details and category inputs
	public SearchFilter(String details, String category) {
		this.details = details.toLowerCase();
		this.category = category;
	}

	@Override
	//filter based on an entry
	public boolean include(Entry entry) {
		//boolean values of whether or not the entry has has the detail keywords and matching category
		boolean detailsFilter = entry.getStringValue(4).toLowerCase().indexOf(details) >= 0,
				categoryFilter = entry.getStringValue(3).equals(category);
		//but also if the category is "all" just return based on details
		if (category.equals("All"))
			return detailsFilter;
		//if its income, change category filter to those with values above 0
		else if (category.equals("Income"))
			categoryFilter = ((BigDecimal) entry.getValue(2)).floatValue() >= 0;
		//if its expense, change category filter to those with values below 0
		else if (category.equals("Expense"))
			categoryFilter = ((BigDecimal) entry.getValue(2)).floatValue() <= 0;
		//return based on the boolean values
		return detailsFilter && categoryFilter;
	}
}
