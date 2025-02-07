package app;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import backend.Database;
import backend.Transaction;
import components.Button;

import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;

	//icons in the app
	private static final ImageIcon appIcon = new ImageIcon(App.class.getResource("/icons/app.png"));
	private static final ImageIcon walletIcon = new ImageIcon(App.class.getResource("/icons/wallet.png"));
	private static final ImageIcon pieIcon = new ImageIcon(App.class.getResource("/icons/pie.png"));
	private static final ImageIcon logoutIcon = new ImageIcon(App.class.getResource("/icons/logout.png"));
	private static final ImageIcon pdfIcon = new ImageIcon(App.class.getResource("/icons/pdf.png"));
	
	//components on the app
	private JPanel contentPane; //the pane where all components of the app are held
	private ArrayList<Transaction> transactions; //array list of all the user's transactions
	private BigDecimal balance = new BigDecimal(0); //the number value of the user's balance
	private JLabel balanceLbl; //the label for the user's balance
	private String username; //the user's username
	private PiePage piePage; //the page showing a pie chart report of the user's transactions
	private JPanel pages; //the 

	//constructing the app, taking in a username for input
	public App(String username) {
		this.username = username;

		//setting up dimensions, bordering, position, app icon and background colors.
		setMinimumSize(new Dimension(1050, 850));
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

		//setting up the info panel, where the user will be able to see most of their info either through the top, or 
		JPanel info = new JPanel();
		contentPane.add(info, BorderLayout.CENTER);
		info.setLayout(new BorderLayout());

		//setting up the top header of the app
		JPanel top = new JPanel() {
			//adding a gradient to the header by painting a rectangle gradient inside the top panel
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				Color c1 = new Color(120, 90, 140, 255); // light shade
				Color c2 = new Color(100, 24, 120, 255); // dark shade
				GradientPaint gp = new GradientPaint(0, 0, c2, getWidth(), getHeight(), c1);
				g2.setPaint(gp);
				g2.fill(new Rectangle(getWidth(), getHeight()));
			}
		};
		//setting up the size of the top header
		top.setPreferredSize(new Dimension(10, 40));
		top.setBackground(new Color(255, 168, 0));
		info.add(top, BorderLayout.NORTH);

		//setting up the label welcoming the user, including its border, fonts, etc
		JLabel welcome = new JLabel("Welcome, " + username.toUpperCase() + "!");
		welcome.setBorder(new EmptyBorder(0, 10, 0, 0));
		welcome.setHorizontalAlignment(SwingConstants.LEFT);
		welcome.setForeground(new Color(255, 255, 255));
		welcome.setFont(new Font("Arial", Font.BOLD, 16));

		//setting up the date label, informing them what weekday and date it is
		JLabel date = new JLabel(new SimpleDateFormat("EEEE, MMM dd YYYY").format(Calendar.getInstance().getTime()));
		date.setBorder(new EmptyBorder(0, 0, 0, 10));
		date.setHorizontalAlignment(SwingConstants.RIGHT);
		date.setForeground(Color.WHITE);
		date.setFont(new Font("Arial", Font.BOLD, 16));
		top.setLayout(new BorderLayout(0, 0));
		
		//adding the two prior labels to the top of the app
		top.add(welcome, BorderLayout.WEST);
		top.add(date, BorderLayout.EAST);

		//setting up the balance label, informing the user's current total value after all transactions
		balanceLbl = new JLabel("Balance: $" + balance);
		balanceLbl.setHorizontalAlignment(SwingConstants.CENTER);
		balanceLbl.setForeground(Color.WHITE);
		balanceLbl.setFont(new Font("Dialog", Font.BOLD, 16));
		balanceLbl.setBorder(new EmptyBorder(0, 10, 0, 0));
		
		//adding balance label to the top
		top.add(balanceLbl, BorderLayout.CENTER);

		//content will be split into multiple pages
		pages = new JPanel();
		pages.setBorder(null);
		info.add(pages, BorderLayout.CENTER);
		//so we use a card layout, which will let us change the main pae
		CardLayout pagesLayout = new CardLayout();
		pages.setLayout(pagesLayout);

		//initialize the pie page that will show us the transactions
		piePage = new PiePage(Database.getTransactions(username));
		pages.add(piePage, "pie");
		//refresh in order to make sure all the transactions aligns throughout the pages
		refresh();
		//add the wallet page where you can control transactions
		WalletPage walletPage = new WalletPage(username, transactions, this);
		pages.add(walletPage, "wallet");

		//add the side nav that lets us change the pages
		JPanel nav = new JPanel() {
			//color the nav
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setPaint(new Color(40, 3, 50, 255));
				g2.fill(new Rectangle(getWidth(), getHeight()));
			}
		};
		//set up nav size and aesthetic
		nav.setBorder(null);
		nav.setBackground(new Color(0, 64, 128));
		nav.setPreferredSize(new Dimension(100, 380));
		contentPane.add(nav, BorderLayout.WEST);
		nav.setLayout(new BorderLayout(0, 0));

		//split one part of the nav into page buttons
		JPanel pageBtns = new JPanel();
		pageBtns.setPreferredSize(new Dimension(10, 400));
		FlowLayout flowLayout = (FlowLayout) pageBtns.getLayout();
		flowLayout.setVgap(20);
		pageBtns.setBackground(new Color(0, 0, 0, 0));
		nav.add(pageBtns, BorderLayout.NORTH);

		//add the wallet button to the page buttons with an icon of a wallet
		JLabel walletBtn = new JLabel("");
		walletBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pagesLayout.show(pages, "wallet");
			}
		});
		walletBtn.setIcon(new ImageIcon(walletIcon.getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)));
		pageBtns.add(walletBtn);

		//add the pie button to the page buttons with an icon of a pie chart
		JLabel pieBtn = new JLabel("");
		pieBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pagesLayout.show(pages, "pie");
			}
		});
		pieBtn.setIcon(new ImageIcon(pieIcon.getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)));
		pageBtns.add(pieBtn);

		//another part of the nav will be system buttons
		JPanel sysBtns = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) sysBtns.getLayout();
		sysBtns.setPreferredSize(new Dimension(10, 190));
		flowLayout_1.setVgap(20);
		sysBtns.setBackground(new Color(0, 0, 0, 0));
		nav.add(sysBtns, BorderLayout.SOUTH);

		//add pdf button, outputting and downloading a report
		JLabel pdfBtn = new JLabel("");
		pdfBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//bring up a dialog popup
				PdfDialog dialog = new PdfDialog(transactions, username);
				dialog.setModal(true);
				dialog.setVisible(true);
				if (!dialog.downloaded) {
					//if the user cancelled download, say that
					JOptionPane.showMessageDialog(getParent(), "Download was cancelled and no action has been made.",
							"Cancellation", JOptionPane.INFORMATION_MESSAGE);
				} else {
					//if download successful, say that
					JOptionPane.showMessageDialog(getParent(),
							"Download was successful and you may check your downloads folder!", "Success",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		//pdf button's icon
		pdfBtn.setIcon(new ImageIcon(pdfIcon.getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH)));
		sysBtns.add(pdfBtn);

		//logout button, signs you out and returns to log in section
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

		pagesLayout.show(pages, "wallet");
	}

	public void refresh() {
		//refreshing gets all the transactions from the database
		transactions = Database.getTransactions(username);
		balance = new BigDecimal(0);
		for (Transaction i : transactions) {
			balance = balance.add(i.getValue());
		}
		//also sets up the balance title
		balanceLbl.setText("Balance: $" + new DecimalFormat("#,###.00").format(balance));
		pages.remove(piePage);
		piePage = new PiePage(transactions);
		pages.add(piePage, "pie");
	}
}
