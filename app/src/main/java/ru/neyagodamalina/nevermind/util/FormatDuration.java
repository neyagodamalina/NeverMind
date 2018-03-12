package ru.neyagodamalina.nevermind.util;

/**
 * Created by developer on 17.08.2017.
 */

import android.support.annotation.IntDef;

import static ru.neyagodamalina.nevermind.util.FormatDuration.FORMAT_DAYS;
import static ru.neyagodamalina.nevermind.util.FormatDuration.FORMAT_HOURS;
import static ru.neyagodamalina.nevermind.util.FormatDuration.FORMAT_MINUTES;
import static ru.neyagodamalina.nevermind.util.FormatDuration.FORMAT_MONTHS;
import static ru.neyagodamalina.nevermind.util.FormatDuration.FORMAT_SMART;
import static ru.neyagodamalina.nevermind.util.FormatDuration.FORMAT_YEARS;

/**
 * Created by developer on 25.07.2017.
 */
@IntDef({FORMAT_SMART, FORMAT_YEARS, FORMAT_MONTHS, FORMAT_DAYS, FORMAT_HOURS, FORMAT_MINUTES})
public @interface FormatDuration {
    public static final int FORMAT_SMART      = 0;    //0y 0m 1d 1h 1m 1s    ->  1d 1h 1m 1s
    public static final int FORMAT_YEARS      = 1;    //0y 0m 1d 1h 1m 1s    ->  0y
    public static final int FORMAT_MONTHS     = 2;    //0y 0m 1d 1h 1m 1s    ->  0m
    public static final int FORMAT_DAYS       = 3;    //0y 0m 1d 1h 1m 1s    ->  1d
    public static final int FORMAT_HOURS      = 4;    //0y 0m 1d 1h 1m 1s    ->  25h
    public static final int FORMAT_MINUTES    = 5;    //0y 0m 1d 1h 1m 1s    ->  1501m
}
