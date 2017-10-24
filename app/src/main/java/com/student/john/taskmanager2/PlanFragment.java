package com.student.john.taskmanager2;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.student.john.taskmanager2.addEditTaskViews_Presenters.Add_EditTaskActivity;
import com.student.john.taskmanager2.models.Task;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends android.support.v4.app.Fragment {


    private RecyclerView planTaskList;
    private TaskAdapter planTaskListAdapter;
    private Spinner hoursSpinner;
    private Spinner minutesSpinner;
    private Button saveButton;
    private TextView workingHoursTextView;
    private LinearLayout createPlanLayout;
    private LinearLayout planListLayout;
    private LinearLayout workingHoursLayout;
    private LinearLayout addTasksLayout;
    private LinearLayout createPlan1Layout;
    private LinearLayout outOfTimeLayout;
    private TextView workingHoursTitle;
    private ImageButton editWorkingHoursButton;
    private Button addTasksButton;
    private AutoCompleteTextView planDurationInput;
    private RecyclerView autoCompleteRecyclerView;
    private SuggestionAdapter autoCompleteAdapter;
    private ImageButton inputAcceptedIcon;
    private Button newPlanButton;
    private boolean accepted = false;

    private Paint p = new Paint();

    private PlanPresenter presenter;

    public PlanFragment() {
        // Required empty public constructor
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        if(inputMethodManager.isAcceptingText()) { // verify if the soft keyboard is open
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        planTaskList = v.findViewById(R.id.plan_recyclerView);
        planTaskList.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        initSwipe();
        if (planTaskListAdapter == null) {

            planTaskListAdapter = new TaskAdapter(new ArrayList<Task>());
            planTaskList.setAdapter(planTaskListAdapter);
            planTaskListAdapter.notifyDataSetChanged();
        }

        hoursSpinner = v.findViewById(R.id.create_plan_hours_spinner);
        minutesSpinner = v.findViewById(R.id.create_plan_minutes_spinner);

        createPlanLayout = v.findViewById(R.id.create_plan_1_layout);
        planListLayout = v.findViewById(R.id.plan_list_layout);
        addTasksLayout = v.findViewById(R.id.add_more_tasks_layout);
        outOfTimeLayout = v.findViewById(R.id.out_of_time_layout);
        planDurationInput = v.findViewById(R.id.multiAutoCompleteTextView2);
        autoCompleteRecyclerView = v.findViewById(R.id.auto_complete_recyclerView);
        autoCompleteRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        if (autoCompleteAdapter == null)
        {
            autoCompleteAdapter = new SuggestionAdapter(ClientModel.getInstance().getSuggestionsContaining(""));
            autoCompleteRecyclerView.setAdapter(autoCompleteAdapter);
            autoCompleteAdapter.notifyDataSetChanged();
        }

        planDurationInput = v.findViewById(R.id.multiAutoCompleteTextView2);
        planDurationInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.onPlanDurationInputChanged(editable.toString());
            }
        });

        planDurationInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                // Perform action on key press
                if (accepted)
                {
                    presenter.onAutoCompleteSuggestionClicked(planDurationInput.getText().toString());
                    hideKeyboard(v);
                }
                else
                {
                    makeToast("Please enter a valid duration string. (e.g. 1h 30m, 30m, or 5h)");
                }
                return true;

            }
        });

        inputAcceptedIcon = v.findViewById(R.id.input_accepted_icon);
        inputAcceptedIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAutoCompleteSuggestionClicked(planDurationInput.getText().toString());
                hideKeyboard(view);
            }
        });

        saveButton = v.findViewById(R.id.make_plan_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSaveButtonClicked();
            }
        });

        addTasksButton = v.findViewById(R.id.add_tasks_button);
        addTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddTasksButtonClicked();
            }
        });

        newPlanButton = v.findViewById(R.id.new_plan_button);
        newPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onNewPlanButtonClicked();
            }
        });

        workingHoursTextView = v.findViewById(R.id.plan_duration);
        workingHoursTitle = v.findViewById(R.id.working_textView);
        editWorkingHoursButton = v.findViewById(R.id.plan_duration_edit_button);

        workingHoursLayout = v.findViewById(R.id.working_time_layout);
        workingHoursLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onWorkingHoursClicked();
            }
        });

        this.presenter = new PlanPresenter(this);
        presenter.updatePlanFragment();

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public static PlanFragment newInstance()
    {
        PlanFragment fragment = new PlanFragment();
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_plan_fragment,menu);

        if (createPlanLayout.getVisibility() == VISIBLE)
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            presenter.onPlanListClearClicked();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateTasks(List<Task> tasks)
    {
        planTaskListAdapter = new TaskAdapter(tasks);
        planTaskList.setAdapter(planTaskListAdapter);
        planTaskListAdapter.notifyDataSetChanged();

    }

    public void setFragmentToCreatePlanLayout()
    {
        getActivity().invalidateOptionsMenu();
        planListLayout.setVisibility(GONE);
        createPlanLayout.setVisibility(VISIBLE);
    }

    public void setFragmentToPlanListLayout()
    {
        getActivity().invalidateOptionsMenu();
        createPlanLayout.setVisibility(GONE);
        planListLayout.setVisibility(VISIBLE);
    }

    public String getDurationString()
    {
        if (hoursSpinner.getSelectedItemPosition() > 1 &&
                minutesSpinner.getSelectedItemPosition() > 1)
        {
            return hoursSpinner.getSelectedItem() + "h " + minutesSpinner.getSelectedItem() + "m";
        }
        else if (hoursSpinner.getSelectedItemPosition() > 1)
        {
            return hoursSpinner.getSelectedItem() + "h";
        }
        else if (minutesSpinner.getSelectedItemPosition() > 1)
        {
            return minutesSpinner.getSelectedItem() + "m";
        }
        else
        {
            return null;
        }
    }

    public void setTooManyTasksTheme()
    {
        workingHoursTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
        workingHoursTitle.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
        editWorkingHoursButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_edit_red_24dp));
    }

    public void clearTooManyTasksTheme()
    {
        workingHoursTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.android_default));
        workingHoursTitle.setTextColor(ContextCompat.getColor(getActivity(), R.color.android_default));
        editWorkingHoursButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_edit_black_24dp));
    }

    public void setTextAccepted(Boolean accepted)
    {
        if (accepted)
        {
            inputAcceptedIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_check_circle_green_24dp));
            inputAcceptedIcon.setEnabled(true);
        }
        else
        {
            inputAcceptedIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_check_circle_gray_24dp));
            inputAcceptedIcon.setEnabled(false);
        }
        setEnterKeyEnabled(accepted);
    }

    public void setEnterKeyEnabled(Boolean enabled)
    {
        this.accepted = enabled;
    }

    public void setAddTasksLayoutVisible(boolean visible)
    {
        if (visible)
        {
            addTasksLayout.setVisibility(VISIBLE);
            outOfTimeLayout.setVisibility(GONE);
        }
        else
        {
            addTasksLayout.setVisibility(GONE);
        }
    }

    public void setOutOfTimeLayoutVisible(boolean visible)
    {
        if (visible)
        {
            outOfTimeLayout.setVisibility(VISIBLE);
            addTasksLayout.setVisibility(GONE);
        }
        else
        {
            outOfTimeLayout.setVisibility(GONE);
        }
    }

