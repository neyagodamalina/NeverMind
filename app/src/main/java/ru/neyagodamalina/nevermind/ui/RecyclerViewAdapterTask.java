package ru.neyagodamalina.nevermind.ui;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Task;
import ru.neyagodamalina.nevermind.ui.ListTasksFragment.OnListFragmentInteractionListener;
import ru.neyagodamalina.nevermind.util.Constants;
import ru.neyagodamalina.nevermind.util.FormatDuration;
import ru.neyagodamalina.nevermind.util.SparseBooleanArrayParcelable;
import ru.neyagodamalina.nevermind.util.TaskState;

public class RecyclerViewAdapterTask extends RecyclerView.Adapter<RecyclerViewAdapterTask.ViewHolder> {


    private final List<Task> tasks;
    private final OnListFragmentInteractionListener mListener;
    private SparseBooleanArrayParcelable mSelectedItemsIds;
    public static Parcelable instanceState; // for scroll to this position after refresh RecycleView

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
        holder.mTask = task;
        holder.mIdView.setText(String.valueOf(task.getId()));
        holder.mTitleView.setText(task.getTitle());
        holder.mDurationView.setText(task.toStringDuration(FormatDuration.FORMAT_SMART, holder.itemView.getResources()));
        holder.mDurationView.setTag(R.id.tag_current_format_duration, FormatDuration.FORMAT_SMART);
        DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        holder.mDateCreate.setText(dateFormat.format(new Date(task.getDateCreate())));
        holder.mDateStart.setText((task.getDateStart() == 0) ? "-" : dateFormat.format(new Date(task.getDateStart())));
        holder.mDateLast.setText((task.getDateLast() == 0) ? "-" : dateFormat.format(new Date(task.getDateLast())));

        // ">" near duration while task is working
        if (task.getState() == TaskState.STATE_REC)
            holder.tvMoreDuration.setText(R.string.more_duration);

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
                        holder.mDurationView.setText(task.toStringDuration((Integer) possibleFormats.get(currentFormat), holder.itemView.getResources()));
                        holder.mDurationView.setTag(R.id.tag_current_format_duration, currentFormat);
                    } else {
                        holder.mDurationView.setText(task.toStringDuration(FormatDuration.FORMAT_SMART, holder.itemView.getResources()));
                        holder.mDurationView.setTag(R.id.tag_current_format_duration, FormatDuration.FORMAT_SMART);
                    }

                } catch (Exception e) {
                    Log.e(Constants.LOG_TAG, e.getMessage());
                }
            }
        });
        // endregion

        // region btPlay & btRec

        // PLAY if was playing before
        if (task.getState() == TaskState.STATE_REC) {
            holder.btPlay.setImageResource(R.drawable.anim_rec); // Not set in row_list_task.xml, because play run anywhere where use this button.
            Drawable drawableRec = holder.btPlay.getDrawable();
            if (drawableRec instanceof Animatable)
                ((Animatable) drawableRec).start();

        } else
            holder.btPlay.setImageResource(R.drawable.anim_play); // Not set in row_list_task.xml, because play run anywhere where use this button.

        holder.btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // START
                Drawable drawable = ((ImageButton) v).getDrawable();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {   // API > 23
                    AnimatedVectorDrawable drawableAVD = (AnimatedVectorDrawable) drawable;
                    drawableAVD.registerAnimationCallback(
                            new Animatable2.AnimationCallback() {
                                @Override
                                public void onAnimationEnd(Drawable drawable) {
                                    super.onAnimationEnd(drawable);
                                    holder.btPlay.setImageResource(R.drawable.anim_rec);
                                    Drawable drawableRec = holder.btPlay.getDrawable();
                                    if (drawableRec instanceof Animatable)
                                        ((Animatable) drawableRec).start();

                                    // task START
                                    MainActivity context = (MainActivity) holder.mView.getContext();
                                    ((ListTasksFragment) context.getCurrentFragment()).startTask(task);
                                }
                            }
                    );
                }   //  if API > 23
                else {
                    AnimatedVectorDrawableCompat drawableAVDC = (AnimatedVectorDrawableCompat) drawable;
                    drawableAVDC.registerAnimationCallback(
                            new Animatable2Compat.AnimationCallback() {
                                @Override
                                public void onAnimationEnd(Drawable drawable) {
                                    super.onAnimationEnd(drawable);
                                    holder.btPlay.setImageResource(R.drawable.anim_rec);
                                    Drawable drawableRec = holder.btPlay.getDrawable();
                                    if (drawableRec instanceof Animatable)
                                        ((Animatable) drawableRec).start();
                                    // task START
                                    MainActivity context = (MainActivity) holder.mView.getContext();
                                    ((ListTasksFragment) context.getCurrentFragment()).startTask(task);
                                }
                            }
                    );

                }

                // task STOP
                if (task.getState() == TaskState.STATE_REC) {
                    MainActivity context = (MainActivity) holder.mView.getContext();
                    ((ListTasksFragment) context.getCurrentFragment()).stopTask(task);

                }

                // Save scroll state RecycleView. After refresh data (after press play/stop button) scroll RecycleView to the same position
                MainActivity context = (MainActivity) holder.mView.getContext();
                RecyclerView rv = context.findViewById(R.id.rv_navigation_list_tasks);
                if (rv != null && rv instanceof RecyclerView) {
                    LinearLayoutManager layoutManager = ((LinearLayoutManager) rv.getLayoutManager());
                    instanceState = layoutManager.onSaveInstanceState();
                }
            }
        });
        // endregion
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mTitleView;
        public final TextView mDurationView;
        public final TextView mDateCreate;
        public final TextView mDateStart;
        public final TextView mDateLast;
        public final ImageButton btPlay;
        public final TextView tvMoreDuration;

        public Task mTask;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.tv_task_id);
            mTitleView = view.findViewById(R.id.tv_task_title);
            mDurationView = view.findViewById(R.id.tv_task_duration);
            mDateCreate = view.findViewById(R.id.tv_task_date_created);
            mDateStart = view.findViewById(R.id.tv_task_date_started);
            mDateLast = view.findViewById(R.id.tv_task_date_last);
            btPlay = view.findViewById(R.id.btn_play);
            tvMoreDuration = view.findViewById(R.id.tv_more_duration);
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


    //Put or deleteTask selected position into SparseBooleanArray
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
