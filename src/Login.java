import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import components.TextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.BoxLayout;
import java.awt.Dimension;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import java.awt.Toolkit;
import components.Button;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Panel;
import java.awt.Label;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("Scholash Login");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/icons/fbla.jpg")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 420);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel bg = new JPanel() {
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				Color c1 = new Color(10, 46, 127, 255);
				Color c2 = new Color(29, 82, 188, 255);
				GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
				g2.setPaint(gp);
				g2.fill(new Rectangle(getWidth(), getHeight()));
			}
		};
		bg.setPreferredSize(new Dimension(324, 380));
		contentPane.add(bg, BorderLayout.WEST);
		bg.setLayout(null);
		
		JLabel welcome = new JLabel("Getting Started?");
		welcome.setHorizontalAlignment(SwingConstants.CENTER);
		welcome.setBounds(10, 20, 304, 37);
		welcome.setForeground(new Color(255, 255, 255));
		welcome.setFont(new Font("Arial", Font.BOLD, 32));
		bg.add(welcome);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 68, 324, 2);
		bg.add(separator);
		
		JLabel instructions = new JLabel(""
				+ "<html>"
				+ "To create an account..."
				+ "<ol>"
				+ "<li>Type in a username*</li>"
				+ "<li>Type in a password*</li>"
				+ "<li>Sign up!</li>"
				+ "</ol>"
				+ "To log into account..."
				+ "<ol>"
				+ "<li>Type in your username</li>"
				+ "<li>Type in your password</li>"
				+ "<li>Log in!</li>"
				+ "</ol>"
				+ "</html>");
		instructions.setVerticalAlignment(SwingConstants.TOP);
		instructions.setHorizontalAlignment(SwingConstants.LEFT);
		instructions.setForeground(Color.WHITE);
		instructions.setFont(new Font("Arial", Font.BOLD, 20));
		instructions.setBounds(20, 80, 284, 232);
		bg.add(instructions);
		
		JLabel requirements = new JLabel(""
				+ "<html>"
				+ "*usernames and passwords have to be 32 or less alphanumeric characters."
				+ "</html>");
		requirements.setVerticalAlignment(SwingConstants.BOTTOM);
		requirements.setHorizontalAlignment(SwingConstants.CENTER);
		requirements.setForeground(Color.WHITE);
		requirements.setFont(new Font("Arial", Font.BOLD, 10));
		requirements.setBounds(10, 333, 304, 37);
		bg.add(requirements);

		JPanel login = new JPanel();
		login.setBackground(Color.WHITE);
		login.setPreferredSize(new Dimension(380, 380));
		contentPane.add(login, BorderLayout.CENTER);
		login.setLayout(null);
		
		Button loginBtn = new Button();
		loginBtn.setText("Log In");
		loginBtn.setColorClick(new Color(192, 192, 192));
		loginBtn.setBorderColor(new Color(0, 0, 0));
		loginBtn.setBounds(65, 300, 120, 40);
		login.add(loginBtn);

		TextField username = new TextField((String) null, (Color) null, (Color) null, (Color) null);
		username.setTitle("Username");
		username.setBounds(65, 110, 250, 64);
		login.add(username);

		TextField password = new TextField((String) null, (Color) null, (Color) null, (Color) null);
		password.setTitle("Password");
		password.setBounds(65, 200, 250, 64);
		login.add(password);
		ImageIcon imageIcon = new ImageIcon(Login.class.getResource("/icons/wallet.png")); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		
		Panel top = new Panel();
		FlowLayout flowLayout = (FlowLayout) top.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(20);
		top.setBounds(0, 0, 380, 80);
		login.add(top);
		
		JLabel icon = new JLabel("");
		top.add(icon);
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setIcon(imageIcon);
		
		JLabel title = new JLabel("Scholash Login");
		top.add(title);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 32));
		
		Button signupBtn = new Button();
		signupBtn.setText("Sign Up");
		signupBtn.setBounds(195, 300, 120, 40);
		login.add(signupBtn);
	}
}
