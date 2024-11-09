package app;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TransactionsTab extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public TransactionsTab(String username) {
		setBackground(new Color(240, 240, 240));
		setBorder(new LineBorder(new Color(255, 0, 0)));
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		Object[][] transactions = Transaction.toTable(Database.getTransactions(username));
		
		table = new JTable(transactions, new String[]{"Date", "Value", "Category", "Details"}) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.EAST);
	}

}
