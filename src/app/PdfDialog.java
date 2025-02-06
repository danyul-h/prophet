package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
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
	private String username;
	public boolean downloaded = false;
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public PdfDialog(ArrayList<Transaction> entries, String user) {
		this.transactions = entries;
		this.username = user;
		
		setResizable(false);
		setTitle("Prophet Transactions");
		setIconImage(appIcon.getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 400);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		contentPanel.setPreferredSize(new Dimension(300, 360));
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
		helpLbl.setBounds(10, 116, 250, 220);
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
				LocalDate startDate;
				LocalDate endDate;
				if(startDateField.getDate() == null) startDate = Transaction.getOldest(transactions).getDate().toLocalDate();
				else startDate = LocalDate.ofInstant(startDateField.getDate().toInstant(), ZoneId.systemDefault());
				if(endDateField.getDate() == null) endDate = Transaction.getLatest(transactions).getDate().toLocalDate();
				else endDate = LocalDate.ofInstant(endDateField.getDate().toInstant(), ZoneId.systemDefault());
				downloaded = true;
				generatePdf(startDate, endDate);
				dispose();
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
	
	public void generatePdf(LocalDate start, LocalDate end) {
		try {
			String home = System.getProperty("user.home");
			LocalDateTime retrieveDate = LocalDateTime.now();
			PdfWriter writer = new PdfWriter(home+"/Downloads/Prophet-Transactions_" + retrieveDate.format(DateTimeFormatter.ofPattern("LLLL-d-yyyy_k-m-s")) + ".pdf");
			PdfDocument pdf = new PdfDocument(writer);
			pdf.setDefaultPageSize(PageSize.A4);
			Document document = new Document(pdf);
			
			Paragraph title = new Paragraph("Prophet Transactions")
					.setFontColor(new DeviceRgb(40, 3, 50))
					.setFontSize(20f)
					.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD));
			Paragraph subtitle = new Paragraph("Retrieved " + retrieveDate.format(DateTimeFormatter.ofPattern("LLLL d yyyy, k:m:s")))
					.setFontColor(new DeviceRgb(100, 24, 120))
					.setFontSize(12f)
					.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE));
			
			Table header = new Table(UnitValue.createPercentArray(new float[] {60,40}))
					.setBorder(Border.NO_BORDER)
					.useAllAvailableWidth();
			
			Table tableTitle = new Table(1)
					.useAllAvailableWidth()
					.setBorder(Border.NO_BORDER);
			tableTitle.addCell(new Cell().add(title).setBorder(Border.NO_BORDER));
			tableTitle.addCell(new Cell().add(subtitle).setBorder(Border.NO_BORDER));
			header.addCell(new Cell().add(tableTitle).setBorder(Border.NO_BORDER));
			
			Table tableDates = new Table(1)
					.useAllAvailableWidth()
					.setBorder(Border.NO_BORDER);
			tableDates.addCell(new Cell().add(new Paragraph("START DATE: " + start.format(DateTimeFormatter.ofPattern("LLLL d, yyyy"))).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
			tableDates.addCell(new Cell().add(new Paragraph("END DATE: " + end.format(DateTimeFormatter.ofPattern("LLLL d, yyyy"))).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
			tableDates.addCell(new Cell().add(new Paragraph("USER: " + username).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
			header.addCell(new Cell().add(tableDates).setBorder(Border.NO_BORDER));
			
			document.add(header);
			
			Table divider = new Table(1)
					.useAllAvailableWidth()
					.setBorder(new SolidBorder(1f/5f));
			document.add(divider);
			
			Table summary = new Table(UnitValue.createPercentArray(new float[] {65,35}))
					.useAllAvailableWidth()
					.setBorder(Border.NO_BORDER);
			
			ArrayList<Transaction> filteredTransactions = Transaction.filterDayRange(transactions, start, end);
			String startValue = String.format("%.2f", Transaction.getDayValue(filteredTransactions, start));
			String endValue = String.format("%.2f", Transaction.getDayValue(filteredTransactions, end));
			String totalExpenses = String.format("%.2f", Transaction.getExpenses(filteredTransactions));
			String totalIncomes = String.format("%.2f", Transaction.getIncomes(filteredTransactions));
			
			document.add(new Paragraph("Transactions Summary").setTextAlignment(TextAlignment.CENTER).setFontColor(new DeviceRgb(100, 24, 120))).setBorder(Border.NO_BORDER);
			summary.addCell(new Cell().add(new Paragraph("Beginning Balance")).setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph(startValue).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph("Total Period Expenses")).setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph(totalExpenses).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph("Total Period Income")).setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph(totalIncomes).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph("Ending Balance")).setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph(endValue).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
			document.add(summary);
			document.add(divider);
			document.add(new Paragraph("All Transactions").setTextAlignment(TextAlignment.CENTER).setFontColor(new DeviceRgb(100, 24, 120))).setBorder(Border.NO_BORDER);
			
			Table table = new Table(5)
					.useAllAvailableWidth();
			for(Transaction i : filteredTransactions) {
				System.out.println(i);
			}
			System.out.println("heheehehehehehe");
			Transaction.sortTransactions(filteredTransactions);
			for(Transaction i : filteredTransactions) {
				System.out.println(i);
			}
			
			document.close();			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
