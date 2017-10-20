package com.student.john.taskmanager2.models;


import com.student.john.taskmanager2.ClientModel;

import org.joda.time.Duration;

import static com.student.john.taskmanager2.models.Plan.PlanState.EMPTY;
import static com.student.john.taskmanager2.models.Plan.PlanState.FULL;
import static com.student.john.taskmanager2.models.Plan.PlanState.OVERFULL;
import static com.student.john.taskmanager2.models.Plan.PlanState.UNDERFULL;

public class Plan {

    public static final class PlanState {
        public static final String EMPTY = "PlanEmpty";
        public static final String OVERFULL = "Overfull";
        public static final String FULL = "Full";
        public static final String UNDERFULL = "Underfull";
    }

    private TaskList tasks;
    private ICustomTimePeriod duration;
    private TaskList removedTasks = new TaskList();
    private ClientModel model = ClientModel.getInstance();

    public Plan(TaskList tasks, CustomTimePeriod duration)
    {
        this.tasks = tasks;
        this.duration = duration;
    }

    public TaskList getTaskList() {

        return tasks;
    }

    public void refreshTasks()
    {
        for (Task task : tasks.getTaskList())
        {
            if (task.getCompleted() || task.getDeleted() || !task.getPlanned())
            {
                tasks.removeTaskByID(task.getTaskID());
            }
        }
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

    public void addTaskToPlan(Task task)
    {
        long extraMinutes = duration.getTotalAsMinutes() - tasks.getTotalDurationPlannedOfTasksInMin();
        //task is planned and we're adding more time
        if (task.getPlanned() && task.getDurationLeftUnplanned().getTotalAsMinutes() > 0)
        {
            while (this.duration.getTotalAsMinutes() >= tasks.getTotalDurationPlannedOfTasksInMin() &&
                    task.getDurationLeftUnplanned().getTotalAsMinutes() >= 0) {
                task.markOneDivisibleUnitPlanned();
            }
        }
        else if (!task.getPlanned() && task.getDivisibleUnit() != null &&
                task.getDivisibleUnit().getTotalAsMinutes() > 0)
        {
            while (this.duration.getTotalAsMinutes() > tasks.getTotalDurationPlannedOfTasksInMin() &&
                    task.getDurationLeftUnplanned().getTotalAsMinutes() > 0) {
                task.markOneDivisibleUnitPlanned();
                task.setPlanned(true);
                if (tasks.getTask(task.getTaskID()) == null)
                {
                    tasks.add(task);
                }
            }
            System.out.println();
        }
        else if (!task.getPlanned() && task.getDurationLeft().getTotalAsMinutes() <= extraMinutes)
        {
            task.setPlanned(true);
            task.setDurationPlanned(new CustomTimePeriod(new Duration(task.getDurationLeft().getDurationObject().getMillis())));
            tasks.add(task);
        }



    }

    public void removeTask(String taskID)
    {
        if (tasks.getTask(taskID) != null)
        {
            //removedTasks.add(tasks.getTask(taskID));
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

    public String getStatus()
    {
        if (tasks.getTaskList().isEmpty())
        {
            return EMPTY;
        }
        else if (isOverfull())
        {
            return OVERFULL;
        }
        else
        {
            TaskList possibleTasksToFill = getPossibleTasksToFillIn();
            if (!possibleTasksToFill.getTaskList().isEmpty())
            {
                return UNDERFULL;
            }
            else
            {
                return FULL;
            }
        }

    }

    private boolean isOverfull()
    {
        return tasks.getTotalDurationPlannedOfTasksInMin() > duration.getTotalAsMinutes();
    }

    public TaskList getPossibleTasksToFillIn()
    {

        TaskList eligibleTasks = new TaskList();
        TaskList sortableTasks = model.getSortableTasks();
        long planMinutesToWork = duration.getTotalAsMinutes() - tasks.getTotalDurationPlannedOfTasksInMin();

        for (Task task : sortableTasks.getTaskList())
        {
            if (task.getDivisibleUnit() != null)
            {
                if (task.getDivisibleUnit().getTotalAsMinutes() != 0 &&
                        task.getDivisibleUnit().getTotalAsMinutes() <= planMinutesToWork &&
                        task.getDivisibleUnit().getTotalAsMinutes() <= task.getDurationLeftUnplanned().getTotalAsMinutes())
                {

                    eligibleTasks.add(task);
                }
            }
            else if ((int)task.getDurationLeftUnplanned().getTotalAsMinutes() <= (int)planMinutesToWork + 1)
            {
                eligibleTasks.add(task);
            }

        }

        return eligibleTasks;

    }
}
