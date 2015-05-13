package planning.view;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

import planning.model.MyCalendar;

public class CalendarFrame extends JFrame implements ActionListener {
	JMenuBar menubar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenu editMenu = new JMenu("Edit");
	JMenu toolMenu = new JMenu("Tool");
	JMenu helpMenu = new JMenu("Help");
	Choice yearChoice = new Choice();
	Choice monthChoice = new Choice();
	JButton jButton1 = new JButton("OK");
	JLabel dayLabels[] = new JLabel[42];
	JTextField text = new JTextField(10);
	JButton titleName[] = new JButton[7];
	JButton jButton2 = new JButton();
	String weeks[] = { "lundi", "mardi", "mercredi", "jeudi", "vendredi",
			"samedi", "dimanche" };
	int year = 2015, month = 5;
	MyCalendar calendar;

	public CalendarFrame() {
		this.setJMenuBar(menubar);
		menubar.add(fileMenu);
		menubar.add(editMenu);
		menubar.add(toolMenu);
		menubar.add(helpMenu);
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(7, 7));
		for (int i = 0; i < 7; i++) {
			titleName[i] = new JButton(weeks[i]);
			pCenter.add(titleName[i]);
		}
		for (int i = 0; i < 42; i++) {
			JPanel jPanel = new JPanel();
			jPanel.setLayout(new GridLayout(3, 1));
			dayLabels[i] = new JLabel("", JLabel.CENTER);
			JTextField jTextField1 = new JTextField();
			JTextField jTextField2 = new JTextField();
			jPanel.add(dayLabels[i]);
			jPanel.add(jTextField1);
			jPanel.add(jTextField2);
			pCenter.add(jPanel);
//			pCenter.add(dayLabels[i]);
		}

		for (int i = 2010; i <= 2020; i++) {
			yearChoice.addItem(i + "");
		}

		for (int i = 1; i <= 12; i++) {
			monthChoice.addItem(i + "");
		}

		yearChoice.select(String.valueOf(year));
		monthChoice.select(String.valueOf(month));
		text.addActionListener(this);
		calendar = new MyCalendar();
		calendar.setYear(year);
		calendar.setMonth(month);
		String day[] = calendar.getCalendar();

		for (int i = 0; i < 42; i++) {
			dayLabels[i].setText(day[i]);
		}

		jButton1.addActionListener(this);
		JPanel pNorth = new JPanel();
		JPanel pSouth = new JPanel();
		pNorth.add(yearChoice);
		pNorth.add(monthChoice);
		pNorth.add(jButton1);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.add(pCenter);
		add(scrollPane, BorderLayout.CENTER);
		add(pNorth, BorderLayout.NORTH);
		add(pSouth, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jButton1) {
			month = Integer.parseInt(monthChoice.getSelectedItem());
			year = Integer.parseInt(yearChoice.getSelectedItem());
			calendar.setMonth(month);
			calendar.setYear(year);
			String day[] = calendar.getCalendar();

			for (int i = 0; i < 42; i++) {
				dayLabels[i].setText(day[i]);
			}

		}

	}
}
