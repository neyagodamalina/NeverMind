package ru.neyagodamalina.nevermind.business;

import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.content.res.Resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.business.exception.WrongTimeStopTimeStartException;
import ru.neyagodamalina.nevermind.business.exception.WrongMonthNumberException;
import ru.neyagodamalina.nevermind.business.util.FormatDuration;
import ru.neyagodamalina.nevermind.business.util.Month;

import static ru.neyagodamalina.nevermind.business.util.FormatDuration.FORMAT_DAYS;
import static ru.neyagodamalina.nevermind.business.util.FormatDuration.FORMAT_HOURS;
import static ru.neyagodamalina.nevermind.business.util.FormatDuration.FORMAT_MINUTES;
import static ru.neyagodamalina.nevermind.business.util.FormatDuration.FORMAT_MONTHS;
import static ru.neyagodamalina.nevermind.business.util.FormatDuration.FORMAT_SMART;
import static ru.neyagodamalina.nevermind.business.util.FormatDuration.FORMAT_YEARS;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

public class Duration {

    private long timeStart;
    private long timeStop;


    public Duration(long timeStart, long timeStop){
        this.setTimeStart(timeStart);
        this.setTimeStop(timeStop);
    }


    private static boolean isLeap(long year) {
        return ((year & 3) == 0) && ((year % 100) != 0 || (year % 400) == 0);
    }

