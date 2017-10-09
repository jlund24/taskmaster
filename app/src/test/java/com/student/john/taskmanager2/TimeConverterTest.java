package com.student.john.taskmanager2;


import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.AFTERNOON;
import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.EVENING;
import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.MIDNIGHT;
import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.MORNING;
import static junit.framework.Assert.assertEquals;

public class TimeConverterTest {

   private TimeConverter converter;

    @Before
    public void setUp()
    {
        // all these tests will be conducted as if today were October 5th, 2017 (guess when I wrote this :P)
        converter = new TimeConverter();
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testGetMorning()
    {
        LocalTime time = converter.getTimeFromWord(MORNING);
        System.out.println("MORNING");
        printTime(time);
        assertEquals(time,new LocalTime(10,0));
    }

    @Test
    public void testGetAfternoon()
    {
        LocalTime time = converter.getTimeFromWord(AFTERNOON);
        System.out.println("AFTERNOON");
        printTime(time);
        assertEquals(time,new LocalTime(15,0));
    }

    @Test
    public void testGetEvening()
    {
        LocalTime time = converter.getTimeFromWord(EVENING);
        System.out.println("EVENING");
        printTime(time);
        assertEquals(time,new LocalTime(20,0));
    }

    @Test
    public void testGetMidnight()
    {
        LocalTime time = converter.getTimeFromWord(MIDNIGHT);
        System.out.println("MIDNIGHT");
        printTime(time);
        assertEquals(time,new LocalTime(23,59));
    }

    private void printTime(LocalTime time)
    {
        System.out.println("Due Time: " + time.getHourOfDay() + ":" + time.getMinuteOfHour());
        System.out.println();
    }
}
