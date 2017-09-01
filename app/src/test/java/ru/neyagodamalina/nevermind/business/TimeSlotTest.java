package ru.neyagodamalina.nevermind.business;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;

import ru.neyagodamalina.nevermind.business.exception.WrongTimeStopTimeStartException;
import ru.neyagodamalina.nevermind.business.util.FormatDuration;

import static org.junit.Assert.*;

/**
 * Created by developer on 03.08.2017.
 */
public class TimeSlotTest {


    @Test
    public void getPossibleFormats5() throws Exception {
        //0y 0m 0d 0h 1m 59s

        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.JANUARY, 1, 0, 1, 59);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(timeSlot.toString());
        System.out.println(timeSlot.getPossibleFormats());
        List<Integer> expectedFormats = Arrays.asList(
                FormatDuration.FORMAT_SMART
//                FormatDuration.FORMAT_YEARS,
//                FormatDuration.FORMAT_MONTHS,
//                FormatDuration.FORMAT_DAYS,
//                FormatDuration.FORMAT_HOURS,
//                FormatDuration.FORMAT_MINUTES
        );
        assertEquals(expectedFormats, timeSlot.getPossibleFormats());
    }


    @Test
    public void getPossibleFormats4() throws Exception {
        //0y 0m 0d 23h 59m 59s

        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.JANUARY, 1, 23, 59, 59);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(timeSlot.toString());
        System.out.println(timeSlot.getPossibleFormats());
        List<Integer> expectedFormats = Arrays.asList(
                FormatDuration.FORMAT_SMART,
//                FormatDuration.FORMAT_YEARS,
//                FormatDuration.FORMAT_MONTHS,
//                FormatDuration.FORMAT_DAYS,
//                FormatDuration.FORMAT_HOURS,
                FormatDuration.FORMAT_MINUTES);
        assertEquals(expectedFormats, timeSlot.getPossibleFormats());
    }

    @Test
    public void getPossibleFormats3() throws Exception {
        //0y 0m 30d 23h 59m 59s

        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.JANUARY, 31, 23, 59, 59);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(timeSlot.toString());
        System.out.println(timeSlot.getPossibleFormats());
        List<Integer> expectedFormats = Arrays.asList(
                FormatDuration.FORMAT_SMART,
//                FormatDuration.FORMAT_YEARS,
//                FormatDuration.FORMAT_MONTHS,
//                FormatDuration.FORMAT_DAYS,
                FormatDuration.FORMAT_HOURS,
                FormatDuration.FORMAT_MINUTES);
        assertEquals(expectedFormats, timeSlot.getPossibleFormats());
    }

    @Test
    public void getPossibleFormats2() throws Exception {
        //0y 11m 30d 23h 59m 59s

        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.DECEMBER, 31, 23, 59, 59);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(timeSlot.toString());
        System.out.println(timeSlot.getPossibleFormats());
        List<Integer> expectedFormats = Arrays.asList(
                FormatDuration.FORMAT_SMART,
//                FormatDuration.FORMAT_YEARS,
//                FormatDuration.FORMAT_MONTHS,
                FormatDuration.FORMAT_DAYS,
                FormatDuration.FORMAT_HOURS,
                FormatDuration.FORMAT_MINUTES);
        assertEquals(expectedFormats, timeSlot.getPossibleFormats());
    }

    @Test
    public void getPossibleFormats1() throws Exception {
        //1y 11m 30d 23h 59m 59s

        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2018, Calendar.DECEMBER, 31, 23, 59, 59);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(timeSlot.toString());
        System.out.println(timeSlot.getPossibleFormats());
        List<Integer> expectedFormats = Arrays.asList(
                FormatDuration.FORMAT_SMART,
//                FormatDuration.FORMAT_YEARS,
                FormatDuration.FORMAT_MONTHS,
                FormatDuration.FORMAT_DAYS,
                FormatDuration.FORMAT_HOURS,
                FormatDuration.FORMAT_MINUTES);
        assertEquals(expectedFormats, timeSlot.getPossibleFormats());
    }

    @Test
    public void getPossibleFormats() throws Exception {
        //11y 11m 30d 23h 59m 59s

        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2028, Calendar.DECEMBER, 31, 23, 59, 59);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(timeSlot.toString());
        System.out.println(timeSlot.getPossibleFormats());
        List<Integer> expectedFormats = Arrays.asList(
                FormatDuration.FORMAT_SMART,
                FormatDuration.FORMAT_YEARS,
                FormatDuration.FORMAT_MONTHS,
                FormatDuration.FORMAT_DAYS,
                FormatDuration.FORMAT_HOURS,
                FormatDuration.FORMAT_MINUTES);
        assertEquals(expectedFormats, timeSlot.getPossibleFormats());
    }

    @Test
    public void toStringPeriodMoreOneDay() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 1, 1, 1);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 2, 1, 2, 1);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals("01.03.17 1:01 - 02.03.17 1:02", timeSlot.toStringPeriod());
    }

    @Test
    public void toStringPeriodLessOneDay() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 1, 1, 1);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 3, 1, 1);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals("01.03.17 1:01 - 3:01", timeSlot.toStringPeriod());
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