package ru.neyagodamalina.nevermind.business.exception;

import android.util.Log;

import ru.neyagodamalina.nevermind.business.util.Constants;

/**
 * Created by developer on 08.08.2017.
 */

public class WrongMonthNumberException extends Exception{
        public WrongMonthNumberException(String message) {
            super(message);
        }

}
