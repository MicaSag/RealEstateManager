package com.openclassrooms.realestatemanager.PhotoList;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.EstateList.EstateListAdapter;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = PhotoListViewHolder.class.getSimpleName();

    @BindView(R.id.fragment_list_photo_image) ImageView mImage;

    private PhotoListAdapter.OnPhotoClick mOnPhotoClick;
    private String mPhoto;

    // Constructor
    public PhotoListViewHolder(View photoView) {
        super(photoView);
        ButterKnife.bind(this, photoView);
        photoView.setOnClickListener(this);
    }

    // Method to update the current item
    public void updateWithPhoto(String photo, RequestManager glide, PhotoListAdapter.OnPhotoClick callback){
        mPhoto = photo;
        mOnPhotoClick = callback;

        // Display Photo
        if (photo !=null) glide.load(photo).into(mImage);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        Log.d(TAG, "onClick: getAdapterPosition = "+getAdapterPosition());
        if (mOnPhotoClick != null) mOnPhotoClick.onPhotoClick(mPhoto,getAdapterPosition());
    }
}
