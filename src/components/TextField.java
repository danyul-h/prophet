package components;

import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import java.awt.Dimension;

public class TextField extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField field;
	private JLabel label;

	public void reset() {
		field.setText("");
	}

	public String getText() {
		return field.getText();
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

	public void setFontSize(int size) {
		this.size = size;
		field.setFont(new Font("Arial", Font.PLAIN, size));
	}

	private int size;
	private String title;
	private Color primaryColor;
	private Color secondaryColor;
	private Color fontColor;

	public TextField(String str, Color pri, Color sec, Color font, int size) {
		setMaximumSize(new Dimension(32767, 64));
		setMinimumSize(new Dimension(10, 64));
		setPreferredSize(new Dimension(10, 64));
		this.size = size;
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

		field = new JTextField();
		field.setForeground(fontColor);
		field.setBackground(primaryColor);
		field.setBorder(null);
		field.setCaretColor(fontColor);
		field.setFont(new Font("Arial", Font.PLAIN, size));
		add(field, BorderLayout.CENTER);
		field.setColumns(10);
	}
}
