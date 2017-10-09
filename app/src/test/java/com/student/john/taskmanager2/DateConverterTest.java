package com.student.john.taskmanager2;


import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.student.john.taskmanager2.DateConverter.DateStringValues.A_WEEK;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.FRIDAY;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.NEXT_2_WEEKS;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.TODAY;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.TOMORROW;
import static junit.framework.Assert.assertEquals;

public class DateConverterTest {

    DateConverter converter;

    @Before
    public void setUp()
    {
        // all these tests will be conducted as if today were October 5th, 2017 (guess when I wrote this :P)
        converter = new DateConverter(new LocalDateTime(2017,10,5,0,0));
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testTodayCreation()
    {
        LocalDateTime dueDateTime = converter.getDateFromWord(TODAY);
        System.out.println("TODAY");
        printDate(dueDateTime);
        assertEquals(dueDateTime, new LocalDateTime(2017,10,5,11,59));
    }

    @Test
    public void testTomorrowCreation()
    {
        System.out.println("TOMORROW");
        LocalDateTime dueDateTime = converter.getDateFromWord(TOMORROW);
        printDate(dueDateTime);
        assertEquals(dueDateTime, new LocalDateTime(2017,10,6,11,59));
    }

    @Test
    public void testFridayCreation()
    {
        System.out.println("FRIDAY");
        LocalDateTime dueDateTime = converter.getDateFromWord(FRIDAY);
        printDate(dueDateTime);
        assertEquals(dueDateTime, new LocalDateTime(2017,10,6,11,59));
    }

    @Test
    public void testAWeekCreation()
    {
        System.out.println("A WEEK");
        LocalDateTime dueDateTime = converter.getDateFromWord(A_WEEK);
        printDate(dueDateTime);
        assertEquals(dueDateTime, new LocalDateTime(2017,10,12,11,59));
    }

    @Test
    public void testNext2WeeksCreation()
    {
        System.out.println("NEXT 2 WEEKS");
        LocalDateTime dueDateTime = converter.getDateFromWord(NEXT_2_WEEKS);
        printDate(dueDateTime);
        assertEquals(dueDateTime, new LocalDateTime(2017,10,19,11,59));
    }

    private void printDate(LocalDateTime dueDateTime)
    {
        System.out.println("Due Date: " + dueDateTime.getMonthOfYear() + "/" + dueDateTime.getDayOfMonth() + "/" + dueDateTime.getYear());
        System.out.println("Due Time: " + dueDateTime.getHourOfDay() + ":" + dueDateTime.getMinuteOfHour());
        System.out.println();
    }


}
