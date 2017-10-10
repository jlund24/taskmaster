package com.student.john.taskmanager2.addEditTaskViews_Presenters;


import com.student.john.taskmanager2.models.Task;

import java.util.Map;

public interface IAdd_EditTaskActivity {

    Map<String, Object> getChangedFields();
    void setExistingFields(Task task);
}
