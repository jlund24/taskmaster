package com.student.john.taskmanager2;


import android.support.v4.app.FragmentManager;
import android.util.Log;


import com.student.john.taskmanager2.models.CustomTimePeriod;
import com.student.john.taskmanager2.models.Task;

import static com.student.john.taskmanager2.models.Plan.PlanState.EMPTY;
import static com.student.john.taskmanager2.models.Plan.PlanState.FULL;
import static com.student.john.taskmanager2.models.Plan.PlanState.OVERFULL;
import static com.student.john.taskmanager2.models.Plan.PlanState.UNDERFULL;


public class PlanPresenter {

    private PlanFragment planFragment;
    private NotEnoughTimeDialogFragment notEnoughTimeDialogFragment;
    private ClientModel model = ClientModel.getInstance();
    private CustomDurationConverter converter = new CustomDurationConverter();
    private Task swipedTask = null;

    public final String NOT_ENOUGH_TIME_DIALOG = "NotEnoughTimeDialog";
    public final String COMPLETION_DIALOG = "CompletionDialog";
    public final String ADD_TASK_DIALOG = "AddTaskDialog";

    public PlanPresenter(PlanFragment planFragment)
    {
        this.planFragment = planFragment;
    }

    public void updatePlanFragment()
    {
        if (model.getCurrentPlan() == null)
        {
            //set layout to be create plan mode
            planFragment.setFragmentToCreatePlanLayout();
        }
        else
        {
            switch (model.getCurrentPlan().getStatus())
            {
                case EMPTY:
                    onPlanEmpty();
                    break;
                case OVERFULL:
                    onPlanOverfull();
                    break;
                case UNDERFULL:
                    onPlanUnderfull();
                    break;
                case FULL:
                    onPlanFull();
                    break;
                default:
                    Log.d("PlanPresenter", "Status from plan was not recognized");
            }

        }

    }

    private void onPlanEmpty()
    {
        planFragment.setFragmentToPlanListLayout();
        planFragment.updateTasks(model.getCurrentPlan().getTaskList().getTaskList());
        String durationString = converter.getWordFromDuration(model.getCurrentPlan().getDuration());
        if (durationString == null || durationString.equals(""))
        {
            durationString = "No time left!";
        }
        planFragment.setWorkingTime(durationString);
        planFragment.clearTooManyTasksTheme();

        //show text view saying the plan's empty, either add more time or some new tasks to the list
        //and try making another plan
        planFragment.setOutOfTimeLayoutVisible(true);

    }

    private void onPlanOverfull()
    {
        planFragment.setFragmentToPlanListLayout();
        planFragment.updateTasks(model.getCurrentPlan().getTaskList().getTaskList());
        planFragment.setWorkingTime(converter.getWordFromDuration(model.getCurrentPlan().getDuration()));

        //we have too many tasks, need to show dialog and mark everything red
        FragmentManager manager = planFragment.getActivity().getSupportFragmentManager();
        notEnoughTimeDialogFragment = NotEnoughTimeDialogFragment.newInstance();
        notEnoughTimeDialogFragment.show(manager,NOT_ENOUGH_TIME_DIALOG);
        notEnoughTimeDialogFragment.setPresenter(this);

        //mark stuff red
        planFragment.setTooManyTasksTheme();
        planFragment.setAddTasksLayoutVisible(false);
    }

    private void onPlanUnderfull()
    {
        planFragment.setFragmentToPlanListLayout();
        planFragment.updateTasks(model.getCurrentPlan().getTaskList().getTaskList());
        planFragment.setWorkingTime(converter.getWordFromDuration(model.getCurrentPlan().getDuration()));
        planFragment.clearTooManyTasksTheme();

        planFragment.setAddTasksLayoutVisible(true);
    }

