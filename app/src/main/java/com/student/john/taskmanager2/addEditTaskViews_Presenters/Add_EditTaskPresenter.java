package com.student.john.taskmanager2.addEditTaskViews_Presenters;


import android.support.v4.app.FragmentManager;

import com.student.john.taskmanager2.ClientModel;
import com.student.john.taskmanager2.DateConverter;
import com.student.john.taskmanager2.CustomDurationConverter;
import com.student.john.taskmanager2.TimeConverter;
import com.student.john.taskmanager2.models.CustomTimePeriod;
import com.student.john.taskmanager2.models.Task;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.util.HashMap;
import java.util.Map;

import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.MIDNIGHT;


public class Add_EditTaskPresenter implements IAdd_EditTaskPresenter {

    private Add_EditTaskActivity activity;
    private DueDatePickerFragment dueDateDialog;
    private DueTimePickerFragment dueTimeDialog;
    private DurationPickerFragment durationDialog;

    private Map<String, Object> taskParams = new HashMap<>();
    private Task task = new Task();
    private Task originalTask = null;
    private LocalDateTime dueDateTime = null;
    private String dueDateString = null;
    private String dueTimeString = null;
    private String durationString = null;
    private String divisibleUnitString = null;
    private ClientModel model = ClientModel.getInstance();
    private DateConverter dateConverter = new DateConverter();
    private TimeConverter timeConverter = new TimeConverter();
    private CustomDurationConverter durationConverter = new CustomDurationConverter();


    public final String DUE_DATE_PICKER_DIALOG = "DueDatePickerDialog";
    public final String DUE_TIME_PICKER_DIALOG = "DueTimePickerDialog";
    public final String DURATION_PICKER_DIALOG = "DurationPickerDialog";

    public Add_EditTaskPresenter(Add_EditTaskActivity activity)
    {
        this.activity = activity;
    }

