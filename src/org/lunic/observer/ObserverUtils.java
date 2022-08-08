package org.lunic.observer;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class ObserverUtils {
    public static long getDaysBetween(LocalDate expirationDate,
                                      LocalDate currentDate) {
        return DAYS.between(currentDate, expirationDate);
    }
}
