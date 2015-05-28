package planning.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
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
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import jdk.internal.jfr.events.FileWriteEvent;
import planning.model.Jour;
import planning.model.Module;
import planning.model.MyCalendar;
import planning.model.Planning;
import planning.model.Seance;

public class CalendarFrame extends JFrame implements ActionListener, Observer,
		ItemListener {
	private final static String PATH_STRING = "C:/Users/piao/Desktop/mycalendar.dat";
	JMenuBar menubar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenu editMenu = new JMenu("Edit");
	JMenu toolMenu = new JMenu("Tool");
	JMenu helpMenu = new JMenu("Help");
	JMenuItem openItem = new JMenuItem("Open");
	JMenuItem saveItem = new JMenuItem("Save");
	JMenuItem exportItem = new JMenuItem("Export HTML");
	JMenuItem undoItem = new JMenuItem("Undo");
	JMenuItem redoItem = new JMenuItem("Redo");
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
	private ArrayList<MyCalendar> calendarList;
	private JPanel pCenter;
	private UndoManager undoManager;
	private Module copyModule;

	public CalendarFrame() {
		planning = new Planning();
		modules = planning.getFormation().getModules();
		calendarList = planning.getCalendarList();
		moduleNames = new String[modules.size()];
		for (int i = 0; i < modules.size(); i++) {
			moduleNames[i] = modules.get(i).getNom();
		}
		undoManager = new UndoManager();
		initView();

	}

	private void initView() {
		this.setJMenuBar(menubar);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exportItem);
		editMenu.add(undoItem);
		editMenu.add(redoItem);
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
		yearChoice.addItemListener(this);
		monthChoice.addItemListener(this);

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
			textFieldsAM[i].getDocument().addUndoableEditListener(undoManager);
			textFieldsPM[i].getDocument().addUndoableEditListener(undoManager);
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
		exportItem.addActionListener(this);
		undoItem.addActionListener(this);
		redoItem.addActionListener(this);

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
				calendar.addObserver(this);
			} else {
				mCalendar.addObserver(this);
				calendarList.add(mCalendar);
				calendar = mCalendar;
			}
			calendar.update();
		} else if (e.getSource() == openItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(this);
			File file = fileChooser.getSelectedFile();
			try {
				deSerialiser(file);
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == saveItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showSaveDialog(this);
			try {
				FileOutputStream fos = new FileOutputStream(fileChooser
						.getSelectedFile().getAbsolutePath());
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(planning);
				oos.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == exportItem) {
			JFileChooser fileChooser = new JFileChooser();
			File file = new File("planning.html");
			fileChooser.setSelectedFile(file);
			fileChooser.showSaveDialog(this);
			try {
				exportEnHtml(fileChooser.getSelectedFile().getAbsolutePath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == undoItem) {

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
				// popEditDialog(e, index);
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
		titleTextField.setEditable(false);
		jPanel.add(titleTextField);
		jPanel.add(new JLabel("Nombre"));
		JTextField nombreTextField = new JTextField();
		nombreTextField.setEditable(false);
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
					nombreTextField.setText(module.getNbSeance() + "");
				}
			}
			clickedTextField = textFieldsAM[index];
		} else if (e.getSource() == textFieldsPM[index]) {
			if (calendar.getJour(index) != null) {
				if (calendar.getJour(index).getAfternoon() != null) {
					Module module = calendar.getJour(index).getAfternoon();
					titleTextField.setText(module.getNom());
					nombreTextField.setText(module.getNbSeance() + "");
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
				addSeance(module, onClickedIndex);
				dialog.setVisible(false);
			}
		});
		cancerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
	}

	/**
	 * Placer une seance sur un jour choisi
	 * 
	 * @param module
	 * @param onClickedIndex
	 */
	private void addSeance(Module module, int onClickedIndex) {
		if (module.getSeances().size() < module.getNbSeance()) {
			Seance seance = new Seance(module);
			Jour jour;
			if (calendar.getJour(onClickedIndex) == null) {
				jour = new Jour();
			} else {
				jour = calendar.getJour(onClickedIndex);
			}
			if (clickedTextField == textFieldsAM[onClickedIndex]) {
				jour.setMorning(module);
				seance.setTime(Seance.AM);
			}
			if (clickedTextField == textFieldsPM[onClickedIndex]) {
				jour.setAfternoon(module);
				seance.setTime(Seance.PM);
			}
			Date date = new Date(year, month - 1,
					Integer.parseInt(dayLabels[onClickedIndex].getText()));
			seance.setDate(date);
			module.getSeances().add(seance);
			calendar.setJour(onClickedIndex, jour);
			calendar.update();
		} else {
			JDialog alertDialog = new JDialog();
			alertDialog.setTitle("Error!");
			Container container = alertDialog.getContentPane();
			JLabel alertLabel = new JLabel("Le nombre de seance est depasse.");
			alertLabel.setBackground(Color.RED);
			container.add(alertLabel);
			alertDialog.setBounds(500, 300, 250, 100);
			alertDialog.setVisible(true);
		}
	}

	private void popUpMenu(final MouseEvent e, final int index) {
		JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem editItem = new JMenuItem("Edit");
		JMenuItem delMenuItem = new JMenuItem("Delete");
		JMenuItem copyItem = new JMenuItem("Copy");
		JMenuItem pasteItem = new JMenuItem("Paste");
		jPopupMenu.add(editItem);
		jPopupMenu.add(delMenuItem);
		jPopupMenu.add(copyItem);
		jPopupMenu.add(pasteItem);
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
							Module module = calendar.getJour(index)
									.getMorning();
							Seance seance = module.getSeance(
									new Date(year, month - 1, Integer
											.parseInt(dayLabels[index]
													.getText())), Seance.AM);
							module.getSeances().remove(seance);
							calendar.getJour(index).setMorning(null);

						}
					}
				} else if (e.getSource() == textFieldsPM[index]) {
					if (calendar.getJour(index) != null) {
						if (calendar.getJour(index).getAfternoon() != null) {
							Module module = calendar.getJour(index)
									.getAfternoon();
							Seance seance = module.getSeance(
									new Date(year, month - 1, Integer
											.parseInt(dayLabels[index]
													.getText())), Seance.PM);
							module.getSeances().remove(seance);
							calendar.getJour(index).setAfternoon(null);
						}
					}
				}
				calendar.update();
			}
		});
		copyItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e3) {
				if (e.getSource() == textFieldsAM[index]) {
					if (calendar.getJour(index) != null) {
						if (calendar.getJour(index).getMorning() != null) {
							copyModule = calendar.getJour(index).getMorning();
						}
					}
				} else if (e.getSource() == textFieldsPM[index]) {
					if (calendar.getJour(index) != null) {
						if (calendar.getJour(index).getAfternoon() != null) {
							copyModule = calendar.getJour(index)
									.getAfternoon();
						}
					}
				}
			}
		});
		pasteItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e4) {
				if (copyModule != null) {
					if (e.getSource() == textFieldsAM[index]) {
						clickedTextField = textFieldsAM[index];
					}else if (e.getSource() == textFieldsPM[index]) {
						clickedTextField = textFieldsPM[index];
					}
					addSeance(copyModule, index);
				}
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
						ArrayList<Seance> seanceList = amModule.getSeances();
						Seance seance = amModule.getSeance(
								new Date(year, month - 1, Integer
										.parseInt(dayLabels[i].getText())),
								Seance.AM);
						Collections.sort(seanceList);
						int numSeance = seanceList.indexOf(seance) + 1;
						textFieldsAM[i].setText(amModule.getAbreviation() + " "
								+ amModule.getDuree() + "h" + " " + numSeance
								+ "/" + amModule.getNbSeance());
						textFieldsAM[i].setBackground(amModule.getColor());
					} else {
						textFieldsAM[i].setText("");
						textFieldsAM[i].setBackground(Color.WHITE);
					}
					if (jour.getAfternoon() != null) {
						Module pmModule = jour.getAfternoon();
						ArrayList<Seance> seanceList = pmModule.getSeances();
						Seance seance = pmModule.getSeance(
								new Date(year, month - 1, Integer
										.parseInt(dayLabels[i].getText())),
								Seance.PM);
						Collections.sort(seanceList);
						int numSeance = seanceList.indexOf(seance) + 1;
						textFieldsPM[i].setText(pmModule.getAbreviation() + " "
								+ pmModule.getDuree() + "h" + " " + numSeance
								+ "/" + pmModule.getNbSeance());
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

	public void deSerialiser(File file) throws IOException,
			ClassNotFoundException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		planning = (Planning) ois.readObject();
		calendarList = planning.getCalendarList();
		modules = planning.getFormation().getModules();
		month = Integer.parseInt(monthChoice.getSelectedItem());
		year = Integer.parseInt(yearChoice.getSelectedItem());
		MyCalendar mCalendar = new MyCalendar(year, month);
		if (calendarList.contains(mCalendar)) {
			calendar = calendarList.get(calendarList.indexOf(mCalendar));
			calendar.addObserver(this);
		}
		calendar.update();
		ois.close();
	}

	/**
	 * export planning en HTML
	 * 
	 * @param path
	 *            : destination file
	 * @throws IOException
	 */
	public void exportEnHtml(String path) throws IOException {
		FileWriter fileWriter = new FileWriter(new File(path));
		BufferedWriter bw = new BufferedWriter(fileWriter);
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("<html><body>");
		sBuffer.append("<h1 align=" + "center" + ">");
		sBuffer.append(planning.getFormation().getNom() + " ");
		sBuffer.append(year + "-" + month);
		sBuffer.append("</h1>");
		sBuffer.append("<table border=" + "1" + ">");
		sBuffer.append("<tr height=30px>");
		for (int i = 0; i < weeks.length; i++) {
			sBuffer.append("<th width=" + "100px" + ">");
			sBuffer.append(weeks[i]);
			sBuffer.append("</th>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 0; i < weeks.length; i++) {
			sBuffer.append("<td align=" + "center" + ">");
			if (day[i] == null) {
				sBuffer.append("");
			} else {
				sBuffer.append(day[i]);
			}

			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 0; i < weeks.length; i++) {
			sBuffer.append("<td align=" + "center");
			sBuffer.append(" bgcolor="
					+ getHxColor(textFieldsAM[i].getBackground()) + ">");
			sBuffer.append(textFieldsAM[i].getText());
			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 0; i < weeks.length; i++) {
			sBuffer.append("<td align=" + "center");
			sBuffer.append(" bgcolor="
					+ getHxColor(textFieldsPM[i].getBackground()) + ">");
			sBuffer.append(textFieldsPM[i].getText());
			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 7; i < weeks.length + 7; i++) {
			sBuffer.append("<td align=" + "center" + ">");
			if (day[i] == null) {
				sBuffer.append("");
			} else {
				sBuffer.append(day[i]);
			}

			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 7; i < weeks.length + 7; i++) {
			sBuffer.append("<td align=" + "center");
			sBuffer.append(" bgcolor="
					+ getHxColor(textFieldsAM[i].getBackground()) + ">");
			sBuffer.append(textFieldsAM[i].getText());
			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 7; i < weeks.length + 7; i++) {
			sBuffer.append("<td align=" + "center");
			sBuffer.append(" bgcolor="
					+ getHxColor(textFieldsPM[i].getBackground()) + ">");
			sBuffer.append(textFieldsPM[i].getText());
			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 14; i < weeks.length + 14; i++) {
			sBuffer.append("<td align=" + "center" + ">");
			if (day[i] == null) {
				sBuffer.append("");
			} else {
				sBuffer.append(day[i]);
			}

			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 14; i < weeks.length + 14; i++) {
			sBuffer.append("<td align=" + "center");
			sBuffer.append(" bgcolor="
					+ getHxColor(textFieldsAM[i].getBackground()) + ">");
			sBuffer.append(textFieldsAM[i].getText());
			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 14; i < weeks.length + 14; i++) {
			sBuffer.append("<td align=" + "center");
			sBuffer.append(" bgcolor="
					+ getHxColor(textFieldsPM[i].getBackground()) + ">");
			sBuffer.append(textFieldsPM[i].getText());
			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 21; i < weeks.length + 21; i++) {
			sBuffer.append("<td align=" + "center" + ">");
			if (day[i] == null) {
				sBuffer.append("");
			} else {
				sBuffer.append(day[i]);
			}

			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 21; i < weeks.length + 21; i++) {
			sBuffer.append("<td align=" + "center");
			sBuffer.append(" bgcolor="
					+ getHxColor(textFieldsAM[i].getBackground()) + ">");
			sBuffer.append(textFieldsAM[i].getText());
			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 21; i < weeks.length + 21; i++) {
			sBuffer.append("<td align=" + "center");
			sBuffer.append(" bgcolor="
					+ getHxColor(textFieldsPM[i].getBackground()) + ">");
			sBuffer.append(textFieldsPM[i].getText());
			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 28; i < weeks.length + 28; i++) {
			sBuffer.append("<td align=" + "center" + ">");
			if (day[i] == null) {
				sBuffer.append("");
			} else {
				sBuffer.append(day[i]);
			}

			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 28; i < weeks.length + 28; i++) {
			sBuffer.append("<td align=" + "center");
			sBuffer.append(" bgcolor="
					+ getHxColor(textFieldsAM[i].getBackground()) + ">");
			sBuffer.append(textFieldsAM[i].getText());
			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("<tr height=30px>");
		for (int i = 28; i < weeks.length + 28; i++) {
			sBuffer.append("<td align=" + "center");
			sBuffer.append(" bgcolor="
					+ getHxColor(textFieldsPM[i].getBackground()) + ">");
			sBuffer.append(textFieldsPM[i].getText());
			sBuffer.append("</td>");
		}
		sBuffer.append("</tr>");

		sBuffer.append("</table></body></html>");
		bw.write(sBuffer.toString());
		bw.close();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == yearChoice) {
			year = Integer.parseInt(e.getItem().toString());
		} else if (e.getSource() == monthChoice) {
			month = Integer.parseInt(e.getItem().toString());
		}

	}

	/**
	 * convert color to hex
	 * 
	 * @param color
	 * @return
	 */
	public String getHxColor(Color color) {
		String R, G, B;
		StringBuffer sb = new StringBuffer();
		R = Integer.toHexString(color.getRed());
		G = Integer.toHexString(color.getGreen());
		B = Integer.toHexString(color.getBlue());

		R = R.length() == 1 ? "0" + R : R;
		G = G.length() == 1 ? "0" + G : G;
		B = B.length() == 1 ? "0" + B : B;

		sb.append("#");
		sb.append(R);
		sb.append(G);
		sb.append(B);
		return sb.toString();

	}

}
