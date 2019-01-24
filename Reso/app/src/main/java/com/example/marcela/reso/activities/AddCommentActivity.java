package com.example.marcela.reso.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.marcela.reso.Constants;
import com.example.marcela.reso.R;
import com.example.marcela.reso.UserHandler;
import com.example.marcela.reso.helpers.IssueHelper;
import com.example.marcela.reso.models.AddIssueModel;
import com.example.marcela.reso.models.CommentModel;
import com.example.marcela.reso.models.IssueGetModel;
import com.example.marcela.reso.models.UserData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class AddCommentActivity extends AppCompatActivity {

    private EditText mContentEditText;
    private Button mAddCommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        mContentEditText = findViewById(R.id.et_activity_add_comment_content);
        mAddCommentButton = findViewById(R.id.btn_activity_add_comment_submit);

        mAddCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addComment();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addComment() throws JSONException {
        final UserHandler userHandler = new UserHandler(this);
        final UserData userData = userHandler.getUserData();
        Bundle extras = getIntent().getExtras();
        if (extras == null)
        {
            return;
        }

        String issueId = extras.getString(Constants.ISSUE_ID_TAG);
        String id = new Gson().fromJson(issueId, String.class);

        final CommentModel mCommentModel = new CommentModel();
        mCommentModel.Id = UUID.randomUUID();
        mCommentModel.Content = mContentEditText.getText().toString();
        mCommentModel.IssueId = UUID.fromString(id);
        mCommentModel.CreatedBy = userData.getId();
        mCommentModel.Creator = userData.FullName;


        String url = "http://itec-api.deventure.co/api/Comment/Create";
        final UserHandler data = new UserHandler(this);

        String model = new Gson().toJson(mCommentModel);
        JSONObject parameters = new JSONObject(model);

        Log.d("Tag",parameters.toString());

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                IssueHelper.tempComment = mCommentModel;
                Toast.makeText(AddCommentActivity.this,"Succes!", Toast.LENGTH_SHORT).show();
                AddCommentActivity.this.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AddCommentActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + data.getAccessToken());
                return map;
            }
        };

        Volley.newRequestQueue(this).add(jsonRequest);
    }
}

