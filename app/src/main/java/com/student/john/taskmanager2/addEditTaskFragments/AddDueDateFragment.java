package com.student.john.taskmanager2.addEditTaskFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.student.john.taskmanager2.R;

import org.joda.time.LocalDateTime;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDueDateFragment extends Fragment {


    private DatePicker dueDatePicker;
    private Button skipButton;

    private Add_EditTask1Presenter presenter;

    public AddDueDateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_due_date, container, false);

        dueDatePicker = v.findViewById(R.id.dueDate_picker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        dueDatePicker.setMinDate(System.currentTimeMillis());
        dueDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                presenter.onDueDateClicked();
            }
        });

        skipButton = v.findViewById(R.id.dueDate_skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueDateSkipClicked();
            }
        });
        return v;
    }

    public LocalDateTime getDateFromCalendar()
    {
        return new LocalDateTime(dueDatePicker.getYear(), dueDatePicker.getMonth() + 1, dueDatePicker.getDayOfMonth(), 23,59);
    }

    public static AddDueDateFragment newInstance()
    {
        AddDueDateFragment fragment = new AddDueDateFragment();
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
