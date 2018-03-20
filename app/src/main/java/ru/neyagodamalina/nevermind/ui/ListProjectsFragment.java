package ru.neyagodamalina.nevermind.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.ui.CommonFragment;

/**
 * Created by developer on 20.03.2018.
 */

public class ListProjectsFragment extends CommonFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mViewFragment =  inflater.inflate(R.layout.fragment_list_projects, container, false);
        return mViewFragment;
    }

}
