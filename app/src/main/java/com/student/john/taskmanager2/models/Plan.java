package com.student.john.taskmanager2.models;


public class Plan {

    private TaskList tasks;
    private CustomTimePeriod duration;
    private TaskList removedTasks;

    public Plan(TaskList tasks, CustomTimePeriod duration)
    {
        this.tasks = tasks;
        this.duration = duration;
    }

    public TaskList getTaskList() {

        return tasks;
    }

    public CustomTimePeriod getDuration() {
        return duration;
    }

    public TaskList getRemovedTasks() {
        return removedTasks;
    }

    public void setTasks(TaskList tasks) {
        this.tasks = tasks;
    }

    public void setDuration(CustomTimePeriod duration) {
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

    public void removeTaskByID(String taskID)
    {
        if (tasks.getTask(taskID) != null)
        {
            removedTasks.add(tasks.getTask(taskID));
            tasks.removeTaskByID(taskID);
        }

    }
}
