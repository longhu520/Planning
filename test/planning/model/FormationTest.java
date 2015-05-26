package planning.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class FormationTest {

	@Test
	public void testGetNom() {
		String nom = "MIAGE";
		Formation instance = new Formation();
		instance.setNom(nom);
		assertEquals(nom, instance.getNom());
	}

	@Test
	public void testSetNom() {
		String nom = "MIAGE";
		Formation instance = new Formation();
		instance.setNom(nom);
		assertEquals(nom, instance.getNom());
	}

	@Test
	public void testGetHeures() {
		Formation instance = new Formation();
		assertEquals(0, instance.getHeures());
	}


	@Test
	public void testGetJours() {
		int jours = 30;
		Formation instance = new Formation();
		instance.setJours(jours);
		assertEquals(jours, instance.getJours());
	}

	@Test
	public void testSetJours() {
		int jours = 30;
		Formation instance = new Formation();
		instance.setJours(jours);
		assertEquals(jours, instance.getJours());
	}

	@Test
	public void testGetModules() {
		Formation instance = new Formation();
		assertEquals(0, instance.getModules().size());
	}

}
