package com.example.bittukumar.lab07.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bittukumar.lab07.Fragments.CreatePostFragment;
import com.example.bittukumar.lab07.Fragments.ShowPostsFragment;
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

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        manager = getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportActionBar().setTitle("Create Post");
                changeFragment(new CreatePostFragment());
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Home");
        changeFragment(new ShowPostsFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportActionBar().setTitle("Home");
            changeFragment(new ShowPostsFragment());

        } else if (id == R.id.nav_my_posts) {

        } else if (id == R.id.nav_create_post) {
            getSupportActionBar().setTitle("Create Post");
            changeFragment(new CreatePostFragment());

        } else if (id == R.id.nav_logout) {
            logout();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        HashMap<String, String> params = new HashMap<String, String>();
        VolleyStringRequest.request(HomeActivity.this, AppConstants.LogOut,params,logoutResp);
    }
    private VolleyStringRequest.OnStringResponse logoutResp = new VolleyStringRequest.OnStringResponse() {
        @Override
        public void responseReceived(String response) {
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();

        }
        @Override
        public void errorReceived(int code, String message) {

        }
    };

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment tmpFragment = manager.findFragmentById(R.id.content_frame);
        if (tmpFragment != null)
            transaction.replace(R.id.content_frame, fragment);
        else
            transaction.add(R.id.content_frame, fragment);
        transaction.commitAllowingStateLoss();
    }

    public static List<Data> parseData(String response)
    {
        List<Data> data = new ArrayList<>();
        try {
            JSONObject jsonData = new JSONObject(response);
            JSONArray postarray = jsonData.getJSONArray("data");
            String postuid,text,timestamp,comment;
            int postId;
            boolean more;
            String cuid,cname,ctext,ctimestamp;
            for (int i = postarray.length()-1;i>=0;i--)
            {
                more=false;
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
                if (commentList.size()>3)more=true;
                comment = Util.getComment(commentList,more);
                data.add(new Data(postuid,postId,text,comment,timestamp,commentList,more));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
