package planning.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlanningTest {

	@Test
	public void testConstructeur() {
		Planning planning = new Planning();
		assertEquals(7, planning.getModules().size());
	}


	@Test
	public void testGetModules() {
		Planning planning = new Planning();
		assertEquals(7, planning.getModules().size());
	}

}
