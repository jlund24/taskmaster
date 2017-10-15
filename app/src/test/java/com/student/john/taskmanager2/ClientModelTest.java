package com.student.john.taskmanager2;


import com.student.john.taskmanager2.models.CustomTimePeriod;
import com.student.john.taskmanager2.models.Plan;
import com.student.john.taskmanager2.models.Task;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

public class ClientModelTest {

    private ClientModel model = ClientModel.getInstance();

    @Before
    public void setUp()
    {

    }

    @Test
    public void testPlanCreation()
    {
        Task task = new Task("finish prototype 2 code", null);
        task.setDueDateTime(new LocalDateTime(2017,10,18,23,59));
        task.setDuration(new CustomTimePeriod(new Period(12,0,0,0)));
        task.setDivisibleUnit(new CustomTimePeriod(new Period(0,30,0,0)));
        model.addTask(task);

        task = new Task("test prototype", null);
        task.setDueDateTime(new LocalDateTime(2017,10,19,23,59));
        task.setDuration(new CustomTimePeriod(new Period(4,0,0,0)));
        task.setDivisibleUnit(new CustomTimePeriod(new Period(1,0,0,0)));
        model.addTask(task);

        task = new Task("study for 252 test", null);
        task.setDueDateTime(new LocalDateTime(2017,10,16,23,59));
        task.setDuration(new CustomTimePeriod(new Period(2,0,0,0)));
        task.setDivisibleUnit(new CustomTimePeriod(new Period(1,0,0,0)));
        model.addTask(task);

        task = new Task("read Bom", null);
        task.setDueDateTime(new LocalDateTime(2017,10,14,23,59));
        task.setDuration(new CustomTimePeriod(new Period(0,30,0,0)));
        model.addTask(task);

        task = new Task("read Bom", null);
        task.setDueDateTime(new LocalDateTime(2017,10,15,23,59));
        task.setDuration(new CustomTimePeriod(new Period(0,30,0,0)));
        model.addTask(task);

        task = new Task("read Bom", null);
        task.setDueDateTime(new LocalDateTime(2017,10,16,23,59));
        task.setDuration(new CustomTimePeriod(new Period(0,30,0,0)));
        model.addTask(task);

        task = new Task("read Bom", null);
        task.setDueDateTime(new LocalDateTime(2017,10,17,23,59));
        task.setDuration(new CustomTimePeriod(new Period(0,30,0,0)));
        model.addTask(task);

        task = new Task("read Bom", null);
        task.setDueDateTime(new LocalDateTime(2017,10,18,23,59));
        task.setDuration(new CustomTimePeriod(new Period(0,30,0,0)));
        model.addTask(task);

        task = new Task("read Bom", null);
        task.setDueDateTime(new LocalDateTime(2017,10,19,23,59));
        task.setDuration(new CustomTimePeriod(new Period(0,30,0,0)));
        model.addTask(task);

        task = new Task("read Bom", null);
        task.setDueDateTime(new LocalDateTime(2017,10,20,23,59));
        task.setDuration(new CustomTimePeriod(new Period(0,30,0,0)));
        model.addTask(task);

        task = new Task("read Bom", null);
        task.setDueDateTime(new LocalDateTime(2017,10,21,23,59));
        task.setDuration(new CustomTimePeriod(new Period(0,30,0,0)));
        model.addTask(task);

        task = new Task("3.3 online", null);
        task.setDueDateTime(new LocalDateTime(2017,10,14,23,59));
        task.setDuration(new CustomTimePeriod(new Period(1,0,0,0)));
        model.addTask(task);

        task = new Task("3.3 written", null);
        task.setDueDateTime(new LocalDateTime(2017,10,16,16,59));
        task.setDuration(new CustomTimePeriod(new Period(1,0,0,0)));
        model.addTask(task);

        task = new Task("4.1a online", null);
        task.setDueDateTime(new LocalDateTime(2017,10,17,23,59));
        task.setDuration(new CustomTimePeriod(new Period(0,30,0,0)));
        model.addTask(task);

        task = new Task("4.1a written", null);
        task.setDueDateTime(new LocalDateTime(2017,10,18,16,59));
        task.setDuration(new CustomTimePeriod(new Period(1,0,0,0)));
        model.addTask(task);

        task = new Task("4.1b written", null);
        task.setDueDateTime(new LocalDateTime(2017,10,19,23,59));
        task.setDuration(new CustomTimePeriod(new Period(1,0,0,0)));
        model.addTask(task);

        task = new Task("4.1b written", null);
        task.setDueDateTime(new LocalDateTime(2017,10,20,16,59));
        task.setDuration(new CustomTimePeriod(new Period(1,0,0,0)));
        model.addTask(task);

        task = new Task("NDFS assignment 2", null);
        task.setDueDateTime(new LocalDateTime(2017,10,20,16,59));
        task.setDuration(new CustomTimePeriod(new Period(6,0,0,0)));
        task.setDivisibleUnit(new CustomTimePeriod(new Period(1,0,0,0)));
        model.addTask(task);

        model.generatePlan(new CustomTimePeriod(new Period(4,0,0,0)));

        Plan plan = model.getCurrentPlan();
        System.out.println(plan.getTaskList().toString());

    }
}
