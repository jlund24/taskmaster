package com.student.john.taskmanager2;


import org.joda.time.LocalDateTime;



public class DateConverter {

    //take in a natural language version of a date and return what the due date from right now would be
    //or vice versa
    public static final class DateStringValues {
        public static final String TODAY = "Today";
        public static final String TOMORROW = "Tomorrow";
        public static final String MONDAY = "Monday";
        public static final String TUESDAY = "Tuesday";
        public static final String WEDNESDAY = "Wednesday";
        public static final String THURSDAY = "Thursday";
        public static final String FRIDAY = "Friday";
        public static final String SATURDAY = "Saturday";
        public static final String SUNDAY = "Sunday";
        public static final String A_WEEK = "1 Week";
        public static final String NEXT_2_WEEKS = "Next 2 Weeks";

    }

    private LocalDateTime startDate;

    public DateConverter()
    {
        //initialize dateTime
        this.startDate = new LocalDateTime();
    }

    public DateConverter(LocalDateTime startDate)
    {
        this.startDate = startDate;
    }

    //when a task is created, a user can select a preset concept of when it's due (Today, Tomorrow, etc.)

    public LocalDateTime getDateFromWord(String name)
    {
        switch (name)
        {
            case DateStringValues.TODAY:
                return getToday(startDate);
            case DateStringValues.TOMORROW:
                return getTomorrow(startDate);
            case DateStringValues.FRIDAY:
                return getFriday(startDate);
            case DateStringValues.A_WEEK:
                return getAWeek(startDate);
            case DateStringValues.NEXT_2_WEEKS:
                return getNext2Weeks(startDate);
            default:
                System.out.println("DateConverter got an invalid string: " + name);
                return null;
        }

    }

    private LocalDateTime getToday(LocalDateTime startDate)
    {
        return startDate.withTime(11,59,0,0);
    }

    private LocalDateTime getTomorrow(LocalDateTime startDate)
    {
        LocalDateTime today = startDate.withTime(11,59,0,0);
        return today.plusDays(1);
    }

    private LocalDateTime getFriday(LocalDateTime startDate)
    {
        LocalDateTime today = startDate.withTime(11,59,0,0);
        int difference = 5 - today.getDayOfWeek();
        //fix the difference in case today is Friday, or it's the weekend (after Friday)
        if (difference == 0)
        {
            difference = 7;
        }
        else if (difference < 0)
        {
            difference += 7;
        }
        return today.plusDays(difference);
    }

    private LocalDateTime getAWeek(LocalDateTime startDate)
    {
        LocalDateTime today = startDate.withTime(11,59,0,0);
        return today.plusDays(7);
    }

    private LocalDateTime getNext2Weeks(LocalDateTime startDate)
    {
        LocalDateTime today = startDate.withTime(11,59,0,0);
        return today.plusDays(14);
    }
}
