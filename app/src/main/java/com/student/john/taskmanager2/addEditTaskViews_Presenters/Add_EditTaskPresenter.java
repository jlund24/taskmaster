package com.student.john.taskmanager2.addEditTaskViews_Presenters;


import android.support.v4.app.FragmentManager;

import com.student.john.taskmanager2.ClientModel;
import com.student.john.taskmanager2.DateConverter;
import com.student.john.taskmanager2.TimeConverter;
import com.student.john.taskmanager2.models.Task;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

import static com.student.john.taskmanager2.models.Task.TaskParamTitle.DUE_DATE_TIME;


public class Add_EditTaskPresenter implements IAdd_EditTaskPresenter {

    private Add_EditTaskActivity activity;
    private Map<String, Object> taskParams = new HashMap<>();
    private Task task = new Task();
    private String dueDateString = null;
    private String dueTimeString = null;
    private ClientModel model = ClientModel.getInstance();
    private DateConverter dateConverter = new DateConverter();
    private TimeConverter timeConverter = new TimeConverter();


    public final String DUE_DATE_PICKER_DIALOG = "DueDatePickerDialog";
    public final String DUE_TIME_PICKER_DIALOG = "DueTimePickerDialog";

    public Add_EditTaskPresenter(Add_EditTaskActivity activity)
    {
        this.activity = activity;
    }

    public void setActivity(Add_EditTaskActivity activity) {
        this.activity = activity;
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
    public void onDueTimeClicked() {
        //start up dueTimePickerFragment
        FragmentManager manager = activity.getSupportFragmentManager();
        DueTimePickerFragment dialog = DueTimePickerFragment
                .newInstance();
        dialog.show(manager, DUE_DATE_PICKER_DIALOG);

        dialog.setPresenter(this);
    }

    @Override
    public void onTopLeftDueDateOptionClicked() {
        dueDateString = model.getContentFromButton(ClientModel.ButtonEnum.DD_TOP_LEFT);
        activity.setDueDateSelectedWith(dueDateString);
        task.setDueDateTime(dateConverter.getDateFromWord(dueDateString));
    }

    @Override
    public void onTopRightDueDateOptionClicked() {
        dueDateString = model.getContentFromButton(ClientModel.ButtonEnum.DD_TOP_RIGHT);
        activity.setDueDateSelectedWith(dueDateString);
        task.setDueDateTime(dateConverter.getDateFromWord(dueDateString));
    }

    @Override
    public void onBottomLeftDueDateOptionClicked() {
        dueDateString = model.getContentFromButton(ClientModel.ButtonEnum.DD_BOTTOM_LEFT);
        activity.setDueDateSelectedWith(dueDateString);
        task.setDueDateTime(dateConverter.getDateFromWord(dueDateString));
    }

    @Override
    public void onBottomRightDueDateOptionClicked() {
        dueDateString = model.getContentFromButton(ClientModel.ButtonEnum.DD_BOTTOM_RIGHT);
        activity.setDueDateSelectedWith(dueDateString);
        task.setDueDateTime(dateConverter.getDateFromWord(dueDateString));
    }

    @Override
    public void getDueDatePickerConfiguration(DueDatePickerFragment dialog)
    {
        getDueDateButtonText(dialog);
        setDueDateSelectedElement(dialog);
    }

    private void setDueDateSelectedElement(DueDatePickerFragment dialog)
    {
        if (dueDateString != null && model.getButtonFromContent(dueDateString) != null)
        {
            dialog.setSelectedButton(model.getButtonFromContent(dueDateString));
        }
        else if ( task.getDueDateTime() != null)
        {
            dialog.setDatePickerDate(task.getDueDateTime().getYear(),
                    task.getDueDateTime().getMonthOfYear(), task.getDueDateTime().getDayOfMonth());
        }

    }


    private void getDueDateButtonText(DueDatePickerFragment dialog) {
        dialog.setTopLeftDueDateButtonText(model.getContentFromButton(ClientModel.ButtonEnum.DD_TOP_LEFT));
        dialog.setTopRightDueDateButtonText(model.getContentFromButton(ClientModel.ButtonEnum.DD_TOP_RIGHT));
        dialog.setBottomLeftDueDateButtonText(model.getContentFromButton(ClientModel.ButtonEnum.DD_BOTTOM_LEFT));
        dialog.setBottomRightDueDateButtonText(model.getContentFromButton(ClientModel.ButtonEnum.DD_BOTTOM_RIGHT));
    }

    @Override
    public void onPickerDueDateClicked(int year, int month, int day) {
        LocalDateTime dueDate = new LocalDateTime(year, month, day, 11, 59);
        task.setDueDateTime( dueDate );
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM d");
        dueDateString = null;
        activity.setDueDateSelectedWith(dueDate.toString(fmt));

    }

    @Override
    public void onDueTimeButtonOptionClicked(ClientModel.ButtonEnum button) {

        dueTimeString = model.getContentFromButton(button);
        activity.setDueTimeSelectedWith(dueTimeString);
        LocalTime dueTime = timeConverter.getTimeFromWord(dueTimeString);
        if (task.getDueDateTime() != null)
        {
            task.setDueDateTime(task.getDueDateTime().withTime( dueTime.getHourOfDay(), dueTime.getMinuteOfHour(), 0, 0));
        }
    }

    @Override
    public void onPickerDueTimeClicked(int hour, int minute) {

    }

    @Override
    public void getDueTimePickerConfiguration(DueTimePickerFragment dialog) {
        setDueTimeButtonText(dialog);
        setDueTimeSelectedElement(dialog);
    }

    private void setDueTimeSelectedElement(DueTimePickerFragment dialog) {
        if (dueTimeString != null && model.getButtonFromContent(dueTimeString) != null)
        {
            dialog.setSelectedButton(model.getButtonFromContent(dueTimeString));
        }
        else if ( task.getDueDateTime() != null)
        {
            dialog.setTimePickerTime(task.getDueDateTime().getHourOfDay(),
                    task.getDueDateTime().getMinuteOfHour());
        }

    }

    private void setDueTimeButtonText(DueTimePickerFragment dialog) {
        dialog.setButtonText(ClientModel.ButtonEnum.DT_TOP_LEFT, model.getContentFromButton(ClientModel.ButtonEnum.DT_TOP_LEFT));
        dialog.setButtonText(ClientModel.ButtonEnum.DT_TOP_RIGHT, model.getContentFromButton(ClientModel.ButtonEnum.DT_TOP_RIGHT));
        dialog.setButtonText(ClientModel.ButtonEnum.DT_BOTTOM_LEFT, model.getContentFromButton(ClientModel.ButtonEnum.DT_BOTTOM_LEFT));
        dialog.setButtonText(ClientModel.ButtonEnum.DT_BOTTOM_RIGHT, model.getContentFromButton(ClientModel.ButtonEnum.DT_BOTTOM_RIGHT));
    }


}
