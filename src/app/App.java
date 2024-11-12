package app;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import backend.Database;
import backend.Transaction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import components.Button;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private ArrayList<Transaction> transactions;
	private BigDecimal balance = new BigDecimal(0);
	private JLabel balanceLbl;
	private String username;
	
	/**
	 * Create the frame.
	 */
	public App(String username) {
		this.username = username;

		setMinimumSize(new Dimension(850, 770));
		setTitle("Prophet");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/icons/dark.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 810);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel nav = new JPanel() {
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				Color c1 = new Color(10, 46, 127, 255); // dark
				Color c2 = new Color(29, 82, 188, 255); // light
				GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
				g2.setPaint(gp);
				g2.fill(new Rectangle(getWidth(), getHeight()));
			}
		};
		nav.setBorder(null);
		nav.setBackground(new Color(0, 64, 128));
		nav.setPreferredSize(new Dimension(154, 380));
		contentPane.add(nav, BorderLayout.WEST);
		nav.setLayout(new BorderLayout(0, 0));

		JPanel pageBtns = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pageBtns.getLayout();
		flowLayout.setVgap(10);
		pageBtns.setBackground(new Color(0, 0, 0, 0));
		nav.add(pageBtns, BorderLayout.NORTH);
		
		JLabel pie = new JLabel("");
		pie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultCategoryDataset dcd = new DefaultCategoryDataset();
				dcd.setValue(78, "Marks", "b");
				dcd.setValue(728, "Marks", "b");
				dcd.setValue(79, "Marks", "d");
				dcd.setValue(80, "Marks", "f");
				dcd.setValue(90, "Marks", "g");
				dcd.setValue(40, "Marks", "j");
				JFreeChart jchart = ChartFactory.createBarChart("Strudent Record", "Student Name", "Student Marks", dcd, PlotOrientation.VERTICAL, true, true, false);
				
				CategoryPlot plot = jchart.getCategoryPlot();
				plot.setRangeGridlinePaint(Color.black); 
				ChartFrame chartFrm = new ChartFrame("Student Record", jchart, true);
				chartFrm.setVisible(true);
				chartFrm.setSize(500, 400);
				ChartPanel chartPanel = new ChartPanel(jchart);
			}
		});
		ImageIcon imageIcon = new ImageIcon(Login.class.getResource("/icons/light.png")); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(56, 56, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg);
		pie.setIcon(imageIcon);
		pageBtns.add(pie);

		JPanel sysBtns = new JPanel();
		sysBtns.setBackground(new Color(0, 0, 0, 0));
		nav.add(sysBtns, BorderLayout.SOUTH);

		JPanel info = new JPanel();
		contentPane.add(info, BorderLayout.CENTER);
		info.setLayout(new BorderLayout(0, 0));

		JPanel top = new JPanel() {
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				Color c1 = new Color(255, 168, 0, 255); // light
				Color c2 = new Color(255, 120, 0, 255); // dark
				GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
				g2.setPaint(gp);
				g2.fill(new Rectangle(getWidth(), getHeight()));
			}
		};
		top.setPreferredSize(new Dimension(10, 40));
		top.setBackground(new Color(255, 168, 0));
		info.add(top, BorderLayout.NORTH);
		
		JLabel welcome = new JLabel("Welcome, " + username.toUpperCase() + "!");
		welcome.setBorder(new EmptyBorder(0, 10, 0, 0));
		welcome.setHorizontalAlignment(SwingConstants.LEFT);
		welcome.setForeground(new Color(255, 255, 255));
		welcome.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel date = new JLabel(new SimpleDateFormat("EEEE, MMM dd YYYY").format(Calendar.getInstance().getTime()));
		date.setBorder(new EmptyBorder(0, 0, 0, 10));
		date.setHorizontalAlignment(SwingConstants.RIGHT);
		date.setForeground(Color.WHITE);
		date.setFont(new Font("Arial", Font.BOLD, 16));
		top.setLayout(new BorderLayout(0, 0));
		top.add(welcome, BorderLayout.WEST);
		top.add(date, BorderLayout.EAST);
		
		balanceLbl = new JLabel("Balance: $" + balance);
		balanceLbl.setHorizontalAlignment(SwingConstants.CENTER);
		balanceLbl.setForeground(Color.WHITE);
		balanceLbl.setFont(new Font("Dialog", Font.BOLD, 16));
		balanceLbl.setBorder(new EmptyBorder(0, 10, 0, 0));
		top.add(balanceLbl, BorderLayout.CENTER);
		
		JPanel pages = new JPanel();
		pages.setBorder(null);
		info.add(pages, BorderLayout.CENTER);
		pages.setLayout(new CardLayout(0, 0));
		
		refresh();
		TransactionsPage transactionsPage = new TransactionsPage(username, transactions, this);
		pages.add(transactionsPage, "Transactions");
	}
	
	public void refresh() {
		transactions = Database.getTransactions(username);
		balance = new BigDecimal(0);
		for (Transaction i : transactions) {
			balance = balance.add(i.getValue());
		}
		balanceLbl.setText("Balance: $" + balance);
	}
}
