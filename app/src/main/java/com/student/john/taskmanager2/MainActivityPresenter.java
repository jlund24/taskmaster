package com.student.john.taskmanager2;


public class MainActivityPresenter {

    private Main2Activity activity;
    private TaskFragment taskFragment;
    private PlanFragment planFragment;

    public MainActivityPresenter (Main2Activity activity)
    {
        this.activity = activity;
    }

    public TaskFragment getTaskFragment()
    {
        this.taskFragment = TaskFragment.newInstance(1);
        return taskFragment;
    }

    public PlanFragment getPlanFragment()
    {
        this.planFragment = PlanFragment.newInstance();
        return planFragment;
    }

    public void updateTaskFragment()
    {
        taskFragment.updateUI();
    }

    public void updatePlanFragment()
    {
        planFragment.getPresenter().updatePlanFragment();
    }


}
