package com.example.marcela.reso.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class IssueGetModel {

    public IssueGetModel() {
    }

    public IssueGetModel(AddIssueModel issue )
    {
        Id = issue.Id;
        Description = issue.Description;
        Title = issue.Title;
        Latitude = issue.Latitude;
        Longitude = issue.Longitude;
        UpVotes = 0;
        DownVotes = 0;
        CreatedBy = issue.CreatedBy;
        Images = issue.Images;
    }

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
//    public  List <String> Images;
    public List<String> Images;

    public  List<CommentGetModel> Comments;

}

