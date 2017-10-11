package com.example.bittukumar.lab07.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bittukumar.lab07.R;
import com.example.bittukumar.lab07.RecyclerView.Comment;
import com.example.bittukumar.lab07.RecyclerView.Data;
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

    }

    private void getData() {

        HashMap<String, String> params = new HashMap<String, String>();
        VolleyStringRequest.request(getActivity(), AppConstants.SeePosts,params,seePostsResponse);
    }
    private VolleyStringRequest.OnStringResponse seePostsResponse = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            try {
                JSONObject jsonData = new JSONObject(response);
                JSONArray postarray = jsonData.getJSONArray("data");
                String postuid,text,timestamp,comment;
                int postId;
                String cuid,cname,ctext,ctimestamp;
                for (int i = 0;i<postarray.length();i++)
                {
                    JSONObject post = postarray.getJSONObject(i);
                    postuid = post.getString("uid");
                    text = post.getString("text");
                    postId = post.getInt("postid");
                    timestamp = post.getString("timestamp");
                    List<Comment> commentList = new ArrayList<>();
                    JSONArray commentarray = post.getJSONArray("Comment");
                    for(int j=0;j<commentarray.length();j++)
                    {
                        JSONObject commentobject = commentarray.getJSONObject(j);
                        cuid = commentobject.getString("uid");
                        cname = commentobject.getString("name");
                        ctext = commentobject.getString("text");
                        ctimestamp = commentobject.getString("timestamp");
                        commentList.add(new Comment(cuid,cname,ctext,ctimestamp));
                    }
                    comment = Util.getComment(commentList);
                    data.add(new Data(postuid,postId,text,comment,timestamp,commentList));
                }
                adapter = new Recycler_View_Adapter(data,getActivity());
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void errorReceived(int code, String message) {

        }
    };




}
