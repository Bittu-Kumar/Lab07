package com.example.bittukumar.lab07.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bittukumar.lab07.Activities.HomeActivity;
import com.example.bittukumar.lab07.Activities.MainActivity;
import com.example.bittukumar.lab07.R;
import com.example.bittukumar.lab07.RecyclerView.Comment;
import com.example.bittukumar.lab07.RecyclerView.CustomRVItemTouchListener;
import com.example.bittukumar.lab07.RecyclerView.Data;
import com.example.bittukumar.lab07.RecyclerView.RecyclerViewItemClickListener;
import com.example.bittukumar.lab07.RecyclerView.Recycler_View_Adapter;
import com.example.bittukumar.lab07.Utils.AppConstants;
import com.example.bittukumar.lab07.Utils.Util;
import com.example.bittukumar.lab07.Utils.VolleyStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShowPostsFragment extends Fragment {
    ImageView sendcomment;
    TextView moreTV;
    EditText commentET;
    RecyclerView recyclerView;
    private Recycler_View_Adapter adapter;
    List<Data> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_posts, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        data = new ArrayList<>();
        getData();
        recyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(getActivity(), recyclerView, new RecyclerViewItemClickListener() {
            @Override
            public void onClick(final View view, final int position) {
                sendcomment = (ImageView) view.findViewById(R.id.sendIV);
                sendcomment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commentET = (EditText)view.findViewById(R.id.commentET);
                        String comment = commentET.getText().toString();
                        if(TextUtils.isEmpty(comment))
                        {
                            commentET.requestFocus();
                            commentET.setError("Please enter the comment");
                            return;
                        }
                        commentET.setText("");
                        postComment(comment,position);
                    }
                });

                moreTV = (TextView)view.findViewById(R.id.moreTV);
                moreTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Data data1 = data.get(position);
                        data1.more =false;
                        data1.comments = Util.getComment(data1.commentList,false);
                        data.set(position,data1);
                        moreTV.setVisibility(View.GONE);
                        adapter.refresh(data);
                    }
                });

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }



    private void getData() {
        HashMap<String, String> params = new HashMap<String, String>();
        VolleyStringRequest.request(getActivity(), AppConstants.SeePosts,params,seePostsResponse);
    }
    private VolleyStringRequest.OnStringResponse seePostsResponse = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            data = HomeActivity.parseData(response);
            adapter = new Recycler_View_Adapter(data,getActivity());
            recyclerView.setAdapter(adapter);
        }
        @Override
        public void errorReceived(int code, String message) {

        }
    };


    private void postComment(String comment, int position) {
        int postid = data.get(position).postid;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("content",comment);
        params.put("postid",Integer.toString(postid));

        VolleyStringRequest.request(getActivity(), AppConstants.newComment,params, commentResp);
    }
    private VolleyStringRequest.OnStringResponse commentResp = new VolleyStringRequest.OnStringResponse()
    {

        @Override
        public void responseReceived(String response) {
            refreshData();
        }

        @Override
        public void errorReceived(int code, String message) {

        }
    };

    private void refreshData() {
        HashMap<String, String> params = new HashMap<String, String>();
        VolleyStringRequest.request(getActivity(), AppConstants.SeePosts,params,refreshPostsResponse);
    }

    private VolleyStringRequest.OnStringResponse refreshPostsResponse = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            data = HomeActivity.parseData(response);
            adapter.refresh(data);
        }
        @Override
        public void errorReceived(int code, String message) {

        }
    };


}
