package ru.neyagodamalina.nevermind.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.ui.ListTasksFragment.OnListFragmentInteractionListener;
import ru.neyagodamalina.nevermind.util.Constants;
import ru.neyagodamalina.nevermind.util.FormatDuration;
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
                .inflate(R.layout.row_list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mDurationView.setText(mValues.get(position).toStringDuration(FormatDuration.FORMAT_SMART, holder.mView.getResources()));
        holder.mDurationView.setTag(R.id.tag_current_format_duration, FormatDuration.FORMAT_SMART);

        DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        holder.mDateCreate.setText(dateFormat.format(new Date(mValues.get(position).getDateCreate())));



        // Set background for selected or not selected items
        holder.itemView
                .setBackgroundResource(mSelectedItemsIds.get(position) ? R.drawable.item_background_selected
                        : R.drawable.item_background);


        // region duration text
        holder.mDurationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Task task = mValues.get(position);
                    List possibleFormats = task.getPossibleFormats();
                    int currentFormat = (Integer) view.getTag(R.id.tag_current_format_duration);
                    currentFormat++;
                    if (currentFormat < possibleFormats.size()) {
                        holder.mDurationView.setText(mValues.get(position).toStringDuration((Integer) possibleFormats.get(currentFormat), holder.mView.getResources()));
                        holder.mDurationView.setTag(R.id.tag_current_format_duration, currentFormat);
                    }
                    else{
                        holder.mDurationView.setText(mValues.get(position).toStringDuration(FormatDuration.FORMAT_SMART, holder.mView.getResources()));
                        holder.mDurationView.setTag(R.id.tag_current_format_duration, FormatDuration.FORMAT_SMART);
                    }

                } catch (Exception e) {
                    Log.e(Constants.LOG_TAG, e.getMessage());
                }
            }
        });


        // endregion
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
        public final TextView mTitleView;
        public final TextView mDurationView;
        public final TextView mDateCreate;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.tv_task_id);
            mTitleView = view.findViewById(R.id.tv_task_title);
            mDurationView = view.findViewById(R.id.tv_task_duration);
            mDateCreate = view.findViewById(R.id.tv_task_date_created);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
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
