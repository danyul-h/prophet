package components;

import java.math.BigDecimal;

import javax.swing.RowFilter;

public class SearchFilter extends RowFilter {
	private String details;
	private String category;

	public SearchFilter(String details, String category) {
		this.details = details.toLowerCase();
		this.category = category;
	}

	@Override
	public boolean include(Entry entry) {
		boolean detailsFilter = entry.getStringValue(4).toLowerCase().indexOf(details) >= 0,
				categoryFilter = entry.getStringValue(3).equals(category);
		if (category.equals("All"))
			return detailsFilter;
		else if (category.equals("Income"))
			categoryFilter = ((BigDecimal) entry.getValue(2)).floatValue() >= 0;
		else if (category.equals("Expense"))
			categoryFilter = ((BigDecimal) entry.getValue(2)).floatValue() <= 0;
		return detailsFilter && categoryFilter;
	}
}
