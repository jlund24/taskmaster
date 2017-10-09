package com.student.john.taskmanager2.models;


import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import static com.student.john.taskmanager2.models.Task.TaskParamTitle.DIVISIBLE_UNIT;
import static com.student.john.taskmanager2.models.Task.TaskParamTitle.DUE_DATE_TIME;
import static com.student.john.taskmanager2.models.Task.TaskParamTitle.DURATION;
import static org.junit.Assert.*;


public class TaskSortingTest {

    @Before
    public void setUp()
    {

    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testCreation()
    {
        Map<String, Object> params = new HashMap<>();
        LocalDateTime dueDate = new LocalDateTime(2017,9,28,22,59,59);
        params.put(DUE_DATE_TIME, dueDate);
        params.put(DURATION, new CustomTimePeriod(new Period(3,0,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,30,0,0)));
        Task testTask = new Task("do wireframes for CS 301R", params);
        assertNotNull(testTask);
        System.out.println(testTask.toString());
    }

    @Test
    public void testPointsCalc()
    {
        Map<String, Object> params = new HashMap<>();
        LocalDateTime dueDate = new LocalDateTime(2017,9,28,23,59,59);
        params.put(DUE_DATE_TIME, dueDate);
        params.put(DURATION, new CustomTimePeriod(new Period(3,0,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,30,0,0)));
        Task testTask = new Task("do wireframes for CS 301R", params);
        System.out.println("Points: " + testTask.getPointValue() + "\n");

        dueDate = new LocalDateTime(2017,9,25,15,59,59);
        params.put(DUE_DATE_TIME, dueDate);
        params.put(DURATION, new CustomTimePeriod(new Period(0,30,0,0)));
        testTask = new Task("CS 252 hw", params);
        System.out.println("Points: " + testTask.getPointValue() + "\n");
    }

    @Test
    public void testSortingByPoints()
    {
        TaskList taskList = new TaskList();

        Map<String, Object> params = new HashMap<>();
        LocalDateTime dueDate = new LocalDateTime(2017,9,28,23,59,59);
        params.put(DUE_DATE_TIME, dueDate);
        params.put(DURATION, new CustomTimePeriod(new Period(3,0,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,30,0,0)));
        taskList.add( new Task("do wireframes for CS 301R", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,26,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(1,0,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,30,0,0)));
        taskList.add( new Task("1.8 online", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,27,17,0,0));
        params.put(DURATION, new CustomTimePeriod(new Period(1,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,30,0,0)));
        taskList.add( new Task("1.8 written", params) );

        taskList.sortByPoints();
        System.out.println(taskList.toString());
    }

    @Test
    public void testSortingByPointsMei()
    {
        TaskList taskList = new TaskList();

        Map<String, Object> params = new HashMap<>();
        LocalDateTime dueDate = new LocalDateTime(2017,10,1,23,59,59);
        params.put(DUE_DATE_TIME, dueDate);
        params.put(DURATION, new CustomTimePeriod(new Period(2,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(1,0,0,0)));
        taskList.add( new Task("Faith Paper", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,25,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("Read Bom", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,26,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("Read Bom", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,27,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("Read Bom", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,28,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("Read Bom", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,29,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("Read Bom", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,30,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("Read Bom", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,10,1,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("Read Bom", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,29,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(2,0,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("Dishes", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,30,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(2,0,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(1,0,0,0)));
        taskList.add( new Task("Online Humanities HW", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,26,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,15,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("French HW 1", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,27,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,15,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("French HW 2", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,28,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,15,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("French HW 3", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,30,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(4,0,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("Date", params) );

        params.clear();

        taskList.sortByPoints();
        System.out.println(taskList.toString());

        long timeToWork = 2;
        long minutesToWork = timeToWork * 60;
        TaskList forToday = new TaskList();

        while(minutesToWork >= 15)
        {
            for (Task task : taskList.getTaskList())
            {
                if (task.getDivisibleUnit().getTotalAsMinutes() != 0 &&
                        task.getDivisibleUnit().getTotalAsMinutes() <= minutesToWork)
                {
                    Task taskFragment = new Task(task);
                    task.reduceDurationOneUnit();
                    forToday.add(taskFragment);
                    minutesToWork -= taskFragment.getDuration().getTotalAsMinutes();
                    break;
                }
                else if (task.getDuration().getTotalAsMinutes() <= minutesToWork)
                {
                    forToday.add(task);
                    minutesToWork -= task.getDuration().getTotalAsMinutes();
                    taskList.remove(task);
                    break;
                }
            }
            taskList.sortByPoints();
        }
        System.out.println("----------------------------------------------");
        System.out.println(forToday.toString());
        System.out.println("----------------------------------------------");
        System.out.println(taskList.toString());
    }

    @Test
    public void testSortingByPointJohn()
    {
        TaskList taskList = new TaskList();

        Map<String, Object> params = new HashMap<>();
        LocalDateTime dueDate = new LocalDateTime(2017,10,3,23,59,59);
        params.put(DUE_DATE_TIME, dueDate);
        params.put(DURATION, new CustomTimePeriod(new Period(2,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(1,0,0,0)));
        taskList.add( new Task("Faith Paper", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,29,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("Read Bom", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,30,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("Read Bom", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,10,1,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("Read Bom", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,10,2,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(0,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,0,0,0)));
        taskList.add( new Task("Read Bom", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,10,3,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(3,0,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(1,0,0,0)));
        taskList.add( new Task("Study for NDFS Exam 1", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,10,2,23,59,0));
        params.put(DURATION, new CustomTimePeriod(new Period(1,30,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,30,0,0)));
        taskList.add( new Task("2.1 Written", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,9,30,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(1,0,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,30,0,0)));
        taskList.add( new Task("2.1 Online", params) );

        params.clear();

        params.put(DUE_DATE_TIME, new LocalDateTime(2017,10,3,23,59,59));
        params.put(DURATION, new CustomTimePeriod(new Period(3,0,0,0)));
        params.put(DIVISIBLE_UNIT, new CustomTimePeriod(new Period(0,30,0,0)));
        taskList.add( new Task("color mockups", params) );

        params.clear();


        taskList.sortByPoints();
        System.out.println(taskList.toString());

        long timeToWorkHours = 1;
        long timeToWorkMinutes = 30;
        long minutesToWork = timeToWorkHours * 60 + timeToWorkMinutes;

        TaskList forToday = new TaskList();

        while(minutesToWork >= 15)
        {
            for (Task task : taskList.getTaskList())
            {
                if (task.getDivisibleUnit().getTotalAsMinutes() != 0 &&
                        task.getDivisibleUnit().getTotalAsMinutes() <= minutesToWork)
                {
                    Task taskFragment = new Task(task);
                    task.reduceDurationOneUnit();
                    forToday.add(taskFragment);
                    minutesToWork -= taskFragment.getDuration().getTotalAsMinutes();
                    break;
                }
                else if (task.getDuration().getTotalAsMinutes() <= minutesToWork)
                {
                    forToday.add(task);
                    minutesToWork -= task.getDuration().getTotalAsMinutes();
                    taskList.remove(task);
                    break;
                }
            }
            taskList.sortByPoints();
        }
        System.out.println("----------------------------------------------");
        System.out.println(forToday.toString());
        System.out.println("----------------------------------------------");
        System.out.println(taskList.toString());
    }



    @Test
    public void sandbox()
    {
        LocalDateTime now = new LocalDateTime();
        LocalDateTime at4pm = new LocalDateTime(2017,9,25,16,0,0);
        System.out.println(Days.daysBetween(now, at4pm).getDays());
        System.out.println(Hours.hoursBetween(now, at4pm).getHours());
        System.out.println(Minutes.minutesBetween(now, at4pm).getMinutes());

    }



}
