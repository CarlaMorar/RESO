package com.example.marcela.reso.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.marcela.reso.Constants;
import com.example.marcela.reso.R;
import com.example.marcela.reso.UserHandler;
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
import java.util.UUID;

public class IssuesActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private UserData userData;
    private HashMap <MarkerOptions, UUID> markersDictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        userData = new UserHandler(this).getUserData();
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
            LatLng location = new LatLng(issue.Latitude, issue.Longitude);
            MarkerOptions marker = new MarkerOptions().position(location).title(issue.Title);
            markersDictionary.put( marker, issue.Id);

            mMap.addMarker(marker);
        }
        IssueGetModel firstIssue = issues.get(0);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(firstIssue.Latitude, firstIssue.Longitude)));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                marker.
                UUID issueId = markersDictionary.get(marker);
                showIssueScreen(issueId);
                return false;
            }
        });
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