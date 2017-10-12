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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.student.john.taskmanager2.ClientModel;
import com.student.john.taskmanager2.R;

import static android.view.View.VISIBLE;
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

    private Spinner durationHoursSpinner;
    private Spinner durationMinutesSpinner;
    private Spinner divisibleUnitHoursSpinner;
    private Spinner divisibleUnitMinutesSpinner;

    private CheckBox divisibleUnitCheckBox;

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
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onDurationCancelClicked();
            }
        });
        dialogBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onDurationClearClicked();
            }
        });
        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                presenter.onDurationSaveClicked();
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
                clearButtons();
                clearDurationSpinners();
                topLeftDurationButton.setChecked(true);
            }
        });

        topRightDurationButton = v.findViewById(R.id.topRightDurationButton);
        topRightDurationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDurationButtonOptionClicked(DUR_TOP_RIGHT);
                clearButtons();
                clearDurationSpinners();
                topRightDurationButton.setChecked(true);
            }
        });

        bottomLeftDurationButton = v.findViewById(R.id.bottomLeftDurationButton);
        bottomLeftDurationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDurationButtonOptionClicked(DUR_BOTTOM_LEFT);
                clearButtons();
                clearDurationSpinners();
                bottomLeftDurationButton.setChecked(true);
            }
        });

        bottomRightDurationButton = v.findViewById(R.id.bottomRightDurationButton);
        bottomRightDurationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDurationButtonOptionClicked(DUR_BOTTOM_RIGHT);
                clearButtons();
                clearDurationSpinners();
                bottomRightDurationButton.setChecked(true);
            }
        });

        durationHoursSpinner = v.findViewById(R.id.duration_hours_spinner);
        durationHoursSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0)
                {
                    presenter.onDurationSpinnerItemSelected();
                    clearButtons();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        durationMinutesSpinner = v.findViewById(R.id.duration_minutes_spinner);
        durationMinutesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0)
                {
                    presenter.onDurationSpinnerItemSelected();
                    clearButtons();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        divisibleUnitHoursSpinner = v.findViewById(R.id.divisible_unit_hours_spinner);
        divisibleUnitHoursSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0)
                {
                    presenter.onDivisibleUnitItemSelected();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        divisibleUnitMinutesSpinner = v.findViewById(R.id.divisible_unit_minutes_spinner);
        divisibleUnitMinutesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0)
                {
                    presenter.onDivisibleUnitItemSelected();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        divisibleUnitCheckBox = v.findViewById(R.id.divisible_unit_checkBox);
        divisibleUnitCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                presenter.onDivisibleUnitCheckBoxChanged(b);
                if (b)
                {
                    showDivisibleUnitPickers();
                    setDivisibleUnitSpinners(1,0);
                }
                else
                {
                    hideDivisibleUnitPickers();
                }
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
        clearButtons();
        clearDurationSpinners();
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

    public void setDurationSpinners(int hours, int minutes)
    {
        setDurationHourSpinner(hours);
        setDurationMinuteSpinner(minutes);
    }

    private void setDurationHourSpinner(int hours)
    {
        if (hours <= 20 && hours >= 0)
        {
            durationHoursSpinner.setSelection(hours + 1);
        }
        else
        {
            durationHoursSpinner.setSelection(1);
        }

    }

    private void setDurationMinuteSpinner(int minutes)
    {
        switch (minutes)
        {
            case -1:
                durationMinutesSpinner.setSelection(0);
                break;
            case 0:
                durationMinutesSpinner.setSelection(1);
                break;
            case 15:
                durationMinutesSpinner.setSelection(2);
                break;
            case 30:
                durationMinutesSpinner.setSelection(3);
                break;
            case 45:
                durationMinutesSpinner.setSelection(4);
                break;
            default:
                durationMinutesSpinner.setSelection(1);
                break;
        }
    }

    public void setDivisibleUnitSpinners(int hours, int minutes)
    {
        setDivisibleUnitHourSpinner(hours);
        setDivisibleUnitMinuteSpinner(minutes);
    }

    private void setDivisibleUnitHourSpinner(int hours)
    {
        if (hours <= 10 && hours >= 0)
        {
            divisibleUnitHoursSpinner.setSelection(hours + 1);
        }
        else
        {
            divisibleUnitHoursSpinner.setSelection(1);
        }

    }

    private void setDivisibleUnitMinuteSpinner(int minutes)
    {
        switch (minutes)
        {
            case -1:
                divisibleUnitMinutesSpinner.setSelection(0);
                break;
            case 0:
                divisibleUnitMinutesSpinner.setSelection(1);
                break;
            case 15:
                divisibleUnitMinutesSpinner.setSelection(2);
                break;
            case 30:
                divisibleUnitMinutesSpinner.setSelection(3);
                break;
            case 45:
                divisibleUnitMinutesSpinner.setSelection(4);
                break;
            default:
                divisibleUnitMinutesSpinner.setSelection(1);
                break;
        }
    }

    private void clearButtons()
    {
        topLeftDurationButton.setChecked(false);
        topRightDurationButton.setChecked(false);
        bottomLeftDurationButton.setChecked(false);
        bottomRightDurationButton.setChecked(false);

    }

    private void clearDurationSpinners()
    {
        durationHoursSpinner.setSelection(0);
        durationMinutesSpinner.setSelection(0);
    }

    private void setChecked(ToggleButton button, Boolean checked)
    {
        button.setChecked(checked);
    }

    private void hideDivisibleUnitPickers()
    {
        divisibleUnitHoursSpinner.setVisibility(View.INVISIBLE);
        divisibleUnitMinutesSpinner.setVisibility(View.INVISIBLE);
    }

    private void showDivisibleUnitPickers()
    {
        divisibleUnitHoursSpinner.setVisibility(VISIBLE);
        divisibleUnitMinutesSpinner.setVisibility(View.VISIBLE);
    }

    public void setDivisibleUnitCheckBox(boolean checked)
    {
        divisibleUnitCheckBox.setChecked(checked);
        if (checked)
        {
            showDivisibleUnitPickers();
            setDivisibleUnitSpinners(1,0);
        }
        else
        {
            hideDivisibleUnitPickers();
        }
    }

    public String getDurationString()
    {
        if (topLeftDurationButton.isChecked())
        {
            return topLeftDurationButton.getText().toString();
        }
        else if (topRightDurationButton.isChecked())
        {
            return topRightDurationButton.getText().toString();
        }
        else if (bottomLeftDurationButton.isChecked())
        {
            return bottomLeftDurationButton.getText().toString();
        }
        else if (bottomRightDurationButton.isChecked())
        {
            return bottomRightDurationButton.getText().toString();
        }
        else if (durationHoursSpinner.getSelectedItemPosition() > 1 &&
                durationMinutesSpinner.getSelectedItemPosition() > 1)
        {
            return durationHoursSpinner.getSelectedItem() + "h " + durationMinutesSpinner.getSelectedItem() + "m";
        }
        else if (durationHoursSpinner.getSelectedItemPosition() > 1)
        {
            return durationHoursSpinner.getSelectedItem() + "h";
        }
        else if (durationMinutesSpinner.getSelectedItemPosition() > 1)
        {
            return durationMinutesSpinner.getSelectedItem() + "m";
        }
        else
        {
            return null;
        }
    }

    public String getDivisibleUnitString()
    {
        if (divisibleUnitCheckBox.isChecked())
        {
            if (divisibleUnitHoursSpinner.getSelectedItemPosition() > 1 &&
                    divisibleUnitMinutesSpinner.getSelectedItemPosition() > 1)
            {
                return divisibleUnitHoursSpinner.getSelectedItem() + "h " + divisibleUnitMinutesSpinner.getSelectedItem() + "m";
            }
            else if (divisibleUnitHoursSpinner.getSelectedItemPosition() > 1)
            {
                return divisibleUnitHoursSpinner.getSelectedItem() + "h";
            }
            else if (divisibleUnitMinutesSpinner.getSelectedItemPosition() > 1)
            {
                return divisibleUnitMinutesSpinner.getSelectedItem() + "m";
            }
            else
            {
                return null;
            }

        }
        else
        {
            return null;
        }
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
