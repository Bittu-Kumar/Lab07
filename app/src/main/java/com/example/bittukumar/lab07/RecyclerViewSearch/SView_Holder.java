package com.example.bittukumar.lab07.RecyclerViewSearch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bittukumar.lab07.R;


//The adapters View Holder
public class SView_Holder extends RecyclerView.ViewHolder {

    TextView suid;
    TextView sname;
    TextView semail;

    SView_Holder(View itemView) {
        super(itemView);
        suid = (TextView)itemView.findViewById(R.id.suidTV);
        sname = (TextView)itemView.findViewById(R.id.snameTV);
        semail = (TextView)itemView.findViewById(R.id.semailTV);
    }

}
