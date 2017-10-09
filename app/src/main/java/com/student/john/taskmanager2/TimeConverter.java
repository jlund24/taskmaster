package com.student.john.taskmanager2;


import org.joda.time.LocalTime;

import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.AFTERNOON;
import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.EVENING;
import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.MIDNIGHT;
import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.MORNING;

public class TimeConverter {

    public static final class TimeStringValues {
        public static final String MORNING = "Morning";
        public static final String AFTERNOON = "Afternoon";
        public static final String EVENING = "Evening";
        public static final String MIDNIGHT = "Midnight";
    }

    public TimeConverter(){}

    public LocalTime getTimeFromWord(String name)
    {
        switch (name)
        {
            case MORNING:
                return getMorning();
            case AFTERNOON:
                return getAfternoon();
            case EVENING:
                return getEvening();
            case MIDNIGHT:
                return getMidnight();
            default:
                System.out.println("TimeConverter got an invalid string: " + name);
                return null;
        }
    }

    private LocalTime getMorning()
    {
        return new LocalTime(10,0);
    }

    private LocalTime getAfternoon()
    {
        return new LocalTime(15,0);
    }

    private LocalTime getEvening()
    {
        return new LocalTime(20,0);
    }

    private LocalTime getMidnight()
    {
        return new LocalTime(23,59);
    }
}
