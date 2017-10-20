package com.student.john.taskmanager2;


import com.student.john.taskmanager2.models.CustomTimePeriod;
import com.student.john.taskmanager2.models.Plan;
import com.student.john.taskmanager2.models.Task;
import com.student.john.taskmanager2.models.TaskList;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.student.john.taskmanager2.DateConverter.DateStringValues.A_WEEK;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.FRIDAY;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.TODAY;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.TOMORROW;
import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.HR_1;
import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.HR_1_5;
import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.HR_3;
import static com.student.john.taskmanager2.CustomDurationConverter.DurationStringValues.MIN_30;
import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.AFTERNOON;
import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.EVENING;
import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.MIDNIGHT;
import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.MORNING;


public class ClientModel {

    public enum ButtonEnum {
        DD_TOP_LEFT, DD_TOP_RIGHT, DD_BOTTOM_LEFT, DD_BOTTOM_RIGHT,
        DT_TOP_LEFT, DT_TOP_RIGHT, DT_BOTTOM_LEFT, DT_BOTTOM_RIGHT,
        DUR_TOP_LEFT, DUR_TOP_RIGHT, DUR_BOTTOM_LEFT, DUR_BOTTOM_RIGHT,
        PR_TOP_LEFT, PR_TOP_RIGHT, PR_BOTTOM_LEFT, PR_BOTTOM_RIGHT
    }

    public enum SortEnum {
        DUE_DATE, DURATION_LEFT, DUE_DATE_AND_DURATION
    }

    private static final ClientModel ourInstance = new ClientModel();
    private TaskList allTasks = new TaskList();


    private Plan currentPlan;

    private Map<ButtonEnum, String> buttonToContentMap = new HashMap<>();
    private Map<String, ButtonEnum> contentToButtonMap = new HashMap<>();
    private SortEnum sortType;

    private List<String> durationSuggestions = new ArrayList<>();

    //SINGLETON FUNCTIONS------------------------

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel() {

        setDefaultButtonMaps();
        setUpDurationSuggestions();
//        Task task = new Task();
//        task.setTitle("prototype");
//        task.setDueDateTime(new LocalDateTime(2017,10,17,10,0));
//        task.setDuration(new CustomTimePeriod(new Period(5,0,0,0)));
//        task.setDivisibleUnit(new CustomTimePeriod(new Period(1,0,0,0)));
//        allTasks.add(task);
//
//        task = new Task();
//        task.setTitle("study for 252");
//        task.setDueDateTime(new LocalDateTime(2017,10,18,16,0));
//        task.setDuration(new CustomTimePeriod(new Period(1,0,0,0)));
//        allTasks.add(task);
//
//        task = new Task();
//        task.setTitle("read BoM");
//        task.setDueDateTime(new LocalDateTime(2017,10,16,23,59));
//        task.setDuration(new CustomTimePeriod(new Period(0,30,0,0)));
//        allTasks.add(task);
//
//        task = new Task();
//        task.setTitle("4.1 online");
//        task.setDueDateTime(new LocalDateTime(2017,10,17,23,59));
//        task.setDuration(new CustomTimePeriod(new Period(1,0,0,0)));
//        allTasks.add(task);
    }

    //-------------------------------------------

