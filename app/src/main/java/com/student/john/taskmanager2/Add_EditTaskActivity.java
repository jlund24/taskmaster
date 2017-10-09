package com.student.john.taskmanager2;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.student.john.taskmanager2.models.Task;

import java.util.Map;

public class Add_EditTaskActivity extends AppCompatActivity implements IAdd_EditTaskActivity{


    private Button dueDateButton;
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
}
