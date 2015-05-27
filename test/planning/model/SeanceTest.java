package planning.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class SeanceTest {

	@Test
	public void testConstructeur() {
		Module module = new Module();
		String nomFormation = "MIAGE";
		module.setNom(nomFormation);
		Seance seance = new Seance(module);
		assertEquals(nomFormation, seance.getNom());
	}

	@Test
	public void testGetDate() {
		Module module = new Module();
		Seance seance = new Seance(module);
		Date date = new Date();
		seance.setDate(date);
		assertEquals(date, seance.getDate());
	}

	@Test
	public void testSetDate() {
		Module module = new Module();
		Seance seance = new Seance(module);
		Date date = new Date();
		seance.setDate(date);
		assertEquals(date, seance.getDate());
	}

	@Test
	public void testGetTime() {
		Module module = new Module();
		Seance seance = new Seance(module);
		seance.setTime(Seance.AM);
		assertEquals(Seance.AM, seance.getTime());
	}

	@Test
	public void testSetTime() {
		Module module = new Module();
		Seance seance = new Seance(module);
		seance.setTime(Seance.AM);
		assertEquals(Seance.AM, seance.getTime());
	}


}
