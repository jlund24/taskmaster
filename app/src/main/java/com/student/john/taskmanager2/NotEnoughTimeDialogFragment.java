package com.student.john.taskmanager2;


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
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotEnoughTimeDialogFragment extends DialogFragment {


    private TextView taskTotalDurationTextView;
    private TextView planDurationTextView;
    private Button addTimeButton;
    private Button adjustTasks;

    private PlanPresenter presenter;

    public NotEnoughTimeDialogFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_not_enough_time_dialog, null);

        taskTotalDurationTextView = v.findViewById(R.id.task_total_duration);
        planDurationTextView = v.findViewById(R.id.plan_duration);

        addTimeButton = v.findViewById(R.id.add_time_button);
        addTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddTimeClicked();
                dismiss();
            }
        });

        adjustTasks = v.findViewById(R.id.adjust_tasks_button);
        adjustTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(v);

        dialogBuilder.setTitle(R.string.uh_oh);

        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        Dialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(true);
        presenter.setUpNotEnoughTimeDialog();
        return dialog;
    }

    public void setPresenter(PlanPresenter presenter) {
        this.presenter = presenter;
    }

    public void setTaskTotalDuration(String text)
    {
        taskTotalDurationTextView.setText(text);
    }

    public void setPlanDurationText(String text)
    {
        planDurationTextView.setText(text);
    }

    public static NotEnoughTimeDialogFragment newInstance ()
    {
        NotEnoughTimeDialogFragment fragment = new NotEnoughTimeDialogFragment();
        return fragment;
    }
}
