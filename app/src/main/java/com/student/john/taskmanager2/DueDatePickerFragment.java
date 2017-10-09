package com.student.john.taskmanager2;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class DueDatePickerFragment extends DialogFragment {


    private Button topLeftDueDateButton;
    private Button topRightDueDateButton;
    private Button bottomLeftDueDateButton;
    private Button bottomRightDueDateButton;
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
        initializeViews(v);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(v);

        dialogBuilder.setTitle(R.string.due_date);
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
        presenter.getDueDateButtonText(this);
        //commit test
        datePicker = v.findViewById(R.id.dueDate_datePicker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.getYear();
            }
        });
    }

    public static DueDatePickerFragment newInstance ()
    {
        DueDatePickerFragment fragment = new DueDatePickerFragment();
        return fragment;
    }

    public void setTopLeftDueDateButtonText(String text)
    {
        topLeftDueDateButton.setText(text);
    }

    public void setTopRightDueDateButtonText(String text)
    {
        topRightDueDateButton.setText(text);
    }

    public void setBottomLeftDueDateButtonText(String text)
    {
        bottomLeftDueDateButton.setText(text);
    }

    public void setBottomRightDueDateButtonText(String text)
    {
        bottomRightDueDateButton.setText(text);
    }

    public void setPresenter(IAdd_EditTaskPresenter presenter) {
        this.presenter = presenter;
    }
}
