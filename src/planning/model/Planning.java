package planning.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;

public class Planning extends Observable{
	private Formation formation;
	private ArrayList<Module> modules;
	
	public Planning(){
		formation = new Formation();
		formation.setNom("MIAGE");
		
		modules = new ArrayList<Module>();
		Module module1 = new Module();
		module1.setNom("Processus stochastiques");
		module1.setAbreviation("Stochastique");
		module1.setColor(Color.YELLOW);
		module1.setNombre(38);
		module1.setDuree(2);
		modules.add(module1);
		
		Module module2 = new Module();
		module2.setNom("Theorie des graphes");
		module2.setAbreviation("Graphe");
		module2.setColor(Color.CYAN);
		module2.setNombre(38);
		module2.setDuree(2);
		modules.add(module2);
		
		Module module3 = new Module();
		module3.setNom("Conception et programmation des bases de donnees");
		module3.setAbreviation("BD");
		module3.setColor(Color.GREEN);
		module3.setNombre(38);
		module3.setDuree(2);
		modules.add(module3);
		
		Module module4 = new Module();
		module4.setNom("Programmation orientee objet en Java");
		module4.setAbreviation("Java");
		module4.setColor(Color.MAGENTA);
		module4.setNombre(38);
		module4.setDuree(2);
		modules.add(module4);
		
		Module module5 = new Module();
		module5.setNom("Comptabilite de gestion");
		module5.setAbreviation("Compta");
		module5.setColor(Color.ORANGE);
		module5.setNombre(38);
		module5.setDuree(2);
		modules.add(module5);
		
		Module module6 = new Module();
		module6.setNom("Theorie des organisations");
		module6.setAbreviation("THO");
		module6.setColor(Color.PINK);
		module6.setNombre(21);
		module6.setDuree(2);
		modules.add(module6);
		
		Module module7 = new Module();
		module7.setNom("Programmation avancee et IHM en Java");
		module7.setAbreviation("IHM");
		module7.setColor(Color.RED);
		module7.setNombre(38);
		module7.setDuree(2);
		modules.add(module7);
	}
	
	public ArrayList<Module> getModules(){
		return modules;
	}
}
