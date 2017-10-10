package com.student.john.taskmanager2.addEditTaskViews_Presenters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.student.john.taskmanager2.R;
import com.student.john.taskmanager2.models.Task;

import java.util.Map;

public class Add_EditTaskActivity extends AppCompatActivity implements IAdd_EditTaskActivity {


    private Button dueDateButton;
    private Button dueTimeButton;
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_task_menu, menu);
        return true;
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
}
