package com.student.john.taskmanager2.addEditTaskViews_Presenters;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;


import android.widget.Toast;
import android.widget.ToggleButton;

import com.student.john.taskmanager2.ClientModel;
import com.student.john.taskmanager2.R;



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
    //private TimePicker timePicker;

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
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onDueTimeCancelClicked();
            }
        });

        dialogBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onDueTimeClearClicked();
            }
        });

        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onDueTimeSaveClicked();
            }
        });
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
                clearButtons();
                topLeftDueTimeButton.setChecked(true);
            }
        });

        topRightDueTimeButton = v.findViewById(R.id.topRightDueTimeButton);
        topRightDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueTimeButtonOptionClicked(DT_TOP_RIGHT);
                clearButtons();
                topRightDueTimeButton.setChecked(true);
            }
        });

        bottomLeftDueTimeButton = v.findViewById(R.id.bottomLeftDueTimeButton);
        bottomLeftDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueTimeButtonOptionClicked(DT_BOTTOM_LEFT);
                clearButtons();
                bottomLeftDueTimeButton.setChecked(true);
            }
        });

        bottomRightDueTimeButton = v.findViewById(R.id.bottomRightDueTimeButton);
        bottomRightDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueTimeButtonOptionClicked(DT_BOTTOM_RIGHT);
                clearButtons();
                bottomRightDueTimeButton.setChecked(true);
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
        button.setText(text);
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

    private void clearButtons()
    {
        bottomLeftDueTimeButton.setChecked(false);
        bottomRightDueTimeButton.setChecked(false);
        topRightDueTimeButton.setChecked(false);
        topLeftDueTimeButton.setChecked(false);
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
            return null;
        }
    }

    private void setChecked(ToggleButton button, Boolean checked)
    {
        button.setChecked(checked);
    }

    public void setTimePickerTime(int hour, int minute)
    {


    }
}
