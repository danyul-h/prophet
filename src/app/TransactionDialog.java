package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.Transaction;
import components.Button;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class TransactionDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * Create the dialog.
	 */
	private Transaction transaction;
	
	public Transaction getTransaction() {
		return transaction;
	}

	public TransactionDialog(Transaction entry) {
		this.transaction = entry;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			Button saveBtn = new Button();
			saveBtn.setPreferredSize(new Dimension(70, 32));
			saveBtn.setText("Save");
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			buttonPane.add(saveBtn);
			
			Button cancelBtn = new Button();
			cancelBtn.setPreferredSize(new Dimension(70, 32));
			cancelBtn.setText("Cancel");
			cancelBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			buttonPane.add(cancelBtn);
		}
		this.setLocationRelativeTo(null);
	}

}
