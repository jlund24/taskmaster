package com.student.john.taskmanager2.addEditTaskFragments;


import com.student.john.taskmanager2.ClientModel;
import com.student.john.taskmanager2.CustomDurationConverter;
import com.student.john.taskmanager2.DateConverter;
import com.student.john.taskmanager2.TimeConverter;
import com.student.john.taskmanager2.models.ICustomTimePeriod;
import com.student.john.taskmanager2.models.Task;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class Add_EditTask1Presenter {

    private Add_Edit_Task_1 activity;
    private AddTitleFragment titleFragment;
    private AddDueDateFragment dueDateFragment;
    private AddDueTimeFragment dueTimeFragment;
    private AddDurationFragment durationFragment;
    private AddSegmentsFragment segmentsFragment;
    private Task editingTask;

    private ClientModel model = ClientModel.getInstance();

    private boolean dueDateSet = false;

    private DateConverter dateConverter = new DateConverter();
    private TimeConverter timeConverter = new TimeConverter();
    private CustomDurationConverter durationConverter = new CustomDurationConverter();

    public Add_EditTask1Presenter(Add_Edit_Task_1 activity) {
        this.activity = activity;
    }

    public void onSaveButtonClicked()
    {
        //check title is set
        if (titleFragment.getTitleInputText().length() > 0)
        {
            saveTask();
            activity.finish();
        }
        else
        {
            activity.makeToast("Tasks must have a title to be saved.");
            activity.moveToPage(Add_Edit_Task_1.TITLE_INPUT);
        }
    }

    public void updateTaskPreview()
    {
        if (titleFragment.getTitleInputText().length() > 0)
        {
            activity.setTaskTitle(titleFragment.getTitleInputText());
        }


    }

    private void saveTask()
    {
        Task taskToSave;
        if (editingTask != null)
        {
            taskToSave = editingTask;
        }
        else
        {
            taskToSave = new Task();
        }

        taskToSave.setTitle(titleFragment.getTitleInputText());

        if (dueDateSet)
        {
            LocalDateTime dueDate = dueDateFragment.getDateFromCalendar();
            TimeConverter converter = new TimeConverter();

            LocalTime dueTime = converter.getTimeFromWord(dueTimeFragment.getDueTimeString());
            taskToSave.setDueDateTime(dueDate.withTime(dueTime.getHourOfDay(), dueTime.getMinuteOfHour(),0,0));
        }

        if (durationFragment.getDurationInputText().length() > 0)
        {
            CustomDurationConverter durationConverter = new CustomDurationConverter();
            ICustomTimePeriod duration = durationConverter.getDurationFromString(durationFragment.getDurationInputText());
            taskToSave.setDuration(duration);

            ICustomTimePeriod segmentDuration = durationConverter.getDurationFromString(segmentsFragment.getSegmentsInputText());
            taskToSave.setDivisibleUnit(segmentDuration);
        }

        taskToSave.generateTaskID();

        ClientModel.getInstance().addTask(taskToSave);
    }

    public void setUpForEdit(String taskID)
    {
        editingTask = model.getTask(taskID);
        activity.setTaskTitle(editingTask.getTitle());

        if (editingTask.getDueDateTime() != null)
        {
            activity.setDueDate(dateConverter.getWordFromDate(editingTask.getDueDateTime()));
        }

        if (editingTask.getDuration() != null)
        {
            activity.setDuration(durationConverter.getWordFromDuration(editingTask.getDuration()));
        }

        activity.setSaveButtonEnabled(true);
        activity.setTaskTitleFocused(true);
        activity.setTaskDurationFocused(false);
        activity.setTaskDueDateFocused(false);


    }

    public void onBackButtonClicked()
    {

    }

    public void onCancelButtonClicked()
    {

    }

    //Title fragment functions

    public AddTitleFragment getTitleFragment()
    {
        this.titleFragment = AddTitleFragment.newInstance();
        titleFragment.setPresenter(this);
        return titleFragment;
    }

    public void onTitleEnterClicked()
    {
        if (titleFragment.getTitleInputText().length() > 0)
        {
            //set title on task
            activity.setTaskTitle(titleFragment.getTitleInputText());
            //move to next fragment
            activity.setTaskTitleFocused(false);
            activity.setTaskDueDateFocused(true);
            activity.moveToPage(Add_Edit_Task_1.DUEDATE_INPUT);
        }

    }

    public void onTitleInputChanged()
    {
        if (titleFragment.getTitleInputText().length() > 0)
        {
            titleFragment.setAccepted(true);
            //activity.setSaveButtonEnabled(false);
        }
        else
        {
            titleFragment.setAccepted(false);
        }
    }

    public void setUpTitleFragment()
    {
        if (editingTask != null)
        {
            titleFragment.setTitleInputText(editingTask.getTitle());
        }

    }

    //DueDate fragment functions
    public AddDueDateFragment getDueDateFragment()
    {
        this.dueDateFragment = AddDueDateFragment.newInstance();
        dueDateFragment.setPresenter(this);

        return dueDateFragment;
    }

    public void onDueDateClicked()
    {
        //set dueDate on Task
        DateConverter converter = new DateConverter();
        String dueDateString = converter.getWordFromDate(dueDateFragment.getDateFromCalendar());
        activity.setDueDate(dueDateString);
        dueDateSet = true;
        //move to dueTime
        activity.moveToPage(Add_Edit_Task_1.DUETIME_INPUT);
    }

    public void onDueDateSkipClicked()
    {
        //move to duration
        activity.moveToPage(Add_Edit_Task_1.DURATION_INPUT);
    }

    public void setUpDueDateFragment()
    {
        if (editingTask != null && editingTask.getDueDateTime() != null)
        {
            dueDateFragment.setDatePickerDate(editingTask.getDueDateTime());
        }
    }

    //dueTime fragment functions
    public AddDueTimeFragment getDueTimeFragment()
    {
        this.dueTimeFragment = AddDueTimeFragment.newInstance();
        dueTimeFragment.setPresenter(this);
        return dueTimeFragment;
    }

    public void onDueTimeOptionClicked()
    {
        //set dueTime on Task

        //move to duration
        activity.moveToPage(Add_Edit_Task_1.DURATION_INPUT);
    }

    public void onDueTimeSkipClicked()
    {
        //set time to midnight

        //move to duration
        activity.moveToPage(Add_Edit_Task_1.DURATION_INPUT);
    }

    public void setUpDueTimeFragment()
    {
        if (editingTask != null && editingTask.getDueDateTime() != null)
        {
            dueTimeFragment.setSelectedButton(model.getButtonFromContent(timeConverter.getWordFromTime(editingTask.getDueDateTime())));
        }

    }

    //duration fragment functions
    public AddDurationFragment getDurationFragment()
    {
        this.durationFragment = AddDurationFragment.newInstance();
        durationFragment.setPresenter(this);
        return durationFragment;
    }

    public void onDurationSuggestionClicked(String suggestion)
    {
        durationFragment.setDurationInputText(suggestion);
        onDurationEnterClicked();
        activity.moveToPage(Add_Edit_Task_1.SEGMENT_INPUT);
    }

    public void onDurationEnterClicked()
    {
        if (durationFragment.getDurationInputText().equals(""))
        {
            onSaveButtonClicked();
        }
        else if (durationConverter.getDurationFromString(durationFragment.getDurationInputText()) != null)
        {
            activity.setDuration(durationFragment.getDurationInputText());
            activity.moveToPage(Add_Edit_Task_1.SEGMENT_INPUT);
        }
        else
        {
            durationFragment.makeToast("You must enter a valid duration to continue, or leave the field blank to skip.");
        }

    }

    public void onDurationInputChanged()
    {
        durationFragment.updateSuggestions(model.getSuggestionsContaining(durationFragment.getDurationInputText()));
        if (durationConverter.getDurationFromString(durationFragment.getDurationInputText()) != null)
        {
            durationFragment.setAccepted(true);
        }
        else
        {
            durationFragment.setAccepted(false);
        }

    }

    public void setUpDurationFragment()
    {
        if (editingTask != null && editingTask.getDuration() != null)
        {
            durationFragment.setDurationInputText(durationConverter.getWordFromDuration(editingTask.getDuration()));
        }
        durationFragment.updateSuggestions(model.getSuggestionsContaining(durationFragment.getDurationInputText()));
    }

    //segments fragment function
    public AddSegmentsFragment getSegmentFragment()
    {
        this.segmentsFragment = AddSegmentsFragment.newInstance();
        segmentsFragment.setPresenter(this);
        return segmentsFragment;
    }

    public void onSegmentSuggestionClicked(String suggestion)
    {
        segmentsFragment.setSegmentInputText(suggestion);
        onSaveButtonClicked();
    }

    public void onSegmentsEnterClicked()
    {
        if (segmentsFragment.getSegmentsInputText().equals(""))
        {
            onSaveButtonClicked();
        }
        else if (durationConverter.getDurationFromString(segmentsFragment.getSegmentsInputText()) != null)
        {
            onSaveButtonClicked();
        }
        else
        {
            segmentsFragment.makeToast("You must enter a valid duration to continue, or leave the field blank to skip.");
        }

    }

    public void onSegmentsInputChanged()
    {
        segmentsFragment.updateSuggestions(model.getSuggestionsContaining(segmentsFragment.getSegmentsInputText()));
        if (durationConverter.getDurationFromString(segmentsFragment.getSegmentsInputText()) != null)
        {
            segmentsFragment.setAccepted(true);
        }
        else
        {
            segmentsFragment.setAccepted(false);
        }
    }

    public void setUpSegmentsFragment()
    {
        if (editingTask != null && editingTask.getDuration() != null &&
                editingTask.getDivisibleUnit() != null &&
                editingTask.getDivisibleUnit().getTotalAsMinutes() > 0)
        {
            segmentsFragment.setSegmentInputText(durationConverter.getWordFromDuration(editingTask.getDivisibleUnit()));
        }

        segmentsFragment.updateSuggestions(model.getSuggestionsContaining(segmentsFragment.getSegmentsInputText()));
    }
}
