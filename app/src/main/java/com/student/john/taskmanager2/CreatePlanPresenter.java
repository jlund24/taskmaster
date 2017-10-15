package com.student.john.taskmanager2;


import com.student.john.taskmanager2.models.CustomTimePeriod;

public class CreatePlanPresenter {

    private CreatePlanFragment fragment;

    public CreatePlanPresenter(CreatePlanFragment fragment) {
        this.fragment = fragment;
    }

    public void onSaveButtonClicked()
    {
        String durationString = fragment.getDurationString();
        if (durationString != null)
        {
            //generate plan
            CustomDurationConverter converter = new CustomDurationConverter();
            CustomTimePeriod duration = converter.getDurationFromWord(durationString);
            if (duration != null)
            {
                ClientModel.getInstance().generatePlan(duration);
                //start up plan fragment

            }
            else
            {
                fragment.makeToast("You must provide a valid duration to make a plan.");
            }


        }
        else
        {
            fragment.makeToast("You must provide a valid duration to make a plan.");
        }
    }

}
