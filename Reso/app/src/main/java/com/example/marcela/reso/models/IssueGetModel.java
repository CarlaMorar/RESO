package com.example.marcela.reso.models;

import java.io.Serializable;
import java.util.UUID;

public class IssueGetModel {
    public UUID Id;

    public String Title;

    public String Description;

    public double Latitude;

    public double Longitude;

    public int UpVotes;

    public int DownVotes;

    public long CreatedAt;

    public UUID CreatedBy;

    public String Creator;

//    public virtual ICollection<CommentGetModel> Comments;
//
//    public virtual ICollection<string> Images;
}
