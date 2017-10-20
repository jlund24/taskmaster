package com.student.john.taskmanager2.addEditTaskFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.student.john.taskmanager2.PlanFragment;
import com.student.john.taskmanager2.R;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.NORMAL;

public class Add_Edit_Task_1 extends AppCompatActivity {

    private Add_Edit_Task_1.SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager mViewPager;
    private Button saveButton;
    private Add_EditTask1Presenter presenter;
    private TextView taskTitle;
    private TextView taskDueDate;
    private ImageView dueDateIcon;
    private TextView taskDueTime;
    private TextView taskDuration;
    private ImageView durationIcon;
    private TextView taskSegments;

    public static final String EXTRA_TASK_ID_TO_EDIT = "TaskID for new addEditTask";
    public static final int TITLE_INPUT = 0;
    public static final int DUEDATE_INPUT = 1;
    public static final int DUETIME_INPUT = 2;
    public static final int DURATION_INPUT = 3;
    public static final int SEGMENT_INPUT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit__task_1);

        sectionsPagerAdapter = new Add_Edit_Task_1.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(sectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                presenter.updateTaskPreview();
                if (taskTitle.getText().toString().length() > 0 && !taskTitle.getText().toString().equals("Title"))
                {
                    setSaveButtonEnabled(true);
                }
                else
                {
                    setSaveButtonEnabled(false);
                }
                switch (position)
                {
                    case TITLE_INPUT:
                        setTaskTitleFocused(true);
                        setTaskDueDateFocused(false);
                        setTaskDurationFocused(false);
                        break;
                    case DUEDATE_INPUT:

                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        setTaskTitleFocused(false);
                        setTaskDueDateFocused(true);
                        setTaskDurationFocused(false);
                        break;
                    case DUETIME_INPUT:

                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        setTaskTitleFocused(false);
                        setTaskDueDateFocused(true);
                        setTaskDurationFocused(false);
                        break;
                    case DURATION_INPUT:
                        setTaskTitleFocused(false);
                        setTaskDueDateFocused(false);
                        setTaskDurationFocused(true);
                        break;
                    case SEGMENT_INPUT:
                        setTaskTitleFocused(false);
                        setTaskDueDateFocused(false);
                        setTaskDurationFocused(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        this.presenter = new Add_EditTask1Presenter(this);

        taskTitle = (TextView) findViewById(R.id.task_title);
        taskDueDate = (TextView) findViewById(R.id.task_dueDate);
        taskDuration = (TextView) findViewById(R.id.task_duration);
        dueDateIcon = (ImageView) findViewById(R.id.duedate_icon);
        durationIcon = (ImageView) findViewById(R.id.duration_icon);

        saveButton = (Button) findViewById(R.id.task_saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSaveButtonClicked();
            }
        });
        String editingTaskID = getIntent().getStringExtra(EXTRA_TASK_ID_TO_EDIT);
        if (editingTaskID != null) {
            presenter.setUpForEdit(editingTaskID);
        }

    }

    public void setSaveButtonEnabled(boolean enabled)
    {
        if (enabled)
        {
            saveButton.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            saveButton.setEnabled(true);
        }
        else
        {
            saveButton.setTextColor(ContextCompat.getColor(this, R.color.transparent_gray));
            saveButton.setEnabled(false);
        }

    }

    public void setTaskTitle(String text)
    {
        taskTitle.setText(text);
    }

    public void setTaskTitleFocused(boolean focused)
    {
        if (focused)
        {
            taskTitle.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            taskTitle.setTypeface(null, BOLD);
        }
        else
        {
            if (!taskTitle.getText().toString().equals("Title"))
            {
                taskTitle.setTextColor(ContextCompat.getColor(this, R.color.black));
            }
            else
            {
                taskTitle.setTextColor(ContextCompat.getColor(this, R.color.transparent_gray));
            }
            taskTitle.setTypeface(null, NORMAL);

        }
    }

    public void setDueDate(String text)
    {
        taskDueDate.setText(text);
    }

    public void setTaskDueDateFocused(boolean focused)
    {
        if (focused)
        {
            taskDueDate.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            dueDateIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_event_orange_24dp));
            taskDueDate.setTypeface(null, BOLD);
        }
        else
        {
            if (!taskDueDate.getText().toString().equals("Due Date"))
            {
                taskDueDate.setTextColor(ContextCompat.getColor(this, R.color.black));
                dueDateIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_event_black_24dp));
            }
            else
            {
                taskDueDate.setTextColor(ContextCompat.getColor(this, R.color.transparent_gray));
                dueDateIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_event_gray_24dp));
            }

            taskDueDate.setTypeface(null, NORMAL);
        }
    }

    public void setDuration(String text)
    {
        taskDuration.setText(text);
    }

    public void setTaskDurationFocused(boolean focused)
    {
        if (focused)
        {
            taskDuration.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            durationIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_hourglass_empty_orange_24dp));
            taskDuration.setTypeface(null, BOLD);
        }
        else
        {
            if (!taskDuration.getText().toString().equals("Duration"))
            {
                taskDuration.setTextColor(ContextCompat.getColor(this, R.color.black));
                durationIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_hourglass_empty_black_24dp));
            }
            else
            {
                taskDuration.setTextColor(ContextCompat.getColor(this, R.color.transparent_gray));
                durationIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_hourglass_empty_gray_24dp));
            }

            taskDuration.setTypeface(null, NORMAL);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_task_menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_close) {
            onBackPressed();
            return true;
        }
        return false;
        //return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch(position)
            {
                case TITLE_INPUT:
                    return presenter.getTitleFragment();
                case DUEDATE_INPUT:
                    return presenter.getDueDateFragment();
                case DUETIME_INPUT:
                    return presenter.getDueTimeFragment();
                case DURATION_INPUT:
                    return presenter.getDurationFragment();
                case SEGMENT_INPUT:
                    return presenter.getSegmentFragment();
                default:
                    return presenter.getTitleFragment();
            }

        }

        @Override
        public int getCount() {

            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Title";
                case 1:
                    return "Plan";
            }
            return null;
        }


    }

    public static Intent newIntent(Context packageContext, String taskID)
    {
        Intent i = new Intent(packageContext, Add_Edit_Task_1.class);
        i.putExtra(EXTRA_TASK_ID_TO_EDIT, taskID);
        return i;
    }

    public void moveToPage(int position)
    {
        mViewPager.setCurrentItem(position, true);
        if (taskTitle.getText().toString().length() > 0)
        {
            setSaveButtonEnabled(true);
        }
        else
        {
            setSaveButtonEnabled(false);
        }
        switch (position)
        {
            case TITLE_INPUT:
                setTaskTitleFocused(true);
                setTaskDueDateFocused(false);
                setTaskDurationFocused(false);
                break;
            case DUEDATE_INPUT:
                setTaskTitleFocused(false);
                setTaskDueDateFocused(true);
                setTaskDurationFocused(false);
                break;
            case DUETIME_INPUT:
                setTaskTitleFocused(false);
                setTaskDueDateFocused(true);
                setTaskDurationFocused(false);
                break;
            case DURATION_INPUT:
                setTaskTitleFocused(false);
                setTaskDueDateFocused(false);
                setTaskDurationFocused(true);
                break;
            case SEGMENT_INPUT:
                setTaskTitleFocused(false);
                setTaskDueDateFocused(false);
                setTaskDurationFocused(true);
                break;
            default:
                break;
        }
    }

    public void makeToast(String text)
    {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

}