    private void onPlanFull()
    {
        planFragment.setFragmentToPlanListLayout();
        planFragment.updateTasks(model.getCurrentPlan().getTaskList().getTaskList());
        planFragment.setWorkingTime(converter.getWordFromDuration(model.getCurrentPlan().getDuration()));
        planFragment.setAddTasksLayoutVisible(false);
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
                updatePlanFragment();

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

    public void onNewPlanButtonClicked()
    {
        planFragment.setFragmentToCreatePlanLayout();
        planFragment.setOutOfTimeLayoutVisible(false);
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

        updatePlanFragment();


    }

    public void onCompletionCancelled()
    {
        updatePlanFragment();
    }

    private void removeTaskFromPlan(Task taskToRemove, int position)
    {
        model.getCurrentPlan().removeTask(taskToRemove.getTaskID());
        //remove from recycler view
        //planFragment.removeTaskFromList(position);
    }

    public void onPlanItemSwipedRight(int position)
    {
        Task taskSwiped = model.getCurrentPlan().getTaskList().getTaskList().get(position);
        //planFragment.removeTaskFromList(position);
        //if this is a task with segments and they're not 0 and there's at least 1 full segment left
        if (taskSwiped.getDivisibleUnit() != null && taskSwiped.getDivisibleUnit().getTotalAsMinutes() != 0 &&
                taskSwiped.getDivisibleUnit().getTotalAsMinutes() < taskSwiped.getDurationPlanned().getTotalAsMinutes())
        {
            //show dialog to choose between 1 segment or full task completed
            FragmentManager manager = planFragment.getActivity().getSupportFragmentManager();
            CompletionDialogFragment fragment = CompletionDialogFragment.newInstance();
            fragment.show(manager,COMPLETION_DIALOG);
            fragment.setPresenter(this);
            this.swipedTask = taskSwiped;
        }
        else
        {
            markFullTaskCompleted(taskSwiped);
        }
        updatePlanFragment();
    }

    private void markFullTaskCompleted(Task task)
    {
        model.getCurrentPlan().markTaskCompleted(task.getTaskID());
        swipedTask = null;
        updatePlanFragment();
    }

    private void markTaskSegmentCompleted(Task task)
    {
        model.getCurrentPlan().markTaskSegmentCompleted(task.getTaskID());
        swipedTask = null;
        updatePlanFragment();
    }

    public void onAddTasksButtonClicked()
    {
        FragmentManager manager = planFragment.getActivity().getSupportFragmentManager();
        AddTaskToPlanDialogFragment fragment = AddTaskToPlanDialogFragment.newInstance();
        fragment.show(manager,ADD_TASK_DIALOG);
        fragment.setPresenter(this);
    }

    public void onFullTimeButtonClicked()
    {
        markFullTaskCompleted(swipedTask);
    }

    public void onSegmentButtonClicked()
    {
        markTaskSegmentCompleted(swipedTask);
    }

    public void onTaskToAddClicked(String taskID)
    {
        Task taskToAdd = model.getTask(taskID);
        model.getCurrentPlan().addTaskToPlan(taskToAdd);
        updatePlanFragment();
    }

    public void setUpAddTaskDialog(AddTaskToPlanDialogFragment dialog)
    {
        dialog.updateTasks(model.getCurrentPlan().getPossibleTasksToFillIn().getTaskList());
    }

    public void onAutoCompleteSuggestionClicked(String suggestion)
    {
        if (suggestion != null)
        {
            //generate plan
            CustomTimePeriod duration = converter.getDurationFromString(suggestion);
            if (duration != null)
            {
                ClientModel.getInstance().generatePlan(duration);
                updatePlanFragment();

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

    public void onPlanDurationInputChanged(String input)
    {
        planFragment.updateSuggestions(model.getSuggestionsContaining(input));
        if (input.equals(""))
        {
            planFragment.setTextAccepted(false);
        }
        if (converter.getDurationFromString(input) != null)
        {
            planFragment.setTextAccepted(true);
        }
        else
        {
            planFragment.setTextAccepted(false);
        }
    }



}


