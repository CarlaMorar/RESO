package com.example.marcela.reso.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.marcela.reso.Constants;
import com.example.marcela.reso.R;
import com.example.marcela.reso.UserHandler;
import com.example.marcela.reso.helpers.DownloadImageTask;
import com.example.marcela.reso.helpers.ImageLoadTask;
import com.example.marcela.reso.helpers.IssueHelper;
import com.example.marcela.reso.models.CommentGetModel;
import com.example.marcela.reso.models.IssueGetModel;
import com.example.marcela.reso.models.UserData;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IssueActivity extends AppCompatActivity {

    private static final int UNBOUNDED = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

    private IssueGetModel currentIssue;

    private TextView mTitleTextView;
    private TextView mDetailsTextView;
    private TextView mCreatorTextView;
    private TextView mUpVotes;
    private TextView mDownVotes;
    private ImageView mIssueImageView;
    private Button mAddCommentButton;

    private IssueGetModel mIssue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        mTitleTextView = (TextView) findViewById(R.id.tv_titlu);
        mDetailsTextView = (TextView) findViewById(R.id.tv_detalii);
        mCreatorTextView = (TextView) findViewById(R.id.tv_activity_issue_creator);
        mUpVotes = (TextView) findViewById(R.id.tv_activity_issue_up_votes);
        mDownVotes = (TextView) findViewById(R.id.tv_activity_issue_down_vote);
        mIssueImageView = (ImageView) findViewById(R.id.iv_activity_issue_picture);
        mAddCommentButton=(Button)findViewById(R.id.btn_activity_issue_add_comment);
        mAddCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    goToAddCommentActivity();
                }
                catch (Exception e){
                    return;
                }
            }
        });
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
        mIssue=issue;
        setDisplayData(issue);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (IssueHelper.tempComment == null) {
            return;
        }
        CommentGetModel newComment = new CommentGetModel(IssueHelper.tempComment);

        currentIssue.Comments.add(newComment);

        UserHandler handler = new UserHandler(this);
        UserData userData = handler.getUserData();
        for (IssueGetModel issue : userData.Issues)
        {
            if (issue.Id.equals(currentIssue.Id))
            {
                issue.Comments.add(newComment);
                break;
            }
        }

        handler.setUserdata(userData);
        final ListView listview = (ListView) findViewById(R.id.lv_activity_issue_list);
        final IssuesArrayAdapter adapter = new IssuesArrayAdapter(this, currentIssue.Comments);
        listview.setAdapter(adapter);

        for(int i=0; i<currentIssue.Comments.size();i++){
            if(currentIssue.Comments.get(i).Creator == "No Comments"){
                currentIssue.Comments.remove(currentIssue.Comments.get(i));
            }
        }

        int grossElementHeight = 0;
        for (int i = 0; i < currentIssue.Comments.size(); i++) {

            View childView = adapter.getView(i, null, listview);
            childView.measure(UNBOUNDED, UNBOUNDED);
            grossElementHeight += childView.getMeasuredHeight();
        }
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) listview.getLayoutParams();
        lp.height = grossElementHeight;
        listview.setLayoutParams(lp);

        IssueHelper.tempComment = null;
    }

    private void goToAddCommentActivity(){
        Intent intent = new Intent(this, AddCommentActivity.class);
        intent.putExtra(Constants.ISSUE_ID_TAG, new Gson().toJson(mIssue.Id));
        startActivity(intent);
    }

    private void setDisplayData(IssueGetModel issue)
    {
        mTitleTextView.setText(issue.Title);
        mDetailsTextView.setText(issue.Description);
        mCreatorTextView.setText(issue.Creator);
        mUpVotes.setText(String.valueOf(issue.UpVotes));
        mDownVotes.setText(String.valueOf(issue.DownVotes));

        if(issue.Comments == null)
        {
            issue.Comments = new ArrayList<>();
        }

        if(issue.Images == null)
        {
            issue.Images = new ArrayList<>();
        }

        if( issue.Comments.size() == 0)
        {
            CommentGetModel comments = new CommentGetModel();
            comments.setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
            comments.Creator = "No Comments";
            comments.Content = "";
            issue.Comments.add(comments);
        }

        final ListView listview = (ListView) findViewById(R.id.lv_activity_issue_list);
        final IssuesArrayAdapter adapter = new IssuesArrayAdapter(this, currentIssue.Comments);
        listview.setAdapter(adapter);

        //set listview height

        int grossElementHeight = 0;
        for (int i = 0; i < issue.Comments.size(); i++) {
            View childView = adapter.getView(i, null, listview);
            childView.measure(UNBOUNDED, UNBOUNDED);
            grossElementHeight += childView.getMeasuredHeight();
        }
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) listview.getLayoutParams();
        lp.height = grossElementHeight;
        listview.setLayoutParams(lp);

        if(issue.Images.size() > 0)
        {
            String imageUrl = issue.Images.get(0);
            Picasso.get().load(imageUrl).into(mIssueImageView);
        }
        else
        {
            Picasso.get().load("https://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png").into(mIssueImageView);
        }
    }
}

