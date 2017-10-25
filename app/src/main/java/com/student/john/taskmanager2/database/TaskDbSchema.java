package com.student.john.taskmanager2.database;


public class TaskDbSchema {

    public static final class TaskTable {
        public static final String NAME = "tasks";

        public static final class Cols {
            public static final String TASK_ID = "TaskID";
            public static final String TITLE = "Title";
            public static final String DUE_DATE_TIME = "DueDateTime";
            public static final String DURATION = "Duration";
            public static final String DIVISIBLE_UNIT = "DivisibleUnit";
            public static final String DURATION_COMPLETED = "DurationCompleted";
            public static final String DURATION_PLANNED = "DurationPlanned";
            public static final String PLANNED = "Planned";
            public static final String COMPLETED = "Completed";
            public static final String DELETED = "Deleted";
        }
    }
}
