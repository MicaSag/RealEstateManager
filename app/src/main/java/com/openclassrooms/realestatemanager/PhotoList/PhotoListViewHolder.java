package com.openclassrooms.realestatemanager.PhotoList;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoListViewHolder extends RecyclerView.ViewHolder{

    private static final String TAG = PhotoListViewHolder.class.getSimpleName();

    @BindView(R.id.fragment_list_photo_image) ImageView mImage;

    private String mPhoto;

    // Constructor
    public PhotoListViewHolder(View photoView) {
        super(photoView);
        ButterKnife.bind(this, photoView);
    }

    // Method to update the current item
    public void updateWithPhoto(String photo, RequestManager glide){
        mPhoto = photo;

        // Display Photo
        if (photo !=null) glide.load(photo).into(mImage);
    }
}
