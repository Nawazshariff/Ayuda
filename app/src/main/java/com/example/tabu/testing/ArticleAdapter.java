package com.example.tabu.testing;

/**
 * Created by SummyTabu on 3/12/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by nawazshariff on 29-09-2015.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> implements View.OnClickListener {
    List<DataHolder> itemsData= Collections.EMPTY_LIST;
    DataHolder current,accept;
    private static Context context;
    ViewHolder viewHolder;

    public ArticleAdapter(Context c, List<DataHolder> itemsData) {
        this.context = c;
        this.itemsData = itemsData;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, null);

        viewHolder = new ViewHolder(itemLayoutView);
        itemLayoutView.setOnClickListener(this);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        current=itemsData.get(position);
        viewHolder.txtViewTitle.setText(current.name);
        viewHolder.textView.setText(current.des);


    }

    @Override
    public void onClick(View view) {

        int position = viewHolder.getAdapterPosition();
            accept=itemsData.get(position);
            Intent send = new Intent(context,Accept.class);
                send.putExtra("name",accept.name);
        Log.d("nameaccept",accept.name);
            send.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(send);

    }


    /*
        @Override
        public void onClick(View v) {
            int position = FragmentC.recyclerView.getChildLayoutPosition(v);
            Intent in;
            in = new Intent(context, Nexttocard.class);
            context.startActivity(in);


        }
    */
    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public Button b1;
        public TextView txtViewTitle;
        public TextView textView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            textView = (TextView) itemLayoutView.findViewById(R.id.who);

        }
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}
