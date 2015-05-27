package planning.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Module
 * @author Piao
 *
 */
public class Module implements Serializable{
	private String nom;
	private String abreviation;
	private Color color;
	private int nbSeance;
	private int duree;
	private ArrayList<Seance> seances;
	
	public ArrayList<Seance> getSeances() {
		return seances;
	}
	public void setSeances(ArrayList<Seance> seances) {
		this.seances = seances;
	}
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
		this.duree = duree;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getAbreviation() {
		return abreviation;
	}
	public void setAbreviation(String abreviation) {
		this.abreviation = abreviation;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getNbSeance() {
		return nbSeance;
	}
	public void setNbSeance(int nbSeance) {
		this.nbSeance = nbSeance;
	}
	
	public Seance getSeance(Date date, int time){
		Seance seance = null;
		for (int i = 0; i < seances.size(); i++) {
			if (seances.get(i).getDate().getTime() == date.getTime() && seances.get(i).getTime() == time) {
				seance = seances.get(i);
			}
		}
		return seance;
	}
	
}
