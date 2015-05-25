package planning.model;

import java.util.Calendar;
import java.util.Observable;

public class MyCalendar extends Observable{
	private int year;
    private int month;

    public MyCalendar(){}
    
    public MyCalendar(int year, int month){
        this.year = year;
        this.month = month;
        
    }
    
    public void setYear(int year) {
        this.year = year;
        setChanged();
        notifyObservers(this);
    }

    public int getYear() {
        return this.year;
    }

    public void setMonth(int month) {
        this.month = month;
        setChanged();
        notifyObservers(this);
    }

    public int getMonth() {
        return month;
    }

    public String[] getCalendar() {
        String a[] = new String[42];
        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, 1);
        int week = date.get(Calendar.DAY_OF_WEEK) - 2;
        if (week < 0) {
			week = 6;
		}
        int day = 0;

        //les mois de 31 jours  
        if (month == 1 || month == 3 || month == 5 || month == 7
                || month == 8 || month == 10 || month == 12) {
            day = 31;
        }

        //les mois de 30 jours 
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = 30;
        }

        //le mois de fevrier
        if (month == 2) {
            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                day = 29;
            } else {
                day = 28;
            }
        }

        for (int i = week, n = 1; i < week + day; i++) {
            a[i] = String.valueOf(n);
            n++;
        }
        return a;
    }
}
