package ru.neyagodamalina.nevermind.business;


import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


import ru.neyagodamalina.nevermind.util.FormatDuration;
import ru.neyagodamalina.nevermind.util.Duration;

import static org.junit.Assert.*;

/**
 * Created by developer on 03.08.2017.
 */
public class DurationTest {


    @Test
    public void getPossibleFormats5() throws Exception {
        //0y 0m 0d 0h 1m 59s

        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.JANUARY, 1, 0, 1, 59);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toString());
        System.out.println(slot.getPossibleFormats());
        List<Integer> expectedFormats = Arrays.asList(
                FormatDuration.FORMAT_SMART
//                FormatDuration.FORMAT_YEARS,
//                FormatDuration.FORMAT_MONTHS,
//                FormatDuration.FORMAT_DAYS,
//                FormatDuration.FORMAT_HOURS,
//                FormatDuration.FORMAT_MINUTES
        );
        assertEquals(expectedFormats, slot.getPossibleFormats());
    }


    @Test
    public void getPossibleFormats4() throws Exception {
        //0y 0m 0d 23h 59m 59s

        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.JANUARY, 1, 23, 59, 59);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toString());
        System.out.println(slot.getPossibleFormats());
        List<Integer> expectedFormats = Arrays.asList(
                FormatDuration.FORMAT_SMART,
//                FormatDuration.FORMAT_YEARS,
//                FormatDuration.FORMAT_MONTHS,
//                FormatDuration.FORMAT_DAYS,
//                FormatDuration.FORMAT_HOURS,
                FormatDuration.FORMAT_MINUTES);
        assertEquals(expectedFormats, slot.getPossibleFormats());
    }

    @Test
    public void getPossibleFormats3() throws Exception {
        //0y 0m 30d 23h 59m 59s

        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.JANUARY, 31, 23, 59, 59);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toString());
        System.out.println(slot.getPossibleFormats());
        List<Integer> expectedFormats = Arrays.asList(
                FormatDuration.FORMAT_SMART,
//                FormatDuration.FORMAT_YEARS,
//                FormatDuration.FORMAT_MONTHS,
//                FormatDuration.FORMAT_DAYS,
                FormatDuration.FORMAT_HOURS,
                FormatDuration.FORMAT_MINUTES);
        assertEquals(expectedFormats, slot.getPossibleFormats());
    }

    @Test
    public void getPossibleFormats2() throws Exception {
        //0y 11m 30d 23h 59m 59s

        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.DECEMBER, 31, 23, 59, 59);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toString());
        System.out.println(slot.getPossibleFormats());
        List<Integer> expectedFormats = Arrays.asList(
                FormatDuration.FORMAT_SMART,
//                FormatDuration.FORMAT_YEARS,
//                FormatDuration.FORMAT_MONTHS,
                FormatDuration.FORMAT_DAYS,
                FormatDuration.FORMAT_HOURS,
                FormatDuration.FORMAT_MINUTES);
        assertEquals(expectedFormats, slot.getPossibleFormats());
    }

    @Test
    public void getPossibleFormats1() throws Exception {
        //1y 11m 30d 23h 59m 59s

        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2018, Calendar.DECEMBER, 31, 23, 59, 59);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toString());
        System.out.println(slot.getPossibleFormats());
        List<Integer> expectedFormats = Arrays.asList(
                FormatDuration.FORMAT_SMART,
//                FormatDuration.FORMAT_YEARS,
                FormatDuration.FORMAT_MONTHS,
                FormatDuration.FORMAT_DAYS,
                FormatDuration.FORMAT_HOURS,
                FormatDuration.FORMAT_MINUTES);
        assertEquals(expectedFormats, slot.getPossibleFormats());
    }

    @Test
    public void getPossibleFormats() throws Exception {
        //11y 11m 30d 23h 59m 59s

        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.JANUARY, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2028, Calendar.DECEMBER, 31, 23, 59, 59);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toString());
        System.out.println(slot.getPossibleFormats());
        List<Integer> expectedFormats = Arrays.asList(
                FormatDuration.FORMAT_SMART,
                FormatDuration.FORMAT_YEARS,
                FormatDuration.FORMAT_MONTHS,
                FormatDuration.FORMAT_DAYS,
                FormatDuration.FORMAT_HOURS,
                FormatDuration.FORMAT_MINUTES);
        assertEquals(expectedFormats, slot.getPossibleFormats());
    }

    @Test
    public void toStringPeriodLessOneMinute() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 1, 1, 1);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 1, 1, 50);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals("01.03.17 1:01:01 - 1:01:50", slot.toStringPeriod());
    }

    @Test
    public void toStringPeriodMoreOneMinute() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 1, 1, 1);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 1, 3, 1);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals("01.03.17 1:01 - 1:03", slot.toStringPeriod());
    }

    @Test
    public void toStringPeriodChangeDay() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 23, 59, 59);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 2, 0, 0, 0);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals("01.03.17 23:59 - 02.03.17 0:00", slot.toStringPeriod());
    }

    @Test
    public void toStringPeriodTimeZone() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2018, Calendar.NOVEMBER, 16, 0, 59, 12);
        Calendar stop = Calendar.getInstance();
        stop.set(2018, Calendar.NOVEMBER, 16, 1, 1, 30);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toStringPeriod());
        assertEquals("16.11.18 0:59 - 1:01", slot.toStringPeriod());
    }


    @Test
    public void toDaysPart() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 0, 0, 0);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.APRIL, 2, 0, 0, 0);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals(1, slot.toDaysPart());
    }

    @Test
    public void toHours() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 1, 1, 1);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 2, 1, 1);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toStringCalendarUTC());
        assertEquals(1, slot.toHours());
    }

    @Test
    public void toDays() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 1, 1, 1);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.APRIL, 2, 1, 1, 1);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals(32, slot.toDays());
    }

    @Test
    public void toMonths() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 1, 1, 1);
        Calendar stop = Calendar.getInstance();
        stop.set(2018, Calendar.MARCH, 1, 1, 1, 1);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals(12, slot.toMonths());
    }


    @Test
    public void toStringDuration1() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 32);
        Calendar stop = Calendar.getInstance();
        stop.set(2020, Calendar.APRIL, 2, 14, 21, 33);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        StringBuffer actual = new StringBuffer()
                .append(slot.toYearsPart())
                .append(slot.toMonthPart())
                .append(slot.toDaysPart())
                .append(slot.toHoursPart())
                .append(slot.toMinutesPart())
                .append(slot.toSecondsPart());

        assertEquals("311111", actual.toString()); // 3y1m1d1h1min1s
    }

    @Test
    public void toStringDuration2() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 13, 20, 32);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        StringBuffer actual = new StringBuffer()
                .append(slot.toYearsPart())
                .append(slot.toMonthPart())
                .append(slot.toDaysPart())
                .append(slot.toHoursPart())
                .append(slot.toMinutesPart())
                .append(slot.toSecondsPart());

        assertEquals("000001", actual.toString());
    }

    @Test
    public void toStringDuration3() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 16, 59, 12);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toStringCalendarUTC());
        StringBuffer actual = new StringBuffer()
                .append(slot.toYearsPart())
                .append(slot.toMonthPart())
                .append(slot.toDaysPart())
                .append(slot.toHoursPart())
                .append(slot.toMinutesPart())
                .append(slot.toSecondsPart());

        assertEquals("00033841", actual.toString());
    }
    @Test
    public void toStringDurationZero() throws Exception {
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        StringBuffer actual = new StringBuffer()
                .append(slot.toYearsPart())
                .append(slot.toMonthPart())
                .append(slot.toDaysPart())
                .append(slot.toHoursPart())
                .append(slot.toMinutesPart())
                .append(slot.toSecondsPart());

        assertEquals("000000", actual.toString());
    }


    @Test
    public void wrongTimeStopTimeStart() throws Exception{
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.FEBRUARY, 1, 13, 20, 31);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toString());
    }

}