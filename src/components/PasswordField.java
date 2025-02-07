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

	//components of the password field
	private JPasswordField field;
	private JLabel label;

	//reset the password field, setting the text to empty string
	public void reset() {
		field.setText("");
	}

	//getting the input password from the field
	public String getPassword() {
		return String.valueOf(field.getPassword());
	}

	//setters and getters
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

	//constructing password fields
	public PasswordField(String str, Color pri, Color sec, Color font) {
		//setting dimensions of password field
		setMaximumSize(new Dimension(32767, 64));
		setMinimumSize(new Dimension(10, 64));
		setPreferredSize(new Dimension(10, 64));
		
		//setting the attributes of the passwordfield
		title = str;
		primaryColor = pri;
		secondaryColor = sec;
		fontColor = font;

		//setting background color and border, and layout (how things are arranged inside)
		setBackground(primaryColor);
		setBorder(new CompoundBorder(new LineBorder(secondaryColor), new EmptyBorder(5, 10, 5, 10)));
		setBounds(new Rectangle(0, 0, 0, 64));
		setLayout(new BorderLayout(0, 0));

		//creating the label for the field
		label = new JLabel(title);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setForeground(secondaryColor);
		label.setFont(new Font("Arial", Font.PLAIN, 12));
		add(label, BorderLayout.NORTH);

		//creating the actual input field for the password field
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
