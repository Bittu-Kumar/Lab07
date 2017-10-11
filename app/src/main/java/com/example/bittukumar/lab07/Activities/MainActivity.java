package com.example.bittukumar.lab07.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bittukumar.lab07.R;
import com.example.bittukumar.lab07.Utils.AppConstants;
import com.example.bittukumar.lab07.Utils.VolleyJsonRequest;
import com.example.bittukumar.lab07.Utils.VolleyStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CookieManager cookieManager = new CookieManager();

    private Button login_button;
    private EditText usernameET,passwordET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CookieHandler.setDefault(cookieManager);

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
        if(TextUtils.isEmpty(username))
        {
            usernameET.requestFocus();
            usernameET.setError("Please enter the username");
            return;

        }
        if(TextUtils.isEmpty(password))
        {
            passwordET.requestFocus();
            passwordET.setError("Please enter the Password");
            return;
        }
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id",username);
        params.put("password",password);


        VolleyStringRequest.request(MainActivity.this, AppConstants.loginUrl,params, loginResp);

    }
    private VolleyStringRequest.OnStringResponse loginResp = new VolleyStringRequest.OnStringResponse() {

        @Override
        public void responseReceived(String resonse) {

            try {
                JSONObject obj = new JSONObject(resonse);
                Toast.makeText(MainActivity.this,obj.getString("data"),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void errorReceived(int code, String message) {
            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();

        }
    };
}
