package com.example.bittukumar.lab07.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.bittukumar.lab07.R;

import java.util.Collections;
import java.util.List;


public class Recycler_View_Adapter extends RecyclerView.Adapter<View_Holder> {

    List<Data> list = Collections.emptyList();
    Context context;

    public Recycler_View_Adapter(List<Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {
        holder.postuid.setText(list.get(position).uid);
        holder.postText.setText(list.get(position).postText);
        holder.comments.setText(list.get(position).comments);
        if(list.get(position).more)holder.moreTV.setVisibility(View.VISIBLE);

//        TextView tv = new TextView(holder.ll.getContext());
//        tv.setText("bittu");
//        holder.ll.addView(tv);




//        animate(holder);
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
    public void insert(int position, Data data) {
        list.add(position, data);
        notifyItemInserted(position);

    }
    // Remove a RecyclerView item containing the SData object
    public void remove(int position) {
//        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }
    public void refresh(List<Data>data)
    {
        list = data;
        notifyDataSetChanged();
    }

//    public void animate(RecyclerView.ViewHolder viewHolder) {
//        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.anticipate_overshoot_interpolator);
//        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
//    }


}
