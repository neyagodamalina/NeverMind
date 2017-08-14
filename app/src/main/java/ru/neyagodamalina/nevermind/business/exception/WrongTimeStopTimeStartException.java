package ru.neyagodamalina.nevermind.business.exception;

import android.util.Log;

import ru.neyagodamalina.nevermind.business.util.Constants;

/**
 * Created by developer on 28.07.2017.
 */

public class WrongTimeStopTimeStartException extends Exception {
    public WrongTimeStopTimeStartException(String message) {
        super(message);
    }
}
