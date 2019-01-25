package ru.neyagodamalina.nevermind.ui;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.neyagodamalina.nevermind.R;
import ru.neyagodamalina.nevermind.db.Project;
//import ru.neyagodamalina.nevermind.fragment.dummy.DummyContent.DummyItem;


public class RecyclerViewAdapterProject extends RecyclerView.Adapter<RecyclerViewAdapterProject.ViewHolder>{
    private final List<Project> mValues;
    private final ListProjectsFragment.OnListFragmentInteractionListener mListener;

    public RecyclerViewAdapterProject(List<Project> items, ListProjectsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_tasks, parent, false);
        return new RecyclerViewAdapterProject.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterProject.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
        holder.mContentView.setText(mValues.get(position).getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    Toast.makeText(v.getContext(), "onClick in onBindViewHolder", Toast.LENGTH_LONG);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Project mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.tv_task_id);
            mContentView = (TextView) view.findViewById(R.id.tv_task_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}


