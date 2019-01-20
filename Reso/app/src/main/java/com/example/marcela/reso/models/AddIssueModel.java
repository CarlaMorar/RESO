package com.example.marcela.reso.models;

import java.util.List;
import java.util.UUID;

public class AddIssueModel {

    public UUID Id;

    public String Title;

    public String Description;

    public double Latitude;

    public double Longitude;

    public UUID CreatedBy;

    public List<String> Images;
}
