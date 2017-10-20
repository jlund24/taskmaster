package com.student.john.taskmanager2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.student.john.taskmanager2.TaskFragment.OnListFragmentInteractionListener;

import com.student.john.taskmanager2.models.Task;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * {@link RecyclerView.Adapter} that can display a  and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private List<Task> mValues;
    private final OnListFragmentInteractionListener mListener;
    private CustomDurationConverter durationConverter = new CustomDurationConverter();

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
                .inflate(R.layout.task_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getTitle());
        String dueDateString = mValues.get(position).getDueDateString();
        String durationString;
        if (holder.mItem.getDurationLeft() != null)
        {
            durationString = durationConverter.getWordFromDuration(mValues.get(position).getDurationLeft());
        }
        else
        {
            durationString = null;
        }
        if (dueDateString != null && durationString != null)
        {
            holder.mDueDateView.setText(dueDateString);
            holder.mDueDateView.setVisibility(VISIBLE);
            holder.dueDateIcon.setVisibility(VISIBLE);
            holder.mDurationTextView.setText(durationString);
            holder.mDurationTextView.setVisibility(VISIBLE);
            holder.durationIcon.setVisibility(VISIBLE);
        }
        else if (dueDateString != null)
        {
            holder.mDueDateView.setText(dueDateString);
            holder.mDueDateView.setVisibility(VISIBLE);
            holder.dueDateIcon.setVisibility(VISIBLE);
            holder.mDurationTextView.setVisibility(GONE);
            holder.durationIcon.setVisibility(GONE);
            holder.durationLabel.setVisibility(GONE);
        }
        else if (durationString != null)
        {
            holder.mDurationTextView.setText(durationString);
            holder.mDurationTextView.setVisibility(VISIBLE);
            holder.durationIcon.setVisibility(VISIBLE);
            holder.mDueDateView.setVisibility(GONE);
            holder.dueDateIcon.setVisibility(GONE);
        }
        else
        {
            holder.mDueDateView.setText(R.string.no_info);
            holder.mDueDateView.setVisibility(VISIBLE);
            holder.durationIcon.setVisibility(GONE);
            holder.mDurationTextView.setVisibility(GONE);
            holder.durationLabel.setVisibility(GONE);
        }

        if (durationString != null && holder.mItem.getDivisibleUnit() != null &&
                holder.mItem.getDivisibleUnit().getTotalAsMinutes() > 0)
        {
            holder.progressBar.setVisibility(VISIBLE);
            holder.progressBar.setProgress(holder.mItem.getProgress());
        }
        else
        {
            holder.progressBar.setVisibility(View.INVISIBLE);
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
        public final ImageView dueDateIcon;
        public final ImageView durationIcon;
        public final TextView mDurationTextView;
        public final TextView durationLabel;
        public final ProgressBar progressBar;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView =  view.findViewById(R.id.task_item_title);
            dueDateIcon = view.findViewById(R.id.duedate_icon);
            mDueDateView =  view.findViewById(R.id.task_item_dueDate);
            durationIcon = view.findViewById(R.id.duration_icon);
            mDurationTextView = view.findViewById(R.id.task_item_durationLeft);
            progressBar = view.findViewById(R.id.task_progressBar);
            durationLabel = view.findViewById(R.id.task_duration_label);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDueDateView.getText() + "'";
        }
    }
}