//    public void removeTaskFromList(int position)
//    {
//        planTaskListAdapter.removeItem(position);
//    }

    public void makeToast(String text)
    {
        Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void setWorkingTime(String durationString)
    {
        workingHoursTextView.setText(durationString);
    }

    private class TaskAdapter extends RecyclerView.Adapter<PlanFragment.TaskHolder> {
        private List<Task> tasks;

        public TaskAdapter(List<Task> tasks)
        {
            this.tasks = tasks;
        }

        @Override
        public PlanFragment.TaskHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(PlanFragment.this.getContext());
            View view = layoutInflater.inflate(R.layout.plan_fragment_task_list_item, parent, false);
            return new PlanFragment.TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(PlanFragment.TaskHolder holder, int position)
        {
            Task task = tasks.get(position);
            holder.bindTask(task);
        }
        @Override
        public int getItemCount()
        {
            return tasks.size();
        }

        //public void setTasks(List<Task> tasks)
        {
            this.tasks = tasks;
        }

        public void addItem(Task task) {
            tasks.add(task);
            notifyItemInserted(tasks.size());
        }

//        public void removeItem(int position) {
//            tasks.remove(position);
//            notifyItemRemoved(position);
//            notifyItemRangeChanged(position, tasks.size());
//        }
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView taskTitle;
        TextView dueDateTextView;
        TextView durationTextView;

        public TaskHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);
            taskTitle = itemView.findViewById(R.id.plan_list_item_title);
            dueDateTextView = itemView.findViewById(R.id.plan_list_item_dueDate);
            durationTextView = itemView.findViewById(R.id.plan_list_item_duration);

        }

        public void bindTask(Task newTask)
        {
            taskTitle.setText(newTask.getTitle());
            String dueDateString = newTask.getDueDateString();
            String durationString = newTask.getDurationPlannedString();
            if (dueDateString != null && durationString != null)
            {
                dueDateTextView.setText(dueDateString);
                dueDateTextView.setVisibility(VISIBLE);
                durationTextView.setText(durationString);
                durationTextView.setVisibility(VISIBLE);
            }
            else if (dueDateString != null)
            {
                dueDateTextView.setText(dueDateString);
                dueDateTextView.setVisibility(VISIBLE);
                durationTextView.setVisibility(GONE);
            }
            else if (durationString != null)
            {
                durationTextView.setText(durationString);
                durationTextView.setVisibility(VISIBLE);
                dueDateTextView.setVisibility(GONE);
            }
            else
            {
                dueDateTextView.setText(R.string.no_info);
                dueDateTextView.setVisibility(VISIBLE);
                durationTextView.setVisibility(View.INVISIBLE);
            }

        }



        @Override
        public void onClick(View v) {
            //call presenter's joinGame()
//            presenter.joinGame(game.getGameName());
        }
    }

    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    presenter.onPlanItemSwipedLeft(position);
                } else {
                    presenter.onPlanItemSwipedRight(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(ContextCompat.getColor(getActivity(), R.color.green));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = drawableToBitmap(R.drawable.ic_check_white_24dp);
                        //icon = BitmapFactory.decodeResource(getResources(), drawableToBitmap(R.drawable.ic_check_white_24dp));
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else if (dX < 0) {
                        p.setColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = drawableToBitmap(R.drawable.ic_remove_circle_outline_white_24dp);
                        //icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(planTaskList);
    }

    public Bitmap drawableToBitmap (int id) {

        Drawable drawable = ContextCompat.getDrawable(getActivity(), id);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public void updateSuggestions(List<String> suggestions)
    {
        autoCompleteAdapter = new SuggestionAdapter(suggestions);
        autoCompleteRecyclerView.setAdapter(autoCompleteAdapter);
        autoCompleteAdapter.notifyDataSetChanged();

    }

    private class SuggestionAdapter extends RecyclerView.Adapter<PlanFragment.SuggestionHolder> {
        private List<String> suggestions;

        public SuggestionAdapter(List<String> suggestions)
        {
            this.suggestions = suggestions;
        }

        @Override
        public PlanFragment.SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(PlanFragment.this.getContext());
            View view = layoutInflater.inflate(R.layout.autocomplete_list_item, parent, false);
            return new PlanFragment.SuggestionHolder(view);
        }

        @Override
        public void onBindViewHolder(PlanFragment.SuggestionHolder holder, int position)
        {
            String suggestion = suggestions.get(position);
            holder.bindSuggestion(suggestion);
        }
        @Override
        public int getItemCount()
        {
            return suggestions.size();
        }



        public void addItem(String suggestion) {
            suggestions.add(suggestion);
            notifyItemInserted(suggestions.size());
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
            presenter.onAutoCompleteSuggestionClicked(suggestion);
            hideKeyboard(v);
        }
    }

    public PlanPresenter getPresenter() {
        return presenter;
    }
}
