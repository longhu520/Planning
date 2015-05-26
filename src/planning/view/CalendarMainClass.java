package planning.view;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class CalendarMainClass {
	public static void main(String args[]) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // windows
																							// theme
		} catch (Exception e) {
			e.printStackTrace();
		}
		CalendarFrame frame = new CalendarFrame();
		frame.setBounds(200, 200, 800, 600);
		frame.setTitle("Planning");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
