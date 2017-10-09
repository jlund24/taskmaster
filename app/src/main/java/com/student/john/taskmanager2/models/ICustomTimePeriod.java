package com.student.john.taskmanager2.models;


import org.joda.time.Duration;
import org.joda.time.Period;

public interface ICustomTimePeriod {

    Double getTotalAsDays();
    Double getTotalAsHours();
    long getTotalAsMinutes();
    int getDays();
    int getHours();
    int getMinutes();
    void minus(ICustomTimePeriod duration);
    Duration getDurationObject();
    Period getPeriodObject();

}
