package com.student.john.taskmanager2.addEditTaskFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.student.john.taskmanager2.ClientModel;
import com.student.john.taskmanager2.R;

import org.joda.time.LocalDateTime;

import java.util.Calendar;

import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DT_BOTTOM_LEFT;
import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DT_BOTTOM_RIGHT;
import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DT_TOP_LEFT;
import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DT_TOP_RIGHT;
import static com.student.john.taskmanager2.TimeConverter.TimeStringValues.MIDNIGHT;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDueTimeFragment extends Fragment {



    private ToggleButton topLeftDueTimeButton;
    private ToggleButton topRightDueTimeButton;
    private ToggleButton bottomLeftDueTimeButton;
    private ToggleButton bottomRightDueTimeButton;
    private Button skipButton;

    private Add_EditTask1Presenter presenter;

    public AddDueTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_due_time, container, false);

        topLeftDueTimeButton = v.findViewById(R.id.topLeftDueTimeButton);
        topLeftDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueTimeOptionClicked();
                clearButtons();
                topLeftDueTimeButton.setChecked(true);
            }
        });

        topRightDueTimeButton = v.findViewById(R.id.topRightDueTimeButton);
        topRightDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueTimeOptionClicked();
                clearButtons();
                topRightDueTimeButton.setChecked(true);
            }
        });

        bottomLeftDueTimeButton = v.findViewById(R.id.bottomLeftDueTimeButton);
        bottomLeftDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueTimeOptionClicked();
                clearButtons();
                bottomLeftDueTimeButton.setChecked(true);
            }
        });

        bottomRightDueTimeButton = v.findViewById(R.id.bottomRightDueTimeButton);
        bottomRightDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueTimeOptionClicked();
                clearButtons();
                bottomRightDueTimeButton.setChecked(true);
            }
        });

        presenter.setUpDueTimeFragment();
        skipButton = v.findViewById(R.id.dueTime_skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueTimeSkipClicked();
            }
        });
        return v;
    }

    public void setSelectedButton(ClientModel.ButtonEnum button)
    {
        switch (button)
        {
            case DT_TOP_LEFT:
                setChecked(topLeftDueTimeButton, true);
                break;
            case DT_TOP_RIGHT:
                setChecked(topRightDueTimeButton, true);
                break;
            case DT_BOTTOM_LEFT:
                setChecked(bottomLeftDueTimeButton, true);
                break;
            case DT_BOTTOM_RIGHT:
                setChecked(bottomRightDueTimeButton, true);
                break;
            default:
                Toast toast = Toast.makeText(getActivity(), "Unknown button specified", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }

    public String getDueTimeString()
    {
        if (topLeftDueTimeButton.isChecked())
        {
            return topLeftDueTimeButton.getText().toString();
        }
        else if (topRightDueTimeButton.isChecked())
        {
            return topRightDueTimeButton.getText().toString();
        }
        else if (bottomLeftDueTimeButton.isChecked())
        {
            return bottomLeftDueTimeButton.getText().toString();
        }
        else if (bottomRightDueTimeButton.isChecked())
        {
            return bottomRightDueTimeButton.getText().toString();
        }
        else
        {
            return MIDNIGHT;
        }
    }

    private void clearButtons()
    {
        bottomLeftDueTimeButton.setChecked(false);
        bottomRightDueTimeButton.setChecked(false);
        topRightDueTimeButton.setChecked(false);
        topLeftDueTimeButton.setChecked(false);
    }

    private void setChecked(ToggleButton button, Boolean checked)
    {
        button.setChecked(checked);
    }

    public static AddDueTimeFragment newInstance()
    {
        AddDueTimeFragment fragment = new AddDueTimeFragment();
        return fragment;
    }

    public void makeToast(String text)
    {
        Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void setPresenter(Add_EditTask1Presenter presenter) {
        this.presenter = presenter;
    }

}
