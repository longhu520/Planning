package planning.model;

import java.util.Date;

public class Seance extends Module implements Comparable<Seance>{
	public static final int AM = 1;
	public static final int PM = 2;
	private Date date;
	private int time;
	
	public Seance(Module module){
		this.setNom(module.getNom());
		this.setAbreviation(module.getAbreviation());
		this.setColor(module.getColor());
		this.setNbSeance(module.getNbSeance());
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public int compareTo(Seance o) {
		if (this.date.before(o.date)) {
			return -1;
		}else if (this.date.after(o.date)) {
			return 1;
		}else {
			if (this.time < o.time) {
				return -1;
			}else if (this.time > o.time) {
				return 1;
			}else {
				return 0;
			}
		}
	}
}
