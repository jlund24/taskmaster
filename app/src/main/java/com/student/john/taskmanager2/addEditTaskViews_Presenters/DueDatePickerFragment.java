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
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.student.john.taskmanager2.ClientModel;
import com.student.john.taskmanager2.DateConverter;
import com.student.john.taskmanager2.R;

import org.joda.time.LocalDateTime;

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
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onDueDateCancelClicked();
            }
        });
        dialogBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onDueDateClearClicked();
            }
        });
        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onDueDateSaveClicked();
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
                clearButtons();
                clearDatePickerDate();
                topLeftDueDateButton.setChecked(true);
            }
        });

        topRightDueDateButton = v.findViewById(R.id.topRightDueDateButton);
        topRightDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onTopRightDueDateOptionClicked();
                clearButtons();
                clearDatePickerDate();
                topRightDueDateButton.setChecked(true);
            }
        });

        bottomLeftDueDateButton = v.findViewById(R.id.bottomLeftDueDateButton);
        bottomLeftDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBottomLeftDueDateOptionClicked();
                clearButtons();
                clearDatePickerDate();
                bottomLeftDueDateButton.setChecked(true);
            }
        });

        bottomRightDueDateButton = v.findViewById(R.id.bottomRightDueDateButton);
        bottomRightDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBottomRightDueDateOptionClicked();
                clearButtons();
                clearDatePickerDate();
                bottomRightDueDateButton.setChecked(true);
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
                clearButtons();
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
        button.setText(text);
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
                clearButtons();
            }
        });
    }

    private void clearDatePickerDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                presenter.onPickerDueDateClicked(datePicker.getYear(), datePicker.getMonth(),
                        datePicker.getDayOfMonth());
                clearButtons();
            }
        });
    }

    private void clearButtons()
    {
        topLeftDueDateButton.setChecked(false);
        topRightDueDateButton.setChecked(false);
        bottomLeftDueDateButton.setChecked(false);
        bottomRightDueDateButton.setChecked(false);

    }

    public LocalDateTime getDateFromDatePicker()
    {
        return new LocalDateTime(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth(),
                23,59);
    }

    public String getDueDateString()
    {
        if (topLeftDueDateButton.isChecked())
        {
            return topLeftDueDateButton.getText().toString();
        }
        else if (topRightDueDateButton.isChecked())
        {
            return topRightDueDateButton.getText().toString();
        }
        else if (bottomLeftDueDateButton.isChecked())
        {
            return bottomLeftDueDateButton.getText().toString();
        }
        else if (bottomRightDueDateButton.isChecked())
        {
            return bottomRightDueDateButton.getText().toString();
        }
        else
        {
            DateConverter converter = new DateConverter();
            return converter.getWordFromDate(new LocalDateTime(datePicker.getYear(),
                    datePicker.getMonth() + 1, datePicker.getDayOfMonth(),23,59));
        }
    }

    public void setPresenter(IAdd_EditTaskPresenter presenter) {
        this.presenter = presenter;
    }
}
