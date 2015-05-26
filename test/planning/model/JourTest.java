package planning.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class JourTest {

	@Test
	public void testGetMorning() {
		Jour jour = new Jour();
		Module module = new Module();
		jour.setMorning(module);
		assertEquals(module, jour.getMorning());
	}

	@Test
	public void testSetMorning() {
		Jour jour = new Jour();
		Module module = new Module();
		jour.setMorning(module);
		assertEquals(module, jour.getMorning());
	}

	@Test
	public void testGetAfternoon() {
		Jour jour = new Jour();
		Module module = new Module();
		jour.setAfternoon(module);
		assertEquals(module, jour.getAfternoon());
	}

	@Test
	public void testSetAfternoon() {
		Jour jour = new Jour();
		Module module = new Module();
		jour.setAfternoon(module);
		assertEquals(module, jour.getAfternoon());
	}

}
