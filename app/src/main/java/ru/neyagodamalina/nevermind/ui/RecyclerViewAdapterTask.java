package ru.neyagodamalina.nevermind.ui;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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


    private final List<Task> tasks;
    private final OnListFragmentInteractionListener mListener;
    private SparseBooleanArrayParcelable mSelectedItemsIds;
    private static int playingPosition = -1; // not exist playing task

    public RecyclerViewAdapterTask(List<Task> items, OnListFragmentInteractionListener listener) {
        tasks = items;
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
        final Task task = tasks.get(position);
        holder.mItem = task;
        holder.mIdView.setText(String.valueOf(task.getId()));
        holder.mTitleView.setText(task.getTitle());
        holder.mDurationView.setText(task.toStringDuration(FormatDuration.FORMAT_SMART, holder.mView.getResources()));
        holder.mDurationView.setTag(R.id.tag_current_format_duration, FormatDuration.FORMAT_SMART);

        DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        holder.mDateCreate.setText(dateFormat.format(new Date(task.getDateCreate())));
        holder.mDateStarted.setText(dateFormat.format(new Date(task.getTimeStart())));
        holder.mDateLast.setText(dateFormat.format(new Date(task.getTimeStop())));


        // Set background for selected or not selected items
        holder.itemView
                .setBackgroundResource(mSelectedItemsIds.get(position) ? R.drawable.item_background_selected
                        : R.drawable.item_background);


        // region duration text
        holder.mDurationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Task task = tasks.get(position);
                    List possibleFormats = task.getPossibleFormats();
                    int currentFormat = (Integer) view.getTag(R.id.tag_current_format_duration);
                    currentFormat++;
                    if (currentFormat < possibleFormats.size()) {
                        holder.mDurationView.setText(task.toStringDuration((Integer) possibleFormats.get(currentFormat), holder.mView.getResources()));
                        holder.mDurationView.setTag(R.id.tag_current_format_duration, currentFormat);
                    } else {
                        holder.mDurationView.setText(task.toStringDuration(FormatDuration.FORMAT_SMART, holder.mView.getResources()));
                        holder.mDurationView.setTag(R.id.tag_current_format_duration, FormatDuration.FORMAT_SMART);
                    }

                } catch (Exception e) {
                    Log.e(Constants.LOG_TAG, e.getMessage());
                }
            }
        });
        // endregion

        // region duration btPlay & btRec

        Log.d(Constants.LOG_TAG, position + "\t" + holder.btPlay.toString());

        // change button Play to Rec
        if ((playingPosition != -1) && (playingPosition == position)){
            Log.d(Constants.LOG_TAG, position + "->onBindViewHolder");
            holder.btPlay.setVisibility(View.GONE);
            holder.btRec.setVisibility(View.VISIBLE);
            Drawable drawableRec = holder.btRec.getDrawable();
            if (drawableRec instanceof Animatable)
                ((Animatable) drawableRec).start();
        }



        holder.btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = ((ImageButton) v).getDrawable();
                if (drawable instanceof Animatable)
                    ((Animatable) drawable).start();

                // start task after and of animation
                if (drawable instanceof AnimatedVectorDrawable)
                    ((AnimatedVectorDrawable) drawable).registerAnimationCallback(new Animatable2.AnimationCallback() {
                        @Override
                        public void onAnimationEnd(Drawable drawable) {
                            super.onAnimationEnd(drawable);
                            playingPosition = position;
                            MainActivity context = (MainActivity) holder.mView.getContext();
                            ((ListTasksFragment) context.getCurrentFragment()).startTask(task);
                        }
                    });

            }
        });
        // endregion
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mTitleView;
        public final TextView mDurationView;
        public final TextView mDateCreate;
        public final TextView mDateStarted;
        public final TextView mDateLast;
        public ImageButton btPlay;
        public ImageButton btRec;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.tv_task_id);
            mTitleView = view.findViewById(R.id.tv_task_title);
            mDurationView = view.findViewById(R.id.tv_task_duration);
            mDateCreate = view.findViewById(R.id.tv_task_date_created);
            mDateStarted = view.findViewById(R.id.tv_task_date_started);
            mDateLast = view.findViewById(R.id.tv_task_date_last);
            btPlay = view.findViewById(R.id.btn_play);
            btRec = view.findViewById(R.id.btn_rec);
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

    public void startTask(Task task) {

    }

}
