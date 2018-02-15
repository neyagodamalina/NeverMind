package ru.neyagodamalina.nevermind.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Executors;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.business.persistence.AppDatabase;
import ru.neyagodamalina.nevermind.business.persistence.Task;
import ru.neyagodamalina.nevermind.business.util.Constants;

/**
 * Created by developer on 05.12.2017.
 */

public class CreateTaskFragment extends CommonFragment {
    private Button  mCleanTaskTextButton;
    private Button  mCancelAlarmButton; 
    private EditText mEditTaskText;
    private View mViewFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mViewFragment =  inflater.inflate(R.layout.fragment_create_task, container, false);

        // Покажем имя-время создания этого фрагмента
        mEditTaskText = (EditText) mViewFragment.findViewById(R.id.etTask);
        mEditTaskText.setText(this.getName());

        mCleanTaskTextButton = (Button) mViewFragment.findViewById(R.id.btCleanTaskText);
        mCleanTaskTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d(Constants.LOG_TAG, "Clean task text.");
                    mEditTaskText = (EditText) mViewFragment.findViewById(R.id.etTask);
                    mEditTaskText.setText("");
                }
                catch (Exception e) {Log.e(Constants.LOG_TAG, e.getMessage());}
            }
        });

        mCancelAlarmButton = (Button) mViewFragment.findViewById(R.id.btCancelAlarm);
        mCancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(Constants.LOG_TAG, "Save task without alarm.");
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase database = AppDatabase.getDatabase(getContext());
                        mEditTaskText = (EditText) mViewFragment.findViewById(R.id.etTask);
                        Task task = new Task(mEditTaskText.getText().toString());
                        database.getTaskDao().insertTask(task);
                    }
                });
                Toast.makeText(getContext(), "Task without reminder saved.", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("CREATE_TASK_FRAGMENT")).commit();

            }
        });

        return mViewFragment;
    }


}
