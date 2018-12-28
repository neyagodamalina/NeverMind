package ru.neyagodamalina.nevermind.ui;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.util.Constants;
import ru.neyagodamalina.nevermind.util.SparseBooleanArrayParcelable;
import ru.neyagodamalina.nevermind.viewmodel.ListTasksViewModel;
import ru.neyagodamalina.nevermind.viewmodel.TaskViewModel;

public class ListTasksFragment extends CommonFragment {
    private ListTasksFragment.OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private ActionMode mActionMode;
    private SparseBooleanArrayParcelable mSelectedTasks;
    private static List<Task> tasks;
    private static RecyclerViewAdapterTask adapter;
    private static TaskViewModel taskViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Add items menu for list of task
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActionMode != null)
            outState.putParcelable("selectedItems", adapter.getSelectedIds());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        Log.d(Constants.LOG_TAG, "------------------ ListTasksFragment onCreateView ------------------");
        View mViewFragment = inflater.inflate(R.layout.fragment_list_tasks, container, false);
        ListTasksViewModel listTasksViewModel = ViewModelProviders.of(this).get(ListTasksViewModel.class);
        this.taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        MainActivity mainActivity = (MainActivity) mViewFragment.getContext();
        mainActivity.setCurrentFragment(this);

        mainActivity.setTitle(R.string.title_tasks);

        recyclerView = mViewFragment.findViewById(R.id.navigation_list_tasks);

        // Set the adapter
        if (recyclerView instanceof RecyclerView) {

            recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

            LiveData<List<Task>> liveDataTasks = listTasksViewModel.getAllTasks();
            liveDataTasks.observe(this, new Observer<List<Task>>(
                    ) {
                        @Override
                        public void onChanged(@Nullable List<Task> tasks) {
                            Log.d(Constants.LOG_TAG, "------------------ Observer onChanged ------------------");
                            adapter = new RecyclerViewAdapterTask(tasks, mListener);
                            if (mSelectedTasks != null) {
                                adapter.setSelectedItemsIds(mSelectedTasks);
                                mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new Toolbar_ActionMode_Callback(getActivity(), adapter, tasks));
                                mActionMode.setTitle(String.valueOf(adapter.getSelectedCount()) + " " + getContext().getResources().getString(R.string.selected));
                            }


                            // auto scroll to last position
                            if (RecyclerViewAdapterTask.instanceState != null)
                                recyclerView.getLayoutManager().onRestoreInstanceState(RecyclerViewAdapterTask.instanceState);

                            recyclerView.setAdapter(adapter);
                            ListTasksFragment.tasks = tasks;

                        }
                    }
            );
        }

        implementRecyclerViewClickListeners();
        Log.d(Constants.LOG_TAG, "CreateView\t" + this.toString());
        return mViewFragment;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
            mSelectedTasks = savedInstanceState.getParcelable("selectedItems");
    }

    @Override
    public void onDestroyView() {
        if (mActionMode != null)
            mActionMode.finish();
        super.onDestroyView();
    }

    /**
     * Add items menu for list of task
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_list_tasks, menu);
    }

    /**
     * Press menu item on main toolbar, only action for this fragment.
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add_task:
                Toast.makeText(this.getActivity(), "Press add task", Toast.LENGTH_SHORT).show();
                return true;
        }

        return false;
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
        void onListFragmentInteraction(Task item);

    }

    //Implement item click and long click over recycler view
    private void implementRecyclerViewClickListeners() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerClick_Listener() {
            @Override
            public void onClick(View view, int position) {
                //If ActionMode not null select item
                if (mActionMode != null)
                    onListItemSelect(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                //Select item on long click
                onListItemSelect(position);
            }
        }));
    }

    //List item select method
    private void onListItemSelect(int position) {

        adapter.toggleSelection(position);//Toggle the selection

        boolean hasCheckedItems = adapter.getSelectedCount() > 0;//Check if any items are already selected or not


        if (hasCheckedItems && mActionMode == null)
            // there are some selected items, start the actionMode
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new Toolbar_ActionMode_Callback(getActivity(), adapter, tasks));
        else if (!hasCheckedItems && mActionMode != null)
            // there no selected items, finish the actionMode
            mActionMode.finish();

        if (mActionMode != null)
            //set action mode title on item selection
            mActionMode.setTitle(String.valueOf(adapter
                    .getSelectedCount()) + " " + getContext().getResources().getString(R.string.selected));


    }

    //Set action mode null after use
    public void setNullToActionMode() {
        mActionMode = null;
        mSelectedTasks = null;
    }

    //Delete selected rows
    public void deleteTasks() {
        SparseBooleanArray selected = adapter.getSelectedIds();//Get selected ids

        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item
                Log.d(Constants.LOG_TAG, "Task has been deleted Id=" + tasks.get(selected.keyAt(i)).getId());
                deleteTask(tasks.get(selected.keyAt(i)));
            }

        }
        Toast.makeText(getActivity(), selected.size() + " item deleted.", Toast.LENGTH_SHORT).show();//Show Toast
        mActionMode.finish();//Finish action mode after use

        // After scrolling tasks, BottomNavigationView hides.
        // When delete tasks, BottomNavigationView was be invisible (slideDown)
        // and can't show it again without few tasks. Show (slideUp) BottomNavigationView any case.
        BottomNavigationView bottomNavigationView = this.getActivity().findViewById(R.id.bottom_navigation_view);
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        ((HideBottomViewOnScrollBehavior) params.getBehavior()).slideUp(bottomNavigationView);

    }

    public void startTask(Task task) {
        taskViewModel.startTask(task);
    }


    public void stopTask(Task task) {
        taskViewModel.stopTask(task);
    }

    public void deleteTask(Task task) {
        taskViewModel.deleteTask(task);


    }

}

