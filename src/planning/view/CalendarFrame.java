 package planning.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import planning.model.Jour;
import planning.model.Module;
import planning.model.MyCalendar;
import planning.model.Planning;

public class CalendarFrame extends JFrame implements ActionListener,Observer {
	JMenuBar menubar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenu editMenu = new JMenu("Edit");
	JMenu toolMenu = new JMenu("Tool");
	JMenu helpMenu = new JMenu("Help");
	JMenuItem openItem = new JMenuItem("open");
	JMenuItem saveItem = new JMenuItem("save");
	Choice yearChoice = new Choice();
	Choice monthChoice = new Choice();
	JButton jButton1 = new JButton("OK");
	JLabel dayLabels[] = new JLabel[42];
	JTextField textFieldsAM[] = new JTextField[42];
	JTextField textFieldsPM[] = new JTextField[42];
	JTextField clickedTextField;
	JButton titleName[] = new JButton[7];
	JButton jButton2 = new JButton();
	String weeks[] = { "lundi", "mardi", "mercredi", "jeudi", "vendredi",
			"samedi", "dimanche" };
	int year = 2015, month = 5;
	MyCalendar calendar;
	private String[] day;
	private ArrayList<Jour> jours;
	private JButton editButton;
	private JButton cancerButton;
	private JDialog dialog;
	private Planning planning;
	private ArrayList<Module> modules;
	private String[] moduleNames;
	private Choice moduleChoice;
	private int onClickedIndex;
	
	public CalendarFrame() {
		planning = new Planning();
		planning.addObserver(this);
		modules = planning.getModules();
		moduleNames = new String[modules.size()];
		for (int i = 0; i < modules.size(); i++) {
			moduleNames[i] = modules.get(i).getNom();
		}
		initView();
		
	}
	
	private void initView(){
		this.setJMenuBar(menubar);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
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

		for (int i = 2010; i <= 2020; i++) {
			yearChoice.addItem(i + "");
		}

		for (int i = 1; i <= 12; i++) {
			monthChoice.addItem(i + "");
		}
		yearChoice.select(String.valueOf(year));
		monthChoice.select(String.valueOf(month));
		
		calendar = new MyCalendar();
		calendar.setYear(year);
		calendar.setMonth(month);
		day = calendar.getCalendar();
		jours = calendar.getJours();
		calendar.addObserver(CalendarFrame.this);

		for (int i = 0; i < day.length; i++) {
			JPanel jPanel = new JPanel();
			jPanel.setLayout(new GridLayout(3, 1));
			dayLabels[i] = new JLabel("", JLabel.CENTER);
			textFieldsAM[i] = new JTextField();
			textFieldsPM[i] = new JTextField();
			textFieldsAM[i].setEditable(false);
			textFieldsPM[i].setEditable(false);
			textFieldsAM[i].setBackground(Color.WHITE);
			textFieldsPM[i].setBackground(Color.WHITE);
			jPanel.add(dayLabels[i]);
			jPanel.add(textFieldsAM[i]);
			jPanel.add(textFieldsPM[i]);
			pCenter.add(jPanel);
			dayLabels[i].setText(day[i]);
			if (day[i] == null || (i - 5) % 7 == 0 || (i - 6) % 7 == 0) {
				textFieldsAM[i].setBackground(Color.GRAY);
				textFieldsPM[i].setBackground(Color.GRAY);
			}else {
				textFieldsAM[i].addMouseListener(new onMouseLisener(i));
				textFieldsPM[i].addMouseListener(new onMouseLisener(i));
			}
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
		}else if (e.getSource() == editButton) {
			int indexChoice = moduleChoice.getSelectedIndex();
			Module module = modules.get(indexChoice);
			clickedTextField.setText(module.getAbreviation());
			clickedTextField.setBackground(module.getColor());
			if (clickedTextField == textFieldsAM[onClickedIndex]) {
				
			}else if (clickedTextField == textFieldsPM[onClickedIndex]) {

			}
			dialog.setVisible(false);
		}else if (e.getSource() == cancerButton) {
			dialog.setVisible(false);
		}
	}

	private class onMouseLisener implements MouseListener {
		private int index;

		public onMouseLisener(int i) {
			index = i;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			onClickedIndex = index;
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				popEditDialog(e, index);
				break;
			case MouseEvent.BUTTON3:
				popUpMenu(e, index);
				break;
			default:
				break;
			}

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

	}

	private void popEditDialog(MouseEvent e, int index) {
		dialog = new JDialog();
		dialog.setTitle("Edit Module");
		Container container = dialog.getContentPane();
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(4, 2));
		jPanel.add(new JLabel("Module"));
		moduleChoice = new Choice();
		for (int i = 0; i < moduleNames.length; i++) {
			moduleChoice.addItem(moduleNames[i]);
		}
		jPanel.add(moduleChoice);
		jPanel.add(new JLabel("Title"));
		JTextField titleTextField = new JTextField();
		jPanel.add(titleTextField);
		jPanel.add(new JLabel("Nombre"));
		jPanel.add(new JTextField());
		editButton = new JButton("Edit");
		jPanel.add(editButton);
		cancerButton = new JButton("Cancer");
		jPanel.add(cancerButton);
		
		if (e.getSource() == textFieldsAM[index]) {
			clickedTextField = textFieldsAM[index];
		}else if (e.getSource() == textFieldsPM[index]) {
			clickedTextField = textFieldsPM[index];
		}
		editButton.addActionListener(this);
		cancerButton.addActionListener(this);
		container.add(jPanel);
		dialog.setBounds(500, 300, 250, 250);
		dialog.setVisible(true);
	}

	private void popUpMenu(MouseEvent e, int index) {
		JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem editItem = new JMenuItem("Edit");
		JMenuItem delMenuItem = new JMenuItem("Delete");
		jPopupMenu.add(editItem);
		jPopupMenu.add(delMenuItem);
		if (e.getSource() == textFieldsAM[index]) {
			jPopupMenu.show(textFieldsAM[index], e.getX(), e.getY());
		}else if (e.getSource() == textFieldsPM[index]) {
			jPopupMenu.show(textFieldsPM[index], e.getX(), e.getY());
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		day = calendar.getCalendar();
		for (int i = 0; i < day.length; i++) {
			dayLabels[i].setText(day[i]);
			if (day[i] == null || (i - 5) % 7 == 0 || (i - 6) % 7 == 0) {
				textFieldsAM[i].setBackground(Color.GRAY);
				textFieldsPM[i].setBackground(Color.GRAY);
			} else {
				textFieldsAM[i].setBackground(Color.WHITE);
				textFieldsPM[i].setBackground(Color.WHITE);
			}
		}
	}
}
