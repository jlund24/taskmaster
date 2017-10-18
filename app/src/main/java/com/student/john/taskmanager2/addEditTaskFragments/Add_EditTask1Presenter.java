package com.student.john.taskmanager2.addEditTaskFragments;


import com.student.john.taskmanager2.CustomDurationConverter;
import com.student.john.taskmanager2.DateConverter;
import com.student.john.taskmanager2.addEditTaskViews_Presenters.DueDatePickerFragment;

import org.joda.time.LocalDateTime;

public class Add_EditTask1Presenter {

    private Add_Edit_Task_1 activity;
    private AddTitleFragment titleFragment;
    private AddDueDateFragment dueDateFragment;
    private AddDueTimeFragment dueTimeFragment;
    private AddDurationFragment durationFragment;
    private AddSegmentsFragment segmentsFragment;

    private CustomDurationConverter converter = new CustomDurationConverter();

    public Add_EditTask1Presenter(Add_Edit_Task_1 activity) {
        this.activity = activity;
    }

    public void onSaveButtonClicked()
    {

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
        }
        else
        {
            titleFragment.setAccepted(false);
        }
    }

    public void setUpTitleFragment()
    {

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
        activity.moveToPage(Add_Edit_Task_1.SEGMENT_INPUT);
    }

    public void onDurationEnterClicked()
    {
        if (durationFragment.getDurationInputText().equals(""))
        {
            activity.moveToPage(Add_Edit_Task_1.TITLE_INPUT);
        }
        else if (converter.getDurationFromString(durationFragment.getDurationInputText()) != null)
        {
            activity.moveToPage(Add_Edit_Task_1.SEGMENT_INPUT);
        }
        else
        {
            durationFragment.makeToast("You must enter a valid duration to continue, or leave the field blank to skip.");
        }

    }

    public void onDurationInputChanged()
    {
        if (converter.getDurationFromString(durationFragment.getDurationInputText()) != null)
        {
            durationFragment.setAccepted(true);
        }
        else
        {
            durationFragment.setAccepted(false);
        }

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
    }

    public void onSegmentsEnterClicked()
    {
        if (segmentsFragment.getSegmentsInputText().equals(""))
        {
            activity.moveToPage(Add_Edit_Task_1.TITLE_INPUT);
        }
        else if (converter.getDurationFromString(segmentsFragment.getSegmentsInputText()) != null)
        {
            //activity.moveToPage(Add_Edit_Task_1.TITLE_INPUT);
            //set task to have segments
        }
        else
        {
            segmentsFragment.makeToast("You must enter a valid duration to continue, or leave the field blank to skip.");
        }

    }

    public void onSegmentsInputChanged()
    {
        if (converter.getDurationFromString(segmentsFragment.getSegmentsInputText()) != null)
        {
            segmentsFragment.setAccepted(true);
        }
        else
        {
            segmentsFragment.setAccepted(false);
        }
    }
}
