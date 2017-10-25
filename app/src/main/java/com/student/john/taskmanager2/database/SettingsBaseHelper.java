package com.student.john.taskmanager2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.student.john.taskmanager2.database.SettingsDbSchema.SettingsTable.Cols.TITLE;
import static com.student.john.taskmanager2.database.SettingsDbSchema.SettingsTable.Cols.VALUE;
import static com.student.john.taskmanager2.database.SettingsDbSchema.SettingsTable.NAME;


public class SettingsBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "settingsBase.db";

    public SettingsBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + NAME + "( " +
        TITLE + ", " +
        VALUE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
