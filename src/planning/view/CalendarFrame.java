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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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

public class CalendarFrame extends JFrame implements ActionListener, Observer {
	private final static String PATH_STRING = "C:/Users/piao/Desktop/mycalendar.dat";
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
	private Jour[] jours;
	private JButton editButton;
	private JButton cancerButton;
	private Planning planning;
	private ArrayList<Module> modules;
	private String[] moduleNames;
	private Choice moduleChoice;
	private int onClickedIndex;
	private ArrayList<MyCalendar> calendarList = new ArrayList<MyCalendar>();
	private JPanel pCenter;

	public CalendarFrame() {
		planning = new Planning();
		modules = planning.getModules();
		moduleNames = new String[modules.size()];
		for (int i = 0; i < modules.size(); i++) {
			moduleNames[i] = modules.get(i).getNom();
		}
		initView();

	}

	private void initView() {
		this.setJMenuBar(menubar);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		menubar.add(fileMenu);
		menubar.add(editMenu);
		menubar.add(toolMenu);
		menubar.add(helpMenu);
		pCenter = new JPanel();
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
		calendarList.add(calendar);
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
			} else {
				textFieldsAM[i].addMouseListener(new onMouseLisener(i));
				textFieldsPM[i].addMouseListener(new onMouseLisener(i));
			}
		}

		jButton1.addActionListener(this);
		saveItem.addActionListener(this);
		openItem.addActionListener(this);
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
			MyCalendar mCalendar = new MyCalendar(year, month);
			if (calendarList.contains(mCalendar)) {
				calendar = calendarList.get(calendarList.indexOf(mCalendar));
			} else {
				mCalendar.addObserver(this);
				calendarList.add(mCalendar);
				calendar = mCalendar;
			}
			calendar.update();
		}else if (e.getSource() == openItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(this);
			File file = fileChooser.getSelectedFile();
			try {
				calendar.deSerialiser(file);
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if (e.getSource() == saveItem) {
			try {
				FileOutputStream fos = new FileOutputStream(PATH_STRING);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(calendar);
				oos.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
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
		final JDialog dialog = new JDialog();
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
		JTextField nombreTextField = new JTextField();
		jPanel.add(nombreTextField);
		editButton = new JButton("Edit");
		jPanel.add(editButton);
		cancerButton = new JButton("Cancer");
		jPanel.add(cancerButton);

		if (e.getSource() == textFieldsAM[index]) {
			if (calendar.getJour(index) != null) {
				if (calendar.getJour(index).getMorning() != null) {
					Module module = calendar.getJour(index).getMorning();
					titleTextField.setText(module.getNom());
					nombreTextField.setText(module.getNombre() + "");
				}
			}
			clickedTextField = textFieldsAM[index];
		} else if (e.getSource() == textFieldsPM[index]) {
			if (calendar.getJour(index) != null) {
				if (calendar.getJour(index).getAfternoon() != null) {
					Module module = calendar.getJour(index).getAfternoon();
					titleTextField.setText(module.getNom());
					nombreTextField.setText(module.getNombre() + "");
				}
			}
			clickedTextField = textFieldsPM[index];
		}
		container.add(jPanel);
		dialog.setBounds(500, 300, 250, 250);
		dialog.setVisible(true);
		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int indexChoice = moduleChoice.getSelectedIndex();
				Module module = modules.get(indexChoice);
				Jour jour;
				if (calendar.getJour(onClickedIndex) == null) {
					jour = new Jour();
				} else {
					jour = calendar.getJour(onClickedIndex);
				}
				if (clickedTextField == textFieldsAM[onClickedIndex]) {
					jour.setMorning(module);
				}
				if (clickedTextField == textFieldsPM[onClickedIndex]) {
					jour.setAfternoon(module);
				}
				calendar.setJour(onClickedIndex, jour);
				dialog.setVisible(false);
				calendar.update();
			}
		});
		cancerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});

	}

	private void popUpMenu(final MouseEvent e, final int index) {
		JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem editItem = new JMenuItem("Edit");
		JMenuItem delMenuItem = new JMenuItem("Delete");
		jPopupMenu.add(editItem);
		jPopupMenu.add(delMenuItem);
		if (e.getSource() == textFieldsAM[index]) {
			jPopupMenu.show(textFieldsAM[index], e.getX(), e.getY());
		} else if (e.getSource() == textFieldsPM[index]) {
			jPopupMenu.show(textFieldsPM[index], e.getX(), e.getY());
		}
		editItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e1) {
				popEditDialog(e, onClickedIndex);
			}
		});
		delMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e2) {
				if (e.getSource() == textFieldsAM[index]) {
					if (calendar.getJour(index) != null) {
						if (calendar.getJour(index).getMorning() != null) {
							calendar.getJour(index).setMorning(null);
						}
					}
				} else if (e.getSource() == textFieldsPM[index]) {
					if (calendar.getJour(index) != null) {
						if (calendar.getJour(index).getAfternoon() != null) {
							calendar.getJour(index).setAfternoon(null);
						}
					}
				}
				calendar.update();
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		day = calendar.getCalendar();
		jours = calendar.getJours();
		for (int i = 0; i < day.length; i++) {
			dayLabels[i].setText(day[i]);
			if (day[i] == null || (i - 5) % 7 == 0 || (i - 6) % 7 == 0) {
				textFieldsAM[i].setText("");
				textFieldsPM[i].setText("");
				textFieldsAM[i].setBackground(Color.GRAY);
				textFieldsPM[i].setBackground(Color.GRAY);

			} else {
				if (jours[i] != null) {
					Jour jour = jours[i];
					if (jour.getMorning() != null) {
						Module amModule = jour.getMorning();
						textFieldsAM[i].setText(amModule.getAbreviation() + " "
								+ amModule.getDuree() + "h");
						textFieldsAM[i].setBackground(amModule.getColor());
					} else {
						textFieldsAM[i].setText("");
						textFieldsAM[i].setBackground(Color.WHITE);
					}
					if (jour.getAfternoon() != null) {
						Module pmModule = jour.getAfternoon();
						textFieldsPM[i].setText(pmModule.getAbreviation() + " "
								+ pmModule.getDuree() + "h");
						textFieldsPM[i].setBackground(pmModule.getColor());
					} else {
						textFieldsPM[i].setText("");
						textFieldsPM[i].setBackground(Color.WHITE);
					}
				} else {
					textFieldsAM[i].setText("");
					textFieldsPM[i].setText("");
					textFieldsAM[i].setBackground(Color.WHITE);
					textFieldsPM[i].setBackground(Color.WHITE);
				}
				textFieldsAM[i].addMouseListener(new onMouseLisener(i));
				textFieldsPM[i].addMouseListener(new onMouseLisener(i));
			}
		}
	}
}
