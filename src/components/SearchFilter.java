package components;

import javax.swing.RowFilter;

public class SearchFilter extends RowFilter{
	private String search;
	private String category;
	
	public SearchFilter(String search, String category){
		this.search = search;
		this.category = category;
	}
	
	@Override
	public boolean include(Entry entry) {
		if (category.equals("All")) return entry.getStringValue(4).indexOf(search) >= 0;
		return entry.getStringValue(4).indexOf(search) >= 0 && entry.getStringValue(3).equals(category);
	}
}
