package com.student.john.taskmanager2;


import com.student.john.taskmanager2.models.CustomTimePeriod;
import com.student.john.taskmanager2.models.ICustomTimePeriod;

import org.joda.time.Period;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CustomDurationConverterTest {

    private CustomDurationConverter converter = new CustomDurationConverter();

    @Test
    public void testGetDurationStrings()
    {
        ICustomTimePeriod duration = new CustomTimePeriod(new Period(5,23,0,0));
        String durationString = converter.getWordFromDuration(duration);
        assertEquals("5h 23m", durationString);
    }
}
