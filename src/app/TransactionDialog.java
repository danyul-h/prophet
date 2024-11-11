package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.Transaction;
import backend.Database.Status;
import components.Button;

import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import com.toedter.calendar.JCalendar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;

public class TransactionDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * Create the dialog.
	 */
	private Transaction transaction;
	private JDateChooser dateField;
	private JTextField valueField;
	private JComboBox<String> categoryField;
	private JTextArea detailsField;
	
	public Transaction getTransaction() {
		return transaction;
	}

	public TransactionDialog(Transaction entry) {
		this.transaction = entry;
		
		setResizable(false);
		setTitle("Prophet Transactions");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/icons/dark.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 520, 300);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		contentPanel.setPreferredSize(new Dimension(500, 215));
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		
		JLabel dateLbl = new JLabel("Transaction Date:");
		dateLbl.setBounds(10, 11, 153, 32);
		dateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		dateLbl.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPanel.add(dateLbl);
		
		dateField = new JDateChooser();
		dateField.setBorder(new LineBorder(new Color(0, 0, 0)));
		dateField.setBounds(170, 11, 136, 32);
		dateField.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPanel.add(dateField);
		
		
		JLabel valueLbl = new JLabel("Transaction Value:");
		valueLbl.setBounds(10, 54, 153, 32);
		valueLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		valueLbl.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPanel.add(valueLbl);
		
		valueField = new JTextField();
		valueField.setBorder(new LineBorder(new Color(0, 0, 0)));
		valueField.setBounds(170, 54, 136, 32);
		valueField.setFont(new Font("Arial", Font.PLAIN, 16));
		valueField.setColumns(10);
		contentPanel.add(valueField);
		
		JLabel categoryLbl = new JLabel("Transaction Category:");
		categoryLbl.setBounds(10, 97, 153, 32);
		categoryLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		categoryLbl.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPanel.add(categoryLbl);
		
		categoryField = new JComboBox<String>(new String[] {"Miscellaneous", "Salary", "Bills", "Entertainment", "Dining", "Education", "Insurance", "Health", "Groceries", "Transportation", "Home", "Travel"});
		categoryField.setBorder(new LineBorder(new Color(0, 0, 0)));
		categoryField.setBounds(170, 97, 136, 32);
		categoryField.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPanel.add(categoryField);
		
		JLabel detailsLbl = new JLabel("Transaction Details:");
		detailsLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		detailsLbl.setFont(new Font("Arial", Font.PLAIN, 16));
		detailsLbl.setBounds(10, 140, 153, 32);
		contentPanel.add(detailsLbl);
		
		detailsField = new JTextArea();
		detailsField.setFont(new Font("Arial", Font.PLAIN, 16));
		detailsField.setTabSize(4);
		detailsField.setLineWrap(true);
		detailsField.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(5, 5, 5, 5)));
		detailsField.setBounds(170, 140, 320, 64);
		contentPanel.add(detailsField);
		
		JLabel helpLbl = new JLabel(""
				+ "<html>"
				+ "<h2 style=\"margin-bottom:-5;text-align:center;margin-top:-5;\">Transactions</h2>"
				+ "<ul style=\"margin-left:20\">"
					+ "<li>Use the fields to create your desired transaction!</li>"
					+ "<li>Keep values as appropriate types and under 45 characters!</li>"
				+ "</ul>"
				+ "</html>");
		helpLbl.setFont(new Font("Arial", Font.PLAIN, 12));
		helpLbl.setBounds(316, 11, 174, 118);
		contentPanel.add(helpLbl);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(255, 255, 255));
		FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
		fl_buttonPane.setVgap(10);
		fl_buttonPane.setHgap(10);
		buttonPane.setLayout(fl_buttonPane);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		Button saveBtn = new Button();
		saveBtn.setPreferredSize(new Dimension(70, 32));
		saveBtn.setText("Save");
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					Date date = Date.valueOf(LocalDate.ofInstant(dateField.getDate().toInstant(), ZoneId.systemDefault()));
					if(date == null) JOptionPane.showMessageDialog(rootPane, "Invalid \"Date\" field, try again.", "Error", JOptionPane.WARNING_MESSAGE);
					BigDecimal value = new BigDecimal(new DecimalFormat("#.00").format((double) Math.round(Double.parseDouble(valueField.getText())*100)/100));
					String category = categoryField.getSelectedItem().toString();
					String details = detailsField.getText();
					if(details.length() > 45) JOptionPane.showMessageDialog(rootPane, "\"Details\" field too long, try again.", "Error", JOptionPane.WARNING_MESSAGE);
					transaction = new Transaction(transaction.getId(), transaction.getUsername(), date, value, category, details);
					dispose();
				} catch (java.lang.NumberFormatException e) {
					JOptionPane.showMessageDialog(rootPane, "Invalid \"Value\" field, try again.", "Error",JOptionPane.WARNING_MESSAGE);
				} catch (java.lang.NullPointerException e) {
					JOptionPane.showMessageDialog(rootPane, "Missing fields, try again.", "Error", JOptionPane.WARNING_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(rootPane, "Invalid fields, try again.", "Error", JOptionPane.WARNING_MESSAGE);
				} finally {
					refresh();
				}
			}
		});
		buttonPane.add(saveBtn);
		
		Button cancelBtn = new Button();
		cancelBtn.setPreferredSize(new Dimension(70, 32));
		cancelBtn.setText("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				transaction = null;
				dispose();
			}
		});
		buttonPane.add(cancelBtn);
		
		this.setLocationRelativeTo(null);
		refresh();
	}
	
	public void refresh() {
		dateField.setDate(transaction.getDate());
		valueField.setText(transaction.getValue().toString());
		categoryField.setSelectedItem(transaction.getCategory());
		detailsField.setText(transaction.getDetails());
	}
}
