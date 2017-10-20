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
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompletionDialogFragment extends DialogFragment {

    private PlanPresenter presenter;
    private TaskPresenter taskPresenter;
    private Button fullTimeButton;
    private Button segmentButton;

    public CompletionDialogFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_completion_dialog, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(v);

        fullTimeButton = v.findViewById(R.id.full_time_button);
        fullTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFullTimeButtonClicked();
                dismiss();
            }
        });

        segmentButton = v.findViewById(R.id.segment_button);
        segmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSegmentButtonClicked();
                dismiss();
            }
        });

        dialogBuilder.setTitle(R.string.completion_type);
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        Dialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    public void setPresenter(PlanPresenter presenter) {
        this.presenter = presenter;
    }

    public void setTaskPresenter(TaskPresenter taskPresenter) {
        this.taskPresenter = taskPresenter;
    }

    private void onFullTimeButtonClicked()
    {
        if (presenter != null)
        {
            presenter.onFullTimeButtonClicked();
        }
        else if (taskPresenter != null)
        {
            taskPresenter.onFullTimeButtonClicked();
        }
    }

    private void onSegmentButtonClicked()
    {
        if (presenter != null)
        {
            presenter.onSegmentButtonClicked();
        }
        else if (taskPresenter != null)
        {
            taskPresenter.onSegmentButtonClicked();
        }
    }

    public static CompletionDialogFragment newInstance()
    {
        CompletionDialogFragment fragment = new CompletionDialogFragment();
        return fragment;
    }
}
