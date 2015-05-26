package planning.model;

import java.util.ArrayList;

/**
 * Formation
 * @author Piao
 *
 */
public class Formation {
	private String nom;
	private int heures;
	private int jours;
	private ArrayList<Module> modules = new ArrayList<Module>();
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getHeures() {
		int h = 0;
		for (int i = 0; i < modules.size(); i++) {
			h += modules.get(i).getNombre();
		}
		return h; 
	}
	public void setHeures(int heures) {
		this.heures = heures;
	}
	public int getJours() {
		return jours;
	}
	public void setJours(int jours) {
		this.jours = jours;
	}
	public ArrayList<Module> getModules() {
		return modules;
	}
	public void setModules(ArrayList<Module> modules) {
		this.modules = modules;
	}
	
}
