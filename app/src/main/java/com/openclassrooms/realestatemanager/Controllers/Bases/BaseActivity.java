package com.openclassrooms.realestatemanager.Controllers.Bases;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Michaël SAGOT on 15/07/2019.
 */

public abstract class BaseActivity extends AppCompatActivity {

    // Force developer implement those methods
    protected abstract int getActivityLayout(); // Layout of the Child Activity
    protected abstract View getCoordinatorLayout(); // Layout of the CoordinatorLayout of the Child Activity

    // For debugging Mode
    private static final String TAG = BaseActivity.class.getSimpleName();

    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getActivityLayout());

        // Get & serialise all views
        ButterKnife.bind(this);
    }

    // ---------------------------------------------------------------------------------------------
    //                                     VIEW MODEL ACCESS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    //                                        PERMISSIONS
    // ---------------------------------------------------------------------------------------------
}
