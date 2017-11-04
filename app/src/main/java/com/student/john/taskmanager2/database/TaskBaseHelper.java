package com.student.john.taskmanager2.database;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.student.john.taskmanager2.database.TaskDbSchema.TaskTable;

import java.util.ArrayList;

import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.COMPLETED;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.DELETED;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.DIVISIBLE_UNIT;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.DUE_DATE_TIME;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.DURATION;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.DURATION_COMPLETED;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.DURATION_PLANNED;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.Cols.PLANNED;
import static com.student.john.taskmanager2.database.TaskDbSchema.TaskTable.NAME;


public class TaskBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "taskBase.db";

    public TaskBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NAME + "( " +
                TaskTable.Cols.TASK_ID + ", " +
                TaskTable.Cols.TITLE + ", " +
                DUE_DATE_TIME + ", " +
                DURATION + ", " +
                TaskTable.Cols.DIVISIBLE_UNIT + ", " +
                DURATION_COMPLETED + ", " +
                DURATION_PLANNED + ", " +
                PLANNED + ", " +
                COMPLETED + ", " +
                DELETED + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
