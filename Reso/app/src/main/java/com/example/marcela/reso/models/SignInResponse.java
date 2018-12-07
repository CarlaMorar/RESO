package com.example.marcela.reso.models;

import com.google.gson.annotations.SerializedName;

public class SignInResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("userData")
    private String userDataString;

    public UserData userDataObject;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserDataString() {
        return userDataString;
    }

    public void setUserDataString(String userDataString) {
        this.userDataString = userDataString;
    }
}
