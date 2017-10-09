package com.student.john.taskmanager2;


import com.student.john.taskmanager2.models.Task;
import com.student.john.taskmanager2.models.TaskList;

import java.util.HashMap;
import java.util.Map;

import static com.student.john.taskmanager2.DateConverter.DateStringValues.A_WEEK;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.FRIDAY;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.TODAY;
import static com.student.john.taskmanager2.DateConverter.DateStringValues.TOMORROW;


class ClientModel {

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


    //SINGLETON FUNCTIONS------------------------

    static ClientModel getInstance() {
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
    }

    public String getContentFromButton(ButtonEnum button)
    {
        return buttonToContentMap.get(button);
    }

    public void addTask(Task task)
    {
        allTasks.add(task);
    }
}
