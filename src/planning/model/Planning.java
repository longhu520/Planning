package planning.model;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class Planning extends Observable implements Serializable{
	private Formation formation;
	private ArrayList<MyCalendar> calendarList;
	
	public ArrayList<MyCalendar> getCalendarList() {
		return calendarList;
	}

	public void setCalendarList(ArrayList<MyCalendar> calendarList) {
		this.calendarList = calendarList;
	}

	public Planning(){
		formation = new Formation();
		formation.setNom("MIAGE");
		calendarList = new ArrayList<MyCalendar>();
		
		ArrayList<Module> modules = new ArrayList<Module>();
		Module module1 = new Module();
		module1.setNom("Processus stochastiques");
		module1.setAbreviation("Stochastique");
		module1.setColor(Color.YELLOW);
		module1.setNbSeance(38);
		module1.setDuree(2);
		ArrayList<Seance> seances1 = new ArrayList<Seance>(module1.getNbSeance());
		module1.setSeances(seances1);
		modules.add(module1);
		
		Module module2 = new Module();
		module2.setNom("Theorie des graphes");
		module2.setAbreviation("Graphe");
		module2.setColor(Color.CYAN);
		module2.setNbSeance(38);
		module2.setDuree(2);
		ArrayList<Seance> seances2 = new ArrayList<Seance>(module2.getNbSeance());
		module2.setSeances(seances2);
		modules.add(module2);
		
		Module module3 = new Module();
		module3.setNom("Conception et programmation des bases de donnees");
		module3.setAbreviation("BD");
		module3.setColor(Color.GREEN);
		module3.setNbSeance(38);
		module3.setDuree(2);
		ArrayList<Seance> seances3 = new ArrayList<Seance>(module3.getNbSeance());
		module3.setSeances(seances3);
		modules.add(module3);
		
		Module module4 = new Module();
		module4.setNom("Programmation orientee objet en Java");
		module4.setAbreviation("Java");
		module4.setColor(Color.MAGENTA);
		module4.setNbSeance(38);
		module4.setDuree(2);
		ArrayList<Seance> seances4 = new ArrayList<Seance>(module4.getNbSeance());
		module4.setSeances(seances4);
		modules.add(module4);
		
		Module module5 = new Module();
		module5.setNom("Comptabilite de gestion");
		module5.setAbreviation("Compta");
		module5.setColor(Color.ORANGE);
		module5.setNbSeance(38);
		module5.setDuree(2);
		ArrayList<Seance> seances5 = new ArrayList<Seance>(module5.getNbSeance());
		module5.setSeances(seances5);
		modules.add(module5);
		
		Module module6 = new Module();
		module6.setNom("Theorie des organisations");
		module6.setAbreviation("THO");
		module6.setColor(Color.PINK);
		module6.setNbSeance(21);
		module6.setDuree(2);
		ArrayList<Seance> seances6 = new ArrayList<Seance>(module6.getNbSeance());
		module6.setSeances(seances6);
		modules.add(module6);
		
		Module module7 = new Module();
		module7.setNom("Programmation avancee et IHM en Java");
		module7.setAbreviation("IHM");
		module7.setColor(Color.RED);
		module7.setNbSeance(38);
		module7.setDuree(2);
		ArrayList<Seance> seances7 = new ArrayList<Seance>(module7.getNbSeance());
		module7.setSeances(seances7);
		modules.add(module7);
		
		formation.setModules(modules);
	}
	
	
	public Formation getFormation(){
		return formation;
	}
}
