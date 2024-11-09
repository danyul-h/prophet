package app;

import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.CardLayout;

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
					App frame = new App();
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
	public App() {
//		setResizable(false);
		setTitle("Scholash");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/icons/dark.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 420);
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
		nav.add(pageBtns, BorderLayout.NORTH);

		JPanel sysBtns = new JPanel();
		nav.add(sysBtns, BorderLayout.SOUTH);

		JPanel info = new JPanel();
		contentPane.add(info, BorderLayout.CENTER);
		info.setLayout(new BorderLayout(0, 0));

		JPanel welcome = new JPanel() {
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				Color c1 = new Color(255, 168, 0, 255); // light
				Color c2 = new Color(255, 120, 0, 255); // dark
				GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
				g2.setPaint(gp);
				g2.fill(new Rectangle(getWidth(), getHeight()));
			}
		};
		welcome.setPreferredSize(new Dimension(10, 40));
		welcome.setBackground(new Color(255, 168, 0));
		info.add(welcome, BorderLayout.NORTH);
		welcome.setLayout(null);
		
		JPanel pages = new JPanel();
		pages.setBorder(null);
		info.add(pages, BorderLayout.CENTER);
		pages.setLayout(new CardLayout(0, 0));
		
		TransactionsTab transactionsTab = new TransactionsTab("admin");
		pages.add(transactionsTab, "Transactions");
	}
}
