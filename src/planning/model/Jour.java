package planning.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Jour
 * @author Piao
 *
 */
public class Jour implements Serializable{
	private Module morning;
	private Module afternoon;
	
	public Module getMorning() {
		return morning;
	}
	public void setMorning(Module morning) {
		this.morning = morning;
	}
	public Module getAfternoon() {
		return afternoon;
	}
	public void setAfternoon(Module afternoon) {
		this.afternoon = afternoon;
	}
	
	
}
