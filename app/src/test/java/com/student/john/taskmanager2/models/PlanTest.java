package com.student.john.taskmanager2.models;


import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class PlanTest {

    private Plan plan;

    @Before
    public void setUp()
    {
        TaskList taskList = new TaskList();

        Task task = new Task("finish prototype 2 code", null);
        task.setDueDateTime(new LocalDateTime(2017,10,18,23,59));
        task.setDuration(new CustomTimePeriod(new Period(12,0,0,0)));
        task.setDivisibleUnit(new CustomTimePeriod(new Period(0,30,0,0)));
        task.setDurationPlanned(new CustomTimePeriod(new Period(2,30,0,0)));
        task.setTaskID("prototype");
        taskList.add(task);

        task = new Task("read Bom", null);
        task.setDueDateTime(new LocalDateTime(2017,10,14,23,59));
        task.setDuration(new CustomTimePeriod(new Period(0,30,0,0)));
        task.setDurationPlanned(new CustomTimePeriod(new Period(0,30,0,0)));
        task.setTaskID("bom");
        taskList.add(task);

        task = new Task("3.3 online", null);
        task.setDueDateTime(new LocalDateTime(2017,10,14,23,59));
        task.setDuration(new CustomTimePeriod(new Period(1,0,0,0)));
        task.setDurationPlanned(new CustomTimePeriod(new Period(1,0,0,0)));
        task.setTaskID("math");
        taskList.add(task);

        plan = new Plan(taskList, new CustomTimePeriod(new Period(4,0,0,0)));
    }

    @Test
    public void testRemovingTask()
    {
        Task bomTask = plan.getTaskList().getTask("bom");
        plan.removeTask(bomTask.getTaskID());

        assertEquals(bomTask, plan.getRemovedTasks().getTask("bom"));
        assertFalse(bomTask.getPlanned());
        assertTrue(bomTask.getDurationPlanned() == null);
        assertNull(plan.getTaskList().getTask("bom"));
    }

    @Test
    public void testMarkingSegmentOfTaskCompleted()
    {
        Task prototypeTask = plan.getTaskList().getTask("prototype");
        plan.markTaskSegmentCompleted("prototype");

        assertEquals(new CustomTimePeriod(new Period(2,0,0,0)).getTotalAsMinutes(),
                prototypeTask.getDurationPlanned().getTotalAsMinutes());
        assertEquals(new CustomTimePeriod(new Period(0,30,0,0)).getTotalAsMinutes(),
                prototypeTask.getDurationCompleted().getTotalAsMinutes());
        assertEquals(new CustomTimePeriod(new Period(3,30,0,0)).getTotalAsMinutes(),
                plan.getDuration().getTotalAsMinutes());
    }

    @Test
    public void testMarkingWholePlannedTaskCompleted()
    {
        Task mathTask = plan.getTaskList().getTask("math");
        plan.markTaskCompleted("math");

        assertTrue(mathTask.getCompleted());
        assertFalse(mathTask.getPlanned());
        assertEquals(new CustomTimePeriod(new Period(1,0,0,0)).getTotalAsMinutes(),
                mathTask.getDurationCompleted().getTotalAsMinutes());
        assertEquals(new CustomTimePeriod(new Period(3,0,0,0)).getTotalAsMinutes(),
                plan.getDuration().getTotalAsMinutes());
    }


}
