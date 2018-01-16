package ru.neyagodamalina.nevermind.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.business.util.Constants;

/**
 * Created by developer on 05.12.2017.
 */

public class CreateTaskFragment extends Fragment {
    private Button  mCleanTaskTextButton;
    private EditText mEditTaskText;
    private View mViewFragment;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mViewFragment =  inflater.inflate(R.layout.create_task_fragment, container, false);
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

        return mViewFragment;
    }


}
