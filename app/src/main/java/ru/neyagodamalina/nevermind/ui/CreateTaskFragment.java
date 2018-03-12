package ru.neyagodamalina.nevermind.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Executors;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.AppDatabase;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.repository.TaskRepository;
import ru.neyagodamalina.nevermind.util.Constants;
import ru.neyagodamalina.nevermind.viewmodel.TaskViewModel;

/**
 * Created by developer on 05.12.2017.
 */

public class CreateTaskFragment extends CommonFragment {
    private Button  mCleanTaskTextButton;
    private Button  mCancelAlarmButton;
    private Button  mListButton;
    private EditText mEditTaskText;
    private View mViewFragment;
    private TaskViewModel taskViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mViewFragment =  inflater.inflate(R.layout.fragment_create_task, container, false);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        // Покажем имя-время создания этого фрагмента
        mEditTaskText = (EditText) mViewFragment.findViewById(R.id.etTask);
        mEditTaskText.setText(this.getName());

        mEditTaskText.requestFocus();

        mCleanTaskTextButton = (Button) mViewFragment.findViewById(R.id.btCleanTaskText);
        mCleanTaskTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d(Constants.LOG_TAG, "Clean task text.");
                    mEditTaskText = (EditText) mViewFragment.findViewById(R.id.etTask);
                    mEditTaskText.setText("");
                    mEditTaskText.requestFocus();
                }
                catch (Exception e) {Log.e(Constants.LOG_TAG, e.getMessage());}
            }
        });

        mCancelAlarmButton = (Button) mViewFragment.findViewById(R.id.btCancelAlarm);
        mCancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Constants.LOG_TAG, "Add task without alarm.");
                addTask();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("CREATE_TASK_FRAGMENT")).commit();

            }
        });



        mListButton = (Button) mViewFragment.findViewById(R.id.btList);
        mListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(Constants.LOG_TAG, "Delete all projects from database.");
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        TaskRepository taskRepository = new TaskRepository(getContext());
                        taskRepository.deleteAllProjects();
                    }
                });
                Toast.makeText(getContext(), "Deleted all projects from database.", Toast.LENGTH_LONG).show();

            }
        });

        return mViewFragment;
    }

    private void addTask(){
        Task task = new Task( mEditTaskText.getText().toString());
        taskViewModel.addTask(task);
        Toast.makeText(this.getContext(), "Task added.", Toast.LENGTH_LONG).show();
    }


}
