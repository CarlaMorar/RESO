package com.example.marcela.reso.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.example.marcela.reso.Constants;
import com.example.marcela.reso.R;
import com.example.marcela.reso.UserHandler;
import com.example.marcela.reso.helpers.IssueHelper;
import com.example.marcela.reso.models.AddIssueModel;
import com.example.marcela.reso.models.IssueGetModel;
import com.example.marcela.reso.models.UserData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class IssuesActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private UserData userData;
    private HashMap <Marker, UUID> markersDictionary;
    private FloatingActionButton mAddIssueButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        userData = new UserHandler(this).getUserData();

        mAddIssueButton = findViewById(R.id.btn_activity_issues_fab);
        mAddIssueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuesActivity.this, AddIssueActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<IssueGetModel> issues = userData.Issues;
        if (issues == null || issues.size() == 0)
        {
            return;
        }

        markersDictionary = new HashMap<>();
        for (IssueGetModel issue : issues)
        {
            Random rand = new Random();
            int nr = rand.nextInt(20500) + 1000 ;

            LatLng location = new LatLng(issue.Latitude - nr, issue.Longitude + nr);
            MarkerOptions marker = new MarkerOptions().position(location).title(issue.Title);

            Marker amarker = mMap.addMarker(marker);
            markersDictionary.put( amarker, issue.Id);

            mMap.addMarker(marker);
        }
        IssueGetModel firstIssue = issues.get(0);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(firstIssue.Latitude, firstIssue.Longitude)));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                UUID issueId = markersDictionary.get(marker);
                showIssueScreen(issueId);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (IssueHelper.tempIssue == null)
        {
            return;
        }

        AddIssueModel issue = IssueHelper.tempIssue;

        Random rand = new Random();
        int nr = rand.nextInt(20500) + 1000 ;

        LatLng location = new LatLng(issue.Latitude - nr, issue.Longitude + nr);
        MarkerOptions marker = new MarkerOptions().position(location).title(issue.Title);

        Marker amarker = mMap.addMarker(marker);
        markersDictionary.put( amarker, issue.Id);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.latitude, location.longitude),13f));
        mMap.addMarker(marker);

        IssueGetModel newIssue = new IssueGetModel(issue);

        userData.Issues.add(newIssue);
        IssueHelper.tempIssue = null;
    }

    private void showIssueScreen(UUID issueId) {
        Intent intent = new Intent(IssuesActivity.this, IssueActivity.class);
        IssueGetModel selectedIssue = null;
        for(IssueGetModel issue : userData.Issues)
        {
            if (issue.Id == issueId)
            {
                selectedIssue = issue;
                break;
            }
        }
        if (selectedIssue == null)
        {
            return;
        }
        intent.putExtra(Constants.ISSUE_ID_TAG, new Gson().toJson(selectedIssue));
        startActivity(intent);
    }
}