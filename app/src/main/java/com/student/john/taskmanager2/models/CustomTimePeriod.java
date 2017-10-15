package com.student.john.taskmanager2.models;


import android.support.annotation.NonNull;

import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import static com.student.john.taskmanager2.DateConverter.DateStringValues.OVERDUE;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.TODAY;

public class CustomTimePeriod implements ICustomTimePeriod{

    private Period period;
    private Duration duration;

    public CustomTimePeriod(Duration duration)
    {
        this.duration = duration;
        this.period = new Period(duration);
    }

    public CustomTimePeriod(Period period)
    {
        this.period = period;
        this.duration = new Duration( getMillisFromPeriod(period) );
    }

    private long getMillisFromPeriod(Period period)
    {
        long totalMillis = period.getDays() * 24L * 60L * 1000L;
        totalMillis += period.getHours() * 60L * 60L * 1000L;
        totalMillis += period.getMinutes() * 60L * 1000L;
        return totalMillis;
    }

    @Override
    public Double getTotalAsDays() {
        return null;
    }

    @Override
    public Double getTotalAsHours() {
        return null;
    }

    @Override
    public long getTotalAsMinutes() {
        return duration.getStandardMinutes();
    }

    @Override
    public int getDays() {
        return period.getDays();
    }

    @Override
    public int getHours() {
        return period.getHours();
    }

    @Override
    public int getMinutes() {
        return period.getMinutes();
    }

    public Duration minus(ICustomTimePeriod duration)
    {
        Duration returnValue = this.duration.minus(duration.getDurationObject());
        if (returnValue.getStandardMinutes() < 0)
        {
            returnValue = new Duration(0);
        }
        return returnValue;
    }

    public Duration plus(ICustomTimePeriod duration)
    {
        return this.duration.plus(duration.getDurationObject());

    }

    @Override
    public Duration getDurationObject() {
        return this.duration;
    }

    @Override
    public Period getPeriodObject() {
        return this.period;
    }



}
