package com.student.john.taskmanager2.addEditTaskFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.student.john.taskmanager2.R;

import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTitleFragment extends Fragment {


    private AutoCompleteTextView titleInput;
    private ImageView inputAccepted;
    private boolean accepted;
    private TextView flowHint;

    private Add_EditTask1Presenter presenter;

    public AddTitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_title, container, false);
        titleInput = v.findViewById(R.id.task_title_input);

        titleInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.onTitleInputChanged();
            }
        });

        titleInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                // Perform action on key press
                if (!titleInput.getText().toString().equals(""))
                {
                    presenter.onTitleEnterClicked();
                }
                else
                {
                    makeToast("You must enter a title to move on.");
                }
                return true;

            }
        });

        inputAccepted = v.findViewById(R.id.input_accepted_icon);
        flowHint = v.findViewById(R.id.title_flow_hint);
        presenter.setUpTitleFragment();
        return v;
    }

    public String getTitleInputText()
    {
        return titleInput.getText().toString();
    }

    public void setAccepted(boolean accepted)
    {
        if (accepted)
        {
            inputAccepted.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_check_circle_green_24dp));
            flowHint.setVisibility(VISIBLE);
        }
        else
        {
            inputAccepted.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_check_circle_gray_24dp));
            flowHint.setVisibility(View.INVISIBLE);
        }
    }

    public static AddTitleFragment newInstance()
    {
        AddTitleFragment fragment = new AddTitleFragment();
        return fragment;
    }

    public void makeToast(String text)
    {
        Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void setPresenter(Add_EditTask1Presenter presenter) {
        this.presenter = presenter;
    }
}
