package com.student.john.taskmanager2;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePlanFragment extends Fragment {

    private CreatePlanPresenter presenter;
    private Spinner hoursSpinner;
    private Spinner minutesSpinner;
    private Button saveButton;

    public CreatePlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_plan, container, false);

        hoursSpinner = v.findViewById(R.id.create_plan_hours_spinner);
        minutesSpinner = v.findViewById(R.id.create_plan_minutes_spinner);

        saveButton = v.findViewById(R.id.make_plan_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSaveButtonClicked();
            }
        });

        this.presenter = new CreatePlanPresenter(this);

        return v;
    }

    public String getDurationString()
    {
        if (hoursSpinner.getSelectedItemPosition() > 1 &&
            minutesSpinner.getSelectedItemPosition() > 1)
        {
            return hoursSpinner.getSelectedItem() + "h " + minutesSpinner.getSelectedItem() + "m";
        }
        else if (hoursSpinner.getSelectedItemPosition() > 1)
        {
            return hoursSpinner.getSelectedItem() + "h";
        }
        else if (minutesSpinner.getSelectedItemPosition() > 1)
        {
            return minutesSpinner.getSelectedItem() + "m";
        }
        else
        {
            return null;
        }
    }

    public void makeToast(String text)
    {
        Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static CreatePlanFragment newInstance()
    {
        CreatePlanFragment fragment = new CreatePlanFragment();
        return fragment;
    }



}
