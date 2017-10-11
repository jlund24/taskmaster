package com.student.john.taskmanager2;


import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.student.john.taskmanager2.DateConverter.DateStringValues.A_WEEK;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.FRIDAY;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.MONDAY;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.NEXT_2_WEEKS;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.OVERDUE;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.TODAY;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.TOMORROW;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.WEDNESDAY;
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
        assertEquals(dueDateTime, new LocalDateTime(2017,10,5,23,59));
    }

    @Test
    public void testTomorrowCreation()
    {
        System.out.println("TOMORROW");
        LocalDateTime dueDateTime = converter.getDateFromWord(TOMORROW);
        printDate(dueDateTime);
        assertEquals(dueDateTime, new LocalDateTime(2017,10,6,23,59));
    }

    @Test
    public void testFridayCreation()
    {
        System.out.println("FRIDAY");
        LocalDateTime dueDateTime = converter.getDateFromWord(FRIDAY);
        printDate(dueDateTime);
        assertEquals(dueDateTime, new LocalDateTime(2017,10,6,23,59));
    }

    @Test
    public void testAWeekCreation()
    {
        System.out.println("A WEEK");
        LocalDateTime dueDateTime = converter.getDateFromWord(A_WEEK);
        printDate(dueDateTime);
        assertEquals(dueDateTime, new LocalDateTime(2017,10,12,23,59));
    }

    @Test
    public void testNext2WeeksCreation()
    {
        System.out.println("NEXT 2 WEEKS");
        LocalDateTime dueDateTime = converter.getDateFromWord(NEXT_2_WEEKS);
        printDate(dueDateTime);
        assertEquals(dueDateTime, new LocalDateTime(2017,10,19,23,59));
    }

    @Test
    public void testOverdue()
    {
        LocalDateTime dueDate = new LocalDateTime(2017,10,4,23,59);
        String dueDateString = converter.getWordFromDate(dueDate);
        assertEquals(dueDateString, OVERDUE);
    }

    @Test
    public void testDueToday()
    {
        System.out.println("DUE TODAY");
        LocalDateTime dueDate = new LocalDateTime(2017,10,5,4,59);
        String dueDateString = converter.getWordFromDate(dueDate);
        printDate(dueDate);
        printCurrentDate();
        assertEquals(dueDateString, TODAY);
    }

    @Test
    public void testDueTomorrow()
    {
        System.out.println("DUE TOMORROW");
        LocalDateTime dueDate = new LocalDateTime(2017,10,6,23,59);
        String dueDateString = converter.getWordFromDate(dueDate);
        printDate(dueDate);
        printCurrentDate();
        assertEquals(dueDateString, TOMORROW);
    }

    @Test
    public void testDueWeekday()
    {
        System.out.println("DUE WEEKDAY");
        LocalDateTime dueDate = new LocalDateTime(2017,10,11,23,59);
        String dueDateString = converter.getWordFromDate(dueDate);
        printDate(dueDate);
        printCurrentDate();
        assertEquals(dueDateString, WEDNESDAY);
    }

    @Test
    public void testDueOtherDate()
    {
        System.out.println("DUE OTHER DATE");
        LocalDateTime dueDate = new LocalDateTime(2017,12,28,4,59);
        String dueDateString = converter.getWordFromDate(dueDate);
        printDate(dueDate);
        printCurrentDate();
        assertEquals(dueDateString, "Dec 28");
    }

    private void printDate(LocalDateTime dueDateTime)
    {
        System.out.println("Due Date: " + dueDateTime.getMonthOfYear() + "/" + dueDateTime.getDayOfMonth() + "/" + dueDateTime.getYear());
        System.out.println("Due Time: " + dueDateTime.getHourOfDay() + ":" + dueDateTime.getMinuteOfHour());
        System.out.println();
    }

    private void printCurrentDate()
    {
        LocalDateTime currentDate = new LocalDateTime(2017,10,5,0,0);
        System.out.println("Current Date: " + currentDate.getMonthOfYear() + "/" + currentDate.getDayOfMonth() + "/" + currentDate.getYear());
        System.out.println("Current Time: " + currentDate.getHourOfDay() + ":" + currentDate.getMinuteOfHour());
        System.out.println();
    }


}
