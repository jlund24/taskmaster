package com.student.john.taskmanager2;


import android.support.v4.app.FragmentManager;


import com.student.john.taskmanager2.models.CustomTimePeriod;
import com.student.john.taskmanager2.models.Task;

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

    public void onPlanItemSwipedLeft(int position)
    {
        Task taskSwiped = model.getCurrentPlan().getTaskList().getTaskList().get(position);
        this.removeTaskFromPlan(taskSwiped, position);

        //check Plan status
        //model.getCurrentPlan().getStatus();
        //reload Plan page (to make sure tasks are removed, time is update, red text is cleared, etc.
    }

    private void removeTaskFromPlan(Task taskToRemove, int position)
    {
        model.getCurrentPlan().removeTask(taskToRemove.getTaskID());
        //remove from recycler view
        planFragment.removeTaskFromList(position);
    }

    public void onPlanItemSwipedRight(int position)
    {
        Task taskSwiped = model.getCurrentPlan().getTaskList().getTaskList().get(position);
        planFragment.removeTaskFromList(position);
        //if this is a task with segments and they're not 0 and there's at least 1 full segment left
        if (taskSwiped.getDivisibleUnit() != null && taskSwiped.getDivisibleUnit().getTotalAsMinutes() != 0 &&
                taskSwiped.getDivisibleUnit().getTotalAsMinutes() > taskSwiped.getDurationPlanned().getTotalAsMinutes())
        {
            //show dialog to choose between 1 segment or full task completed
        }
        else
        {
            markFullTaskCompleted(taskSwiped);
        }

    }

    private void markFullTaskCompleted(Task task)
    {
        model.getCurrentPlan().markTaskCompleted(task.getTaskID());
    }

    private void markTaskSegmentCompleted()
    {

    }
}


