package ru.neyagodamalina.nevermind;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import ru.neyagodamalina.nevermind.business.TimeSlot;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TimeSlotInstrumentedTest {
    @Test
    public void toStringDuration1() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 32);
        Calendar stop = Calendar.getInstance();
        stop.set(2020, Calendar.APRIL, 2, 14, 21, 33);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());

        assertEquals("3y 1m 1d 1h 1m 1s", timeSlot.toStringDuration(appContext.getResources()));
    }

    @Test
    public void toStringDuration2() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 13, 20, 32);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals("1s", timeSlot.toStringDuration(appContext.getResources()));
    }

    @Test
    public void toStringDuration3() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 16, 59, 12);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(timeSlot.toStringCalendarUTC());
        assertEquals("3h 38m 41s", timeSlot.toStringDuration(appContext.getResources()));
    }

    @Test
    public void toStringDurationZero() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        TimeSlot timeSlot = new TimeSlot(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals("", timeSlot.toStringDuration(appContext.getResources()));
    }
}
