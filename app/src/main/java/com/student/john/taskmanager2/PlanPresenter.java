package com.student.john.taskmanager2;


import android.support.v4.app.FragmentManager;


import com.student.john.taskmanager2.models.CustomTimePeriod;

import org.joda.time.Duration;


public class PlanPresenter {

    private PlanFragment planFragment;
    private NotEnoughTimeDialogFragment notEnoughTimeDialogFragment;
    private ClientModel model = ClientModel.getInstance();
    private CustomDurationConverter converter = new CustomDurationConverter();

    public final String NOT_ENOUGH_TIME_DIALOG = "NotEnoughTimeDialog";

    public PlanPresenter(PlanFragment planFragment)
    {
        this.planFragment = planFragment;
    }

    public void setUpPlanFragment()
    {
        if (model.getCurrentPlan() == null)
        {
            //set layout to be create plan mode
            planFragment.setFragmentToCreatePlanLayout();
        }
        else
        {
            //set layout to be plan list mode
            planFragment.setFragmentToPlanListLayout();
            planFragment.updateTasks(model.getCurrentPlan().getTaskList().getTaskList());
            planFragment.setWorkingTime(converter.getWordFromDuration(model.getCurrentPlan().getDuration()));
            if (model.getCurrentPlan().getDuration().getTotalAsMinutes() < model.getCurrentPlan().getTaskList().getTotalDurationOfTasksInMin())
            {
                //we have too many tasks, need to show dialog and mark everything red
                FragmentManager manager = planFragment.getActivity().getSupportFragmentManager();
                notEnoughTimeDialogFragment = NotEnoughTimeDialogFragment.newInstance();
                notEnoughTimeDialogFragment.show(manager,NOT_ENOUGH_TIME_DIALOG);
                notEnoughTimeDialogFragment.setPresenter(this);

                //mark stuff red
                planFragment.setTooManyTasksTheme();
            }
        }

    }

    public void setUpNotEnoughTimeDialog()
    {
        //set text fields
        String planDurationText = converter.getWordFromDuration(model.getCurrentPlan().getDuration());
        notEnoughTimeDialogFragment.setPlanDurationText(planDurationText);
        String taskDurationText = converter.getWordFromDuration(model.getCurrentPlan().getTaskList().getTotalDurationOfTasks());
        notEnoughTimeDialogFragment.setTaskTotalDuration(taskDurationText);
    }

    public void onSaveButtonClicked()
    {
        String durationString = planFragment.getDurationString();
        if (durationString != null)
        {
            //generate plan
            CustomTimePeriod duration = converter.getDurationFromWord(durationString);
            if (duration != null)
            {
                ClientModel.getInstance().generatePlan(duration);
                setUpPlanFragment();

            }
            else
            {
                planFragment.makeToast("You must provide a valid duration to make a plan.");
            }


        }
        else
        {
            planFragment.makeToast("You must provide a valid duration to make a plan.");
        }
    }

    public void onPlanListClearClicked()
    {
        model.getCurrentPlan().clear();
        model.setCurrentPlan(null);
        planFragment.setFragmentToCreatePlanLayout();
    }

    public void onWorkingHoursClicked()
    {
        onPlanListClearClicked();
    }


    public void onAddTimeClicked()
    {
        //change plan's duration to task total duration
        model.getCurrentPlan().setDuration( model.getCurrentPlan().
                getTaskList().getTotalDurationOfTasks() );

        planFragment.setWorkingTime( converter.getWordFromDuration(model.getCurrentPlan().getDuration()) );
        //change color of text back to normal on plan list
        planFragment.clearTooManyTasksTheme();
    }
}


