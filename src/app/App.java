package app;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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

import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final ImageIcon appIcon = new ImageIcon(App.class.getResource("/icons/app.png"));
	private static final ImageIcon walletIcon = new ImageIcon(App.class.getResource("/icons/wallet.png"));
	private static final ImageIcon pieIcon = new ImageIcon(App.class.getResource("/icons/pie.png"));
	private static final ImageIcon logoutIcon = new ImageIcon(App.class.getResource("/icons/logout.png"));
	
	private JPanel contentPane;
	private ArrayList<Transaction> transactions;
	private BigDecimal balance = new BigDecimal(0);
	private JLabel balanceLbl;
	private String username;
	
	public App(String username) {
		this.username = username;

		setMinimumSize(new Dimension(850, 770));
		setTitle("Prophet");
		setIconImage(appIcon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 810);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel info = new JPanel();
		contentPane.add(info, BorderLayout.CENTER);
		info.setLayout(new BorderLayout());

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
		CardLayout pagesLayout = new CardLayout();
		pages.setLayout(pagesLayout);
		
		refresh();
		WalletPage walletPage = new WalletPage(username, transactions, this);
		PiePage piePage = new PiePage(transactions);
		pages.add(walletPage, "wallet");
		pages.add(piePage, "pie");
		
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
		nav.setPreferredSize(new Dimension(100, 380));
		contentPane.add(nav, BorderLayout.WEST);
		nav.setLayout(new BorderLayout(0, 0));

		JPanel pageBtns = new JPanel();
		pageBtns.setPreferredSize(new Dimension(10, 400));
		FlowLayout flowLayout = (FlowLayout) pageBtns.getLayout();
		flowLayout.setVgap(20);
		pageBtns.setBackground(new Color(0, 0, 0, 0));
		nav.add(pageBtns, BorderLayout.NORTH);
		
		JLabel walletBtn = new JLabel("");
		walletBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pagesLayout.show(pages, "wallet");
			}
		});
		walletBtn.setIcon(new ImageIcon(walletIcon.getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)));
		pageBtns.add(walletBtn);
		
		JLabel pieBtn = new JLabel("");
		pieBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pagesLayout.show(pages, "pie");
			}
		});
		pieBtn.setIcon(new ImageIcon(pieIcon.getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)));
		pageBtns.add(pieBtn);

		JPanel sysBtns = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) sysBtns.getLayout();
		flowLayout_1.setVgap(20);
		sysBtns.setBackground(new Color(0, 0, 0, 0));
		nav.add(sysBtns, BorderLayout.SOUTH);
		
		JLabel logout = new JLabel("");
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				Login login = new Login();
				login.setVisible(true);
			}
		});
		logout.setIcon(new ImageIcon(logoutIcon.getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)));
		sysBtns.add(logout);
		
		
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
