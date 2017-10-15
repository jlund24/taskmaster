package com.student.john.taskmanager2.models;


import org.joda.time.Duration;

public class Plan {

    private TaskList tasks;
    private ICustomTimePeriod duration;
    private TaskList removedTasks = new TaskList();

    public Plan(TaskList tasks, CustomTimePeriod duration)
    {
        this.tasks = tasks;
        this.duration = duration;
    }

    public TaskList getTaskList() {

        return tasks;
    }

    public ICustomTimePeriod getDuration() {
        return duration;
    }

    public TaskList getRemovedTasks() {
        return removedTasks;
    }

    public void setTasks(TaskList tasks) {
        this.tasks = tasks;
    }

    public void setDuration(ICustomTimePeriod duration) {
        this.duration = duration;
    }

    public void setRemovedTasks(TaskList removedTasks) {
        this.removedTasks = removedTasks;
    }

    public void addTask(Task task)
    {
        tasks.add(task);
        removedTasks.removeTask(task);
    }

    public void removeTask(String taskID)
    {
        if (tasks.getTask(taskID) != null)
        {
            removedTasks.add(tasks.getTask(taskID));
            tasks.getTask(taskID).setPlanned(false);
            tasks.getTask(taskID).setDurationPlanned(null);
            tasks.removeTaskByID(taskID);

        }

    }

    public void decrementDurationBy(ICustomTimePeriod duration)
    {
        this.duration = new CustomTimePeriod(this.duration.minus(duration));
    }

    public void markTaskCompleted(String taskID)
    {
        Task task = tasks.getTask(taskID);
        decrementDurationBy(task.getDurationPlanned());
        task.markFullTaskDoneFromPlan();
        tasks.removeTaskByID(taskID);

    }

    public void markTaskSegmentCompleted(String taskID)
    {
        Task task = tasks.getTask(taskID);
        task.markOneUnitDoneFromPlan();
        decrementDurationBy(task.getDivisibleUnit());
    }

    public void clear()
    {
        for (Task task : tasks.getTaskList())
        {
            task.setDurationPlanned(null);
            task.setPlanned(false);
        }
    }
}
