package com.student.john.taskmanager2;


import com.student.john.taskmanager2.models.CustomTimePeriod;
import com.student.john.taskmanager2.models.ICustomTimePeriod;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.HR_1;
import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.HR_10;
import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.HR_1_5;
import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.HR_2;
import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.HR_3;
import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.HR_4;
import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.HR_5;
import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.MIN_15;
import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.MIN_30;
import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.MIN_45;

public class CustomDurationConverter {

    public static final class DurationStringValues {
        public static final String MIN_15 = "15m";
        public static final String MIN_30 = "30m";
        public static final String MIN_45 = "45m";
        public static final String HR_1 = "1h";
        public static final String HR_1_5 = "1h 30m";
        public static final String HR_2 = "2h";
        public static final String HR_3 = "3h";
        public static final String HR_4 = "4h";
        public static final String HR_5 = "5h";
        public static final String HR_10 = "10h";

    }

    public CustomDurationConverter(){}

    public CustomTimePeriod getDurationFromWord(String name)
    {
        switch (name)
        {
            case MIN_15:
                return new CustomTimePeriod(new Period(0,15,0,0));
            case MIN_30:
                return new CustomTimePeriod(new Period(0,30,0,0));
            case MIN_45:
                return new CustomTimePeriod(new Period(0,45,0,0));
            case HR_1:
                return new CustomTimePeriod(new Period(1,0,0,0));
            case HR_1_5:
                return new CustomTimePeriod(new Period(1,30,0,0));
            case HR_2:
                return new CustomTimePeriod(new Period(2,0,0,0));
            case HR_3:
                return new CustomTimePeriod(new Period(3,0,0,0));
            case HR_4:
                return new CustomTimePeriod(new Period(4,0,0,0));
            case HR_5:
                return new CustomTimePeriod(new Period(5,0,0,0));
            case HR_10:
                return new CustomTimePeriod(new Period(10,0,0,0));
            default:
                System.out.println("CustomDurationConverter got an invalid string: " + name);
                return null;
        }
    }

    public String getWordFromDuration(ICustomTimePeriod duration)
    {
        PeriodFormatterBuilder builder = new PeriodFormatterBuilder();
        if (duration == null)
        {
            return null;
        }
        if (duration.getHours() > 0)
        {
            builder.appendHours().appendSuffix("h");
            if (duration.getMinutes() > 0)
            {
                builder.appendLiteral(" ");
            }
        }

        if (duration.getMinutes() > 0)
        {
            builder.printZeroAlways()
                .minimumPrintedDigits(2)
                .appendMinutes()
                .appendSuffix("m");
        }

        PeriodFormatter fmt = builder.toFormatter();

        return duration.getPeriodObject().toString(fmt);

    }

    public CustomTimePeriod getDurationFromString(String input)
    {
        if (input == null || input.equals(""))
        {
            return null;
        }
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendHours().appendSuffix("h").appendSeparatorIfFieldsAfter(" ")
                .appendMinutes().appendSuffix("m") .toFormatter();

        try
        {
            return new CustomTimePeriod(formatter.parsePeriod(input));
        }
        catch(IllegalArgumentException e)
        {

        }
        try
        {
            formatter = new PeriodFormatterBuilder()
                    .appendMinutes().appendSuffix("m").toFormatter();
            return new CustomTimePeriod(formatter.parsePeriod(input));
        }
        catch(IllegalArgumentException e)
        {

        }
        return null;

    }
}
