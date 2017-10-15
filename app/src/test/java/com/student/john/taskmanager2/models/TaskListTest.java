package com.student.john.taskmanager2.models;


import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TaskListTest {

    private TaskList taskList;

    @Before
    public void setUp()
    {
        taskList = new TaskList();

        Task task = new Task("task1", null);
        task.setDuration(new CustomTimePeriod(new Period(1,0,0,0)));
        task.setDueDateTime(new LocalDateTime(2017,10,14,23,59));
        taskList.add(task);

        task = new Task("task2", null);
        task.setDueDateTime(new LocalDateTime(2017,10,14,1,0));
        task.setDuration(new CustomTimePeriod(new Period(4,0,0,0)));
        task.setDivisibleUnit(new CustomTimePeriod(new Period(0,30,0,0)));
        task.markOneDivisibleUnitCompleted();
        task.setDivisibleUnit(new CustomTimePeriod(new Period(1,0,0,0)));
        task.markOneDivisibleUnitCompleted();
        taskList.add(task);

        task = new Task ("task3", null);
        task.setDuration(new CustomTimePeriod(new Period(0,30,0,0)));
        task.setDueDateTime(new LocalDateTime(2017,10,15,1,0));
        taskList.add(task);

    }

    @Test
    public void testGetTotalDurationOfTasksInMin()
    {
        ICustomTimePeriod expected = new CustomTimePeriod(new Period(4,0,0,0));
        assertEquals(expected.getTotalAsMinutes(), taskList.getTotalDurationOfTasksInMin());
    }

    @Test
    public void testGetTasksByDueDate()
    {
        TaskList tasksDueToday = taskList.getTasksByDueDate(new LocalDateTime(2017,10,14,9,30));
        assertEquals(2, tasksDueToday.getTaskList().size());
    }
}
