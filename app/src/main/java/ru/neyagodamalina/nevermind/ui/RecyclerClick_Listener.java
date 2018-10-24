package ru.neyagodamalina.nevermind.ui;

import android.view.View;

/**
 * For Action Mode Context Menu
 */
public interface RecyclerClick_Listener {

    /**
     * Interface for Recycler View Click listener
     **/

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}