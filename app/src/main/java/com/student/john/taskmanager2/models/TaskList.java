package com.student.john.taskmanager2.models;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskList {

    List<Task> taskList;

    public TaskList(List<Task> taskList)
    {
        this.taskList = taskList;
    }

    public TaskList()
    {
        taskList = new ArrayList<Task>();
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
        taskList.add(task);
    }

    @Override
    public String toString() {
        String str = "TaskList\n\n";

        for (Task task: this.taskList) {
            str += task.toString() + "\n\n";
        }

        return str;
    }

    public void remove(Task task)
    {
        this.taskList.remove(task);
    }
}
