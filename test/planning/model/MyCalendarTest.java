package planning.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyCalendarTest {

	@Test
    public void testConstructeur() {
        MyCalendar mc = new  MyCalendar();
        int exp = 0;
        int res = mc.getYear();
        assertEquals(exp, res);
    }


    @Test
    public void testSetYear() {
        System.out.println("setYear");
        int year = 2015;
        MyCalendar instance = new MyCalendar();
        instance.setYear(year);
        assertEquals(year, instance.getYear());
    }

    @Test
    public void testGetYear() {
        System.out.println("getYear");
        MyCalendar instance = new MyCalendar();
        int expResult = 0;
        int result = instance.getYear();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetMonth() {
        System.out.println("setMonth");
        int month = 0;
        MyCalendar instance = new MyCalendar();
        instance.setMonth(month);
        assertEquals(month, instance.getMonth());
    }

    @Test
    public void testGetMonth() {
        System.out.println("getMonth");
        MyCalendar instance = new MyCalendar();
        int expResult = 0;
        int result = instance.getMonth();
        assertEquals(expResult, result);
        
    }

    @Test
    public void testGetCalendar() {
        System.out.println("getCalendar");
        MyCalendar instance = new MyCalendar();
        String[] expResult = new String[42];
        String[] result = instance.getCalendar();
        assertArrayEquals(expResult, result);
    }

}