    private void setDefaultButtonMaps()
    {
        buttonToContentMap.put(ButtonEnum.DD_TOP_LEFT, TODAY);
        buttonToContentMap.put(ButtonEnum.DD_TOP_RIGHT, TOMORROW);
        buttonToContentMap.put(ButtonEnum.DD_BOTTOM_LEFT, FRIDAY);
        buttonToContentMap.put(ButtonEnum.DD_BOTTOM_RIGHT, A_WEEK);
        buttonToContentMap.put(ButtonEnum.DT_TOP_LEFT, MORNING);
        buttonToContentMap.put(ButtonEnum.DT_TOP_RIGHT, AFTERNOON);
        buttonToContentMap.put(ButtonEnum.DT_BOTTOM_LEFT, EVENING);
        buttonToContentMap.put(ButtonEnum.DT_BOTTOM_RIGHT, MIDNIGHT);
        buttonToContentMap.put(ButtonEnum.DUR_TOP_LEFT, MIN_30);
        buttonToContentMap.put(ButtonEnum.DUR_TOP_RIGHT, HR_1);
        buttonToContentMap.put(ButtonEnum.DUR_BOTTOM_LEFT, HR_1_5);
        buttonToContentMap.put(ButtonEnum.DUR_BOTTOM_RIGHT, HR_3);


        contentToButtonMap.put(TODAY, ButtonEnum.DD_TOP_LEFT);
        contentToButtonMap.put(TOMORROW, ButtonEnum.DD_TOP_RIGHT);
        contentToButtonMap.put(FRIDAY, ButtonEnum.DD_BOTTOM_LEFT);
        contentToButtonMap.put(A_WEEK, ButtonEnum.DD_BOTTOM_RIGHT);
        contentToButtonMap.put(MORNING, ButtonEnum.DT_TOP_LEFT);
        contentToButtonMap.put(AFTERNOON, ButtonEnum.DT_TOP_RIGHT);
        contentToButtonMap.put(EVENING, ButtonEnum.DT_BOTTOM_LEFT);
        contentToButtonMap.put(MIDNIGHT, ButtonEnum.DT_BOTTOM_RIGHT);
        contentToButtonMap.put(MIN_30, ButtonEnum.DUR_TOP_LEFT);
        contentToButtonMap.put(HR_1, ButtonEnum.DUR_TOP_RIGHT);
        contentToButtonMap.put(HR_1_5, ButtonEnum.DUR_BOTTOM_LEFT);
        contentToButtonMap.put(HR_3, ButtonEnum.DUR_BOTTOM_RIGHT);

    }

    private void setUpDurationSuggestions()
    {
        durationSuggestions.add("15m");
        durationSuggestions.add("30m");
        durationSuggestions.add("1h");
        durationSuggestions.add("1h 30m");
        durationSuggestions.add("2h");
        durationSuggestions.add("3h");
        durationSuggestions.add("4h");
        durationSuggestions.add("5h");
        durationSuggestions.add("6h");
        durationSuggestions.add("7h");
        durationSuggestions.add("8h");
        durationSuggestions.add("9h");
        durationSuggestions.add("10h");
        durationSuggestions.add("11h");
        durationSuggestions.add("12h");
        durationSuggestions.add("13h");
        durationSuggestions.add("14h");
        durationSuggestions.add("15h");
        durationSuggestions.add("16h");
        durationSuggestions.add("17h");
        durationSuggestions.add("18h");
        durationSuggestions.add("19h");
        durationSuggestions.add("20h");
        durationSuggestions.add("45m");
    }

    public List<String> getSuggestionsContaining(String s)
    {
        ArrayList<String> suggestions = new ArrayList<>();
        if (s.equals(""))
        {
            return durationSuggestions.subList(1,6);
        }

        for (String suggestion : durationSuggestions)
        {
            if (suggestion.contains(s))
            {
                suggestions.add(suggestion);
            }
        }
        return suggestions;
    }

    public String getContentFromButton(ButtonEnum button)
    {
        return buttonToContentMap.get(button);
    }

    public ButtonEnum getButtonFromContent(String content)
    {
        return contentToButtonMap.get(content);
    }

    public void addTask(Task task)
    {

        allTasks.add(task);
    }

    public TaskList getAllTasks()
    {
        return this.allTasks;
    }

    public Task getTask(String taskID)
    {
        return allTasks.getTask(taskID);
    }

    public void setCurrentPlan(Plan plan)
    {
        this.currentPlan = plan;
    }

    public Plan getCurrentPlan()
    {
//        ArrayList<Task> taskList = new ArrayList<>();
//        taskList.add(new Task("hey", new HashMap<String, Object>()));
//        taskList.add(new Task("hi", new HashMap<String, Object>()));
//        TaskList tasks = new TaskList(taskList);
//        currentPlan = new Plan(tasks, new CustomTimePeriod(new Period(3,0,0,0)));
        return currentPlan;
    }

