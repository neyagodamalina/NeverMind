package ru.neyagodamalina.nevermind.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Project;
import ru.neyagodamalina.nevermind.viewmodel.ListProjectsViewModel;

/**
 * Created by developer on 20.03.2018.
 */

public class ListProjectsFragment extends CommonFragment {

    private ListProjectsFragment.OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mViewFragment =  inflater.inflate(R.layout.fragment_list_projects, container, false);
        ListProjectsViewModel listProjectsViewModel = ViewModelProviders.of(this).get(ListProjectsViewModel.class);

        MainActivity mainActivity = (MainActivity) mViewFragment.getContext();
        mainActivity.setTitle(R.string.title_projects);


        // Set the adapter
        if (mViewFragment instanceof RecyclerView) {
            Context context = mViewFragment.getContext();
            recyclerView = (RecyclerView) mViewFragment;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            LiveData<List<Project>> liveDataProjects = listProjectsViewModel.getAllProjects();
            liveDataProjects.observe(this, new Observer<List<Project>>(
                    ) {
                        @Override
                        public void onChanged(@Nullable List<Project> projects) {
                            recyclerView.setAdapter(new RecyclerViewAdapterProject(projects, mListener));
                        }
                    }
            );
        }


        return mViewFragment;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Project item);
    }
}
