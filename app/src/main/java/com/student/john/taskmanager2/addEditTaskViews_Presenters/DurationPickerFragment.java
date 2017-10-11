package com.student.john.taskmanager2.addEditTaskViews_Presenters;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.student.john.taskmanager2.ClientModel;
import com.student.john.taskmanager2.R;

import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DT_BOTTOM_LEFT;
import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DT_BOTTOM_RIGHT;
import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DT_TOP_LEFT;
import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DT_TOP_RIGHT;
import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DUR_BOTTOM_LEFT;
import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DUR_BOTTOM_RIGHT;
import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DUR_TOP_LEFT;
import static com.student.john.taskmanager2.ClientModel.ButtonEnum.DUR_TOP_RIGHT;

/**
 * A simple {@link Fragment} subclass.
 */
public class DurationPickerFragment extends DialogFragment {


    private ToggleButton topLeftDurationButton;
    private ToggleButton topRightDurationButton;
    private ToggleButton bottomLeftDurationButton;
    private ToggleButton bottomRightDurationButton;

    private IAdd_EditTaskPresenter presenter;

    public DurationPickerFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_duration_picker, null);
        initializeViews(v);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(v);

        dialogBuilder.setTitle(R.string.duration);
        dialogBuilder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.clearDuration();
            }
        });
        Dialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private void initializeViews(View v)
    {
        topLeftDurationButton = v.findViewById(R.id.topLeftDurationButton);
        topLeftDurationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDurationButtonOptionClicked(DUR_TOP_LEFT);
                dismiss();
            }
        });

        topRightDurationButton = v.findViewById(R.id.topRightDurationButton);
        topRightDurationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDurationButtonOptionClicked(DUR_TOP_RIGHT);
                dismiss();
            }
        });

        bottomLeftDurationButton = v.findViewById(R.id.bottomLeftDurationButton);
        bottomLeftDurationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDurationButtonOptionClicked(DUR_BOTTOM_LEFT);
                dismiss();
            }
        });

        bottomRightDurationButton = v.findViewById(R.id.bottomRightDurationButton);
        bottomRightDurationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDurationButtonOptionClicked(DUR_BOTTOM_RIGHT);
                dismiss();
            }
        });


        presenter.getDurationPickerConfiguration(this);

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
            case DUR_TOP_LEFT:
                setButtonText(topLeftDurationButton, text);
                break;
            case DUR_TOP_RIGHT:
                setButtonText(topRightDurationButton, text);
                break;
            case DUR_BOTTOM_LEFT:
                setButtonText(bottomLeftDurationButton, text);
                break;
            case DUR_BOTTOM_RIGHT:
                setButtonText(bottomRightDurationButton, text);
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
            case DUR_TOP_LEFT:
                setChecked(topLeftDurationButton, true);
                break;
            case DUR_TOP_RIGHT:
                setChecked(topRightDurationButton, true);
                break;
            case DUR_BOTTOM_LEFT:
                setChecked(bottomLeftDurationButton, true);
                break;
            case DUR_BOTTOM_RIGHT:
                setChecked(bottomRightDurationButton, true);
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

    public static DurationPickerFragment newInstance ()
    {
        DurationPickerFragment fragment = new DurationPickerFragment();
        return fragment;
    }

    public void setPresenter(IAdd_EditTaskPresenter presenter) {
        this.presenter = presenter;
    }


}
