package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PasswordField extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPasswordField field;
	private JLabel label;
	
	public String getPassword() {
		return String.valueOf(field.getPassword());
	}
	
	public void setTitle(String str) {
		title = str;
		label.setText(title);
	}
	
	public void setPrimaryColor(Color pri) {
		primaryColor = pri;
		setBackground(primaryColor);
		field.setBackground(primaryColor);
	}
	
	public void setSecondaryColor(Color sec) {
		secondaryColor = sec;
		label.setForeground(secondaryColor);
		setBorder(new CompoundBorder(new LineBorder(secondaryColor), new EmptyBorder(5, 10, 5, 10)));
	}
	
	public void setFontColor(Color font) {
		fontColor = font;
		field.setForeground(font);
	}
	
	private String title;
	private Color primaryColor;
	private Color secondaryColor;
	private Color fontColor;
	
	public PasswordField(String str, Color pri, Color sec, Color font) {
		setMaximumSize(new Dimension(32767, 64));
		setMinimumSize(new Dimension(10, 64));
		setPreferredSize(new Dimension(10, 64));
		title = str;
		primaryColor = pri;
		secondaryColor = sec;
		fontColor = font;
		
		setBackground(primaryColor);
		setBorder(new CompoundBorder(new LineBorder(secondaryColor), new EmptyBorder(5, 10, 5, 10)));
		setBounds(new Rectangle(0, 0, 0, 64));
		setLayout(new BorderLayout(0, 0));
		
		label = new JLabel(title);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setForeground(secondaryColor);
		label.setFont(new Font("Arial", Font.PLAIN, 12));
		add(label, BorderLayout.NORTH);
		
		field = new JPasswordField();
		field.setForeground(fontColor);
		field.setBackground(primaryColor);
		field.setBorder(null);
		field.setCaretColor(fontColor);
		field.setFont(new Font("Arial", Font.PLAIN, 24));
		add(field, BorderLayout.CENTER);
		field.setColumns(10);
	}

}
