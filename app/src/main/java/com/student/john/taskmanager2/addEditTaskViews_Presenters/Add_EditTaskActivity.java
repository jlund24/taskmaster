package com.student.john.taskmanager2.addEditTaskViews_Presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.student.john.taskmanager2.R;
import com.student.john.taskmanager2.models.Task;

import java.util.Map;

public class Add_EditTaskActivity extends AppCompatActivity implements IAdd_EditTaskActivity {


    private Button dueDateButton;
    private Button dueTimeButton;
    private Button durationButton;
    private Button saveButton;
    private EditText titleEditText;
    private IAdd_EditTaskPresenter presenter;

    private static final String EXTRA_TASK_ID_TO_EDIT = "com.student.john.taskmanager2.add_edittaskactivity.task_id_to_edit";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);
        this.presenter = new Add_EditTaskPresenter(this);

        dueDateButton = (Button) findViewById(R.id.dueDateButton);
        dueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                presenter.onDueDateClicked();
            }
        });


        dueTimeButton = (Button) findViewById(R.id.dueTimeButton);
        dueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                presenter.onDueTimeClicked();

            }
        });

        durationButton = (Button) findViewById(R.id.durationButton);
        durationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                presenter.onDurationClicked();
            }
        });

        saveButton = (Button) findViewById(R.id.taskSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSave();
            }
        });

        titleEditText = (EditText) findViewById(R.id.taskTitleEditText);
        titleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        String editingTaskID = getIntent().getStringExtra(EXTRA_TASK_ID_TO_EDIT);
        if (editingTaskID != null)
        {
            presenter.initiateEditMode(editingTaskID);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_task_menu, menu);
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
            closeActivity();
            return true;
        }
        return false;
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public Map<String, Object> getChangedFields() {
        return null;
    }

    @Override
    public void setExistingFields(Task task) {

    }

    public static Intent newIntent(Context packageContext, String taskID)
    {
        Intent i = new Intent(packageContext, Add_EditTaskActivity.class);
        i.putExtra(EXTRA_TASK_ID_TO_EDIT, taskID);
        return i;
    }

    public void setDueDateSelectedWith(String text)
    {
        dueDateButton.setText(text);
    }

    public void setDueTimeSelectedWith(String text)
    {
        dueTimeButton.setText(text);
    }

    public void setDurationSelectedWith(String text)
    {
        durationButton.setText(text);
    }

    public String getTaskTitle()
    {
        return titleEditText.getText().toString();
    }

    public void clearDueDateSelection()
    {
        dueDateButton.setText(R.string.add_due_date);
    }

    public void clearDueTimeSelection()
    {
        dueTimeButton.setText(R.string.add_due_time);
    }

    public void clearDurationSelection(){
        durationButton.setText(R.string.add_duration);
    }

    public void clearPrioritySelection()
    {

    }

    public void makeToast(String text)
    {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void closeActivity()
    {
        onBackPressed();
    }

    public void setTaskTitle(String title)
    {
        titleEditText.setText(title);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Add_EditTaskActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
