package com.student.john.taskmanager2.models;


import android.support.annotation.NonNull;
import android.util.Log;

import com.student.john.taskmanager2.DateConverter;
import com.student.john.taskmanager2.CustomDurationConverter;
import com.student.john.taskmanager2.TimeConverter;

import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

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
    private ICustomTimePeriod duration = null;
    //how much of the task has been completed (used for DivisibleUnit stuff)
    private ICustomTimePeriod durationCompleted = null;
    private ICustomTimePeriod durationPlanned = null;
    private ICustomTimePeriod divisibleUnit = null;
    private Boolean planned = false;
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
    public Task(){
        this.taskID = UUID.randomUUID().toString();
    };

    public Task (String title, Map<String, Object> params)
    {
        this.title = title;
        if (params != null)
        {
            //get rest of the provided parameters
            this.dueDateTime = params.containsKey(DUE_DATE_TIME) ? (LocalDateTime) params.get(DUE_DATE_TIME) : null;

            this.priority = params.containsKey(PRIORITY) ? (Integer) params.get(PRIORITY) : null;

            this.duration = params.containsKey(DURATION) ? (ICustomTimePeriod) params.get(DURATION) : new CustomTimePeriod(new Duration(0));

            this.divisibleUnit = params.containsKey(DIVISIBLE_UNIT) ? (ICustomTimePeriod) params.get(DIVISIBLE_UNIT) :
                    new CustomTimePeriod(new Duration(0));

            this.completed = params.containsKey(COMPLETED) ? (Boolean) params.get(COMPLETED) : false;

            this.taskID = params.containsKey(TASKID) ? (String) params.get(TASKID) : null;
        }


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

        if (taskID == null)
        {
            generateTaskID();
        }
        return taskID;
    }

    public Double getPointValue()
    {
        if (this.completed)
        {
            return 0d;
        }
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
        if (durationPlanned != null)
        {
            sb.append("Duration Planned: " + this.durationPlanned.getHours() + ":" + this.durationPlanned.getMinutes() + "\n");
        }
        sb.append("Priority: " + this.priority + "\n");
        if (divisibleUnit != null)
        {
            sb.append("Divisible Unit: " + this.divisibleUnit.getHours() + ":" + this.divisibleUnit.getMinutes() + "\n");
        }
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

    public void generateTaskID()
    {
        if (this.taskID == null)
        {
            this.taskID = UUID.randomUUID().toString();
        }
    }

    public String getDueDateString()
    {
        if (dueDateTime != null)
        {
            DateConverter converter = new DateConverter();
            return converter.getWordFromDate(this.dueDateTime);
        }
        return null;
    }

    public String getDurationString()
    {
        if (duration != null)
        {
            CustomDurationConverter converter = new CustomDurationConverter();
            return converter.getWordFromDuration(this.duration);
        }
        return null;
    }

    public String getDueTimeString()
    {
        if (dueDateTime != null)
        {
            TimeConverter converter = new TimeConverter();
            return converter.getWordFromTime(this.dueDateTime);
        }
        return null;
    }

    public ICustomTimePeriod getDurationCompleted() {

        if (durationCompleted == null)
        {
            durationCompleted = new CustomTimePeriod(new Duration(0));
        }
        return durationCompleted;
    }

    public void setDurationCompleted(ICustomTimePeriod durationCompleted) {
        this.durationCompleted = durationCompleted;
    }

    public ICustomTimePeriod getDurationPlanned() {
        return durationPlanned;
    }

    public void setDurationPlanned(ICustomTimePeriod durationPlanned) {
        this.durationPlanned = durationPlanned;
    }

    public ICustomTimePeriod getDurationLeft()
    {
        if (this.durationCompleted != null && this.duration != null)
        {
           return new CustomTimePeriod(this.duration.minus(durationCompleted));
        }
        else if (this.duration != null)
        {
            return new CustomTimePeriod(this.duration.getDurationObject());
        }
        else
        {
            return null;
        }
    }

    public ICustomTimePeriod getDurationLeftUnplanned()
    {
        ICustomTimePeriod durationLeft = getDurationLeft();
        if (this.durationPlanned != null && durationLeft != null)
        {
            return new CustomTimePeriod(durationLeft.minus(durationPlanned));
        }
        else if (durationLeft != null)
        {
            return new CustomTimePeriod(durationLeft.getDurationObject());
        }
        else
        {
            return null;
        }
    }

    public void markOneDivisibleUnitCompleted()
    {
        if (this.durationCompleted == null)
        {
            this.durationCompleted = new CustomTimePeriod(new Duration(0));
        }
        if (this.divisibleUnit == null)
        {
            Log.d("Task","no divisible unit when markOneDivisbleUnitCompleted() called");
            return;
        }
        this.durationCompleted = new CustomTimePeriod(this.durationCompleted.plus(this.divisibleUnit));
    }

    public void markOneDivisibleUnitPlanned()
    {
        if (this.durationPlanned == null)
        {
            this.durationPlanned = new CustomTimePeriod(new Duration(0));
        }
        if (this.divisibleUnit == null)
        {
            Log.d("Task","no divisible unit when markOneDivisbleUnitPlanned() called");
            return;
        }

        this.durationPlanned = new CustomTimePeriod(this.durationPlanned.plus(this.divisibleUnit));
    }

    public void markOnePlannedUnitDone()
    {
        if (this.durationPlanned == null) {
            Log.d("Task","no durationPlanned when markOnePlannedUnitDone() called");
            return;
        }
        if (this.divisibleUnit == null){
            Log.d("Task","no divisible unit when markOneDivisbleUnitPlanned() called");
            return;
        }

        this.durationPlanned = new CustomTimePeriod(this.durationPlanned.minus(this.divisibleUnit));
    }

    public void markOneUnitDoneFromPlan()
    {
        markOneDivisibleUnitCompleted();
        markOnePlannedUnitDone();
    }

    public void markFullTaskDoneFromPlan()
    {
        this.durationCompleted = new CustomTimePeriod(getDurationCompleted().plus(this.durationPlanned));
        setDurationPlanned(null);
        if (duration.getTotalAsMinutes() == durationCompleted.getTotalAsMinutes())
        {
            setCompleted(true);
        }
        setPlanned(false);
    }

    public Boolean getPlanned() {
        return planned;
    }

    public void setPlanned(Boolean planned) {
        this.planned = planned;
    }
}
