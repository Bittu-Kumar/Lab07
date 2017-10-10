package com.example.bittukumar.lab07.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bittukumar.lab07.R;
import com.example.bittukumar.lab07.RecyclerView.Data;
import com.example.bittukumar.lab07.RecyclerView.Recycler_View_Adapter;

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

    }
}
