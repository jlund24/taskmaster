package com.student.john.taskmanager2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.student.john.taskmanager2.TaskFragment.OnListFragmentInteractionListener;
import com.student.john.taskmanager2.dummy.DummyContent.DummyItem;
import com.student.john.taskmanager2.models.Task;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private List<Task> mValues;
    private final OnListFragmentInteractionListener mListener;

    public TaskRecyclerViewAdapter(List<Task> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void setValues(List<Task> tasks)
    {
        mValues = tasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getTitle());
        String dueDateString = mValues.get(position).getDueDateString();
        String durationString = mValues.get(position).getDurationString();
        if (dueDateString != null && durationString != null)
        {
            holder.mDueDateView.setText(dueDateString);
            holder.mDueDateView.setVisibility(VISIBLE);
            holder.mSeparatorTextView.setVisibility(VISIBLE);
            holder.mDurationTextView.setText(durationString);
            holder.mDurationTextView.setVisibility(VISIBLE);
        }
        else if (dueDateString != null)
        {
            holder.mDueDateView.setText(dueDateString);
            holder.mDueDateView.setVisibility(VISIBLE);
            holder.mSeparatorTextView.setVisibility(GONE);
            holder.mDurationTextView.setVisibility(GONE);
        }
        else if (durationString != null)
        {
            holder.mDurationTextView.setText(durationString);
            holder.mDurationTextView.setVisibility(VISIBLE);
            holder.mSeparatorTextView.setVisibility(GONE);
            holder.mDueDateView.setVisibility(GONE);
        }
        else
        {
            holder.mDueDateView.setText(R.string.no_info);
            holder.mDueDateView.setVisibility(VISIBLE);
            holder.mSeparatorTextView.setVisibility(View.INVISIBLE);
            holder.mDurationTextView.setVisibility(View.INVISIBLE);
        }





        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mDueDateView;
        public final TextView mSeparatorTextView;
        public final TextView mDurationTextView;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView =  view.findViewById(R.id.task_list_item_title);
            mDueDateView =  view.findViewById(R.id.task_list_item_dueDate);
            mSeparatorTextView = view.findViewById(R.id.task_list_item_separator);
            mDurationTextView = view.findViewById(R.id.task_list_item_duration);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDueDateView.getText() + "'";
        }
    }
}
