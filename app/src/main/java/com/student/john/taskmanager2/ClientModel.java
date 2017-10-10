package com.student.john.taskmanager2;


import com.student.john.taskmanager2.models.Task;
import com.student.john.taskmanager2.models.TaskList;

import java.util.HashMap;
import java.util.Map;

import static com.student.john.taskmanager2.DateConverter.DateStringValues.A_WEEK;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.FRIDAY;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.TODAY;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.TOMORROW;
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

    private static final ClientModel ourInstance = new ClientModel();
    private TaskList allTasks = new TaskList();
    private TaskList currentlyShownTasks = new TaskList();

    private Map<ButtonEnum, String> buttonToContentMap = new HashMap<>();
    private Map<String, ButtonEnum> contentToButtonMap = new HashMap<>();


    //SINGLETON FUNCTIONS------------------------

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel() {
        setDefaultButtonMaps();
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

        contentToButtonMap.put(TODAY, ButtonEnum.DD_TOP_LEFT);
        contentToButtonMap.put(TOMORROW, ButtonEnum.DD_TOP_RIGHT);
        contentToButtonMap.put(FRIDAY, ButtonEnum.DD_BOTTOM_LEFT);
        contentToButtonMap.put(A_WEEK, ButtonEnum.DD_BOTTOM_RIGHT);
        contentToButtonMap.put(MORNING, ButtonEnum.DT_TOP_LEFT);
        contentToButtonMap.put(AFTERNOON, ButtonEnum.DT_TOP_RIGHT);
        contentToButtonMap.put(EVENING, ButtonEnum.DT_BOTTOM_LEFT);
        contentToButtonMap.put(MIDNIGHT, ButtonEnum.DT_BOTTOM_RIGHT);
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
}
