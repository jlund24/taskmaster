package com.student.john.taskmanager2.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import static com.student.john.taskmanager2.database.SettingsDbSchema.SettingsTable.Cols.TITLE;
import static com.student.john.taskmanager2.database.SettingsDbSchema.SettingsTable.Cols.VALUE;


public class SettingCursorWrapper extends CursorWrapper {


    public SettingCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public String[] getSetting()
    {
        String title = getString(getColumnIndex(TITLE));
        String value = getString(getColumnIndex(VALUE));

        return new String[]{title, value};
    }
}
