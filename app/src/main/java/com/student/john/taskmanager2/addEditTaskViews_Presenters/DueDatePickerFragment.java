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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.student.john.taskmanager2.ClientModel;
import com.student.john.taskmanager2.R;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DueDatePickerFragment extends DialogFragment {


    private ToggleButton topLeftDueDateButton;
    private ToggleButton topRightDueDateButton;
    private ToggleButton bottomLeftDueDateButton;
    private ToggleButton bottomRightDueDateButton;
    private DatePicker datePicker;

    private IAdd_EditTaskPresenter presenter;

    public DueDatePickerFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_due_date_picker, null);


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(v);
        initializeViews(v);
        dialogBuilder.setTitle(R.string.due_date);
        dialogBuilder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.clearDueDate();
            }
        });
        Dialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private void initializeViews(View v)
    {
        topLeftDueDateButton = v.findViewById(R.id.topLeftDueDateButton);
        topLeftDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onTopLeftDueDateOptionClicked();
                dismiss();
            }
        });

        topRightDueDateButton = v.findViewById(R.id.topRightDueDateButton);
        topRightDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onTopRightDueDateOptionClicked();
                dismiss();
            }
        });

        bottomLeftDueDateButton = v.findViewById(R.id.bottomLeftDueDateButton);
        bottomLeftDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBottomLeftDueDateOptionClicked();
                dismiss();
            }
        });

        bottomRightDueDateButton = v.findViewById(R.id.bottomRightDueDateButton);
        bottomRightDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBottomRightDueDateOptionClicked();
                dismiss();
            }
        });

        datePicker = v.findViewById(R.id.dueDate_datePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.setMinDate(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                presenter.onPickerDueDateClicked(datePicker.getYear(), datePicker.getMonth(),
                        datePicker.getDayOfMonth());
                dismiss();
            }
        });

        presenter.getDueDatePickerConfiguration(this);

    }

    public static DueDatePickerFragment newInstance ()
    {
        DueDatePickerFragment fragment = new DueDatePickerFragment();
        return fragment;
    }

    public void setTopLeftDueDateButtonText(String text)
    {
        topLeftDueDateButton.setTextOff(text);
        topLeftDueDateButton.setTextOn(text);
        topLeftDueDateButton.setText(text);
    }

    public void setTopRightDueDateButtonText(String text)
    {
        topRightDueDateButton.setTextOff(text);
        topRightDueDateButton.setTextOn(text);
        topRightDueDateButton.setText(text);
    }

    public void setBottomLeftDueDateButtonText(String text)
    {
        bottomLeftDueDateButton.setTextOff(text);
        bottomLeftDueDateButton.setTextOn(text);
        bottomLeftDueDateButton.setText(text);
    }

    public void setBottomRightDueDateButtonText(String text)
    {
        bottomRightDueDateButton.setTextOff(text);
        bottomRightDueDateButton.setTextOn(text);
        bottomRightDueDateButton.setText(text);
    }

    private void setButtonText(ToggleButton button, String text)
    {
        button.setTextOn(text);
        button.setTextOff(text);
    }

    private void setChecked(ToggleButton button, Boolean checked)
    {
        button.setChecked(checked);
    }

    public void setSelectedButton(ClientModel.ButtonEnum button)
    {
        switch (button)
        {
            case DD_TOP_LEFT:
                setChecked(topLeftDueDateButton, true);
                break;
            case DD_TOP_RIGHT:
                setChecked(topRightDueDateButton, true);
                break;
            case DD_BOTTOM_LEFT:
                setChecked(bottomLeftDueDateButton, true);
                break;
            case DD_BOTTOM_RIGHT:
                setChecked(bottomRightDueDateButton, true);
                break;
            default:
                Toast toast = Toast.makeText(getActivity(), "Unknown button specified", Toast.LENGTH_SHORT);
                toast.show();
                break;

        }
    }

    public void setDatePickerDate(int year, int month, int day)
    {
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                presenter.onPickerDueDateClicked(datePicker.getYear(), datePicker.getMonth(),
                        datePicker.getDayOfMonth());
                dismiss();
            }
        });
    }

    public void setPresenter(IAdd_EditTaskPresenter presenter) {
        this.presenter = presenter;
    }
}
