package com.example.marcela.reso.activities;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marcela.reso.R;
import com.example.marcela.reso.models.AuthorizedGetModel;
import com.example.marcela.reso.models.RegisterModel;
import com.example.marcela.reso.models.SignInResponse;
import com.example.marcela.reso.models.UserData;
import com.google.gson.Gson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mAgeEditText;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNameEditText = (EditText) findViewById(R.id.et_activity_register_name);
        mEmailEditText = (EditText) findViewById(R.id.et_activity_register_email);
        mPasswordEditText = (EditText) findViewById(R.id.et_activity_register_password);
        mRegisterButton = (Button) findViewById(R.id.b_activity_register_register);
        mAgeEditText = (EditText) findViewById(R.id.et_activity_register_age);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Register();
                }
                catch (Exception e){
                    return;
                }
            }
        });
    }

    private void Register() throws JSONException {
        String name = mNameEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        double latitude = 45.27;
        double longitude = 21.28;
        int radius = 100;
        int age = Integer.parseInt(mAgeEditText.getText().toString());
        int gender = 1;
        UUID id = UUID.randomUUID();

        RegisterModel registerModel = new RegisterModel();
        registerModel.Age=age;
        registerModel.Email=email.trim();
        registerModel.FullName=name;
        registerModel.Gender=gender;
        registerModel.Id=id;
        registerModel.Latitude=latitude;
        registerModel.Longitude=longitude;
        registerModel.Radius=radius;
        registerModel.Password=password;

        String url = "http://itec-api.deventure.co/api/Account/Register";

        String model = new Gson().toJson(registerModel);
        JSONObject parameters = new JSONObject(model);
        Log.d("Tag",parameters.toString());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                Toast.makeText(RegisterActivity.this,"Succes!", Toast.LENGTH_SHORT).show();
                RegisterActivity.this.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(RegisterActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);

    }
}
