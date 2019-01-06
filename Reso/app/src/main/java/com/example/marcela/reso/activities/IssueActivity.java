package com.example.marcela.reso.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.marcela.reso.Constants;
import com.example.marcela.reso.R;
import com.example.marcela.reso.models.IssueGetModel;
import com.google.gson.Gson;

public class IssueActivity extends AppCompatActivity {

    private IssueGetModel currentIssue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        Bundle extras = getIntent().getExtras();
        if (extras == null)
        {
            return;
        }

        String issueString = extras.getString(Constants.ISSUE_ID_TAG);
        IssueGetModel issue = new Gson().fromJson(issueString, IssueGetModel.class);
        if (issue == null)
        {
            return;
        }

        currentIssue = issue;
        setDisplayData(issue);
    }

    private void setDisplayData(IssueGetModel issue)
    {

    }
}
