package ru.neyagodamalina.nevermind.business;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import ru.neyagodamalina.nevermind.business.exception.WrongTimeStopTimeStartException;

import static org.junit.Assert.*;

/**
 * Created by developer on 03.08.2017.
 */
public class TimeSlotTest {
    @Test
    public void getPossibleFormat() throws Exception {

    }

    @Test
    public void toStringPeriodMoreOneDay() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 1, 1, 1);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 2, 1, 2, 1);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals("01.03.17 1:01-02.03.17 1:02", timeSlot.toStringPeriod());
    }

    @Test
    public void toStringPeriodLessOneDay() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 1, 1, 1);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 3, 1, 1);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals("01.03.17 1:01-3:01", timeSlot.toStringPeriod());
    }

    @Test
    public void toDaysPart() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.APRIL, 2, 0, 0, 0);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals(1, timeSlot.toDaysPart());
    }

    @Test
    public void toHours() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 1, 1, 1);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 2, 1, 1);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(timeSlot.toStringCalendarUTC());
        assertEquals(1, timeSlot.toHours());
    }

    @Test
    public void toDays() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 1, 1, 1);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.APRIL, 2, 1, 1, 1);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals(32, timeSlot.toDays());
    }

    @Test
    public void toMonths() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 1, 1, 1);
        Calendar stop = Calendar.getInstance();
        stop.set(2018, Calendar.MARCH, 1, 1, 1, 1);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals(12, timeSlot.toMonths());
    }


    @Test
    public void toStringDuration1() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 32);
        Calendar stop = Calendar.getInstance();
        stop.set(2020, Calendar.APRIL, 2, 14, 21, 33);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        StringBuffer actual = new StringBuffer()
                .append(timeSlot.toYearsPart())
                .append(timeSlot.toMonthPart())
                .append(timeSlot.toDaysPart())
                .append(timeSlot.toHoursPart())
                .append(timeSlot.toMinutesPart())
                .append(timeSlot.toSecondsPart());

        assertEquals("311111", actual.toString()); // 3y1m1d1h1min1s
    }

    @Test
    public void toStringDuration2() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 13, 20, 32);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        StringBuffer actual = new StringBuffer()
                .append(timeSlot.toYearsPart())
                .append(timeSlot.toMonthPart())
                .append(timeSlot.toDaysPart())
                .append(timeSlot.toHoursPart())
                .append(timeSlot.toMinutesPart())
                .append(timeSlot.toSecondsPart());

        assertEquals("000001", actual.toString());
    }

    @Test
    public void toStringDuration3() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 16, 59, 12);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(timeSlot.toStringCalendarUTC());
        StringBuffer actual = new StringBuffer()
                .append(timeSlot.toYearsPart())
                .append(timeSlot.toMonthPart())
                .append(timeSlot.toDaysPart())
                .append(timeSlot.toHoursPart())
                .append(timeSlot.toMinutesPart())
                .append(timeSlot.toSecondsPart());

        assertEquals("00033841", actual.toString());
    }
    @Test
    public void toStringDurationZero() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        StringBuffer actual = new StringBuffer()
                .append(timeSlot.toYearsPart())
                .append(timeSlot.toMonthPart())
                .append(timeSlot.toDaysPart())
                .append(timeSlot.toHoursPart())
                .append(timeSlot.toMinutesPart())
                .append(timeSlot.toSecondsPart());

        assertEquals("000000", actual.toString());
    }


    @Test(expected = WrongTimeStopTimeStartException.class)
    public void wrongTimeStopTimeStart() throws Exception{
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.FEBRUARY, 1, 13, 20, 31);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
    }

    @Test
    public void nullTimeSlot() throws Exception{
        TimeSlot timeSlot = new TimeSlot();
        System.out.println(timeSlot.toString());
    }
}