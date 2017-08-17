package ru.neyagodamalina.nevermind.business;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.IntDef;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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


public class TimeSlot {
    private long timeStart;
    private long timeStop;


    public TimeSlot(long timeStart, long timeStop) throws WrongTimeStopTimeStartException {
        this.setTimeStop(timeStop);
        this.setTimeStart(timeStart);
    }

    public TimeSlot() {
    }

    public void start() throws WrongTimeStopTimeStartException {
        this.setTimeStart(Calendar.getInstance().getTimeInMillis());
    }

    public void stop() throws WrongTimeStopTimeStartException {
        this.setTimeStop(Calendar.getInstance().getTimeInMillis());
    }

    public long toMillis(){
        return timeStop - timeStart;
    }

    public long toSeconds(){
        return (long) (this.toMillis() / 1E3);
    }

    public long toMinutes(){
        return this.toSeconds() / 60;
    }

    public long toHours(){
        return this.toMinutes() / 60;
    }

    public long toDays(){
        return (int) this.toHours() / 24;
    }

    public long toMonths(){
        Calendar calendar = this.getCalendarUTC();
        return (calendar.get(Calendar.YEAR)-1970)*12 + calendar.get(Calendar.MONTH);
    }

    public double toYears(){
        return this.toMonths() / 12;
    }

    public long toSecondsPart(){
        return toSeconds() % 60;
    }

    public long toMinutesPart(){
        return toMinutes() % 60;
    }

    public long toHoursPart(){
        return toHours() % 24;
    }

    public long toDaysPart(){
        long countMonth  = this.toMonths();
        long countDays   = this.toDays();
        int year = 1970;
        for (int numberMonth = 0; numberMonth < countMonth ; numberMonth++) {
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

    public long toMonthPart(){
        return toMonths() % 12;
    }

    public long toYearsPart(){
        return (long) toYears();
    }


    @Override
    public String toString() {
        return this.toStringCalendarUTC() + "\n" + this.toStringPeriod();
    }

    public String toStringDuration(@FormatDuration int format, Resources resources){


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

                // "3y1m1d1h1m" -> "3y 1m 1d 1h 1m"
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

    public String toStringPeriod(){
        StringBuffer result = new StringBuffer();
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        if (this.toDays() >= 1){
            result.append(dateTimeFormat.format(new Date(timeStart)))
                    .append("-")
                    .append(dateTimeFormat.format(new Date(timeStop)));
        }
        else {
            result.append(dateFormat.format(new Date(timeStart)))
                    .append(" ")
                    .append(timeFormat.format(new Date(timeStart)))
                    .append("-")
                    .append(timeFormat.format(new Date(timeStop)));
        }
        return result.toString();
    }

    public String toStringCalendarUTC(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Это длительность от 01.01.1970 00:00:00, а не дата. Установим временную зону UTC. Иначе длительность в 1 час будет видна как 1970-01-01 04:00:00 MSK (+3ч часового пояса)
        Calendar calendar = this.getCalendarUTC();
        return dateFormat.format(calendar.getTime());
    }

    public long getTimeStart() {
        return timeStart;
    }

    public long getTimeStop() {
        return timeStop;
    }

    public void setTimeStart(long timeStart) throws WrongTimeStopTimeStartException {
        if (timeStart > this.getTimeStop()) {
            StringBuffer message = new StringBuffer();
            message.append("Time start: ")
                    .append(toStringDateTime(timeStart))
                    .append(" > Time stop: ")
                    .append(toStringDateTime(this.getTimeStop()));
            throw new WrongTimeStopTimeStartException(message.toString());
        }
        this.timeStart = timeStart;
    }

    public void setTimeStop(long timeStop) throws WrongTimeStopTimeStartException
    {
        if (timeStop < this.getTimeStart()) {
            StringBuffer message = new StringBuffer();
            message.append("Time stop: ")
                    .append(toStringDateTime(timeStop))
                    .append(" < Time start: ")
                    .append(toStringDateTime(this.getTimeStart()));
            throw new WrongTimeStopTimeStartException(message.toString());
        }
        this.timeStop = timeStop;
    }

    private Calendar getCalendarUTC(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));  // Это длительность от 01.01.1970 00:00:00, а не дата. Установим временную зону UTC.
        calendar.setTimeInMillis(this.toMillis());
        return calendar;
    }


    public static boolean isLeap(long year) {
        return ((year & 3) == 0) && ((year % 100) != 0 || (year % 400) == 0);
    }

    public static String toStringDateTime(long msTime){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        return dateFormat.format(new Date(msTime));
    }

    public List<Integer> getPossibleFormat(){
        List<Integer> formats = new ArrayList();
        formats.add(FORMAT_SMART);
        if (this.toYears()      != 0) formats.add(FORMAT_YEARS);
        if (this.toMonths()     > 12) formats.add(FORMAT_MONTHS);
        if (this.toDays()       > 24) formats.add(FORMAT_DAYS);
        if (this.toHours()      > 60) formats.add(FORMAT_HOURS);
        if (this.toMinutes()    != 0) formats.add(FORMAT_MINUTES);
        return formats;
    }



}
