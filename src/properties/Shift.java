package properties;

import java.time.DayOfWeek;

public class Shift {

    private final int startHour, endHour;
    private final DayOfWeek dayOfWeek;

    public Shift(int startHour, int endHour, DayOfWeek dayOfWeek) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.dayOfWeek = dayOfWeek;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "startHour=" + startHour +
                ", endHour=" + endHour +
                ", dayOfWeek=" + dayOfWeek +
                '}';
    }
}
