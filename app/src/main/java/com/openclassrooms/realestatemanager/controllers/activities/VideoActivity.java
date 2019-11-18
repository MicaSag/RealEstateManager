package com.openclassrooms.realestatemanager.controllers.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {

    private static final String TAG = VideoActivity.class.getSimpleName();

    public static final String BUNDLE_VIDEO_ACTIVITY_URI = "BUNDLE_VIDEO_ACTIVITY_URI";

    // For toolBar configuration
    protected ActionBar mActionBar;

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    public @BindView(R.id.toolbar) Toolbar mToolBar;

    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        // Get & serialise all views
        ButterKnife.bind(this);

        // Configuring Toolbar
        this.configureToolbar();

        // Configure Video
        this.configureVideo(getIntent().getStringExtra(BUNDLE_VIDEO_ACTIVITY_URI));
    }

    // --------------------------------------------------------------------------------------------
    //                                    CONFIGURATION
    // --------------------------------------------------------------------------------------------
    // Configure Video
    private void configureVideo(String videoPath){
        Log.d(TAG, "configureVideo() called with: videoPath = [" + videoPath + "]");

        // Find the video view by id so we can use it.
        VideoView myVideo = findViewById(R.id.videoView);

        // To set the path to the video. Sample.mp4 is in the "raw" folder.
        myVideo.setVideoPath(videoPath);

        // To create the media controller (play, pause, etc.)
        MediaController myController = new MediaController(this);

        // To link the media controller to the video view.
        myController.setAnchorView(myVideo);

        // To link the video view to the media controller.
        myVideo.setMediaController(myController);

        // To autoPlay on opening.
        myVideo.start();
    }
    // ---------------------------------------------------------------------------------------------
    //                                           ACTIONS
    // ---------------------------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d(TAG, "onOptionsItemSelected: ");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Here we will not use what is in the manifest (because we will not do onBackPressed),
        // but we will simply finish the activity and it will make the calling activity visible
        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------
    protected void configureToolbar(){
        Log.d(TAG, "configureToolbar: ");
        //Set the toolbar
        setSupportActionBar(mToolBar);
        // Get a support ActionBar corresponding to this toolbar
        mActionBar = getSupportActionBar();
        // Enable the Up button
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }
}
