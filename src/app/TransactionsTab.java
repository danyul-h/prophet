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
		
		table = new JTable();
		table.getTableHeader().setPreferredSize(new Dimension(24, 24));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
		
		DefaultTableModel model = new DefaultTableModel(transactions, new String[] {"ID", "Date", "Value", "Category", "Details"}) {
//			@Override
//			public Class<?> getColumnClass(int column){
//	             switch (column) {
//		            case 0: return Integer.class;
//	             	case 1: return Date.class;
//	             	case 2: return BigDecimal.class;
//	             	case 3: return String.class;
//	             	case 4: return String.class;
//	             	default: return Object.class;
//	             }
//			}
		};
		
		table.setModel(model);
//		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
		
		for( int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setMinWidth(100);
		}

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
		
		JLabel instructions = new JLabel(""
				+ "<html>"
				+ "<ul style=\"margin-left:20;\">"
				+ "<li>Clicking \"Add\" or \"Edit\" will open a separate window to input transaction details</li>"
				+ "<li>Select a row on the table before clicking \"Edit\" or \"Delete\" </li>"
				+ "</ul>"
				+ "</html>");
		instructions.setHorizontalAlignment(SwingConstants.CENTER);
		instructions.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JLabel editorLabel = new JLabel("Transaction Editor");
		editorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		editorLabel.setFont(new Font("Arial", Font.BOLD, 16));
		
		Button add = new Button();
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		add.setText("Add Transaction");
		
		Button edit = new Button();
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
					model.removeRow(row);
					Database.deleteTransaction((int)table.getModel().getValueAt(row, 0));
					JOptionPane.showMessageDialog(getParent(),
							"Transaction deleted!",
							"Success",
							JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		delete.setText("Delete Transaction");
		
		GroupLayout gl_editor = new GroupLayout(editor);
		gl_editor.setHorizontalGroup(
			gl_editor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(4)
					.addGroup(gl_editor.createParallelGroup(Alignment.LEADING)
						.addComponent(instructions, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
						.addComponent(editorLabel, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
					.addGap(4))
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(19)
					.addComponent(add, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
					.addGap(19))
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(19)
					.addComponent(edit, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
					.addGap(19))
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(19)
					.addComponent(delete, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
					.addGap(19))
		);
		gl_editor.setVerticalGroup(
			gl_editor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_editor.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_editor.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_editor.createSequentialGroup()
							.addGap(25)
							.addComponent(instructions, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
						.addComponent(editorLabel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addComponent(add, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(edit, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(delete, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
		);
		editor.setLayout(gl_editor);
	}
}
