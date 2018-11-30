package ru.neyagodamalina.nevermind.util;


import androidx.annotation.IntDef;

import static ru.neyagodamalina.nevermind.util.FormatDuration.FORMAT_DAYS;
import static ru.neyagodamalina.nevermind.util.FormatDuration.FORMAT_HOURS;
import static ru.neyagodamalina.nevermind.util.FormatDuration.FORMAT_MINUTES;
import static ru.neyagodamalina.nevermind.util.FormatDuration.FORMAT_MONTHS;
import static ru.neyagodamalina.nevermind.util.FormatDuration.FORMAT_SMART;
import static ru.neyagodamalina.nevermind.util.FormatDuration.FORMAT_YEARS;
import static ru.neyagodamalina.nevermind.util.TaskState.STATE_PLAY;
import static ru.neyagodamalina.nevermind.util.TaskState.STATE_STOP;

@IntDef({STATE_PLAY, STATE_STOP})
public @interface TaskState {
    public static final int STATE_PLAY      = 0;
    public static final int STATE_STOP      = 1;
}
