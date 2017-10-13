package com.example.bittukumar.lab07.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bittukumar.lab07.Activities.HomeActivity;
import com.example.bittukumar.lab07.R;
import com.example.bittukumar.lab07.RecyclerView.Recycler_View_Adapter;
import com.example.bittukumar.lab07.RecyclerViewSearch.CustomSRVItemTouchListener;
import com.example.bittukumar.lab07.RecyclerViewSearch.SData;
import com.example.bittukumar.lab07.RecyclerViewSearch.SRecyclerViewItemClickListener;
import com.example.bittukumar.lab07.RecyclerViewSearch.SRecycler_View_Adapter;
import com.example.bittukumar.lab07.Utils.AppConstants;
import com.example.bittukumar.lab07.Utils.VolleyStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bittu Kumar on 12-10-2017.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {
    private CardView btnCardView;
    private Button userpostsbtn,cancelbtn,followbtn;
    private boolean isfollowing;
    private EditText searchET;
    private RecyclerView recyclerView;
    private SRecycler_View_Adapter adapter;
    public List<SData> data;
    public int dataposition;
    private HomeActivity homeActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        data = new ArrayList<>();
        init();
        searchET = (EditText)getActivity().findViewById(R.id.searchET);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (searchET.length()>=3)
                {
                    String text = searchET.getText().toString();
                    getData(text);

                }
                else
                {
                    data = new ArrayList<SData>();
                    adapter.refresh(data);
                }

            }
        });

        btnCardView = (CardView)getActivity().findViewById(R.id.buttonsCardView);
        userpostsbtn = (Button)getActivity().findViewById(R.id.userPostsBtn);
        cancelbtn = (Button)getActivity().findViewById(R.id.cancelBtn);
        followbtn = (Button)getActivity().findViewById(R.id.followBtn);
        userpostsbtn.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);
        followbtn.setOnClickListener(this);

    }

    private void init() {
        adapter = new SRecycler_View_Adapter(data,getActivity());

        recyclerView = (RecyclerView)getActivity().findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        recyclerView.addOnItemTouchListener(new CustomSRVItemTouchListener(getActivity(), recyclerView, new SRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                recyclerView.setVisibility(View.GONE);
                searchET.setVisibility(View.GONE);
                dataposition = position;
                checkfollow();
                btnCardView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeActivity = (HomeActivity)context;
    }

    @Override
    public void onClick(View v) {
        if (v==userpostsbtn)
        {
            homeActivity.getSupportActionBar().setTitle(data.get(dataposition).suidname);
            Bundle args = new Bundle();
            args.putInt(getString(R.string.show_posts_type),homeActivity.USERPOSTS);
            args.putString(getString(R.string.show_post_uid),data.get(dataposition).suid);
            homeActivity.changeFragmentWithBundle(args,new ShowPostsFragment());


        }
        else if (v==followbtn)
        {
            if (isfollowing)
            {
                unfollow();
            }
            else
            {
                follow();
            }



        }
        else if(v==cancelbtn)
        {
            btnCardView.setVisibility(View.GONE);
            searchET.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            init();

        }

    }




    private void getData(String text) {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("uid",text);
        VolleyStringRequest.request(getActivity(), AppConstants.searchurl,params,searchResp);
    }
    private VolleyStringRequest.OnStringResponse searchResp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            data = HomeActivity.parseSearchData(response);
            adapter.refresh(data);
        }
        @Override
        public void errorReceived(int code, String message) {

        }
    };

    private void checkfollow() {
        HashMap<String,String> params = new HashMap<String,String>();
        VolleyStringRequest.request(getActivity(), AppConstants.UserFollow,params,checkresp);
    }
    private VolleyStringRequest.OnStringResponse checkresp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            isfollowing = false;
            followbtn.setText(R.string.follow);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray dataarray = jsonObject.getJSONArray("data");
                for (int i=0;i<dataarray.length();i++)
                {
                    JSONObject obj = dataarray.getJSONObject(i);
                    if (obj.getString("uid").equals(data.get(dataposition).suid))
                    {
                        isfollowing=true;
                        Log.v("uid isfwing position", ": "+data.get(dataposition).suid+"  "+obj.getString("uid")+"  "+isfollowing+"  "+dataposition);

                        followbtn.setText(R.string.unfollow_text);
                        break;
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        @Override
        public void errorReceived(int code, String message) {

        }
    };

    private void follow() {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("uid",data.get(dataposition).suid);
        VolleyStringRequest.request(getActivity(), AppConstants.Follow,params,followresp);
    }
    private VolleyStringRequest.OnStringResponse followresp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            followbtn.setText(R.string.unfollow_text);
            isfollowing =true;
        }
        @Override
        public void errorReceived(int code, String message) {

        }
    };
    private void unfollow() {
        HashMap<String,String> params = new HashMap<String,String>();
        params.put("uid",data.get(dataposition).suid);
        VolleyStringRequest.request(getActivity(), AppConstants.unFollow,params,unfollowresp);
    }
    private VolleyStringRequest.OnStringResponse unfollowresp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            followbtn.setText(R.string.follow);
            isfollowing =false;
        }
        @Override
        public void errorReceived(int code, String message) {

        }
    };


}

