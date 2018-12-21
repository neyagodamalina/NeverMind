package ru.neyagodamalina.nevermind.util;


import androidx.annotation.IntDef;

import static ru.neyagodamalina.nevermind.util.TaskState.STATE_STOP;
import static ru.neyagodamalina.nevermind.util.TaskState.STATE_REC;

@IntDef({STATE_STOP, STATE_REC})
public @interface TaskState {
    public static final int STATE_STOP = 0;
    public static final int STATE_REC = 1;
}
