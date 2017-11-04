package com.student.john.taskmanager2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.student.john.taskmanager2.database.SettingCursorWrapper;
import com.student.john.taskmanager2.database.SettingsBaseHelper;
import com.student.john.taskmanager2.database.TaskBaseHelper;
import com.student.john.taskmanager2.database.TaskCursorWrapper;
import com.student.john.taskmanager2.database.TaskDbSchema;
import com.student.john.taskmanager2.models.CustomTimePeriod;
import com.student.john.taskmanager2.models.Plan;
import com.student.john.taskmanager2.models.Task;
import com.student.john.taskmanager2.models.TaskList;

import org.joda.time.LocalDateTime;

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
import static com.student.john.taskmanager2.database.SettingsDbSchema.SettingsTable.Cols.VALUE;
import static com.student.john.taskmanager2.database.SettingsDbSchema.SettingsTable.NAME;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.COMPLETED;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.DELETED;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.DIVISIBLE_UNIT;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.DUE_DATE_TIME;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.DURATION;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.DURATION_COMPLETED;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.DURATION_PLANNED;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.PLANNED;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.TITLE;


public class ClientModel {

    public enum ButtonEnum {
        DD_TOP_LEFT, DD_TOP_RIGHT, DD_BOTTOM_LEFT, DD_BOTTOM_RIGHT,
        DT_TOP_LEFT, DT_TOP_RIGHT, DT_BOTTOM_LEFT, DT_BOTTOM_RIGHT,
        DUR_TOP_LEFT, DUR_TOP_RIGHT, DUR_BOTTOM_LEFT, DUR_BOTTOM_RIGHT,
        PR_TOP_LEFT, PR_TOP_RIGHT, PR_BOTTOM_LEFT, PR_BOTTOM_RIGHT
    }

    public enum SortEnum {
        DUE_DATE, DURATION_LEFT, DUE_DATE_AND_DURATION, TITLE
    }

    private static final ClientModel ourInstance = new ClientModel();
    private TaskList allTasks = new TaskList();


    private Plan currentPlan;

    private Map<ButtonEnum, String> buttonToContentMap = new HashMap<>();
    private Map<String, ButtonEnum> contentToButtonMap = new HashMap<>();
    private SortEnum sortType;

    private List<String> durationSuggestions = new ArrayList<>();

    private Context context;
    private SQLiteDatabase taskDatabase;
    private SQLiteDatabase settingDatabase;

