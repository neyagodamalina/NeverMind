package ru.neyagodamalina.nevermind.ui;

import android.app.Activity;
import android.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Project;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.util.Constants;
import ru.neyagodamalina.nevermind.viewmodel.TaskViewModel;

/**
 * Created by developer on 05.12.2017.
 */

public class CreateEditTaskFragment extends CommonFragment {
    private Button  mCleanTaskTextButton;
    private Button  mCancelAlarmButton;
    private Button  mListButton;
    private Button  mPhotoButton;
    private EditText mEditTaskText;
    private View mViewFragment;
    private TaskViewModel taskViewModel;
    private Task mTask = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mViewFragment =  inflater.inflate(R.layout.fragment_create_task, container, false);

        CommonActivity activity = (CommonActivity) mViewFragment.getContext();
        activity.setTitle(R.string.title_new_task);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        mEditTaskText = mViewFragment.findViewById(R.id.etTask);
        mEditTaskText.requestFocus();
        mEditTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {

                if (mTask == null){
                    addTask();
                }
                else if (!mEditTaskText.getText().toString().equals(mTask.getText())) {
                    editTask(mTask);
                }
            }
        });


        Bundle args = getArguments();
        if (args != null) {
            long taskId = args.getLong("taskId", 0);
            if (taskId != 0) {// this is "Edit task" activity
                activity.setTitle(R.string.title_edit_task);
                LiveData<Task> liveDataTask = taskViewModel.getTask(taskId);
                liveDataTask.observe(this, new Observer<Task>() {
                    @Override
                    public void onChanged(Task task) {
                        Log.d(Constants.LOG_TAG, "onChange change text");
                        mTask = task;
                        if (!task.getText().equals(mEditTaskText.getText().toString())) {
                            mEditTaskText.setText(task.getText());
                        }
                        else{
                            Log.d(Constants.LOG_TAG, "onChange NO change text");
                        }

                    }
                });
            }
            else {
                // temp Покажем имя-время создания этого фрагмента
                mEditTaskText = mViewFragment.findViewById(R.id.etTask);
                mEditTaskText.setText(this.getName());
                mEditTaskText.requestFocus();

            }

        }





        // region Button clean text
        mCleanTaskTextButton = (Button) mViewFragment.findViewById(R.id.btCleanTaskText);
        mCleanTaskTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.i(Constants.LOG_TAG, "Clean task text.");
                    mEditTaskText = (EditText) mViewFragment.findViewById(R.id.etTask);
                    mEditTaskText.setText("");
                    mEditTaskText.requestFocus();
                }
                catch (Exception e) {Log.e(Constants.LOG_TAG, e.getMessage());}
            }
        });
        // endregion

        // region Button cancel alarm
        mCancelAlarmButton = (Button) mViewFragment.findViewById(R.id.btCancelAlarm);
        mCancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(Constants.LOG_TAG, "Add task without alarm.");
                addTask();
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation_view);
                bottomNavigationView.setSelectedItemId(R.id.rv_navigation_list_tasks);
                Toast.makeText(view.getContext(), R.string.toast_task_added, Toast.LENGTH_SHORT).show();
            }
        });
        // endregion

        // region Button list
        mListButton = (Button) mViewFragment.findViewById(R.id.btList);
        mListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i(Constants.LOG_TAG, "Delete all projects from database.");
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"Press list.", Toast.LENGTH_SHORT).show();
//                        NMRepository NMRepository = new NMRepository(getContext());
//                        NMRepository.deleteAllProjects();
                    }
                });
                Toast.makeText(getContext(), "Deleted all projects from database.", Toast.LENGTH_SHORT).show();

            }
        });
        // endregion

        // region Button photo
        mPhotoButton = mViewFragment.findViewById(R.id.btPhoto);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Calendar createCalendar = Calendar.getInstance();
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                        Project project = new Project("Project " + dateFormat.format(new Date(createCalendar.getTimeInMillis())));
                        taskViewModel.addProject(project);
                    }
                });
                Toast.makeText(getContext(), "Created new project.", Toast.LENGTH_LONG).show();

            }
        });
        // endregion

        // region Spinner list of projects
        Spinner spinner = (Spinner) mViewFragment.findViewById(R.id.spChooseProject);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
        R.array.projects_delete_me, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // endregion


        // Test buttons

        Button button11 = (Button) mViewFragment.findViewById(R.id.buttonTest11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(Constants.LOG_TAG, mViewFragment.getContext().toString());
//                ((MainActivity) mViewFragment.getContext()).logBackStack();
            }
        });


        Button button22 = (Button) mViewFragment.findViewById(R.id.buttonTest22);
        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();
                FragmentManager manager = activity.getFragmentManager();
                manager.popBackStackImmediate();
                //manager.popBackStack(Constants.FRAGMENT_LIST_PROJECTS, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                ((MainActivity) activity).logBackStack();
            }
        });


        //region List of projects
        LiveData<List<Project>> projects = taskViewModel.getAllProjects();
        observerListProjects(projects);
        //endregion


        return mViewFragment;
    }

    private void observerListProjects(LiveData<List<Project>> projectsLive) {
        projectsLive.observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                if(projects == null){
                    return;
                }
                // Обновить список проектов, возможно
                // Toast.makeText(getContext(), "Number of projects: " + projects.size(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void addTask(){
        Task task = new Task(mEditTaskText.getText().toString());
        taskViewModel.addTask(task);
        mTask = task;
    }

    private void editTask(Task task){
        task.setText(mEditTaskText.getText().toString());
        taskViewModel.editTask(task);
    }



    public static CreateEditTaskFragment newInstance(long taskId) {
        CreateEditTaskFragment createEditTaskFragment = new CreateEditTaskFragment();
        Bundle args = new Bundle();
        args.putLong("taskId", taskId);
        createEditTaskFragment.setArguments(args);
        return createEditTaskFragment;
    }

}
