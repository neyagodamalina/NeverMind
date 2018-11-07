package ru.neyagodamalina.nevermind.ui;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.ui.ListTasksFragment.OnListFragmentInteractionListener;
import ru.neyagodamalina.nevermind.util.SparseBooleanArrayParcelable;

public class RecyclerViewAdapterTask extends RecyclerView.Adapter<RecyclerViewAdapterTask.ViewHolder> {

    private final List<Task> mValues;
    private final OnListFragmentInteractionListener mListener;
    private SparseBooleanArrayParcelable mSelectedItemsIds;

    public RecyclerViewAdapterTask(List<Task> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mSelectedItemsIds = new SparseBooleanArrayParcelable();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
        holder.mContentView.setText(mValues.get(position).getTitle());


        // Set background for selected or not selected items
        holder.itemView
                .setBackgroundResource(mSelectedItemsIds.get(position) ? R.drawable.item_background_selected
                        : R.drawable.item_background);



//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                    Toast.makeText(v.getContext(), "onClick in onBindViewHolder", Toast.LENGTH_LONG);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.task_id);
            mContentView = (TextView) view.findViewById(R.id.task_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    /***
     * Methods required for do selections, remove selections, etc.
     */

    //Toggle selection methods
    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }


    //Remove selected selections
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArrayParcelable();
        notifyDataSetChanged();
    }


    //Put or delete selected position into SparseBooleanArray
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    //Get total selected count
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }


    public void setSelectedItemsIds(SparseBooleanArrayParcelable mSelectedItemsIds) {
        this.mSelectedItemsIds = mSelectedItemsIds;
    }

    //Return all selected ids
    public SparseBooleanArrayParcelable getSelectedIds() {
        return mSelectedItemsIds;
    }
}
