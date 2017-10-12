package com.student.john.taskmanager2;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.student.john.taskmanager2.models.Task;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends android.support.v4.app.Fragment {


    private RecyclerView planTaskList;
    private TaskAdapter planTaskListAdapter;

    private PlanPresenter presenter;

    public PlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        planTaskList = v.findViewById(R.id.plan_recyclerView);
        planTaskList.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        if(planTaskListAdapter == null)
        {

            planTaskListAdapter = new TaskAdapter(new ArrayList<Task>());
            planTaskList.setAdapter(planTaskListAdapter);
            planTaskListAdapter.notifyDataSetChanged();
        }
        this.presenter = new PlanPresenter(this);
        presenter.setUpPlanFragment();

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public static PlanFragment newInstance()
    {
        PlanFragment fragment = new PlanFragment();
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_plan_fragment,menu);

    }

    public void updateTasks(List<Task> tasks)
    {
        planTaskListAdapter.setTasks(tasks);
        planTaskListAdapter.notifyDataSetChanged();
    }

    private class TaskAdapter extends RecyclerView.Adapter<PlanFragment.TaskHolder> {
        private List<Task> tasks;

        public TaskAdapter(List<Task> tasks)
        {
            this.tasks = tasks;
        }

        @Override
        public PlanFragment.TaskHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(PlanFragment.this.getContext());
            View view = layoutInflater.inflate(R.layout.plan_fragment_task_list_item, parent, false);
            return new PlanFragment.TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(PlanFragment.TaskHolder holder, int position)
        {
            Task task = tasks.get(position);
            holder.bindTask(task);
        }
        @Override
        public int getItemCount()
        {
            return tasks.size();
        }

        public void setTasks(List<Task> tasks)
        {
            this.tasks = tasks;
        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView taskTitle;
        TextView dueDateTextView;
        TextView durationTextView;
        TextView separatorTextView;

        public TaskHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);
            taskTitle = itemView.findViewById(R.id.plan_list_item_title);
            dueDateTextView = itemView.findViewById(R.id.plan_list_item_dueDate);
            durationTextView = itemView.findViewById(R.id.plan_list_item_duration);
            separatorTextView = itemView.findViewById(R.id.plan_list_item_separator);
        }

        public void bindTask(Task newTask)
        {
            taskTitle.setText(newTask.getTitle());
            String dueDateString = newTask.getDueDateString();
            String durationString = newTask.getDurationString();
            if (dueDateString != null && durationString != null)
            {
                dueDateTextView.setText(dueDateString);
                dueDateTextView.setVisibility(VISIBLE);
                separatorTextView.setVisibility(VISIBLE);
                durationTextView.setText(durationString);
                durationTextView.setVisibility(VISIBLE);
            }
            else if (dueDateString != null)
            {
                dueDateTextView.setText(dueDateString);
                dueDateTextView.setVisibility(VISIBLE);
                separatorTextView.setVisibility(GONE);
                durationTextView.setVisibility(GONE);
            }
            else if (durationString != null)
            {
                durationTextView.setText(durationString);
                durationTextView.setVisibility(VISIBLE);
                separatorTextView.setVisibility(GONE);
                dueDateTextView.setVisibility(GONE);
            }
            else
            {
                dueDateTextView.setText(R.string.no_info);
                dueDateTextView.setVisibility(VISIBLE);
                separatorTextView.setVisibility(View.INVISIBLE);
                durationTextView.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onClick(View v) {
            //call presenter's joinGame()
//            presenter.joinGame(game.getGameName());
        }
    }



}
