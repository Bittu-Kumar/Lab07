package com.example.bittukumar.lab07.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bittukumar.lab07.R;
import com.example.bittukumar.lab07.Utils.AppConstants;
import com.example.bittukumar.lab07.Utils.VolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login_button;
    private EditText usernameET,passwordET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        login_button = (Button)findViewById(R.id.button_login);
        usernameET = (EditText)findViewById(R.id.username);
        passwordET = (EditText)findViewById(R.id.password);
        login_button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==login_button)
        {
            login();
        }
    }

    private void login() {
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();



        try {
            JSONObject requestJson = new JSONObject();
            requestJson.put("id",username);
            requestJson.put("password",password);
            VolleyJsonRequest.request(MainActivity.this, AppConstants.loginUrl, requestJson, loginResp, true);
        } catch (JSONException e) {
            Log.e("MainActivity", "MainActivity: JSONException", e);
        }

    }
    private VolleyJsonRequest.OnJsonResponse loginResp = new VolleyJsonRequest.OnJsonResponse() {
        @Override
        public void responseReceived(JSONObject jsonObj) {
          Toast.makeText(MainActivity.this,"successfully logged in",Toast.LENGTH_LONG).show();
        }

        @Override
        public void errorReceived( String message) {
            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();

        }
    };
}
