package planning.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Formation
 * @author Piao
 *
 */
public class Formation implements Serializable{
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
			h += modules.get(i).getNbSeance();
		}
		return h; 
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
	
	
}
