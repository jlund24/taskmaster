package com.student.john.taskmanager2.models;


import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TaskTest {

    private Task task;

    @Before
    public void setUp()
    {
        task = new Task();
        task.setTitle("test task");
        task.setDuration(new CustomTimePeriod( new Period(1,30,0,0) ));
    }

    @Test
    public void testGetDurationLeftNoCompleted()
    {
        ICustomTimePeriod expected = new CustomTimePeriod( new Period(1,30,0,0) );
        assertEquals(expected.getTotalAsMinutes(), task.getDurationLeft().getTotalAsMinutes());
    }

    @Test
    public void testGetDurationLeftSomeCompleted()
    {
        task.setDurationCompleted(new CustomTimePeriod(new Period(0,30,0,0)));
        ICustomTimePeriod expected = new CustomTimePeriod( new Period(1,0,0,0) );
        assertEquals(expected.getTotalAsMinutes(), task.getDurationLeft().getTotalAsMinutes());
        task.setDurationCompleted(null);
    }

    @Test
    public void testGetDurationLeftAllCompleted()
    {
        task.setDurationCompleted(new CustomTimePeriod(new Period(1,30,0,0)));
        ICustomTimePeriod expected = new CustomTimePeriod( new Period(0,0,0,0) );
        assertEquals(expected.getTotalAsMinutes(), task.getDurationLeft().getTotalAsMinutes());
        task.setDurationCompleted(null);
    }

    @Test
    public void testMarkOneDivisibleUnitCompleted()
    {
        task.setDivisibleUnit(new CustomTimePeriod(new Period(0,30,0,0)));
        task.markOneDivisibleUnitCompleted();
        ICustomTimePeriod expected = new CustomTimePeriod(new Period(1,0,0,0));
        assertEquals(expected.getTotalAsMinutes(),task.getDurationLeft().getTotalAsMinutes());
    }


}
