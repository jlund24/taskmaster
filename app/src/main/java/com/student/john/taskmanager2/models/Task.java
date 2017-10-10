package com.student.john.taskmanager2.models;


import android.support.annotation.NonNull;

import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

import java.util.Comparator;
import java.util.Map;
import java.util.UUID;

import static com.student.john.taskmanager2.models.Task.TaskParamTitle.COMPLETED;
import static com.student.john.taskmanager2.models.Task.TaskParamTitle.DIVISIBLE_UNIT;
import static com.student.john.taskmanager2.models.Task.TaskParamTitle.DUE_DATE_TIME;
import static com.student.john.taskmanager2.models.Task.TaskParamTitle.DURATION;
import static com.student.john.taskmanager2.models.Task.TaskParamTitle.PRIORITY;
import static com.student.john.taskmanager2.models.Task.TaskParamTitle.TASKID;

public class Task implements ITask, Comparable {

    private String title;
    //private String user;
    private Integer priority = null;
    private LocalDateTime dueDateTime = null;
    private ICustomTimePeriod duration = new CustomTimePeriod(new Duration(0));
    private ICustomTimePeriod divisibleUnit = new CustomTimePeriod(new Duration(0));
    private Boolean completed = false;
    private String taskID = null;

    public static final class TaskParamTitle {
        public static final String TASKID = "TaskID";
        public static final String TITLE = "Title";
        public static final String DUE_DATE_TIME = "DueDateTime";
        public static final String PRIORITY = "Priority";
        public static final String DURATION = "Duration";
        public static final String DIVISIBLE_UNIT = "DivisibleUnit";
        public static final String COMPLETED = "Completed";
    }

    //for sorting
    public Task(){};

    public Task (String title, Map<String, Object> params)
    {
        this.title = title;

        //get rest of the provided parameters
        this.dueDateTime = params.containsKey(DUE_DATE_TIME) ? (LocalDateTime) params.get(DUE_DATE_TIME) : null;

        this.priority = params.containsKey(PRIORITY) ? (Integer) params.get(PRIORITY) : null;

        this.duration = params.containsKey(DURATION) ? (ICustomTimePeriod) params.get(DURATION) : new CustomTimePeriod(new Duration(0));

        this.divisibleUnit = params.containsKey(DIVISIBLE_UNIT) ? (ICustomTimePeriod) params.get(DIVISIBLE_UNIT) :
                new CustomTimePeriod(new Duration(0));

        this.completed = params.containsKey(COMPLETED) ? (Boolean) params.get(COMPLETED) : false;

        this.taskID = params.containsKey(TASKID) ? (String) params.get(TASKID) : null;

        if (this.taskID == null)
        {
            this.taskID = UUID.randomUUID().toString();
        }
    }

    //used only for generating segments of a longer duration task
    public Task (Task task)
    {
        this.title = task.title;
        this.dueDateTime = task.getDueDateTime();
        this.priority = task.getPriority();
        this.duration = task.getDivisibleUnit();
        this.divisibleUnit = new CustomTimePeriod(new Duration(0));
        this.completed = task.completed;
        this.taskID = UUID.randomUUID().toString();
    }


    public String getTitle() {
        return title;
    }

    public Integer getPriority() {
        return priority;
    }

    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }

    public ICustomTimePeriod getDuration() {
        return duration;
    }

    public ICustomTimePeriod getDivisibleUnit() {
        return divisibleUnit;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public String getTaskID() {
        return taskID;
    }

    public Double getPointValue()
    {
        return this.duration.getTotalAsMinutes() / (this.getMinutesUntilDue()) * 1000;
    }

    private Double getMinutesUntilDue()
    {
        if (dueDateTime != null)
        {
            Minutes minutes = Minutes.minutesBetween(new LocalDateTime(), dueDateTime);
            if (Days.daysBetween(new LocalDateTime(), dueDateTime).getDays() == 1 )
            {
                minutes = minutes.dividedBy(2);
            }
            else if (Days.daysBetween(new LocalDateTime(), dueDateTime).getDays() == 0)
            {
                minutes = minutes.dividedBy(4);
            }
            return minutes.getMinutes() * 1.0;
        }
        else
        {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: " + this.title + "\n");
        sb.append("Due Date: " + this.dueDateTime.getMonthOfYear() + "/" + this.dueDateTime.getDayOfMonth() + "/" + this.dueDateTime.getYear() + "\n");
        sb.append("Due Time: " + this.dueDateTime.getHourOfDay() + ":" + this.dueDateTime.getMinuteOfHour() + "\n");
        sb.append("Duration: " + this.duration.getHours() + ":" + this.duration.getMinutes() + "\n");
        sb.append("Priority: " + this.priority + "\n");
        sb.append("Divisible Unit: " + this.divisibleUnit.getHours() + ":" + this.divisibleUnit.getMinutes() + "\n");
        sb.append("Completed: " + this.completed + "\n");
        sb.append("Points: " + this.getPointValue());
        return sb.toString();
    }

    @Override
    public int compareTo(@NonNull Object o) {

        Task task = (Task)o;
        if (this.getPointValue() > task.getPointValue())
        {
            return -1;
        }
        else if (this.getPointValue() < task.getPointValue())
        {
            return 1;
        }
        else
        {
            return this.getDueDateTime().compareTo(task.getDueDateTime());
        }
    }

    public void reduceDurationOneUnit()
    {
        this.duration.minus(this.divisibleUnit);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public void setDuration(ICustomTimePeriod duration) {
        this.duration = duration;
    }

    public void setDivisibleUnit(ICustomTimePeriod divisibleUnit) {
        this.divisibleUnit = divisibleUnit;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }
}
