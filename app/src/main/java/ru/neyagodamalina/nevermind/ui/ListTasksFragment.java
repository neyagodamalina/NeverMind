package ru.neyagodamalina.nevermind.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.viewmodel.ListTasksViewModel;

public class ListTasksFragment extends CommonFragment {
    private ListTasksFragment.OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private ActionMode mActionMode;
    private static List<Task> tasks;
    private static RecyclerViewAdapterTask adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Add items menu for list of task
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_list_tasks, container, false);
        ListTasksViewModel listTasksViewModel = ViewModelProviders.of(this).get(ListTasksViewModel.class);

        MainActivity mainActivity = (MainActivity) view.getContext();
        mainActivity.setCurrentFragment(this);

        recyclerView = view.findViewById(R.id.navigation_list_tasks);

        // Set the adapter
        if (recyclerView instanceof RecyclerView) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

            LiveData<List<Task>> liveDataTasks = listTasksViewModel.getAllTasks();
            liveDataTasks.observe(this, new Observer<List<Task>>(
                    ) {
                        @Override
                        public void onChanged(@Nullable List<Task> tasks) {
                            adapter = new RecyclerViewAdapterTask(tasks, mListener);
                            recyclerView.setAdapter(adapter);
                            ListTasksFragment.tasks = tasks;
                        }
                    }
            );
        }

        implementRecyclerViewClickListeners();

        return view;
    }

    /**
     * Add items menu for list of task
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_list_tasks, menu);
    }

    /**
     * Press menu item on main toolbar only action for this fragment.
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add_task:
                Toast.makeText(this.getActivity(),"Press add task", Toast.LENGTH_LONG).show();
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
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new Toolbar_ActionMode_Callback(getActivity(),adapter, tasks));
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
        if (mActionMode != null)
            mActionMode = null;
    }

    //Delete selected rows
    public void deleteRows() {
        SparseBooleanArray selected = adapter
                .getSelectedIds();//Get selected ids

        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                tasks.remove(selected.keyAt(i));
                adapter.notifyDataSetChanged();//notify adapter

            }
        }
        Toast.makeText(getActivity(), selected.size() + " item deleted.", Toast.LENGTH_SHORT).show();//Show Toast
        mActionMode.finish();//Finish action mode after use

    }

}
