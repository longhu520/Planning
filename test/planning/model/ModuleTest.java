package planning.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class ModuleTest {


	@Test
	public void testGetDuree() {
		Module module = new Module();
		int duree = 30;
		module.setDuree(duree);
		assertEquals(duree, module.getDuree());
	}

	@Test
	public void testSetDuree() {
		Module module = new Module();
		int duree = 30;
		module.setDuree(duree);
		assertEquals(duree, module.getDuree());
	}

	@Test
	public void testGetNom() {
		Module module = new Module();
		String nom = "java";
		module.setNom(nom);
		assertEquals(nom, module.getNom());
	}

	@Test
	public void testSetNom() {
		Module module = new Module();
		String nom = "java";
		module.setNom(nom);
		assertEquals(nom, module.getNom());
	}

	@Test
	public void testGetAbreviation() {
		Module module = new Module();
		String abreviation = "java";
		module.setAbreviation(abreviation);
		assertEquals(abreviation, module.getAbreviation());
	}

	@Test
	public void testSetAbreviation() {
		Module module = new Module();
		String abreviation = "java";
		module.setAbreviation(abreviation);
		assertEquals(abreviation, module.getAbreviation());
	}

	@Test
	public void testGetColor() {
		Module module = new Module();
		Color color = Color.BLACK;
		module.setColor(color);
		assertEquals(color, module.getColor());
	}

	@Test
	public void testSetColor() {
		Module module = new Module();
		Color color = Color.BLACK;
		module.setColor(color);
		assertEquals(color, module.getColor());
	}

	@Test
	public void testGetNbSeance() {
		Module module = new Module();
		int nombre = 30;
		module.setNbSeance(nombre);
		assertEquals(nombre, module.getNbSeance());
	}

	@Test
	public void testSetNbSeance() {
		Module module = new Module();
		int nombre = 30;
		module.setNbSeance(nombre);
		assertEquals(nombre, module.getNbSeance());
	}

}
