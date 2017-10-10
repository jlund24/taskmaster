package com.student.john.taskmanager2.addEditTaskViews_Presenters;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.student.john.taskmanager2.ClientModel;
import com.student.john.taskmanager2.R;

import java.util.Calendar;

import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DT_BOTTOM_LEFT;
import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DT_BOTTOM_RIGHT;
import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DT_TOP_LEFT;
import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DT_TOP_RIGHT;

/**
 * A simple {@link Fragment} subclass.
 */
public class DueTimePickerFragment extends DialogFragment {


    private ToggleButton topLeftDueTimeButton;
    private ToggleButton topRightDueTimeButton;
    private ToggleButton bottomLeftDueTimeButton;
    private ToggleButton bottomRightDueTimeButton;
    private TimePicker timePicker;

    private IAdd_EditTaskPresenter presenter;

    public DueTimePickerFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_due_time_picker, null);
        initializeViews(v);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(v);

        dialogBuilder.setTitle(R.string.due_time);
        Dialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private void initializeViews(View v)
    {
        topLeftDueTimeButton = v.findViewById(R.id.topLeftDueTimeButton);
        topLeftDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueTimeButtonOptionClicked(DT_TOP_LEFT);
                dismiss();
            }
        });

        topRightDueTimeButton = v.findViewById(R.id.topRightDueTimeButton);
        topRightDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueTimeButtonOptionClicked(DT_TOP_RIGHT);
                dismiss();
            }
        });

        bottomLeftDueTimeButton = v.findViewById(R.id.bottomLeftDueTimeButton);
        bottomLeftDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueTimeButtonOptionClicked(DT_BOTTOM_LEFT);
                dismiss();
            }
        });

        bottomRightDueTimeButton = v.findViewById(R.id.bottomRightDueTimeButton);
        bottomRightDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueTimeButtonOptionClicked(DT_BOTTOM_RIGHT);
                dismiss();
            }
        });

        timePicker = v.findViewById(R.id.dueTime_timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                presenter.onPickerDueTimeClicked(hour, minute);
                dismiss();
            }
        });

        presenter.getDueTimePickerConfiguration(this);

    }

    public static DueTimePickerFragment newInstance ()
    {
        DueTimePickerFragment fragment = new DueTimePickerFragment();
        return fragment;
    }

    public void setPresenter(IAdd_EditTaskPresenter presenter) {
        this.presenter = presenter;
    }

    private void setButtonText(ToggleButton button, String text)
    {
        button.setTextOff(text);
        button.setTextOn(text);
    }

    public void setButtonText(ClientModel.ButtonEnum button, String text)
    {
        switch (button)
        {
            case DT_TOP_LEFT:
                setButtonText(topLeftDueTimeButton, text);
                break;
            case DT_TOP_RIGHT:
                setButtonText(topRightDueTimeButton, text);
                break;
            case DT_BOTTOM_LEFT:
                setButtonText(bottomLeftDueTimeButton, text);
                break;
            case DT_BOTTOM_RIGHT:
                setButtonText(bottomRightDueTimeButton, text);
                break;
            default:
                Toast toast = Toast.makeText(getActivity(), "Unknown button specified", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }

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

    private void setChecked(ToggleButton button, Boolean checked)
    {
        button.setChecked(checked);
    }

    public void setTimePickerTime(int hour, int minute)
    {
        timePicker.setHour(hour);
        timePicker.setMinute(minute);
    }
}
