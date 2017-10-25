package com.student.john.taskmanager2.addEditTaskFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.student.john.taskmanager2.PlanFragment;
import com.student.john.taskmanager2.R;
import com.student.john.taskmanager2.models.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSegmentsFragment extends Fragment {

    private SuggestionAdapter adapter;
    private RecyclerView segmentSuggestionsList;
    private TextView flowHint;
    private AutoCompleteTextView segmentInputTextView;
    private ImageView acceptedIcon;
    private Button segmentInfoButton;

    private Add_EditTask1Presenter presenter;

    public AddSegmentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_segments, container, false);

        segmentSuggestionsList = v.findViewById(R.id.auto_complete_recyclerView);
        segmentSuggestionsList.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        if (adapter == null) {

            adapter = new AddSegmentsFragment.SuggestionAdapter(new ArrayList<String>());
            segmentSuggestionsList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        segmentInputTextView = v.findViewById(R.id.segment_auto_complete);
        segmentInputTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.onSegmentsInputChanged();
            }
        });

        segmentInputTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                presenter.onSegmentsEnterClicked();
                return true;

            }
        });

        acceptedIcon = v.findViewById(R.id.input_accepted_icon);
        acceptedIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSegmentsEnterClicked();
            }
        });
        segmentInfoButton = v.findViewById(R.id.segment_help_button);
        segmentInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), R.string.segment_description, Toast.LENGTH_LONG)
                .show();

            }
        });
        presenter.setUpSegmentsFragment();
        return v;
    }

    public void updateSuggestions(List<String> suggestions)
    {
        adapter = new AddSegmentsFragment.SuggestionAdapter(suggestions);
        segmentSuggestionsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private class SuggestionAdapter extends RecyclerView.Adapter<AddSegmentsFragment.SuggestionHolder> {
        private List<String> suggestions;

        public SuggestionAdapter(List<String> suggestions)
        {
            this.suggestions = suggestions;
        }

        @Override
        public AddSegmentsFragment.SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(AddSegmentsFragment.this.getContext());
            View view = layoutInflater.inflate(R.layout.autocomplete_list_item, parent, false);
            return new AddSegmentsFragment.SuggestionHolder(view);
        }

        @Override
        public void onBindViewHolder(AddSegmentsFragment.SuggestionHolder holder, int position)
        {
            String suggestion = suggestions.get(position);
            holder.bindSuggestion(suggestion);
        }
        @Override
        public int getItemCount()
        {
            return suggestions.size();
        }



    }

    private class SuggestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        String suggestion;
        TextView suggestionTextView;

        public SuggestionHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);
            suggestionTextView = itemView.findViewById(R.id.suggestion_textView);

        }

        public void bindSuggestion(String newSuggestion)
        {
            suggestionTextView.setText(newSuggestion);
            suggestion = newSuggestion;
        }



        @Override
        public void onClick(View v) {
            presenter.onSegmentSuggestionClicked(suggestion);
        }
    }

    public static AddSegmentsFragment newInstance()
    {
        AddSegmentsFragment fragment = new AddSegmentsFragment();
        return fragment;
    }

    public void setPresenter(Add_EditTask1Presenter presenter) {
        this.presenter = presenter;
    }

    public void setSegmentInputText(String text)
    {
        segmentInputTextView.setText(text);
        presenter.onSegmentsInputChanged();
    }

    public String getSegmentsInputText()
    {
        return segmentInputTextView.getText().toString();
    }

    public void makeToast(String text)
    {
        Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void setAccepted(boolean accepted)
    {
        if (accepted)
        {
            acceptedIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_check_circle_green_24dp));
            acceptedIcon.setEnabled(true);
        }
        else
        {
            acceptedIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_check_circle_gray_24dp));
            acceptedIcon.setEnabled(false);
        }
    }
}
