package com.student.john.taskmanager2;


import com.student.john.taskmanager2.models.CustomTimePeriod;

import org.joda.time.Period;

import static com.student.john.taskmanager2.DurationConverter.DurationStringValues.HR_1;
import static com.student.john.taskmanager2.DurationConverter.DurationStringValues.HR_10;
import static com.student.john.taskmanager2.DurationConverter.DurationStringValues.HR_1_5;
import static com.student.john.taskmanager2.DurationConverter.DurationStringValues.HR_2;
import static com.student.john.taskmanager2.DurationConverter.DurationStringValues.HR_3;
import static com.student.john.taskmanager2.DurationConverter.DurationStringValues.HR_4;
import static com.student.john.taskmanager2.DurationConverter.DurationStringValues.HR_5;
import static com.student.john.taskmanager2.DurationConverter.DurationStringValues.MIN_15;
import static com.student.john.taskmanager2.DurationConverter.DurationStringValues.MIN_30;
import static com.student.john.taskmanager2.DurationConverter.DurationStringValues.MIN_45;

public class DurationConverter {

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

    public DurationConverter(){}

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
                System.out.println("DurationConverter got an invalid string: " + name);
                return null;
        }
    }
}
