package com.openclassrooms.realestatemanager.Controllers.Bases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michaël SAGOT on 15/07/2019.
 */

public abstract class BaseActivity extends AppCompatActivity {

    // Force developer implement those methods
    protected abstract int getActivityLayout(); // Layout of the Child Activity
    protected abstract View getCoordinatorLayout(); // Layout of the CoordinatorLayout of the Child Activity
    protected abstract int getToolbarMenu(); // Layout of the ToolbarMenu of the Child Activity

    // For debugging Mode
    private static final String TAG = BaseActivity.class.getSimpleName();

    // For toolBar configuration
    protected ActionBar mActionBar;

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    @BindView(R.id.toolbar) Toolbar mToolBar;

    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getActivityLayout());

        // Get & serialise all views
        ButterKnife.bind(this);

        // Configuring Toolbar
        this.configureToolbar();
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
    }
    // Configure toolbar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(this.getToolbarMenu(), menu);
        return true;
    }
    // Show Snack Bar with a message
    public void showSnackBar(String message){
        Log.d(TAG, "showSnackBar: ");

        Snackbar.make(this.getCoordinatorLayout(), message, Snackbar.LENGTH_LONG).show();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        PERMISSIONS
    // ---------------------------------------------------------------------------------------------
}
