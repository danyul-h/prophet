package app;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
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

import backend.Database;
import backend.Transaction;
import components.SearchFilter;

import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import components.Button;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import components.TextField;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class TransactionsPage extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField detailsFilter;
	
	/**
	 * Create the panel.
	 */
	public TransactionsPage(String username) {
		setBackground(new Color(255, 255, 255));
		setBorder(new EmptyBorder(10, 10, 10, 5));
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 5), new LineBorder(new Color(0, 0, 0))));
		scrollPane.setFont(new Font("Arial", Font.PLAIN, 16));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.CENTER);
		
		Object[][] transactions = Transaction.toTable(Database.getTransactions(username));
		for (Transaction i : Database.getTransactions(username)) {
			System.out.println(i);
		}
		
		table = new JTable();
		table.setFocusable(false);
		table.getTableHeader().setPreferredSize(new Dimension(24, 24));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		
		DefaultTableModel model = new DefaultTableModel(transactions, new String[] {"ID", "Date", "Value", "Category", "Details"}) {
			@Override
			public Class<?> getColumnClass(int column){
	             switch (column) {
		            case 0: return Integer.class;
	             	case 1: return Date.class;
	             	case 2: return BigDecimal.class;
	             	case 3: return String.class;
	             	case 4: return String.class;
	             	default: return Object.class;
	             }
			}
		};
		
		table.setModel(model);
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
		
		for( int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setMinWidth(100);
		}

		table.setRowHeight(32);
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
		sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
		sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
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
		editor.setBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 255), 5), new LineBorder(new Color(0, 0, 0))));
		editor.setPreferredSize(new Dimension(200, 10));
		editor.setBackground(new Color(255, 255, 255));
		add(editor, BorderLayout.EAST);
		
		JLabel editorInfo = new JLabel(""
				+ "<html>"
					+ "<h2 style=\"margin-bottom:-5;text-align:center\">Transaction Editor</h2>"
					+ "<ul style=\"margin-left:20\">"
						+ "<li>Click on the table headers to sort the transactions based on columns</li>"
						+ "<li>Clicking \"Add\" or \"Edit\" will open a separate window to input transaction details</li>"
						+ "<li>Select a row on the table before clicking \"Edit\" or \"Delete\" </li>"
					+ "</ul>"
				+ "</html>");
		editorInfo.setForeground(new Color(0, 0, 0));
		editorInfo.setBackground(new Color(255, 255, 255));
		editorInfo.setHorizontalAlignment(SwingConstants.CENTER);
		editorInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		
		Button add = new Button();
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		add.setText("Add Transaction");
		
		Button edit = new Button();
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(getParent(), 
							"Please select a transaction from the table to edit first.", 
							"Error", 
							JOptionPane.WARNING_MESSAGE);
				} else {
					row = table.convertRowIndexToModel(row);
					Transaction transaction = Database.getTransaction((int) table.getModel().getValueAt(row, 0));
					TransactionCreator creator = new TransactionCreator(transaction);
					creator.setVisible(true);
//					JOptionPane.showMessageDialog(getParent(),
//							"Transaction edited!",
//							"Success",
//							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		edit.setText("Edit Transaction");
		
		Button delete = new Button();
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(getParent(), 
							"Please select a transaction from the table to delete first.", 
							"Error", 
							JOptionPane.WARNING_MESSAGE);
				} else {
					row = table.convertRowIndexToModel(row);
					Database.deleteTransaction((int)table.getModel().getValueAt(row, 0));
					model.removeRow(row);
					JOptionPane.showMessageDialog(getParent(),
							"Transaction deleted!",
							"Success",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		delete.setText("Delete Transaction");
		
		JLabel filterInfo = new JLabel(""
				+ "<html>"
					+ "<h2 style=\"margin-bottom:-5;text-align:center\">Search Filter</h2>"
					+ "<ul style=\"margin-left:20\">"
						+ "<li>Modify the parameters below to filter the transactions shown in the table</li>"
					+ "</ul>"
				+ "</html>");
		filterInfo.setForeground(new Color(0, 0, 0));
		filterInfo.setBackground(new Color(255, 255, 255));
		filterInfo.setHorizontalAlignment(SwingConstants.CENTER);
		filterInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JComboBox<String> categoryFilter = new JComboBox<String>(new String[] {"All", "Income", "Expense", "Salary", "Entertainment", "Dining", "Education", "Insurance", "Health", "Groceries", "Transportation", "Home", "Travel", "Bills"});
		categoryFilter.setBackground(new Color(255, 255, 255));
		categoryFilter.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Category Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		categoryFilter.setFont(new Font("Arial", Font.PLAIN, 18));
		
		detailsFilter = new JTextField();
		detailsFilter.setForeground(new Color(0, 0, 0));
		detailsFilter.setOpaque(false);
		detailsFilter.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Details Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		detailsFilter.setFont(new Font("Arial", Font.PLAIN, 18));
		detailsFilter.setColumns(10);
		
		Button filter = new Button();
		filter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(new SearchFilter(detailsFilter.getText(), (String) categoryFilter.getSelectedItem()));
			}
		});
		filter.setText("Set Filter");
		
		Button clear = new Button();
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(null);				
			}
		});
		clear.setText("Clear Filter");
		GroupLayout gl_editor = new GroupLayout(editor);
		gl_editor.setHorizontalGroup(
			gl_editor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(4)
					.addComponent(editorInfo, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(8)
					.addComponent(add, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(8)
					.addComponent(edit, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(8)
					.addComponent(delete, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(4)
					.addComponent(filterInfo, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(8)
					.addComponent(detailsFilter, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(8)
					.addComponent(categoryFilter, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(8)
					.addComponent(filter, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(8)
					.addComponent(clear, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
		);
		gl_editor.setVerticalGroup(
			gl_editor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(15)
					.addComponent(editorInfo, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(add, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(edit, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(delete, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(filterInfo, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addComponent(detailsFilter, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(categoryFilter, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(filter, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(clear, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
		);
		editor.setLayout(gl_editor);
	}
}