    //SINGLETON FUNCTIONS------------------------

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel() {

        //allTasks = getUncompletedAndUndeletedTasks();

        setDefaultButtonMaps();
        setUpDurationSuggestions();
        Task task = new Task();
        task.setTitle("Swipe right to complete");
        task.setDueDateTime(new LocalDateTime().withTime(23,59,0,0));
        //task.setDuration(new CustomTimePeriod(new Period(0,1,0,0)));
        allTasks.add(task);

        task = new Task();
        task.setTitle("Swipe left to delete");
        task.setDueDateTime(new LocalDateTime().withTime(23,59,0,0));
        //task.setDuration(new CustomTimePeriod(new Period(0,1,0,0)));
        allTasks.add(task);

//        task = new Task();
//        task.setTitle("");
//        task.setDueDateTime(new LocalDateTime(2017,10,17,23,59));
//        task.setDuration(new CustomTimePeriod(new Period(1,0,0,0)));
//        allTasks.add(task);
        //hey
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
        if (allTasks.getTask(task.getTaskID()) != null)
        {
            updateTaskInDB(task);
        }
        else
        {
            addTaskToDB(task);
        }
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

//    public TaskList getTasksDueToday()
//    {
//        return allTasks.getTasksByDueDate(new LocalDateTime());
//    }

    private TaskList getTasksForPlan(CustomTimePeriod duration)
    {

        //first get all tasks due today
        TaskList forToday = new TaskList();
//        TaskList forToday = allTasks.getTasksByDueDate(new LocalDateTime());
//        for (Task task : forToday.getTaskList())
//        {
//            task.setPlanned(true);
//            task.setDurationPlanned(task.getDurationLeftUnplanned());
//        }
        long minutesToWork = duration.getDurationObject().getStandardMinutes();

        //if the total duration of tasks due today is already more than how much they said they would work,
        //just return what we have
//        if (minutesToWork <= forToday.getTotalDurationPlannedOfTasksInMin())
//        {
//            return forToday;
//        }
//
//        minutesToWork -= forToday.getTotalDurationPlannedOfTasksInMin();

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
                    updateTaskInDB(task);
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
                    updateTaskInDB(task);
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
                case TITLE:
                    visibleTasks.sortByTitle();
                default:
                    break;
            }
        }
        return visibleTasks;
    }

    public void setSortType(SortEnum sortType) {

        if (sortType == null)
        {
            updateSettingInDB("Sort", null);
        }
        else
        {
            switch(sortType)
            {
                case DUE_DATE:
                    updateSettingInDB("Sort", "DueDate");
                    break;
                case DURATION_LEFT:
                    updateSettingInDB("Sort", "Duration");
                    break;
                case DUE_DATE_AND_DURATION:
                    updateSettingInDB("Sort", "DueDateAndDuration");
                    break;
                case TITLE:
                    updateSettingInDB("Sort", "Title");
                    break;
                default:

                    break;
            }
        }

        this.sortType = sortType;
    }

    public SortEnum getSortType() {
        return sortType;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void initializeDB(Context context)
    {
        this.context = context;
        taskDatabase = new TaskBaseHelper(context).getWritableDatabase();
        this.allTasks = getUncompletedAndUndeletedTasks();
        if (getTaskCount() == 0)
        {
            //add tutorial tasks
            Task task = new Task();
            task.setTitle("Swipe right to complete");
            task.setDueDateTime(new LocalDateTime().withTime(23,59,0,0));
            addTask(task);

            task = new Task();
            task.setTitle("Swipe left to delete");
            task.setDueDateTime(new LocalDateTime().withTime(23,59,0,0));
            addTask(task);
        }
        settingDatabase = new SettingsBaseHelper(context).getWritableDatabase();
        populateSettings();
        buildPlanFromDB();
    }

    public void buildPlanFromDB()
    {
        //query settings table for plan
        //if duration isn't null, set the duration to that and query task table for planned tasks
        //that aren't completed or deleted
        String planDuration = getSettingValueFromDB("Plan");
        CustomDurationConverter converter = new CustomDurationConverter();
        CustomTimePeriod duration = converter.getDurationFromString(planDuration);
        if (duration != null)
        {
            TaskList tasksForPlan = getPlannedTasks();
            this.currentPlan = new Plan(tasksForPlan, duration);
        }
    }

    public void setPlanInDB(Plan plan)
    {
        String duration = null;
        if (plan != null)
        {
            duration = new CustomDurationConverter().getWordFromDuration(plan.getDuration());
        }
        updateSettingInDB("Plan", duration);
    }

    public TaskList getPlannedTasks()
    {
        TaskList plannedTasks = new TaskList();
        for (Task task : allTasks.getTaskList())
        {
            if (task.getPlanned())
            {
                plannedTasks.add(task);
            }
        }
        return plannedTasks;
    }

    public void addSettingToDB(String settingTitle, String settingValue)
    {
        ContentValues values = getContentValuesForSetting(settingTitle, settingValue);
        settingDatabase.insert(NAME,null,values);
    }

    public void updateSettingInDB(String settingTitle, String settingValue)
    {
        ContentValues values = getContentValuesForSetting(settingTitle, settingValue);

        settingDatabase.update(NAME, values,
                TITLE + " = ?",
                new String[]{settingTitle});
    }

    public int getTaskCount()
    {
        int taskCount = 0;

        TaskCursorWrapper cursor = queryTasks(null, null);

        try {
            cursor.moveToFirst();
            taskCount = cursor.getCount();
        } finally {
            cursor.close();
        }

        return taskCount;
    }

    public String getSettingValueFromDB(String settingTitle)
    {
        String settingValue = "";
        String whereClause = TITLE + " = ?";

        SettingCursorWrapper cursor = querySettings(whereClause, new String[]{settingTitle});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                settingValue = cursor.getSetting()[1];
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return settingValue;
    }

    public Map<String, String> getAllSettingsFromDB()
    {
        HashMap<String, String> settings = new HashMap<>();

        SettingCursorWrapper cursor = querySettings(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String[] values = cursor.getSetting();
                settings.put(values[0], values[1]);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return settings;
    }

    public void populateSettings()
    {
        Map<String, String> settings = getAllSettingsFromDB();
        if (settings.containsKey("Plan"))
        {
            buildPlanFromDB();
        }
        else
        {
            addSettingToDB("Plan",null);
        }
        if (settings.containsKey("Sort"))
        {
            String sortValue = settings.get("Sort");
            if (sortValue == null)
            {
                sortValue = "";
            }
            switch (sortValue)
            {
                case "DueDate":
                    sortType = SortEnum.DUE_DATE;
                    break;
                case "Duration":
                    sortType = SortEnum.DURATION_LEFT;
                    break;
                case "DueDateAndDuration":
                    sortType = SortEnum.DUE_DATE_AND_DURATION;
                    break;
                case "Title":
                    sortType = SortEnum.TITLE;
                default:
                    break;
            }
        }
        else
        {
            addSettingToDB("Sort", null);
        }
    }

    public void addTaskToDB(Task task)
    {
        ContentValues values = getContentValues(task);
        taskDatabase.insert(TaskDbSchema.TaskTable.NAME, null, values);
    }

    public void updateTaskInDB(Task task)
    {
        String taskID = task.getTaskID();
        ContentValues values = getContentValues(task);

        taskDatabase.update(TaskDbSchema.TaskTable.NAME, values,
                TaskDbSchema.TaskTable.Cols.TASK_ID + " = ?",
                new String[] { taskID });
    }

    public TaskList getUncompletedAndUndeletedTasks()
    {
        TaskList taskList = new TaskList();
        String whereClause = TaskDbSchema.TaskTable.Cols.COMPLETED + " = 0 and " +
                TaskDbSchema.TaskTable.Cols.DELETED + " = 0";

        TaskCursorWrapper cursor = queryTasks(whereClause, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                taskList.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return taskList;
    }

    public TaskList getCompletedTasks()
    {
        TaskList taskList = new TaskList();
        String whereClause = COMPLETED + " = 1";

        TaskCursorWrapper cursor = queryTasks(whereClause, new String[]{});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                taskList.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return taskList;
    }

    public int deleteDeletedTasks()
    {
        String whereClause = DELETED + " = 1";
        return taskDatabase.delete(TaskDbSchema.TaskTable.NAME, whereClause, null);
    }

    private static ContentValues getContentValues(Task task)
    {
        ContentValues values = new ContentValues();
        DateConverter dateConverter = new DateConverter();
        CustomDurationConverter durationConverter = new CustomDurationConverter();
        values.put(TaskDbSchema.TaskTable.Cols.TASK_ID, task.getTaskID());
        values.put(TITLE, task.getTitle());
        values.put(DUE_DATE_TIME, dateConverter.getDBStringFromLocalDateTime(task.getDueDateTime()));
        values.put(DURATION, durationConverter.getWordFromDuration(task.getDuration()));
        values.put(TaskDbSchema.TaskTable.Cols.DIVISIBLE_UNIT, durationConverter.getWordFromDuration(task.getDivisibleUnit()));
        values.put(DURATION_COMPLETED, durationConverter.getWordFromDuration(task.getDurationCompleted()));
        values.put(DURATION_PLANNED, durationConverter.getWordFromDuration(task.getDurationPlanned()));
        values.put(PLANNED, task.getPlanned());
        values.put(COMPLETED, task.getCompleted());
        values.put(DELETED, task.getDeleted());

        return values;
    }

    private TaskCursorWrapper queryTasks(String whereClause, String[] whereArgs)
    {
        Cursor cursor = taskDatabase.query(
                TaskDbSchema.TaskTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new TaskCursorWrapper(cursor);
    }

    private static ContentValues getContentValuesForSetting(String settingTitle, String settingValue)
    {
        ContentValues values = new ContentValues();
        values.put(TITLE, settingTitle);
        values.put(VALUE, settingValue);

        return values;
    }

    private SettingCursorWrapper querySettings(String whereClause, String[] whereArgs)
    {
        Cursor cursor = settingDatabase.query(
                NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new SettingCursorWrapper(cursor);
    }
}
