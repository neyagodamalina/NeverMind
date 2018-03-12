package ru.neyagodamalina.nevermind.util;

import ru.neyagodamalina.nevermind.exception.WrongMonthNumberException;

/**
 * Created by developer on 08.08.2017.
 */

public enum Month{
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;

    private static final Month[] months = Month.values();

    public int length(boolean leapYear) {
        switch (this) {
            case FEBRUARY:
                return (leapYear ? 29 : 28);
            case APRIL:
            case JUNE:
            case SEPTEMBER:
            case NOVEMBER:
                return 30;
            default:
                return 31;
        }
    }

    public static Month of(int month) throws WrongMonthNumberException {
        if (month < 1 || month > 12) {
            throw new WrongMonthNumberException("Wrong month number: " + String.valueOf(month));
        }
        return months[month - 1];
    }
}
