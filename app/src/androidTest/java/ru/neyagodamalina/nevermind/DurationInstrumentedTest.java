package ru.neyagodamalina.nevermind;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import ru.neyagodamalina.nevermind.util.Duration;
import ru.neyagodamalina.nevermind.util.FormatDuration;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DurationInstrumentedTest {
    @Test
    public void toStringDurationSmart1() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 32);
        Calendar stop = Calendar.getInstance();
        stop.set(2020, Calendar.APRIL, 2, 14, 21, 33);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());

        assertEquals("3y 1m 1d 1h 1m", slot.toStringDuration(FormatDuration.FORMAT_SMART, appContext.getResources()));
    }

    @Test
    public void toStringDurationSmart2() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 32);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 13, 21, 32);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals("1m", slot.toStringDuration(FormatDuration.FORMAT_SMART, appContext.getResources()));
    }

    @Test
    public void toStringDurationSmart3() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 16, 59, 12);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toStringCalendarUTC());
        assertEquals("3h 38m", slot.toStringDuration(FormatDuration.FORMAT_SMART, appContext.getResources()));
    }

    @Test
    public void toStringDurationSmart4() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 13, 21, 12);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toStringCalendarUTC());
        assertEquals("41s", slot.toStringDuration(FormatDuration.FORMAT_SMART, appContext.getResources()));
    }

    @Test
    public void toStringDurationSmart5() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 13, 22, 12);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toStringCalendarUTC());
        assertEquals("1m 41s", slot.toStringDuration(FormatDuration.FORMAT_SMART, appContext.getResources()));
    }

    @Test
    public void toStringDurationSmart6() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 13, 23, 12);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        System.out.println(slot.toStringCalendarUTC());
        assertEquals("2m", slot.toStringDuration(FormatDuration.FORMAT_SMART, appContext.getResources()));
    }

    @Test
    public void toStringDurationSmartZero() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Calendar stop = Calendar.getInstance();
        stop.set(2017, Calendar.MARCH, 1, 13, 20, 31);
        Duration slot = new Duration(start.getTimeInMillis(), stop.getTimeInMillis());
        assertEquals("", slot.toStringDuration(FormatDuration.FORMAT_SMART, appContext.getResources()));
    }
}
