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

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import backend.Database;
import backend.Transaction;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PiePage extends JPanel {

	private static final long serialVersionUID = 1L;
	private int days;
	private ArrayList<Transaction> transactions;
	private ChartPanel pie;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PiePage panel = new PiePage(Database.getTransactions("daniel"));
					JFrame frame = new JFrame();
					frame.getContentPane().add(panel);
					frame.setVisible(true);
					frame.pack();
					System.out.println(frame.getSize());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PiePage(ArrayList<Transaction> transactions) {
		setBackground(new Color(255, 255, 255));
		setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(new Color(0, 0, 0))));
		days = 0;
		this.transactions = transactions;
		setData();

		add(pie, BorderLayout.CENTER);
        JPanel desc = new JPanel();
        desc.setBackground(Color.white);
        add(desc, BorderLayout.SOUTH);
        
        JPanel filter = new JPanel();
        filter.setPreferredSize(new Dimension(200, 10));
        filter.setBackground(Color.white);
        add(filter, BorderLayout.EAST);
	}
	
	private void setData() {
		Map<String, Double> unsortedCosts = new HashMap<String, Double>();
		for(String category : Transaction.getCategories()) {
			double cost = Transaction.getCategoryExpenses(Transaction.filterDayDistance(transactions, days), category);
			if (cost > 0) unsortedCosts.put(category, cost);
		}
		
		Map<String, Double> sortedCosts = sortMapValues(unsortedCosts);
		
		DefaultPieDataset data = new DefaultPieDataset();
		for (String i: sortedCosts.keySet()) {
			data.setValue(i, sortedCosts.get(i));
		}
		
		JFreeChart pieChart = ChartFactory.createPieChart("Categorical Expenses in the Past " + days + " Days", data, true, true, false);
		PiePlot plot = (PiePlot) pieChart.getPlot();
		
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: -${1} ({2})", new DecimalFormat("#.##"), new DecimalFormat("#.##%"));
		plot.setLabelGenerator(gen);
		plot.setBackgroundPaint(Color.white);
		plot.setOutlinePaint(Color.white);
		setLayout(new BorderLayout(0, 0));

		pie = new ChartPanel(pieChart);
		pie.setMinimumDrawWidth(690);
		pie.setMinimumDrawHeight(450);
		pie.setMaximumDrawHeight(450);
		pie.setMaximumDrawWidth(690);
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
