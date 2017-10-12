package com.student.john.taskmanager2;


import com.student.john.taskmanager2.models.Plan;

public class PlanPresenter {

    private PlanFragment planFragment;
    private Plan plan;
    private ClientModel model = ClientModel.getInstance();

    public PlanPresenter(PlanFragment planFragment)
    {
        this.planFragment=  planFragment;
        this.plan = model.getCurrentPlan();
    }

    public void setUpPlanFragment()
    {
        planFragment.updateTasks(plan.getTaskList().getTaskList());
    }
}
