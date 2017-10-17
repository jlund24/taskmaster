package com.student.john.taskmanager2;


import com.student.john.taskmanager2.models.CustomTimePeriod;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static com.student.john.taskmanager2.DateConverter.DateStringValues.OVERDUE;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.TODAY;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.TOMORROW;


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
        public static final String OVERDUE = "Overdue";

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
            case TODAY:
                return getToday(startDate);
            case TOMORROW:
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

    public String getWordFromDate(LocalDateTime dateTime)
    {
        CustomTimePeriod diff = new CustomTimePeriod(new Period(startDate, dateTime));

        if (diff.getDurationObject().getMillis() < 0)
        {
            return OVERDUE;
        }
        else if (isToday(startDate, dateTime))
        {
            return TODAY;
        }
        else if (isTomorrow(startDate, dateTime))
        {
            return TOMORROW;
        }
        else if (isWithin7Days(startDate, dateTime))
        {
            DateTimeFormatter fmt = DateTimeFormat.forPattern("EEEE");
            return dateTime.toString(fmt);
        }
        else
        {
            DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM d");
            return dateTime.toString(fmt);
        }
    }

    private LocalDateTime getToday(LocalDateTime startDate)
    {
        return startDate.withTime(23,59,0,0);
    }

    private LocalDateTime getTomorrow(LocalDateTime startDate)
    {
        LocalDateTime today = startDate.withTime(23,59,0,0);
        return today.plusDays(1);
    }

    private LocalDateTime getFriday(LocalDateTime startDate)
    {
        LocalDateTime today = startDate.withTime(23,59,0,0);
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
        LocalDateTime today = startDate.withTime(23,59,0,0);
        return today.plusDays(7);
    }

    private LocalDateTime getNext2Weeks(LocalDateTime startDate)
    {
        LocalDateTime today = startDate.withTime(23,59,0,0);
        return today.plusDays(14);
    }

    private boolean isToday(LocalDateTime date1, LocalDateTime date2)
    {
        return (date1.getDayOfMonth() == date2.getDayOfMonth() &&
                date1.getMonthOfYear() == date2.getMonthOfYear() &&
                date1.getYear() == date2.getYear());

    }

    private boolean isTomorrow(LocalDateTime date1, LocalDateTime date2)
    {
        date1 = date1.plusDays(1);
        return (date1.getDayOfMonth()  == date2.getDayOfMonth() &&
                date1.getMonthOfYear() == date2.getMonthOfYear() &&
                date1.getYear() == date2.getYear());
    }

    private boolean isWithin7Days(LocalDateTime date1, LocalDateTime date2)
    {
        date1 = date1.plusDays(7).withTime(0,0,0,0);
        date2 = date2.withTime(0,0,0,0);
        CustomTimePeriod diff = new CustomTimePeriod(new Period(date1, date2));
        return (diff.getDurationObject().getMillis() < 0);

    }

    public boolean isToday(LocalDateTime date)
    {
        return (startDate.getDayOfMonth() == startDate.getDayOfMonth() &&
                startDate.getMonthOfYear() == startDate.getMonthOfYear() &&
                startDate.getYear() == startDate.getYear());
    }


}
