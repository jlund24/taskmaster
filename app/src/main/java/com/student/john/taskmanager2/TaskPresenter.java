package com.student.john.taskmanager2;


import android.support.v4.app.FragmentManager;

import com.student.john.taskmanager2.models.Task;

public class TaskPresenter implements ISortDialogPresenter{

    private TaskFragment taskFragment;
    private SortDialogFragment sortFragment;
    private Task swipedTask;
    private static final String COMPLETION_DIALOG = "CompletionDialog";

    private ClientModel model = ClientModel.getInstance();

    public TaskPresenter (TaskFragment fragment)
    {
        taskFragment = fragment;
    }

    public void onTaskSwipedLeft(int position)
    {
        Task swipedTask = model.getVisibleTasks().getTaskList().get(position);
        model.getAllTasks().removeTaskByID(swipedTask.getTaskID());
        swipedTask.setDeleted(true);
        if (model.getCurrentPlan() != null)
        {
            model.getCurrentPlan().removeTask(swipedTask.getTaskID());
        }
        else
        {
            swipedTask.updateInDB();
        }
        taskFragment.updateUI();

    }

    public void onTaskSwipedRight(int position)
    {
        //ask whether it was the whole task or a segment
        swipedTask = model.getVisibleTasks().getTaskList().get(position);
        if (swipedTask.getDivisibleUnit() != null && swipedTask.getDivisibleUnit().getTotalAsMinutes() != 0 &&
                swipedTask.getDivisibleUnit().getTotalAsMinutes() < swipedTask.getDurationLeft().getTotalAsMinutes())
        {
            //show dialog to choose between 1 segment or full task completed
            FragmentManager manager = taskFragment.getActivity().getSupportFragmentManager();
            CompletionDialogFragment fragment = CompletionDialogFragment.newInstance();
            fragment.show(manager,COMPLETION_DIALOG);
            fragment.setTaskPresenter(this);
        }
        else
        {
            markFullTaskCompleted(swipedTask);
        }

    }

    public void onFullTimeButtonClicked()
    {
        markFullTaskCompleted(swipedTask);
    }

    public void onSegmentButtonClicked()
    {
        markTaskSegmentCompleted(swipedTask);
    }

    private void markFullTaskCompleted(Task task)
    {

        if (task.getPlanned() && model.getCurrentPlan() != null)
        {
            model.getCurrentPlan().decrementDurationBy(task.getDurationPlanned());
            model.getCurrentPlan().removeTask(task.getTaskID());
        }
        task.markFullTaskDone();
        swipedTask = null;
        taskFragment.updateUI();
    }

    private void markTaskSegmentCompleted(Task task)
    {
        if (task.getPlanned() && model.getCurrentPlan() != null)
        {
            model.getCurrentPlan().markTaskSegmentCompleted(task.getTaskID());
        }
        else
        {
            task.markOneDivisibleUnitCompleted();
        }
        swipedTask = null;
        taskFragment.updateUI();
    }

    public void onSortButtonClicked()
    {
        FragmentManager manager = taskFragment.getActivity().getSupportFragmentManager();
        sortFragment = SortDialogFragment.newInstance();
        sortFragment.show(manager,COMPLETION_DIALOG);
        sortFragment.setPresenter(this);
    }

    @Override
    public void onSortOptionSelected() {
        model.setSortType(sortFragment.getSortType());
        taskFragment.updateUI();
    }

    @Override
    public void setUpSortDialogFragment() {
        if (model.getSortType() != null)
        {
            sortFragment.setSortType(model.getSortType());
        }
    }

    public void onCompletionCancelled()
    {
        taskFragment.updateUI();
    }

    public void onDeletedMenuOptionClicked()
    {
        taskFragment.makeToast(model.deleteDeletedTasks() + " tasks were permanently deleted.");
    }

    public void onSortOptionsClearClicked()
    {
        model.setSortType(null);
    }
}
