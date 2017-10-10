package com.student.john.taskmanager2.addEditTaskViews_Presenters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);
        this.presenter = new Add_EditTaskPresenter(this);

        dueDateButton = (Button) findViewById(R.id.dueDateButton);
        dueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueDateClicked();
            }
        });

        dueTimeButton = (Button) findViewById(R.id.dueTimeButton);
        dueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDueTimeClicked();
            }
        });

        durationButton = (Button) findViewById(R.id.durationButton);
        durationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public void closeActivity()
    {
        this.finish();
    }
}
