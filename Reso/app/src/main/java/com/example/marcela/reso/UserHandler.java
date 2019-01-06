package com.example.marcela.reso;

import android.content.Context;

import com.example.marcela.reso.models.SignInResponse;
import com.example.marcela.reso.models.UserData;

public class UserHandler {
    private Context context;

    public UserHandler(Context context) {
        this.context = context;
    }

    public void setUserdata(SignInResponse response)
    {
        SharedPreferencesHandler handler = new SharedPreferencesHandler(context);
        handler.SetString(Constants.ACCESS_TOKEN_TAG, response.getAccessToken());
        handler.Set(Constants.USER_DATA_TAG, response.userDataObject);
    }
    public void setUserdata(UserData response)
    {
        SharedPreferencesHandler handler = new SharedPreferencesHandler(context);
        handler.Set(Constants.USER_DATA_TAG, response);
    }

    public UserData getUserData()
    {
        SharedPreferencesHandler handler = new SharedPreferencesHandler(context);
        return handler.Get(Constants.USER_DATA_TAG, UserData.class);
    }

    public String getAccessToken()
    {
        SharedPreferencesHandler handler = new SharedPreferencesHandler(context);
        return handler.GetString(Constants.ACCESS_TOKEN_TAG);
    }

    public void setEmail(String email) {
        SharedPreferencesHandler handler = new SharedPreferencesHandler(context);
        handler.SetString(Constants.EMAIL_TAG, email);
    }

    public String getEmail() {
        SharedPreferencesHandler handler = new SharedPreferencesHandler(context);
        return handler.GetString(Constants.EMAIL_TAG);
    }
}
