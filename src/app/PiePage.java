package app;

import java.awt.Color;
import java.awt.EventQueue;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import backend.Transaction;
import java.awt.BorderLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import components.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PiePage extends JPanel {
	
	enum Type{
		Expenses,
		Income,
	}
	
	private static final long serialVersionUID = 1L;
	private int days;
	private Type chartType;
	private ArrayList<Transaction> transactions;
	private JComboBox<Type> categoryField;
	private JComboBox<Integer> dayField;
	private ChartPanel pie;

	public PiePage(ArrayList<Transaction> transactions) {
		days = 30;
		this.transactions = transactions;
		
		setBackground(Color.white);
		setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), null));
        setLayout(new BorderLayout());
        
        JPanel top = new JPanel();
        top.setBackground(new Color(255, 255, 255));
        top.setBorder(new LineBorder(new Color(0, 0, 0)));
        top.setPreferredSize(new Dimension(10, 130));
        add(top, BorderLayout.CENTER);
        top.setLayout(new BorderLayout(0, 0));
        
        JPanel settings = new JPanel();
        settings.setBackground(new Color(255, 255, 255));
        settings.setPreferredSize(new Dimension(400, 10));
        top.add(settings, BorderLayout.WEST);
        settings.setLayout(null);
        
        categoryField = new JComboBox<Type>(new Type[]{Type.Expenses, Type.Income}) ;
        categoryField.setSelectedIndex(0);
        categoryField.setBorder(new LineBorder(new Color(0, 0, 0)));
        categoryField.setBackground(new Color(255, 255, 255));
        categoryField.setBounds(103, 52, 185, 32);
        settings.add(categoryField);
        
        JLabel typeLbl = new JLabel("Chart Type: ");
        typeLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        typeLbl.setBounds(12, 52, 116, 32);
        settings.add(typeLbl);
        
        JLabel dateLbl = new JLabel("Date Range: Past");
        dateLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        dateLbl.setBounds(12, 90, 165, 32);
        settings.add(dateLbl);
        
        JLabel dayLbl = new JLabel("days");
        dayLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        dayLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        dayLbl.setBounds(228, 90, 60, 32);
        settings.add(dayLbl);
        
        dayField = new JComboBox<Integer>(new Integer[]{7, 14, 21, 28, 30, 60, 120, 180, 365});
        dayField.setSelectedIndex(4);
        dayField.setBackground(new Color(255, 255, 255));
        dayField.setBorder(new LineBorder(new Color(0, 0, 0)));
        dayField.setBounds(145, 90, 100, 32);
        settings.add(dayField);
        
        JLabel settingsLbl = new JLabel("Chart Settings");
        settingsLbl.setFont(new Font("Arial", Font.BOLD, 24));
        settingsLbl.setBounds(12, 12, 185, 32);
        settings.add(settingsLbl);
        
        JPanel piePanel = new JPanel();
        piePanel.setBackground(new Color(255, 255, 255));
        piePanel.setBorder(new CompoundBorder(new EmptyBorder(5, 0, 0, 0), new LineBorder(new Color(0, 0, 0))));
        add(piePanel, BorderLayout.SOUTH);
        
        setData();
        pie.setLayout(new BorderLayout(0, 0));
        piePanel.add(pie); 

        Button btnApply = new Button();
        btnApply.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		piePanel.remove(pie);
        		setData();
        		piePanel.add(pie);
        	}
        });
        btnApply.setRadius(40);
        btnApply.setText("Apply");
        btnApply.setBounds(203, 12, 185, 32);
        settings.add(btnApply);
	}
	
	private void setData() {
		chartType = (Type) categoryField.getSelectedItem();
		days = (Integer) dayField.getSelectedItem();
		System.out.println(chartType + ", " + days);
		Map<String, Double> unsortedData = new HashMap<String, Double>();
		for(String category : Transaction.getCategories()) {
			double cost = Transaction.getCategoryExpenses(Transaction.filterDayDistance(transactions, days), category);
			double income = Transaction.getCategoryIncomes(Transaction.filterDayDistance(transactions, days), category);
			switch(chartType) {
				case Type.Expenses: if(cost > 0) unsortedData.put(category, cost);
				case Type.Income: if (income > 0) unsortedData.put(category, income);
			}
		}
		
		Map<String, Double> sortedData = sortMapValues(unsortedData);
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (String i: sortedData.keySet()) {
			dataset.setValue(i, sortedData.get(i));
		}
		
		JFreeChart pieChart = ChartFactory.createPieChart("Categorical " + chartType + " in the Past " + days + " Days", dataset, true, true, false);
		PiePlot plot = (PiePlot) pieChart.getPlot();
		
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: ${1} ({2})", new DecimalFormat("#,###.##"), new DecimalFormat("#,###.##%"));
		plot.setLabelGenerator(gen);
		plot.setBackgroundPaint(Color.white);
		plot.setOutlinePaint(Color.white);

		pie = new ChartPanel(pieChart);
		pie.setBackground(new Color(255, 255, 255));
		pie.setPreferredSize(new Dimension(920, 600));
		pie.setBorder(null);
	}	
	
	private Map<String, Double> sortMapValues(Map<String, Double> unsortedMap){
		List<java.util.Map.Entry<String, Double>> list = new LinkedList<java.util.Map.Entry<String, Double>>(unsortedMap.entrySet());
		
		// Sorting the list based on values
		Collections.sort(list, new Comparator<java.util.Map.Entry<String, Double>>(){
			public int compare(java.util.Map.Entry<String, Double> o1, java.util.Map.Entry<String, Double> o2){
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		// Maintaining insertion order with the help of LinkedList
		Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		for (java.util.Map.Entry<String, Double> entry : list){
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}
}
