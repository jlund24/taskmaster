package com.student.john.taskmanager2;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.student.john.taskmanager2.models.Task;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SortDialogFragment extends DialogFragment {


    private RadioGroup sortRadioGroup;
    private RadioButton sortByDueDate;
    private RadioButton sortByDuration;
    private RadioButton sortByDueDateAndDuration;

    private ISortDialogPresenter presenter;

    public SortDialogFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_sort_dialog, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(v);

        sortRadioGroup = v.findViewById(R.id.sort_radio_group);
        sortByDueDate = v.findViewById(R.id.sort_by_due_date);
        sortByDuration = v.findViewById(R.id.sort_by_duration);
        sortByDueDateAndDuration = v.findViewById(R.id.sort_by_both);
        dialogBuilder.setTitle(R.string.sort_by);
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onSortOptionSelected();
                dismiss();
            }
        });

        presenter.setUpSortDialogFragment();
        Dialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public ClientModel.SortEnum getSortType()
    {
        switch(sortRadioGroup.getCheckedRadioButtonId())
        {
            case R.id.sort_by_due_date:
                return ClientModel.SortEnum.DUE_DATE;
            case R.id.sort_by_duration:
                return ClientModel.SortEnum.DURATION_LEFT;
            case R.id.sort_by_both:
                return ClientModel.SortEnum.DUE_DATE_AND_DURATION;
            default:
                return null;
        }
    }

    public void setSortType(ClientModel.SortEnum sortType)
    {
        switch(sortType)
        {
            case DUE_DATE:
                sortByDueDate.setChecked(true);
                break;
            case DURATION_LEFT:
                sortByDuration.setChecked(true);
                break;
            case DUE_DATE_AND_DURATION:
                sortByDueDateAndDuration.setChecked(true);
                break;
            default:
                break;
        }
    }

    public static SortDialogFragment newInstance()
    {
        SortDialogFragment fragment = new SortDialogFragment();
        return fragment;
    }

    public void setPresenter(ISortDialogPresenter presenter) {
        this.presenter = presenter;
    }
}
