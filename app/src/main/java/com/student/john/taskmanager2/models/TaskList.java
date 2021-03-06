package com.student.john.taskmanager2.models;


import com.student.john.taskmanager2.ClientModel;

import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskList {

    private List<Task> taskList;
    private Map<String, Task> taskMap;

    public TaskList(List<Task> taskList, Map<String, Task> taskMap)
    {

        this.taskList = taskList;
        this.taskMap = taskMap;
    }

    public TaskList(List<Task> taskList)
    {
        this.taskList = taskList;
        this.taskMap = new HashMap<String, Task>();
        for(Task task : taskList)
        {
            taskMap.put(task.getTaskID(), task);
        }
    }

    public TaskList()
    {

        taskList = new ArrayList<>();
        taskMap = new HashMap<>();
    }

    public void sortByPoints()
    {
        Collections.sort(taskList, new Comparator<Task>()
        {
            @Override
            public int compare(Task task1, Task task2)
            {
                if (task1.getPointValue() > task2.getPointValue())
                {
                    return -1;
                }
                else if (task1.getPointValue() < task2.getPointValue())
                {
                    return 1;
                }
                else
                {
                    return task1.getDueDateTime().compareTo(task2.getDueDateTime());
                }
            }
        });
    }

    public void sortByPointsNotPlan()
    {
        Collections.sort(taskList, new Comparator<Task>()
        {
            @Override
            public int compare(Task task1, Task task2)
            {
                if (task1.getPointValueWithDurationLeft() > task2.getPointValueWithDurationLeft())
                {
                    return -1;
                }
                else if (task1.getPointValueWithDurationLeft() < task2.getPointValueWithDurationLeft())
                {
                    return 1;
                }
                else
                {
                    return task1.getDueDateTime().compareTo(task2.getDueDateTime());
                }
            }
        });
    }

    public void sortByDueDate()
    {
        Collections.sort(taskList, new Comparator<Task>()
        {
            @Override
            public int compare(Task task1, Task task2) {
                if (task1.getDueDateTime() == null && task2.getDueDateTime() == null)
                {
                    return -1;
                }
                else if (task1.getDueDateTime() == null)
                {
                    return 1;
                }
                else if (task2.getDueDateTime() == null)
                {
                    return -1;
                }
                else
                {
                    return task1.getDueDateTime().compareTo(task2.getDueDateTime());
                }
            }
        });
    }

    public void sortByDuration()
    {
        Collections.sort(taskList, new Comparator<Task>()
        {
            @Override
            public int compare(Task task1, Task task2) {
                if (task1.getDurationLeft() == null && task2.getDurationLeft() == null)
                {
                    return -1;
                }
                else if (task1.getDurationLeft() == null)
                {
                    return 1;
                }
                else if (task2.getDurationLeft() == null)
                {
                    return -1;
                }
                else
                {
                    if (task1.getDurationLeft().getTotalAsMinutes() > task2.getDurationLeft().getTotalAsMinutes())
                    {
                        return -1;
                    }
                    else
                    {
                        return 1;
                    }
                }
            }
        });
    }

    public void sortByTitle()
    {
        Collections.sort(taskList, new Comparator<Task>()
        {
            @Override
            public int compare(Task task1, Task task2) {
                return task1.getTitle().compareTo(task2.getTitle());
            }
        });
    }



//    public void sortByFields()
//    {
//        Collections.sort(taskList, new Comparator<Task>()
//        {
//            @Override
//            public int compare(Task task1, Task task2) {
//                if (!task1.getDueDateTime().equals(task2.getDueDateTime()))
//                {
//                    return task1.getDueDateTime().compareTo(task2.getDueDateTime());
//                }
//                else if ()
//                {
//
//                }
//            }
//        });
//    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void add(Task task)
    {
        if (taskMap.containsKey(task.getTaskID()))
        {

        }
        else
        {
            taskList.add(task);
            taskMap.put(task.getTaskID(), task);
        }

    }

    @Override
    public String toString() {
        String str = "TaskList\n\n";

        for (Task task: this.taskList) {
            str += task.toString() + "\n\n";
        }

        return str;
    }

    public void removeTask(Task task)
    {
        this.taskList.remove(task);
        this.taskMap.remove(task.getTaskID());
    }

    public void removeTaskByID(String taskID)
    {
        if (taskMap.containsKey(taskID))
        {
            taskList.remove (taskMap.get(taskID));
            taskMap.remove(taskID);
        }
    }

    public Task getTask(String taskID)
    {
        return taskMap.get(taskID);
    }

//    public void clear()
//    {
//        taskList.clear();
//        taskMap.clear();
//    }

    public long getTotalDurationPlannedOfTasksInMin()
    {
        long total = 0;
        for (Task task : taskList)
        {
            if (task.getDuration() != null)
            {
                if (task.getDurationPlanned() != null)
                {
                    total += task.getDurationPlanned().getTotalAsMinutes();
                }
                else
                {
                    total += task.getDurationLeft().getTotalAsMinutes();
                }

            }
        }

        return total;
    }

    public ICustomTimePeriod getTotalDurationOfTasks()
    {
        CustomTimePeriod totalDuration = new CustomTimePeriod(new Duration(0));
        for (Task task : taskList)
        {
            if (task.getDuration() != null)
            {
                if (task.getDurationLeft() != null)
                {
                    totalDuration = new CustomTimePeriod(totalDuration.plus(task.getDurationLeft()));
                }
                else
                {
                    totalDuration = new CustomTimePeriod(totalDuration.plus(task.getDuration()));
                }

            }
        }
        return totalDuration;
    }

//    public Task getTaskByTitle(String title)
//    {
//        for(Task task : taskList)
//        {
//            if (task.getTitle().equals(title))
//            {
//                return task;
//            }
//        }
//        return null;
//    }

//    public TaskList getTasksByDueDate(LocalDateTime dueDate)
//    {
//        TaskList tasksWithDueDate = new TaskList();
//        for (Task task : taskList)
//        {
//            if (!task.getCompleted() && task.getDueDateTime() != null && task.getDuration() != null)
//            {
//                if (dueDate.getDayOfMonth() == task.getDueDateTime().getDayOfMonth() &&
//                        dueDate.getMonthOfYear() == task.getDueDateTime().getMonthOfYear() &&
//                        dueDate.getYear() == task.getDueDateTime().getYear())
//                {
//                    tasksWithDueDate.add(task);
//                }
//
//            }
//        }
//        return tasksWithDueDate;
//    }


}
