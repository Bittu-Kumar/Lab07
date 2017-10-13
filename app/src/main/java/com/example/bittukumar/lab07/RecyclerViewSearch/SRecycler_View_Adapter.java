package com.example.bittukumar.lab07.RecyclerViewSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bittukumar.lab07.R;

import java.util.Collections;
import java.util.List;


public class SRecycler_View_Adapter extends RecyclerView.Adapter<SView_Holder> {

    List<SData> list = Collections.emptyList();
    Context context;

    public SRecycler_View_Adapter(List<SData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public SView_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row_layout, parent, false);
        SView_Holder holder = new SView_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(SView_Holder holder, int position) {
        holder.suid.setText(list.get(position).suid);
        holder.sname.setText(list.get(position).suidname);
        holder.semail.setText(list.get(position).semail);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView
    public void insert(int position, SData data) {
        list.add(position, data);
        notifyItemInserted(position);

    }
    // Remove a RecyclerView item containing the SData object
    public void remove(int position) {
//        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }
    public void refresh(List<SData>data)
    {
        list = data;
        notifyDataSetChanged();
    }

    public void invalidate() {

    }
}
