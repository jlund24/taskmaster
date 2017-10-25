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

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}