    public void generatePlan(CustomTimePeriod duration)
    {
        currentPlan = new Plan(getTasksForPlan(duration), duration);
    }

    public TaskList getSortableTasks()
    {
        TaskList sortableTasks = new TaskList();
        for (Task task : allTasks.getTaskList())
        {
            if (task.getDuration() != null && task.getDueDateTime() != null && !task.getCompleted() &&
                    task.getDurationLeftUnplanned().getTotalAsMinutes() > 0)
            {
                sortableTasks.add(task);
            }
        }
        return sortableTasks;
    }

    public TaskList getTasksDueToday()
    {
        return allTasks.getTasksByDueDate(new LocalDateTime());
    }

    private TaskList getTasksForPlan(CustomTimePeriod duration)
    {

        //first get all tasks due today
        TaskList forToday = allTasks.getTasksByDueDate(new LocalDateTime());
        for (Task task : forToday.getTaskList())
        {
            task.setPlanned(true);
            task.setDurationPlanned(task.getDurationLeftUnplanned());
        }
        long minutesToWork = duration.getDurationObject().getStandardMinutes();

        //if the total duration of tasks due today is already more than how much they said they would work,
        //just return what we have
        if (minutesToWork <= forToday.getTotalDurationPlannedOfTasksInMin())
        {
            return forToday;
        }

        minutesToWork -= forToday.getTotalDurationPlannedOfTasksInMin();

        TaskList sortableTasks = getSortableTasks();
        sortableTasks.sortByPoints();
        //System.out.println(allTasks.toString());

        boolean moreTasks = true;
        while(minutesToWork >= 15 && !sortableTasks.getTaskList().isEmpty() && moreTasks)
        {
            for (Task task : sortableTasks.getTaskList())
            {
                if (task.getDivisibleUnit() != null && task.getDivisibleUnit().getTotalAsMinutes() != 0 &&
                    task.getDivisibleUnit().getTotalAsMinutes() <= minutesToWork &&
                    task.getDivisibleUnit().getTotalAsMinutes() <= task.getDurationLeftUnplanned().getTotalAsMinutes())
                {

                    task.markOneDivisibleUnitPlanned();
                    task.setPlanned(true);
                    forToday.add(task);
                    minutesToWork -= task.getDivisibleUnit().getTotalAsMinutes();
                    break;

                }
                else if ((int)task.getDurationLeftUnplanned().getTotalAsMinutes() <= (int)minutesToWork + 1)
                {
                    forToday.add(task);
                    task.setPlanned(true);
                    minutesToWork -= task.getDurationLeftUnplanned().getTotalAsMinutes();
                    task.setDurationPlanned(new CustomTimePeriod(task.getDurationPlanned().plus(task.getDurationLeftUnplanned())));
                    sortableTasks.removeTask(task);
                    break;
                }
                else if (task == sortableTasks.getTaskList().get(sortableTasks.getTaskList().size()-1))
                {
                    moreTasks = false;
                    break;
                }
            }
            sortableTasks.sortByPoints();
        }

        return forToday;
    }

    public void setAllTasks(TaskList allTasks) {
        this.allTasks = allTasks;
    }

    public TaskList getVisibleTasks()
    {
        TaskList visibleTasks = new TaskList();
        for (Task task : allTasks.getTaskList())
        {
            if (!task.getCompleted())
            {
                visibleTasks.add(task);
            }
        }
        if (sortType != null)
        {
            switch(sortType)
            {
                case DUE_DATE:
                    visibleTasks.sortByDueDate();
                    break;
                case DURATION_LEFT:
                    visibleTasks.sortByDuration();
                    break;
                case DUE_DATE_AND_DURATION:
                    visibleTasks.sortByPoints();
                    break;
                default:
                    break;
            }
        }
        return visibleTasks;
    }

    public void setSortType(SortEnum sortType) {
        this.sortType = sortType;
    }

    public SortEnum getSortType() {
        return sortType;
    }
}