    public void setActivity(Add_EditTaskActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onSave() {
        //make sure there's a title
        if (!activity.getTaskTitle().equals(""))
        {
            task.setTitle(activity.getTaskTitle());
            if (dueDateString != null)
            {
                LocalDateTime dueDateTime = dateConverter.getDateFromWord(dueDateString);
                if (dueDateTime == null)
                {
                    dueDateTime = this.dueDateTime;
                }
                if (dueTimeString != null)
                {
                    LocalTime dueTime = timeConverter.getTimeFromWord(dueTimeString);
                    dueDateTime = dueDateTime.withTime(dueTime.getHourOfDay(), dueTime.getMinuteOfHour(), 0, 0);
                }
                task.setDueDateTime(dueDateTime);
            }

            if (durationString != null)
            {
                CustomTimePeriod duration = durationConverter.getDurationFromWord(durationString);
                task.setDuration(duration);

                if (divisibleUnitString != null)
                {
                    CustomTimePeriod divisibleUnit = durationConverter.getDurationFromWord(divisibleUnitString);
                    if (divisibleUnit.getHours() < duration.getHours())
                    {
                        task.setDivisibleUnit(divisibleUnit);
                    }
                }
            }

            if (task.getDueDateTime() == null || task.getDuration() == null)
            {
                activity.makeToast("This task cannot be planned because it does not have a due date and duration set.");
            }

            task.generateTaskID();
            model.addTask(task);
            activity.closeActivity();
        }
        else
        {
            activity.makeToast("Task does not have a title.");
        }
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
        if (dueDateString == null)
        {
            activity.makeToast("There must be a due date to set a time.");
        }
        else
        {
            FragmentManager manager = activity.getSupportFragmentManager();
            DueTimePickerFragment dialog = DueTimePickerFragment
                    .newInstance();
            dialog.show(manager, DUE_TIME_PICKER_DIALOG);

            dialog.setPresenter(this);
        }

    }

    @Override
    public void onDurationClicked() {
        //start up durationPickerFragment
        FragmentManager manager = activity.getSupportFragmentManager();
        DurationPickerFragment dialog = DurationPickerFragment.newInstance();
        dialog.show(manager, DURATION_PICKER_DIALOG);

        dialog.setPresenter(this);
    }

    @Override
    public void initiateEditMode(String editingTaskID) {
        this.task = model.getTask(editingTaskID);
        dueDateTime = task.getDueDateTime();
        dueDateString = task.getDueDateString();
        durationString = task.getDurationString();
        dueTimeString = task.getDueTimeString();

        activity.setTaskTitle(task.getTitle());
        if (dueDateString != null)
        {
            activity.setDueDateSelectedWith(dueDateString);
        }
        if (dueTimeString != null)
        {
            activity.setDueTimeSelectedWith(dueTimeString);
        }
        if (durationString != null)
        {
            activity.setDurationSelectedWith(durationString);
        }

    }

    @Override
    public void onTopLeftDueDateOptionClicked() {
//        dueDateString = model.getContentFromButton(ClientModel.ButtonEnum.DD_TOP_LEFT);
//        activity.setDueDateSelectedWith(dueDateString);
//        task.setDueDateTime(dateConverter.getDateFromWord(dueDateString));
//        dueTimeString = MIDNIGHT;
//        activity.setDueTimeSelectedWith(dueTimeString);
    }

    @Override
    public void onTopRightDueDateOptionClicked() {
//        dueDateString = model.getContentFromButton(ClientModel.ButtonEnum.DD_TOP_RIGHT);
//        activity.setDueDateSelectedWith(dueDateString);
//        task.setDueDateTime(dateConverter.getDateFromWord(dueDateString));
//        dueTimeString = MIDNIGHT;
//        activity.setDueTimeSelectedWith(dueTimeString);
    }

    @Override
    public void onBottomLeftDueDateOptionClicked() {
//        dueDateString = model.getContentFromButton(ClientModel.ButtonEnum.DD_BOTTOM_LEFT);
//        activity.setDueDateSelectedWith(dueDateString);
//        task.setDueDateTime(dateConverter.getDateFromWord(dueDateString));
//        dueTimeString = MIDNIGHT;
//        activity.setDueTimeSelectedWith(dueTimeString);
    }

    @Override
    public void onBottomRightDueDateOptionClicked() {
//        dueDateString = model.getContentFromButton(ClientModel.ButtonEnum.DD_BOTTOM_RIGHT);
//        activity.setDueDateSelectedWith(dueDateString);
//        task.setDueDateTime(dateConverter.getDateFromWord(dueDateString));
//        dueTimeString = MIDNIGHT;
//        activity.setDueTimeSelectedWith(dueTimeString);
    }

    @Override
    public void getDueDatePickerConfiguration(DueDatePickerFragment dialog)
    {
        this.dueDateDialog = dialog;
        getDueDateButtonText(dialog);
        setDueDateSelectedElement(dialog);
    }

    private void setDueDateSelectedElement(DueDatePickerFragment dialog)
    {
        if (dueDateString != null && model.getButtonFromContent(dueDateString) != null)
        {
            dialog.setSelectedButton(model.getButtonFromContent(dueDateString));
        }
        else if ( dueDateTime != null)
        {
            dialog.setDatePickerDate(dueDateTime.getYear(),
                    dueDateTime.getMonthOfYear() - 1, dueDateTime.getDayOfMonth());
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
//        LocalDateTime dueDate = new LocalDateTime(year, month + 1, day, 23, 59);
//        task.setDueDateTime( dueDate );
//        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM d");
//        dueDateString = null;
//        activity.setDueDateSelectedWith(dueDate.toString(fmt));
//        dueTimeString = MIDNIGHT;
//        activity.setDueTimeSelectedWith(dueTimeString);
    }

    @Override
    public void onDueDateClearClicked() {
        //task.setDueDateTime(null);
        dueDateString = null;
        dueTimeString = null;
        dueDateTime = null;
        activity.clearDueDateSelection();
        activity.clearDueTimeSelection();
    }

    @Override
    public void onDueDateCancelClicked() {

    }

    @Override
    public void onDueDateSaveClicked() {
        getDueDateInfoFromDialog();
        if (dueDateString == null)
        {
            activity.clearDueDateSelection();
            activity.clearDueTimeSelection();
            dueTimeString = null;
            dueDateTime = null;
        }
        else
        {
            activity.setDueDateSelectedWith(dueDateString);
            activity.setDueTimeSelectedWith(dueTimeString);
        }
    }

    private void getDueDateInfoFromDialog()
    {
        dueDateString = dueDateDialog.getDueDateString();
        dueTimeString = MIDNIGHT;
        if (model.getButtonFromContent(dueDateString) == null)
        {
            dueDateTime = dueDateDialog.getDateFromDatePicker();
        }
        else
        {
            dueDateTime = null;
        }
    }

    @Override
    public void onDueTimeClearClicked()
    {
        //task.setDueDateTime(task.getDueDateTime().withTime(23,59,0,0));
        dueTimeString = MIDNIGHT;
        activity.setDueTimeSelectedWith(MIDNIGHT);
    }

    @Override
    public void onDueTimeCancelClicked() {

    }

    @Override
    public void onDueTimeSaveClicked() {
        getDueTimeInfoFromDialog();
        if (dueTimeString == null)
        {
            dueTimeString = MIDNIGHT;
        }
        else
        {
            activity.setDueTimeSelectedWith(dueTimeString);
        }
    }

    private void getDueTimeInfoFromDialog()
    {
        dueTimeString = dueTimeDialog.getDueTimeString();
    }

    @Override
    public void onDurationClearClicked()
    {
        //task.setDuration(null);
        durationString = null;
        divisibleUnitString = null;
        activity.clearDurationSelection();
    }

    @Override
    public void onDurationCancelClicked() {

    }

    @Override
    public void onDurationSaveClicked() {
        getDurationInfoFromDialog();
        if (durationString == null)
        {
            activity.clearDurationSelection();
            divisibleUnitString = null;
        }
        else
        {
            activity.setDurationSelectedWith(durationString);
        }
    }

    private void getDurationInfoFromDialog()
    {
        durationString = durationDialog.getDurationString();
        divisibleUnitString = durationDialog.getDivisibleUnitString();
    }

    @Override
    public void onDurationSpinnerItemSelected() {

    }

    @Override
    public void onDivisibleUnitCheckBoxChanged(boolean checked) {

    }

    @Override
    public void onDivisibleUnitItemSelected() {

    }

    @Override
    public void onDueTimeButtonOptionClicked(ClientModel.ButtonEnum button) {

//        dueTimeString = model.getContentFromButton(button);
//        activity.setDueTimeSelectedWith(dueTimeString);
//        LocalTime dueTime = timeConverter.getTimeFromWord(dueTimeString);
//        if (task.getDueDateTime() != null)
//        {
//            task.setDueDateTime(task.getDueDateTime().withTime( dueTime.getHourOfDay(), dueTime.getMinuteOfHour(), 0, 0));
//        }
    }

    @Override
    public void onPickerDueTimeClicked(int hour, int minute) {

    }

    @Override
    public void getDueTimePickerConfiguration(DueTimePickerFragment dialog) {
        this.dueTimeDialog = dialog;
        setDueTimeButtonText(dialog);
        setDueTimeSelectedElement(dialog);
    }

    @Override
    public void onDurationButtonOptionClicked(ClientModel.ButtonEnum button) {
//        durationString = model.getContentFromButton(button);
//        activity.setDurationSelectedWith(durationString);
//        task.setDuration(durationConverter.getDurationFromWord(durationString));

        //------------

    }

    @Override
    public void getDurationPickerConfiguration(DurationPickerFragment dialog) {
        this.durationDialog = dialog;
        setDurationButtonText(dialog);
        setDurationSelectedElement(dialog);
        setDivisibleUnitSection();
    }

    private void setDurationButtonText(DurationPickerFragment dialog)
    {
        dialog.setButtonText(ClientModel.ButtonEnum.DUR_TOP_LEFT, model.getContentFromButton(ClientModel.ButtonEnum.DUR_TOP_LEFT));
        dialog.setButtonText(ClientModel.ButtonEnum.DUR_TOP_RIGHT, model.getContentFromButton(ClientModel.ButtonEnum.DUR_TOP_RIGHT));
        dialog.setButtonText(ClientModel.ButtonEnum.DUR_BOTTOM_LEFT, model.getContentFromButton(ClientModel.ButtonEnum.DUR_BOTTOM_LEFT));
        dialog.setButtonText(ClientModel.ButtonEnum.DUR_BOTTOM_RIGHT, model.getContentFromButton(ClientModel.ButtonEnum.DUR_BOTTOM_RIGHT));
    }

    private void setDurationSelectedElement(DurationPickerFragment dialog)
    {
        if (durationString != null && model.getButtonFromContent(durationString) != null)
        {
            if (model.getButtonFromContent(durationString) != null)
            {
                dialog.setSelectedButton(model.getButtonFromContent(durationString));
            }
            else
            {
                CustomTimePeriod duration = durationConverter.getDurationFromWord(durationString);
                dialog.setDurationSpinners(duration.getHours(), duration.getMinutes());
            }
        }
    }

    private void setDivisibleUnitSection()
    {
        if (divisibleUnitString != null)
        {
            durationDialog.setDivisibleUnitCheckBox(true);
            CustomTimePeriod divisibleUnit = durationConverter.getDurationFromWord(divisibleUnitString);
            durationDialog.setDivisibleUnitSpinners(divisibleUnit.getHours(), divisibleUnit.getMinutes());
        }
        else
        {
            durationDialog.setDivisibleUnitCheckBox(false);
        }

    }

    private void setDueTimeSelectedElement(DueTimePickerFragment dialog) {
        if (dueTimeString != null && model.getButtonFromContent(dueTimeString) != null)
        {
            dialog.setSelectedButton(model.getButtonFromContent(dueTimeString));
        }

    }

    private void setDueTimeButtonText(DueTimePickerFragment dialog) {
        dialog.setButtonText(ClientModel.ButtonEnum.DT_TOP_LEFT, model.getContentFromButton(ClientModel.ButtonEnum.DT_TOP_LEFT));
        dialog.setButtonText(ClientModel.ButtonEnum.DT_TOP_RIGHT, model.getContentFromButton(ClientModel.ButtonEnum.DT_TOP_RIGHT));
        dialog.setButtonText(ClientModel.ButtonEnum.DT_BOTTOM_LEFT, model.getContentFromButton(ClientModel.ButtonEnum.DT_BOTTOM_LEFT));
        dialog.setButtonText(ClientModel.ButtonEnum.DT_BOTTOM_RIGHT, model.getContentFromButton(ClientModel.ButtonEnum.DT_BOTTOM_RIGHT));
    }


}
