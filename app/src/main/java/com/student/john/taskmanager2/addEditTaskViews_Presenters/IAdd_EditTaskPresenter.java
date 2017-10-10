package com.student.john.taskmanager2.addEditTaskViews_Presenters;


import com.student.john.taskmanager2.ClientModel;

interface IAdd_EditTaskPresenter {

    //Add_EditActivity callbacks
    //void onSave();
    void onDueDateClicked();
    void onDueTimeClicked();

    //DueDatePickerFragment callbacks
    void onTopLeftDueDateOptionClicked();
    void onTopRightDueDateOptionClicked();
    void onBottomLeftDueDateOptionClicked();
    void onBottomRightDueDateOptionClicked();
    void getDueDatePickerConfiguration(DueDatePickerFragment dialog);
    void onPickerDueDateClicked(int year, int month, int day);

    //DueTimePickerFragment callbacks
    void onDueTimeButtonOptionClicked(ClientModel.ButtonEnum button);
    void onPickerDueTimeClicked(int hour, int minute);
    void getDueTimePickerConfiguration(DueTimePickerFragment dialog);
}
