package com.student.john.taskmanager2;


public interface IAdd_EditTaskPresenter {

    void onSave();
    void onDueDateClicked();
    void onTopLeftDueDateOptionClicked();
    void onTopRightDueDateOptionClicked();
    void onBottomLeftDueDateOptionClicked();
    void onBottomRightDueDateOptionClicked();
    void getDueDateButtonText(DueDatePickerFragment dialog);
}
