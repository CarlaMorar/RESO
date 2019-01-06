package com.example.marcela.reso.models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class UserData {
    @SerializedName("Id")
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String AspNetUserId;

    public String FullName;

    public double Latitude;

    public double Longitude ;

    public int Radius ;

    public int Age ;

    public int Gender ;

    public String ProfilePicture ;

//    public ICollection<IssueGeModel> Issues ;
}
