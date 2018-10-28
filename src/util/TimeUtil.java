package util;

import java.time.DayOfWeek;

public class TimeUtil {

    public static boolean isPrime(DayOfWeek dayOfWeek) {
        return dayOfWeek.getValue() >= 5 && dayOfWeek.getValue() <= 7;
    }
}
