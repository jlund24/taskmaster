package com.student.john.taskmanager2.addEditTaskViews_Presenters;


import com.student.john.taskmanager2.ClientModel;

interface IAdd_EditTaskPresenter {

    //Add_EditActivity callbacks
    void onSave();
    void onDueDateClicked();
    void onDueTimeClicked();
    void onDurationClicked();
    void initiateEditMode(String editingTaskID);

    //DueDatePickerFragment callbacks
    void onTopLeftDueDateOptionClicked();
    void onTopRightDueDateOptionClicked();
    void onBottomLeftDueDateOptionClicked();
    void onBottomRightDueDateOptionClicked();
    void getDueDatePickerConfiguration(DueDatePickerFragment dialog);
    void onPickerDueDateClicked(int year, int month, int day);
    void onDueDateClearClicked();
    void onDueDateCancelClicked();
    void onDueDateSaveClicked();

    //DueTimePickerFragment callbacks
    void onDueTimeButtonOptionClicked(ClientModel.ButtonEnum button);
    void onPickerDueTimeClicked(int hour, int minute);
    void getDueTimePickerConfiguration(DueTimePickerFragment dialog);
    void onDueTimeClearClicked();
    void onDueTimeCancelClicked();
    void onDueTimeSaveClicked();

    //DurationPickerFragment callbacks
    void onDurationButtonOptionClicked(ClientModel.ButtonEnum button);
    void getDurationPickerConfiguration(DurationPickerFragment dialog);
    void onDurationClearClicked();
    void onDurationCancelClicked();
    void onDurationSaveClicked();
    void onDurationSpinnerItemSelected();
    void onDivisibleUnitCheckBoxChanged(boolean checked);
    void onDivisibleUnitItemSelected();
}
