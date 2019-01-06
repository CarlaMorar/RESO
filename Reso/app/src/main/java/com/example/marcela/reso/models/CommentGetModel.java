package com.example.marcela.reso.models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class CommentGetModel {
    @SerializedName("Id")
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String Content;

    public String Creator;

    public long CreatedAt;

    public long EditedAt;
}