    private static String toStringNormalFormatDateTime(long msTime) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        return dateFormat.format(new Date(msTime));
    }


    private long toMillis() {
        return timeStop - timeStart;
    }

    private long toSeconds() {
        return (long) (this.toMillis() / 1E3);
    }

    private long toMinutes() {
        return this.toSeconds() / 60;
    }

    public long toHours() {
        return this.toMinutes() / 60;
    }

    public long toDays() {
        return (int) this.toHours() / 24;
    }

    public long toMonths() {
        Calendar calendar = this.getCalendarUTC();
        return (calendar.get(Calendar.YEAR) - 1970) * 12 + calendar.get(Calendar.MONTH);
    }

    public double toYears() {
        return this.toMonths() / 12;
    }

    public long toSecondsPart() {
        return toSeconds() % 60;
    }

    public long toMinutesPart() {
        return toMinutes() % 60;
    }

    public long toHoursPart() {
        return toHours() % 24;
    }

    public long toDaysPart() {
        long countMonth = this.toMonths();
        long countDays = this.toDays();
        int year = 1970;
        for (int numberMonth = 0; numberMonth < countMonth; numberMonth++) {
            try {
                Month month = Month.of(numberMonth % 12 + 1);
                countDays = countDays - month.length(isLeap(year));
                if ((numberMonth + 1) % 12 == 0) year++;

            } catch (WrongMonthNumberException e) {
                e.printStackTrace();
            }
        }
        return countDays;
    }

    public long toMonthPart() {
        return toMonths() % 12;
    }

    public long toYearsPart() {
        return (long) toYears();
    }

    @Override
    public String toString() {
        return this.toStringDurationForTest() + " (" + this.toStringCalendarUTC() + " )" + this.toStringPeriod();
    }

    public String toStringDuration(@FormatDuration int format, Resources resources) {


        String result = null;

        switch (format) {
            case FORMAT_SMART:
                long value = toYearsPart();
                StringBuffer sb = new StringBuffer();

                sb.append(value == 0 ? "" : value + resources.getString(R.string.letter_year));

                value = toMonthPart();
                sb.append(value == 0 ? "" : value + resources.getString(R.string.letter_month));

                value = toDaysPart();
                sb.append(value == 0 ? "" : value + resources.getString(R.string.letter_day));

                value = toHoursPart();
                sb.append(value == 0 ? "" : value + resources.getString(R.string.letter_hour));

                value = toMinutesPart();
                sb.append(value == 0 ? "" : value + resources.getString(R.string.letter_minute));

                if (toMinutes() < 2) {
                    value = toSecondsPart();
                    sb.append(value == 0 ? "" : value + resources.getString(R.string.letter_second));
                }


                // "3y1m1d1h1m1s" -> "3y 1m 1d 1h 1m 1s"
                result = sb.toString().replaceAll("(\\D)", "$1 ").trim();

                break;
            case FORMAT_YEARS:
                result = this.toYears() + resources.getString(R.string.letter_year);
                break;
            case FORMAT_MONTHS:
                result = this.toMonths() + resources.getString(R.string.letter_month);
                break;
            case FORMAT_DAYS:
                result = this.toDays() + resources.getString(R.string.letter_day);
                break;
            case FORMAT_HOURS:
                result = this.toHours() + resources.getString(R.string.letter_hour);
                break;
            case FORMAT_MINUTES:
                result = this.toMinutes() + resources.getString(R.string.letter_minute);
                break;
        }
        return result;
    }

    public String toStringDurationForTest() {

        String result = null;

        long value = toYearsPart();
        StringBuffer sb = new StringBuffer();

        sb.append(value + "y");

        value = toMonthPart();
        sb.append(value + "m");

        value = toDaysPart();
        sb.append(value + "d");

        value = toHoursPart();
        sb.append(value + "h");

        value = toMinutesPart();
        sb.append(value + "m");

        value = toSecondsPart();
        sb.append(value + "s");

        // "3y1m1d1h1m" -> "3y 1m 1d 1h 1m"
        result = sb.toString().replaceAll("(\\D)", "$1 ").trim();

        return result;
    }

    public String toStringPeriod() {
        StringBuffer result = new StringBuffer();
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);


        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(timeStart);

        Calendar stop = Calendar.getInstance();
        stop.setTimeInMillis(timeStop);

        // Если сменились сутки, вернем: дата1 время1 - дата2 время2 (01.03.17 1:01 - 02.03.17 1:02)
        if        ((stop.get(Calendar.YEAR)            > start.get(Calendar.YEAR))
                || (stop.get(Calendar.MONTH)           > start.get(Calendar.MONTH))
                || (stop.get(Calendar.DAY_OF_MONTH)    > start.get(Calendar.DAY_OF_MONTH)))
        {
            result.append(dateTimeFormat.format(new Date(timeStart)))
                    .append(" - ")
                    .append(dateTimeFormat.format(new Date(timeStop)));
        }
        //Если длительность больше 1 минуты, вернем: дата время1 - время2 (01.03.17 1:01 - 3:01)
        else if (this.toMinutes() > 1){
            result.append(dateFormat.format(new Date(timeStart)))
                    .append(" ")
                    .append(timeFormat.format(new Date(timeStart)))
                    .append(" - ")
                    .append(timeFormat.format(new Date(timeStop)));
        }
        // В других случаях, вернем: вернем: дата время1+сек - время2+сек (01.03.17 1:01:01 - 1:01:50)
        else{
            timeFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM);
            result.append(dateFormat.format(new Date(timeStart)))
                    .append(" ")
                    .append(timeFormat.format(new Date(timeStart)))
                    .append(" - ")
                    .append(timeFormat.format(new Date(timeStop)));
        }

        return result.toString();
    }

    public String toStringCalendarUTC() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Это длительность от 01.01.1970 00:00:00, а не дата. Установим временную зону UTC. Иначе длительность в 1 час будет видна как 1970-01-01 04:00:00 MSK (+3ч часового пояса)
        Calendar calendar = this.getCalendarUTC();
        return dateFormat.format(calendar.getTime());
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(long timeStop){
        try {
            if (timeStop < this.getTimeStart()) {
                StringBuffer message = new StringBuffer();
                message.append("Time stop: ")
                        .append(toStringNormalFormatDateTime(timeStop))
                        .append(" < Time start: ")
                        .append(toStringNormalFormatDateTime(this.getTimeStart()));
                throw new WrongTimeStopTimeStartException(message.toString());
            }
        }catch (WrongTimeStopTimeStartException e){
            e.printStackTrace(System.err);
        }
        this.timeStop = timeStop;
    }

    private Calendar getCalendarUTC() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));  // Это длительность от 01.01.1970 00:00:00, а не дата. Установим временную зону UTC.
        calendar.setTimeInMillis(this.toMillis());
        return calendar;
    }

    public List<Integer> getPossibleFormats() {
        List<Integer> formats = new ArrayList<Integer>();
        formats.add(FORMAT_SMART);
        if (this.toYears() > 10)
            formats.add(FORMAT_YEARS);    //11y 11m 30d 23h 59m 59s->11y possible format; 1y 11m 30d 23h 59m 59s->1y not possible format;
        if (this.toMonths() > 12)
            formats.add(FORMAT_MONTHS);   //1y 11m 30d 23h 59m 59s ->23m possible format; 0y 11m 30d 23h 59m 59s->11m not possible format;
        if (this.toDays() > 31)
            formats.add(FORMAT_DAYS);     //1y 11m 30d 23h 59m 59s ->698d possible format; 0y 0m 30d 23h 59m 59s ->30d not possible format;
        if (this.toHours() > 24)
            formats.add(FORMAT_HOURS);    //1y 11m 30d 23h 59m 59s ->24767h possible format; 0y 0m 0d 23h 59m 59s ->23h not possible format;
        if (this.toMinutes() > 1)
            formats.add(FORMAT_MINUTES);  //1y 11m 30d 23h 59m 59s ->1965599m possible format; 0y 0m 0d 0h 1m 59s ->1m not possible format;
        return formats;
    }


}
