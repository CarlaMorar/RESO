package com.example.marcela.reso.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marcela.reso.R;
import com.example.marcela.reso.UserHandler;
import com.example.marcela.reso.helpers.IssueHelper;
import com.example.marcela.reso.models.AddIssueModel;
import com.example.marcela.reso.models.UserData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddIssueActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mDetailsEditText;
    private Button mAddIssueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_issue_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        InitialiseViews();

            mAddIssueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        addIssue();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    private void InitialiseViews()
    {
        mNameEditText = findViewById(R.id.et_activity_add_issue_name);
        mDetailsEditText = findViewById(R.id.et_activity_add_issue_description);
        mAddIssueButton = findViewById(R.id.btn_activity_add_issue_add);
    }

    private void addIssue() throws JSONException {
        final UserHandler userHandler = new UserHandler(this);
        UserData userData = userHandler.getUserData();

        final AddIssueModel mIssueAddModel = new AddIssueModel();
        mIssueAddModel.Id = UUID.randomUUID();
        mIssueAddModel.Title = mNameEditText.getText().toString();
        mIssueAddModel.Description = mDetailsEditText.getText().toString();
        mIssueAddModel.Latitude = 45.7469683;
        mIssueAddModel.Longitude = 21.2440447;
        mIssueAddModel.CreatedBy = userData.getId();
        List<String> images = new ArrayList<>();
        images.add("http://google.ro");

        mIssueAddModel.Images = images;

        String url = "http://itec-api.deventure.co/api/Issue/Create";
        final UserHandler data = new UserHandler(this);
        String model = new Gson().toJson(mIssueAddModel);
        JSONObject parameters = new JSONObject(model);
        Log.d("Tag",parameters.toString());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                Toast.makeText(AddIssueActivity.this,"Succes!", Toast.LENGTH_SHORT).show();
                IssueHelper.tempIssue = mIssueAddModel;
                AddIssueActivity.this.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AddIssueActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("Content-Type", "application/json");
                map.put("Authorization", "Bearer " + data.getAccessToken());
                return map;
            }
        };

        Volley.newRequestQueue(this).add(jsonRequest);
    }
}
