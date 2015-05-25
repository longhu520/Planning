package planning.model;

import java.awt.Color;

/**
 * Module
 * @author PaulPark
 *
 */
public class Module {
	private String nom;
	private String abreviation;
	private Color color;
	private int nombre;
	
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
	public int getNombre() {
		return nombre;
	}
	public void setNombre(int nombre) {
		this.nombre = nombre;
	}
	
	
}
