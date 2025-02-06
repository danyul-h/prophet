package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;

import backend.Transaction;
import components.Button;

public class PdfDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static final ImageIcon appIcon = new ImageIcon(TransactionDialog.class.getResource("/icons/app.png"));
	
	private ArrayList<Transaction> transactions;
	private JDateChooser startDateField;
	private JDateChooser endDateField;
	public boolean downloaded = false;
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public PdfDialog(ArrayList<Transaction> entries) {
		this.transactions = entries;
		
		setResizable(false);
		setTitle("Prophet Transactions");
		setIconImage(appIcon.getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 350);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		contentPanel.setPreferredSize(new Dimension(300, 320));
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		
		JLabel startDateLbl = new JLabel("Start Date:");
		startDateLbl.setBounds(10, 11, 90, 32);
		startDateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		startDateLbl.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPanel.add(startDateLbl);
		
		startDateField = new JDateChooser();
		startDateField.setBorder(new LineBorder(new Color(0, 0, 0)));
		startDateField.setBounds(120, 11, 136, 32);
		startDateField.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPanel.add(startDateField);
		
		JLabel endDateLbl = new JLabel("End Date:");
		endDateLbl.setBounds(10, 60, 90, 32);
		endDateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		endDateLbl.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPanel.add(endDateLbl);
		
		endDateField = new JDateChooser();
		endDateField.setBorder(new LineBorder(new Color(0, 0, 0)));
		endDateField.setBounds(120, 60, 136, 32);
		endDateField.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPanel.add(endDateField);
		
		JLabel helpLbl = new JLabel(""
				+ "<html>"
				+ "<h2 style=\"margin-bottom:-5;text-align:center;margin-top:-5;\">PDF Download</h2>"
				+ "<ul style=\"margin-left:20\">"
					+ "<li>Export a PDF of all your transactions in a date range here!</li>"
					+ "<li>If no valid start date, the date range will start at the first transaction date on your account.</li>"
					+ "<li>If no valid end date, the date range will end at the last transaction date on your account.</li>"
				+ "</ul>"
				+ "</html>");
		helpLbl.setFont(new Font("Arial", Font.PLAIN, 12));
		helpLbl.setBounds(10, 116, 280, 140);
		contentPanel.add(helpLbl);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(255, 255, 255));
		FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
		fl_buttonPane.setVgap(10);
		fl_buttonPane.setHgap(10);
		buttonPane.setLayout(fl_buttonPane);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		Button downloadBtn = new Button();
		downloadBtn.setPreferredSize(new Dimension(90, 32));
		downloadBtn.setText("Download");
		downloadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				Date startDate;
				Date endDate;
				if(startDateField.getDate() == null) startDate = new Date(Long.MIN_VALUE);
				else startDate = Date.valueOf(LocalDate.ofInstant(startDateField.getDate().toInstant(), ZoneId.systemDefault()));
				if(endDateField.getDate() == null) endDate = Date.valueOf(LocalDate.now().plusDays(1));
				else endDate = Date.valueOf(LocalDate.ofInstant(endDateField.getDate().toInstant(), ZoneId.systemDefault()));
				System.out.println();
			}
		});
		buttonPane.add(downloadBtn);
		
		Button cancelBtn = new Button();
		cancelBtn.setPreferredSize(new Dimension(70, 32));
		cancelBtn.setText("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				dispose();
			}
		});
		buttonPane.add(cancelBtn);
		
		this.setLocationRelativeTo(null);
	}
}
