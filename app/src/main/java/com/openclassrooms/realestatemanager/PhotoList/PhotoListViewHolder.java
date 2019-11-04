package com.openclassrooms.realestatemanager.PhotoList;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.Controllers.Activities.CreateEstateActivity;
import com.openclassrooms.realestatemanager.Controllers.Activities.UpdateEstateActivity;
import com.openclassrooms.realestatemanager.Controllers.Fragments.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = PhotoListViewHolder.class.getSimpleName();

    @BindView(R.id.photo_list_image) ImageView mImage;
    @BindView(R.id.photo_list_bt_delete) Button mDeleteButton;
    @BindView(R.id.photo_list_et_room) EditText mRoomType;


    private PhotoListAdapter.OnPhotoClick mOnPhotoClick;
    private String mPhoto;

    // Constructor
    public PhotoListViewHolder(View photoView) {
        super(photoView);
        ButterKnife.bind(this, photoView);
        mImage.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);
    }

    // Method to update the current item
    public void updateWithPhoto(Class caller, String photo, RequestManager glide, PhotoListAdapter.OnPhotoClick callback){
        Log.d(TAG, "updateWithPhoto: ");
        mPhoto = photo;
        mOnPhotoClick = callback;

        // Display Photo
        if (photo !=null) glide.load(photo).into(mImage);

        // Button Visibility
        Log.d(TAG, "updateWithPhoto: caller = "+caller);
        if (caller == CreateEstateActivity.class) {
            mRoomType.setVisibility(View.VISIBLE);
            mDeleteButton.setVisibility(View.VISIBLE);
        }
        if (caller == UpdateEstateActivity.class) {
            mRoomType.setVisibility(View.VISIBLE);
            mDeleteButton.setVisibility(View.VISIBLE);
        }
        if (caller == EstateDetailsFragment.class) {
            mRoomType.setVisibility(View.VISIBLE);
            mRoomType.setFocusable(false);
            mRoomType.setBackgroundColor(Color.TRANSPARENT);
            mRoomType.setHint("");
        }
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        Log.d(TAG, "onClick: getAdapterPosition = "+getAdapterPosition());
        mOnPhotoClick.onPhotoClick(getAdapterPosition(),view);
        if (view == mImage) Log.d(TAG, "onClick: image");
        if (view == mDeleteButton) Log.d(TAG, "onClick: button delete");
    }
}
