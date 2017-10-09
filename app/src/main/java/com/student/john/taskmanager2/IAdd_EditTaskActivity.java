package com.student.john.taskmanager2;


import com.student.john.taskmanager2.models.Task;

import java.util.Map;

public interface IAdd_EditTaskActivity {

    Map<String, Object> getChangedFields();
    void setExistingFields(Task task);
}
