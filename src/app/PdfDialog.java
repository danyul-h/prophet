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

	//fields
	private ArrayList<Transaction> transactions;
	private JDateChooser startDateField;
	private JDateChooser endDateField;
	private String username;
	public boolean downloaded = false;

	//getting the transactions
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	//constructor
	public PdfDialog(ArrayList<Transaction> entries, String user) {
		this.transactions = entries;
		this.username = user;

		//setting up window settings
		setResizable(false);
		setTitle("Prophet Transactions");
		setIconImage(appIcon.getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 400);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		//init the content panel where all components are held
		contentPanel.setPreferredSize(new Dimension(300, 360));
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);

		//label for start date field
		JLabel startDateLbl = new JLabel("Start Date:");
		startDateLbl.setBounds(10, 11, 90, 32);
		startDateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		startDateLbl.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPanel.add(startDateLbl);

		//start date field chooser for user input
		startDateField = new JDateChooser();
		startDateField.setBorder(new LineBorder(new Color(0, 0, 0)));
		startDateField.setBounds(120, 11, 136, 32);
		startDateField.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPanel.add(startDateField);

		//end date label
		JLabel endDateLbl = new JLabel("End Date:");
		endDateLbl.setBounds(10, 60, 90, 32);
		endDateLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		endDateLbl.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPanel.add(endDateLbl);

		//end date chooser
		endDateField = new JDateChooser();
		endDateField.setBorder(new LineBorder(new Color(0, 0, 0)));
		endDateField.setBounds(120, 60, 136, 32);
		endDateField.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPanel.add(endDateField);

		//instructions to help user
		JLabel helpLbl = new JLabel("" 
				+ "<html>"
					+ "<h2 style=\"margin-bottom:-5;text-align:center;margin-top:-5;\">Report Download</h2>"
					+ "<ul style=\"margin-left:20\">"
						+ "<li>Output a report of all your transactions in a date range here!</li>"
						+ "<li>If no valid start date, the date range will start at the first transaction date on your account.</li>"
						+ "<li>If no valid end date, the date range will end at the last transaction date on your account.</li>"
					+ "</ul>" 
				+ "</html>");
		helpLbl.setFont(new Font("Arial", Font.PLAIN, 12));
		helpLbl.setBounds(10, 116, 250, 220);
		contentPanel.add(helpLbl);

		//a bottom section for the buttons
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(255, 255, 255));
		FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
		fl_buttonPane.setVgap(10);
		fl_buttonPane.setHgap(10);
		buttonPane.setLayout(fl_buttonPane);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		//download button
		Button downloadBtn = new Button();
		downloadBtn.setPreferredSize(new Dimension(90, 32));
		downloadBtn.setText("Download");
		downloadBtn.addActionListener(new ActionListener() {
			//when button is clicked...
			public void actionPerformed(ActionEvent a) {
				LocalDate startDate;
				LocalDate endDate;
				//using default values if the fields are not valid, otherwise use input fields
				if (startDateField.getDate() == null)
					startDate = Transaction.getOldest(transactions).getDate().toLocalDate();
				else
					startDate = LocalDate.ofInstant(startDateField.getDate().toInstant(), ZoneId.systemDefault());
				if (endDateField.getDate() == null)
					endDate = Transaction.getLatest(transactions).getDate().toLocalDate();
				else
					endDate = LocalDate.ofInstant(endDateField.getDate().toInstant(), ZoneId.systemDefault());
				downloaded = true;
				//generate the pdf
				generatePdf(startDate, endDate);
				//close window
				dispose();
			}
		});
		buttonPane.add(downloadBtn);

		//cancel btn closes the window
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
		//generating pdf 
		try {
			//locate user's downloads folder
			String home = System.getProperty("user.home");
			LocalDateTime retrieveDate = LocalDateTime.now();
			//create the file with the name including the report and date and time retrieved
			PdfWriter writer = new PdfWriter(home + "/Downloads/Prophet-Transactions-Report_"
					+ retrieveDate.format(DateTimeFormatter.ofPattern("LLLL-d-yyyy_kk-mm-ss")) + ".pdf");
			PdfDocument pdf = new PdfDocument(writer);
			//pdf size
			pdf.setDefaultPageSize(PageSize.A4);
			Document document = new Document(pdf);

			//adding title to pdf
			Paragraph title = new Paragraph("Prophet Transactions Report").setFontColor(new DeviceRgb(40, 3, 50))
					.setFontSize(20f).setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD));
			//adding retrieval date and time to pdf
			Paragraph subtitle = new Paragraph(
					"Retrieved " + retrieveDate.format(DateTimeFormatter.ofPattern("LLLL d yyyy, kk:mm:ss")))
					.setFontColor(new DeviceRgb(100, 24, 120)).setFontSize(12f)
					.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE));

			//adding a header table
			Table header = new Table(UnitValue.createPercentArray(new float[] { 60, 40 })).setBorder(Border.NO_BORDER)
					.useAllAvailableWidth();

			//adding a table to hold titles, add the titles to the table
			Table tableTitle = new Table(1).useAllAvailableWidth().setBorder(Border.NO_BORDER);
			tableTitle.addCell(new Cell().add(title).setBorder(Border.NO_BORDER));
			tableTitle.addCell(new Cell().add(subtitle).setBorder(Border.NO_BORDER));
			//add the titles to the header
			header.addCell(new Cell().add(tableTitle).setBorder(Border.NO_BORDER));

			//adding start and end date range
			Table tableDates = new Table(1).useAllAvailableWidth().setBorder(Border.NO_BORDER);
			tableDates.addCell(new Cell()
					.add(new Paragraph("START DATE: " + start.format(DateTimeFormatter.ofPattern("LLLL d, yyyy")))
							.setTextAlignment(TextAlignment.RIGHT))
					.setBorder(Border.NO_BORDER));
			tableDates.addCell(
					new Cell().add(new Paragraph("END DATE: " + end.format(DateTimeFormatter.ofPattern("LLLL d, yyyy")))
							.setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
			tableDates.addCell(new Cell().add(new Paragraph("USER: " + username).setTextAlignment(TextAlignment.RIGHT))
					.setBorder(Border.NO_BORDER));
			//adding the dates to the header
			header.addCell(new Cell().add(tableDates).setBorder(Border.NO_BORDER));
			document.add(header);

			//add a divider
			Table divider = new Table(1).useAllAvailableWidth().setBorder(new SolidBorder(1f / 5f));
			document.add(divider);

			//add a summary
			Table summary = new Table(UnitValue.createPercentArray(new float[] { 65, 35 })).useAllAvailableWidth()
					.setBorder(Border.NO_BORDER);

			//data for the summary
			ArrayList<Transaction> filteredTransactions = Transaction.filterDayRange(transactions, start, end);
			String startValue = String.format("%.2f",
					Transaction.getDayValue(filteredTransactions, start.minusDays(1)));
			String endValue = String.format("%.2f", Transaction.getDayValue(filteredTransactions, end));
			String totalExpenses = String.format("%.2f", Transaction.getExpenses(filteredTransactions));
			String totalIncomes = String.format("%.2f", Transaction.getIncomes(filteredTransactions));

			//add summary labels and their values
			document.add(new Paragraph("Transactions Summary").setTextAlignment(TextAlignment.CENTER)
					.setFontColor(new DeviceRgb(100, 24, 120))).setBorder(Border.NO_BORDER);
			summary.addCell(new Cell().add(new Paragraph("Beginning Balance")).setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph(startValue).setTextAlignment(TextAlignment.RIGHT))
					.setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph("Total Period Expenses")).setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph(totalExpenses).setTextAlignment(TextAlignment.RIGHT))
					.setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph("Total Period Income")).setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph(totalIncomes).setTextAlignment(TextAlignment.RIGHT))
					.setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph("Ending Balance")).setBorder(Border.NO_BORDER));
			summary.addCell(new Cell().add(new Paragraph(endValue).setTextAlignment(TextAlignment.RIGHT))
					.setBorder(Border.NO_BORDER));
			document.add(summary);
			//add another divider after summary
			document.add(divider);
			//add a list of all transactions
			document.add(new Paragraph("All Transactions").setTextAlignment(TextAlignment.CENTER)
					.setFontColor(new DeviceRgb(100, 24, 120))).setBorder(Border.NO_BORDER);

			//create column headers date, category, details, value, total balance
			Table table = new Table(UnitValue.createPercentArray(new float[] { 15, 20, 30, 15, 20 }))
					.useAllAvailableWidth().setBorder(Border.NO_BORDER);
			table.addCell(new Cell()
					.add((new Paragraph("Date").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))))
					.setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1f / 5f)));
			table.addCell(new Cell()
					.add((new Paragraph("Category").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))))
					.setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1f / 5f)));
			table.addCell(new Cell()
					.add((new Paragraph("Details").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))))
					.setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1f / 5f)));
			table.addCell(new Cell()
					.add((new Paragraph("Value").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))))
					.setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1f / 5f)));
			table.addCell(new Cell().add(
					(new Paragraph("Total Balance").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))))
					.setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1f / 5f)));

			//find double value of start balance
			double balance = Transaction.getDayValue(filteredTransactions, start.minusDays(1));
			Transaction.sortTransactions(filteredTransactions);
			//iterate over sorted transactions
			for (Transaction i : filteredTransactions) {
				//add values to the table
				table.addCell(new Cell().add(new Paragraph(i.getDate().toString())).setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(1f / 5f)));
				table.addCell(new Cell().add(new Paragraph(i.getCategory())).setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(1f / 5f)));
				table.addCell(new Cell().add(new Paragraph(i.getDetails())).setBorder(Border.NO_BORDER)
						.setBorderBottom(new SolidBorder(1f / 5f)));
				table.addCell(new Cell()
						.add(new Paragraph(String.format("%.2f", i.getValue().doubleValue()))
								.setTextAlignment(TextAlignment.RIGHT))
						.setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1f / 5f)));
				balance += i.getValue().doubleValue();
				balance = Math.round(balance * 100) / 100.0;
				table.addCell(new Cell()
						.add(new Paragraph(String.format("%.2f", balance)).setTextAlignment(TextAlignment.RIGHT))
						.setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1f / 5f)));
			}

			//add the table and close the document, it will be in downloads now
			document.add(table);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
