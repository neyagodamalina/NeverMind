package ru.neyagodamalina.nevermind.ui;

import android.content.Context;
import android.os.Build;
import androidx.fragment.app.Fragment;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.view.ActionMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.util.Constants;

/**
 * Created by SONU on 22/03/16.
 */
public class Toolbar_ActionMode_Callback implements ActionMode.Callback {

    private Context context;
    private RecyclerViewAdapterTask recyclerView_adapter;
    private List<Task> tasks;


    public Toolbar_ActionMode_Callback(Context context, RecyclerViewAdapterTask recyclerView_adapter, List<Task> tasks) {
        this.context = context;
        this.recyclerView_adapter = recyclerView_adapter;
        this.tasks = tasks;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.context_action_menu_list_tasks, menu);//Inflate the menu over action mode
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

        //Sometimes the meu will not be visible so for that we need to set their visibility manually in this method
        //So here show action menu according to SDK Levels
        if (Build.VERSION.SDK_INT < 11) {
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_delete), MenuItemCompat.SHOW_AS_ACTION_NEVER);
        } else {
            menu.findItem(R.id.action_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                Fragment fragment = ((MainActivity) context).getCurrentFragment();
                if ((fragment != null) && (fragment instanceof ListTasksFragment)) {
                    ((ListTasksFragment) fragment).deleteTasks();
                }
//                //If current fragment is recycler view fragment
//                Fragment recyclerFragment = new MainActivity().getFragment(1);//Get recycler view fragment
//                if (recyclerFragment != null)
//                    //If recycler fragment not null
//                    ((RecyclerView_Fragment) recyclerFragment).deleteRows();//deleteTask selected rows
                break;
//            case R.id.action_edit:
//                Log.d(Constants.LOG_TAG, "Edit row");
                //Get selected ids on basis of current fragment action mode
//                SparseBooleanArray selected;
//                    selected = recyclerView_adapter
//                            .getSelectedIds();
//
//                int selectedMessageSize = selected.size();
//
//                //Loop to all selected items
//                for (int i = (selectedMessageSize - 1); i >= 0; i--) {
//                    if (selected.valueAt(i)) {
//                        //get selected data in Model
//                        Item_Model model = message_models.get(selected.keyAt(i));
//                        String title = model.getTitle();
//                        String subTitle = model.getSubTitle();
//                        //Print the data to show if its working properly or not
//                        Log.e("Selected Items", "Title - " + title + "n" + "Sub Title - " + subTitle);
//
//                    }
//                }
//                Toast.makeText(context, "You selected Copy menu.", Toast.LENGTH_SHORT).show();//Show toast
//                mode.finish();//Finish action mode
                //break;
//            case R.id.action_create:
//                Log.d(Constants.LOG_TAG, "Create row");
//                mode.finish();//Finish action mode
//                break;
        }
        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {

        //When action mode destroyed remove selected selections and set action mode to null
        //First check current fragment action mode
        recyclerView_adapter.removeSelection();  // remove selection
        Fragment fragment = ((MainActivity) context).getCurrentFragment();
        if (fragment != null)
            ((ListTasksFragment) fragment).setNullToActionMode();//Set action mode null

    }
}