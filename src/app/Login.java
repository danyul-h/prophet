package app;

import components.TextField;
import components.PasswordField;
import components.Button;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import app.Database.Status;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

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
					Login frame = new Login("Classcash Login");
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
	public Login(String s) {
//		setResizable(false);
		setTitle(s);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/icons/dark.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 420);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel bg = new JPanel() {
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				Color c1 = new Color(10, 46, 127, 255); // dark
				Color c2 = new Color(29, 82, 188, 255); // light
				GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
				g2.setPaint(gp);
				g2.fill(new Rectangle(getWidth(), getHeight()));
			}
		};
		bg.setBackground(new Color(0, 64, 128));
		bg.setPreferredSize(new Dimension(324, 380));
		contentPane.add(bg, BorderLayout.WEST);

		JLabel welcome = new JLabel("Getting Started?");
		welcome.setHorizontalAlignment(SwingConstants.CENTER);
		welcome.setForeground(new Color(255, 255, 255));
		welcome.setFont(new Font("Arial", Font.BOLD, 32));

		JLabel instructions = new JLabel("<html>" + "To create an account..." + "<ol>"
				+ "<li>Type in a username*</li>" + "<li>Type in a password*</li>" + "<li>Sign up!</li>" + "</ol>"
				+ "To log into your account..." + "<ol>" + "<li>Type in your username</li>"
				+ "<li>Type in your password</li>" + "<li>Log in!</li>" + "</ol>" + "</html>");
		instructions.setVerticalAlignment(SwingConstants.TOP);
		instructions.setHorizontalAlignment(SwingConstants.LEFT);
		instructions.setForeground(Color.WHITE);
		instructions.setFont(new Font("Arial", Font.BOLD, 20));

		JLabel requirements = new JLabel("<html> *usernames and passwords have to be at least 5 characters and at most 45 characters. </html>");
		requirements.setVerticalAlignment(SwingConstants.BOTTOM);
		requirements.setHorizontalAlignment(SwingConstants.CENTER);
		requirements.setForeground(Color.WHITE);
		requirements.setFont(new Font("Arial", Font.BOLD, 12));

		JSeparator separator = new JSeparator() {
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setPaint(Color.white);
				g2.fill(new Rectangle(getWidth(), getHeight()));
			}
		};
		GroupLayout gl_bg = new GroupLayout(bg);
		gl_bg.setHorizontalGroup(
			gl_bg.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bg.createSequentialGroup()
					.addGap(10)
					.addComponent(welcome, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE))
				.addComponent(separator, GroupLayout.PREFERRED_SIZE, 324, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_bg.createSequentialGroup()
					.addGap(20)
					.addComponent(instructions, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(20, Short.MAX_VALUE))
				.addGroup(gl_bg.createSequentialGroup()
					.addGap(10)
					.addComponent(requirements, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE))
		);
		gl_bg.setVerticalGroup(
			gl_bg.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bg.createSequentialGroup()
					.addGap(20)
					.addComponent(welcome)
					.addGap(11)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 3, GroupLayout.PREFERRED_SIZE)
					.addGap(19)
					.addComponent(instructions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(11)
					.addComponent(requirements, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
					.addGap(11))
		);
		bg.setLayout(gl_bg);

		JPanel login = new JPanel();
		login.setBackground(Color.WHITE);
		login.setPreferredSize(new Dimension(380, 380));
		contentPane.add(login, BorderLayout.CENTER);

		TextField username = new TextField((String) null, (Color) null, (Color) null, (Color) null);
		username.setTitle("Username");

		PasswordField password = new PasswordField((String) null, (Color) null, (Color) null, (Color) null);
		password.setTitle("Password");

		Button loginBtn = new Button();
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				switch (Database.login(username.getText(), password.getPassword())) {
					case Status.UNAVAILABLE: 
						JOptionPane.showMessageDialog(rootPane, 
								"Connection unavailable, try again later.", 
								"Error", 
								JOptionPane.WARNING_MESSAGE);
						break;
					case Status.SUCCESSFUL:
						App app = new App();
						app.setVisible(true);
						dispose();
						break;
					case Status.INVALID:
						JOptionPane.showMessageDialog(rootPane,
								"Invalid username or password", 
								"Error",
								JOptionPane.WARNING_MESSAGE);
						break;
				}
			}
		});

		loginBtn.setText("Log In");
		loginBtn.setColorClick(new Color(192, 192, 192));
		loginBtn.setBorderColor(new Color(0, 0, 0));

		Button signupBtn = new Button();
		signupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				switch (Database.signup(username.getText(), password.getPassword())) {
					case Status.UNAVAILABLE: 
						JOptionPane.showMessageDialog(rootPane, 
								"Connection unavailable, try again later.", 
								"Error", 
								JOptionPane.WARNING_MESSAGE);
						break;
					case Status.SUCCESSFUL:
						JOptionPane.showMessageDialog(rootPane,
								"Sign up successful! Continue to login.",
								"Success",
								JOptionPane.INFORMATION_MESSAGE);
						break;
					case Status.DUPLICATE:
						JOptionPane.showMessageDialog(rootPane, 
								"This username is taken, try a different one.", 
								"Error", 
								JOptionPane.WARNING_MESSAGE);
						break;
					case Status.INVALID:
						JOptionPane.showMessageDialog(rootPane, 
								"Your username or password doesn't meet app guidelines.", 
								"Error", 
								JOptionPane.WARNING_MESSAGE); 
						break;
				}
			}
		});

		signupBtn.setText("Sign Up");

		JPanel top = new JPanel() {
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				Color c1 = new Color(255, 168, 0, 255); // light
				Color c2 = new Color(255, 120, 0, 255); // dark
				GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
				g2.setPaint(gp);
				g2.fill(new Rectangle(getWidth(), separator.getY()));
			}
		};
		top.setBackground(new Color(255, 168, 0));
		FlowLayout flowLayout = (FlowLayout) top.getLayout();
		flowLayout.setVgap(10);

		JPanel bgIcon = new JPanel() {
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				Color c1 = new Color(255, 160, 0, 255); // light
				Color c2 = new Color(255, 140, 0, 255); // dark
				GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
				g2.setPaint(Color.white);
				g2.fillOval(0, 0, getWidth(), getHeight());
				g2.setPaint(gp);
				g2.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
			}
		};
		bgIcon.setPreferredSize(new Dimension(90, 90));
		top.add(bgIcon);

		ImageIcon imageIcon = new ImageIcon(Login.class.getResource("/icons/light.png")); // load the image to a
																							// imageIcon
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(56, 56, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg);
		bgIcon.setLayout(null);
		JLabel icon = new JLabel("");
		icon.setBounds(15, 11, 60, 60);
		bgIcon.add(icon);
		icon.setFont(new Font("Arial", Font.BOLD, 32));
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setIcon(imageIcon);

		JLabel title = new JLabel(s);
		title.setVerticalAlignment(SwingConstants.TOP);
		title.setPreferredSize(new Dimension(270, 70));
		top.add(title);
		title.setForeground(new Color(255, 255, 255));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 32));
		GroupLayout gl_login = new GroupLayout(login);
		gl_login.setHorizontalGroup(
			gl_login.createParallelGroup(Alignment.TRAILING)
				.addComponent(top, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
				.addGroup(gl_login.createSequentialGroup()
					.addGap(65)
					.addComponent(loginBtn, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(signupBtn, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
					.addGap(65))
				.addGroup(gl_login.createSequentialGroup()
					.addGap(65)
					.addGroup(gl_login.createParallelGroup(Alignment.TRAILING)
						.addComponent(username, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
						.addComponent(password, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE))
					.addGap(65))
		);
		gl_login.setVerticalGroup(
			gl_login.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_login.createSequentialGroup()
					.addComponent(top, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addGap(37)
					.addComponent(username, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(password, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_login.createParallelGroup(Alignment.BASELINE)
						.addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addComponent(signupBtn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(41, Short.MAX_VALUE))
		);
		login.setLayout(gl_login);
	}
}
