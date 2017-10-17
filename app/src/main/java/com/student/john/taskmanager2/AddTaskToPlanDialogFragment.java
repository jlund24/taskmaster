package com.student.john.taskmanager2;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.student.john.taskmanager2.models.Task;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskToPlanDialogFragment extends DialogFragment {

    private RecyclerView addTaskList;
    private TaskAdapter addTaskListAdapter;
    PlanPresenter presenter;

    public AddTaskToPlanDialogFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_add_task_to_plan_dialog, null);


        addTaskList = v.findViewById(R.id.add_task_recyclerView);
        addTaskList.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        if (addTaskListAdapter == null)
        {
            addTaskListAdapter = new TaskAdapter(new ArrayList<Task>());
            addTaskList.setAdapter(addTaskListAdapter);
            addTaskListAdapter.notifyDataSetChanged();
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(v);

        dialogBuilder.setTitle(R.string.add_tasks);
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        presenter.setUpAddTaskDialog(this);
        Dialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static AddTaskToPlanDialogFragment newInstance()
    {
        AddTaskToPlanDialogFragment fragment = new AddTaskToPlanDialogFragment();
        return fragment;
    }

    public void setPresenter(PlanPresenter presenter) {
        this.presenter = presenter;
    }

    public void updateTasks(List<Task> tasks)
    {
        addTaskListAdapter = new TaskAdapter(tasks);
        addTaskList.setAdapter(addTaskListAdapter);
        addTaskListAdapter.notifyDataSetChanged();

    }

    private class TaskAdapter extends RecyclerView.Adapter<AddTaskToPlanDialogFragment.TaskHolder> {
        private List<Task> tasks;

        public TaskAdapter(List<Task> tasks)
        {
            this.tasks = tasks;
        }

        @Override
        public AddTaskToPlanDialogFragment.TaskHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(AddTaskToPlanDialogFragment.this.getContext());
            View view = layoutInflater.inflate(R.layout.plan_fragment_task_list_item, parent, false);
            return new AddTaskToPlanDialogFragment.TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(AddTaskToPlanDialogFragment.TaskHolder holder, int position)
        {
            Task task = tasks.get(position);
            holder.bindTask(task);
        }
        @Override
        public int getItemCount()
        {
            return tasks.size();
        }

        public void addItem(Task task) {
            tasks.add(task);
            notifyItemInserted(tasks.size());
        }

    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        Task task;
        TextView taskTitle;
        TextView dueDateTextView;
        TextView durationTextView;
        TextView plannedTextView;

        public TaskHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);
            taskTitle = itemView.findViewById(R.id.plan_list_item_title);
            dueDateTextView = itemView.findViewById(R.id.plan_list_item_dueDate);
            durationTextView = itemView.findViewById(R.id.plan_list_item_duration);
            plannedTextView = itemView.findViewById(R.id.planned_duration_label);
            plannedTextView.setVisibility(GONE);

        }

        public void bindTask(Task newTask)
        {
            task = newTask;
            taskTitle.setText(newTask.getTitle());
            String dueDateString = newTask.getDueDateString();
            String durationString = newTask.getDurationString();
            if (dueDateString != null && durationString != null)
            {
                dueDateTextView.setText(dueDateString);
                dueDateTextView.setVisibility(VISIBLE);
                durationTextView.setText(durationString);
                durationTextView.setVisibility(VISIBLE);
            }
            else if (dueDateString != null)
            {
                dueDateTextView.setText(dueDateString);
                dueDateTextView.setVisibility(VISIBLE);
                durationTextView.setVisibility(GONE);
            }
            else if (durationString != null)
            {
                durationTextView.setText(durationString);
                durationTextView.setVisibility(VISIBLE);
                dueDateTextView.setVisibility(GONE);
            }
            else
            {
                dueDateTextView.setText(R.string.no_info);
                dueDateTextView.setVisibility(VISIBLE);
                durationTextView.setVisibility(View.INVISIBLE);
            }

        }



        @Override
        public void onClick(View v) {
            presenter.onTaskToAddClicked(task.getTaskID());
            dismiss();
        }
    }
}
