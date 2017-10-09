package com.student.john.taskmanager2;


import android.support.v4.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;

import static com.student.john.taskmanager2.models.Task.TaskParamTitle.DUE_DATE_TIME;


public class Add_EditTaskPresenter implements IAdd_EditTaskPresenter {

    private Add_EditTaskActivity activity;
    private Map<String, Object> taskParams = new HashMap<>();
    private ClientModel model = ClientModel.getInstance();

    public final String DUE_DATE_PICKER_DIALOG = "DueDatePickerDialog";

    public Add_EditTaskPresenter(Add_EditTaskActivity activity)
    {
        this.activity = activity;
    }

    public void setActivity(Add_EditTaskActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onSave() {

    }

    @Override
    public void onDueDateClicked() {

        //start up dueDatePickerFragment
        FragmentManager manager = activity.getSupportFragmentManager();
        DueDatePickerFragment dialog = DueDatePickerFragment
                .newInstance();
        dialog.show(manager, DUE_DATE_PICKER_DIALOG);

        dialog.setPresenter(this);

    }

    @Override
    public void onTopLeftDueDateOptionClicked() {
        String dueDateString = model.getContentFromButton(ClientModel.ButtonEnum.DD_TOP_LEFT);
        taskParams.put(DUE_DATE_TIME, dueDateString);
        activity.setDueDateSelectedWith(dueDateString);
    }

    @Override
    public void onTopRightDueDateOptionClicked() {
        String dueDateString = model.getContentFromButton(ClientModel.ButtonEnum.DD_TOP_RIGHT);
        taskParams.put(DUE_DATE_TIME, dueDateString);
        activity.setDueDateSelectedWith(dueDateString);
    }

    @Override
    public void onBottomLeftDueDateOptionClicked() {
        String dueDateString = model.getContentFromButton(ClientModel.ButtonEnum.DD_BOTTOM_LEFT);
        taskParams.put(DUE_DATE_TIME, dueDateString);
        activity.setDueDateSelectedWith(dueDateString);
    }

    @Override
    public void onBottomRightDueDateOptionClicked() {
        String dueDateString = model.getContentFromButton(ClientModel.ButtonEnum.DD_BOTTOM_RIGHT);
        taskParams.put(DUE_DATE_TIME, dueDateString);
        activity.setDueDateSelectedWith(dueDateString);
    }

    @Override
    public void getDueDateButtonText(DueDatePickerFragment dialog) {
        dialog.setTopLeftDueDateButtonText(model.getContentFromButton(ClientModel.ButtonEnum.DD_TOP_LEFT));
        dialog.setTopRightDueDateButtonText(model.getContentFromButton(ClientModel.ButtonEnum.DD_TOP_RIGHT));
        dialog.setBottomLeftDueDateButtonText(model.getContentFromButton(ClientModel.ButtonEnum.DD_BOTTOM_LEFT));
        dialog.setBottomRightDueDateButtonText(model.getContentFromButton(ClientModel.ButtonEnum.DD_BOTTOM_RIGHT));
    }


}
