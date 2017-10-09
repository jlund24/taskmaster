package com.student.john.taskmanager2;


import com.student.john.taskmanager2.models.ICustomTimePeriod;

import org.joda.time.LocalDateTime;

import java.util.Scanner;
import java.util.regex.Pattern;

public class TaskInterpreter {
    private String title;
    //private String user;
    private Integer priority;
    private LocalDateTime dueDateTime;
    private ICustomTimePeriod duration;
    private ICustomTimePeriod divisibleUnit;
    private Boolean completed = false;
    private String taskID;

    public TaskInterpreter (String input)
    {
        Scanner scanner = new Scanner(input);
        while (scanner.hasNext(Pattern.compile("[a-zA-z]")))
        {
        }
    }
}
