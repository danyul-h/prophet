import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;

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
		setResizable(false);
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
		
		JPanel welcome = new JPanel();
		welcome.setBackground(Color.RED);
		info.add(welcome, BorderLayout.NORTH);
		
		JTabbedPane pages = new JTabbedPane();
		pages.setBorder(null);
		pages.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//		pages.setUI(new BasicTabbedPaneUI() {
//		    protected int calculateTabAreaHeight(int tab_placement, int run_count, int max_tab_height) {
//		    	return 0;  
//		    }
//		});
		pages.setBackground(Color.WHITE);
		info.add(pages, BorderLayout.CENTER);
	}
}
