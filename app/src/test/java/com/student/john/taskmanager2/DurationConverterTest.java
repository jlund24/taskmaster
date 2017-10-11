package com.student.john.taskmanager2;


import com.student.john.taskmanager2.models.CustomTimePeriod;
import com.student.john.taskmanager2.models.ICustomTimePeriod;

import org.joda.time.Period;
import org.junit.Test;

import static com.student.john.taskmanager2.DurationConverter.DurationStringValues.MIN_15;
import static junit.framework.Assert.assertEquals;

public class DurationConverterTest {

    private DurationConverter converter = new DurationConverter();

    @Test
    public void testGetDurationStrings()
    {
        ICustomTimePeriod duration = new CustomTimePeriod(new Period(5,23,0,0));
        String durationString = converter.getWordFromDuration(duration);
        assertEquals("5h 23m", durationString);
    }
}
