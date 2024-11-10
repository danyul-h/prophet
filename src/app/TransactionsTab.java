package app;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.BorderLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.math.BigDecimal;
import java.sql.Date;

import javax.swing.ScrollPaneConstants;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class TransactionsTab extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public TransactionsTab(String username) {
		setBackground(new Color(255, 255, 255));
		setBorder(new EmptyBorder(5, 5, 5, 0));
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.CENTER);
		
		Object[][] transactions = Transaction.toTable(Database.getTransactions("admin"));
		for (Transaction i : Database.getTransactions("admin")) {
			System.out.println(i);
		}
		
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.getTableHeader().setPreferredSize(new Dimension(24, 24));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		table.setModel(new DefaultTableModel(transactions, new String[] {"Date", "Value", "Category", "Details"}) {
			@Override
			public Class<?> getColumnClass(int column){
	             switch (column) {
	             	case 0: return Date.class;
	             	case 1: return BigDecimal.class;
	             	case 2: return String.class;
	             	case 3: return String.class;
	             	default: return Object.class;
	             }
			}
		});
		table.getColumnModel().getColumn(0).setMinWidth(100);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(2).setMinWidth(100);
		table.getColumnModel().getColumn(3).setMinWidth(100);

		table.setRowHeight(32);
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
		sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowVerticalLines(false);
		
		//centers all the cells
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for(int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			
		}
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		
		JPanel editor = new JPanel();
		editor.setBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 255), 5), new LineBorder(new Color(128, 128, 128))));
		editor.setPreferredSize(new Dimension(200, 10));
		editor.setBackground(new Color(255, 255, 255));
		add(editor, BorderLayout.EAST);
		editor.setLayout(new BorderLayout(0, 0));
	}
}
