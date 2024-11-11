package app;

import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App("admin");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public App(String username) {
		setMinimumSize(new Dimension(850, 860));
		setTitle("Prophet");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/icons/dark.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 860);
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
		pageBtns.setBackground(new Color(0, 0, 0, 0));
		nav.add(pageBtns, BorderLayout.NORTH);

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
		
		JLabel welcome = new JLabel("Welcome, " + username + "!");
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
		
		JPanel pages = new JPanel();
		pages.setBorder(null);
		info.add(pages, BorderLayout.CENTER);
		pages.setLayout(new CardLayout(0, 0));
		
		TransactionsPage transactionsPage = new TransactionsPage(username);
		pages.add(transactionsPage, "Transactions");
	}
}
