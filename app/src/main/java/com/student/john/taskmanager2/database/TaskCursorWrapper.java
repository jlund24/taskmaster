package com.student.john.taskmanager2.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.student.john.taskmanager2.CustomDurationConverter;
import com.student.john.taskmanager2.DateConverter;
import com.student.john.taskmanager2.models.Task;

import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.DIVISIBLE_UNIT;


public class TaskCursorWrapper extends CursorWrapper {

    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask()
    {
        String taskID = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.TASK_ID));
        String title = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.TITLE));
        String dueDateTime = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.DUE_DATE_TIME));
        String duration = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.DURATION));
        String divisibleUnit = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.DIVISIBLE_UNIT));
        String durationCompleted = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.DURATION_COMPLETED));
        String durationPlanned = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.DURATION_PLANNED));
        int planned = getInt(getColumnIndex(TaskDbSchema.TaskTable.Cols.PLANNED));
        int completed = getInt(getColumnIndex(TaskDbSchema.TaskTable.Cols.COMPLETED));
        int deleted = getInt(getColumnIndex(TaskDbSchema.TaskTable.Cols.DELETED));

        DateConverter dateConverter = new DateConverter();
        CustomDurationConverter durationConverter = new CustomDurationConverter();
        Task task = new Task();
        task.setTaskID(taskID);
        task.setTitle(title);
        task.setDueDateTime(dateConverter.getLocalDateTimeFromDBString(dueDateTime));
        task.setDuration(durationConverter.getDurationFromString(duration));
        task.setDivisibleUnit(durationConverter.getDurationFromString(divisibleUnit));
        task.setDurationCompleted(durationConverter.getDurationFromString(durationCompleted));
        task.setDurationPlanned(durationConverter.getDurationFromString(durationPlanned));
        task.setPlanned(planned != 0);
        task.setCompleted(completed != 0);
        task.setDeleted(deleted != 0);

        return task;
    }
}
