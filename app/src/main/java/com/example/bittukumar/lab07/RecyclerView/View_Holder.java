package com.example.bittukumar.lab07.RecyclerView;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bittukumar.lab07.R;


//The adapters View Holder
public class View_Holder extends RecyclerView.ViewHolder {

    TextView postuid;
    TextView postText;
    TextView comments;
    TextView moreTV;
//    CardView cv;
//    TextView title;
//    TextView description;
//    ImageView imageView;
//    LinearLayout ll;

    View_Holder(View itemView) {
        super(itemView);
        postuid = (TextView)itemView.findViewById(R.id.postNameTV);
        postText = (TextView)itemView.findViewById(R.id.postPostTV);
        comments = (TextView)itemView.findViewById(R.id.commentTV);
        moreTV = (TextView)itemView.findViewById(R.id.moreTV);
//        cv = (CardView) itemView.findViewById(R.id.cardView);
//        title = (TextView) itemView.findViewById(R.id.title);
//        description = (TextView) itemView.findViewById(R.id.description);
//        imageView = (ImageView) itemView.findViewById(R.id.imageView);
//        ll = (LinearLayout)itemView.findViewById(R.id.commentlayout);
    }

}
