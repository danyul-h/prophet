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
import java.awt.FlowLayout;
import java.awt.Rectangle;

public class TransactionsPage extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField detailsField;
	
	/**
	 * Create the panel.
	 */
	public TransactionsPage(String username) {
		setBackground(new Color(255, 255, 255));
		setBorder(new EmptyBorder(10, 10, 10, 5));
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 10), new LineBorder(new Color(0, 0, 0))));
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
			if(i != 3) table.getColumnModel().getColumn(i).setMaxWidth(200);
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
		
		JPanel side = new JPanel();
		side.setBorder(null);
		side.setPreferredSize(new Dimension(200, 10));
		side.setBackground(new Color(255, 255, 255));
		add(side, BorderLayout.EAST);
		side.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel editor = new JPanel();
		editor.setBackground(new Color(255, 255, 255));
		editor.setBorder(new LineBorder(new Color(0, 0, 0)));
		editor.setPreferredSize(new Dimension(200, 380));
		side.add(editor);
		
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
		
		Button addBtn = new Button();
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		addBtn.setText("Add Transaction");
		
		Button editBtn = new Button();
		editBtn.addActionListener(new ActionListener() {
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
				}
			}
		});
		editBtn.setText("Edit Transaction");
		
		Button deleteBtn = new Button();
		deleteBtn.addActionListener(new ActionListener() {
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
		deleteBtn.setText("Delete Transaction");
		
		GroupLayout gl_editor = new GroupLayout(editor);
		gl_editor.setHorizontalGroup(
			gl_editor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_editor.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_editor.createParallelGroup(Alignment.LEADING)
						.addComponent(editorInfo, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
						.addComponent(addBtn, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
						.addComponent(editBtn, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
						.addComponent(deleteBtn, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_editor.setVerticalGroup(
			gl_editor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(9)
					.addComponent(editorInfo, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(addBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(editBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(deleteBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(14))
		);
		editor.setLayout(gl_editor);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setPreferredSize(new Dimension(200, 310));
		side.add(panel);
		
		JLabel filterInfo = new JLabel(""
				+ "<html>"
					+ "<h2 style=\"margin-bottom:-5;text-align:center\">Transaction Filters</h2>"
					+ "<ul style=\"margin-left:20\">"
						+ "<li>Modify the parameters below to filter the transactions shown in the table</li>"
					+ "</ul>"
				+ "</html>");
		filterInfo.setForeground(new Color(0, 0, 0));
		filterInfo.setBackground(new Color(255, 255, 255));
		filterInfo.setHorizontalAlignment(SwingConstants.CENTER);
		filterInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		
		detailsField = new JTextField();
		detailsField.setBorder(new TitledBorder(null, "Details Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		detailsField.setFont(new Font("Arial", Font.PLAIN, 18));
		detailsField.setColumns(10);
		
		JComboBox<String> categoryField = new JComboBox<String>(new String[] {"All", "Income", "Expense", "Salary", "Bills", "Entertainment", "Dining", "Education", "Insurance", "Health", "Groceries", "Transportation", "Home", "Travel"});
		categoryField.setBorder(new TitledBorder(null, "Category Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		Button filterBtn = new Button();
		filterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(new SearchFilter(detailsField.getText(), (String) categoryField.getSelectedItem()));
			}
		});
		filterBtn.setText("Set Filter");
		
		Button resetBtn = new Button();
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sorter.setRowFilter(null);
				detailsField.setText("");
				categoryField.setSelectedIndex(0);
			}
		});
		resetBtn.setText("Clear");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(filterBtn, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(resetBtn, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
						.addComponent(filterInfo, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
						.addComponent(categoryField, 0, 180, Short.MAX_VALUE)
						.addComponent(detailsField, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(filterInfo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(detailsField, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(categoryField, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(resetBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(filterBtn, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(36))
		);
		panel.setLayout(gl_panel);
	}
}
