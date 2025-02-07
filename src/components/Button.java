package components;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton {

	private static final long serialVersionUID = 1L;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		setBackground(color);
	}

	public Color getColorClick() {
		return colorClick;
	}

	public void setColorClick(Color colorClick) {
		this.colorClick = colorClick;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Button() {
		// Init Color
		setFont(new Font("Arial", Font.PLAIN, 16));
		setColor(Color.WHITE);
		colorClick = Color.LIGHT_GRAY;
		setBorder(null);
		setFocusable(false);
		borderColor = Color.black;
		setContentAreaFilled(false);
		// Add event mouse
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				setBackground(colorClick);
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				setBackground(color);
			}
		});
	}

	private Color color;
	private Color colorClick;
	private Color borderColor;
	private int radius = 20;

	@Override
	protected void paintComponent(Graphics grphcs) {
		Graphics2D g2 = (Graphics2D) grphcs;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Paint Border
		g2.setColor(borderColor);
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius + 2, radius + 2);
		g2.setColor(getBackground());
		// Border set 2 Pix
		g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, radius, radius);
		super.paintComponent(grphcs);
	}
}