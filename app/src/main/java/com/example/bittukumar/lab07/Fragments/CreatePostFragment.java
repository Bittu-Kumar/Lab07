package com.example.bittukumar.lab07.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bittukumar.lab07.Activities.HomeActivity;
import com.example.bittukumar.lab07.R;
import com.example.bittukumar.lab07.Utils.AppConstants;
import com.example.bittukumar.lab07.Utils.VolleyStringRequest;

import java.util.HashMap;

/**
 * Created by Bittu Kumar on 12-10-2017.
 */

public class CreatePostFragment extends Fragment {
    private Button createPostBTN;
    private EditText createPostET;
    private HomeActivity homeActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_post, container, false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeActivity = (HomeActivity)context;
    }

    @Override
    public void onStart() {
        super.onStart();
        createPostBTN = (Button)getActivity().findViewById(R.id.createPostBTN);
        createPostET = (EditText)getActivity().findViewById(R.id.createPostET);
        createPostBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cretepost();
            }
        });
    }

    private void cretepost() {
        String posttext = createPostET.getText().toString();
        if(TextUtils.isEmpty(posttext))
        {
            createPostET.requestFocus();
            createPostET.setError("Please enter the post");
            return;
        }
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("content",posttext);

        VolleyStringRequest.request(getActivity(), AppConstants.CreatePost,params,createPostResp);
    }

    private VolleyStringRequest.OnStringResponse createPostResp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            homeActivity.getSupportActionBar().setTitle("Home");
            homeActivity.changeFragment(new ShowPostsFragment());


        }
        @Override
        public void errorReceived(int code, String message) {

        }
    };
}
