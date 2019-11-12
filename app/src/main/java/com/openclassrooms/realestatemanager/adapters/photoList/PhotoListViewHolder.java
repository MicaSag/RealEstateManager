package com.openclassrooms.realestatemanager.adapters.photoList;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.controllers.activities.CreateEstateActivity;
import com.openclassrooms.realestatemanager.controllers.activities.UpdateEstateActivity;
import com.openclassrooms.realestatemanager.controllers.fragments.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class PhotoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private static final String TAG = PhotoListViewHolder.class.getSimpleName();

    @BindView(R.id.photo_list_image) ImageView mImage;
    @BindView(R.id.photo_list_bt_delete) Button mDeleteButton;
    @BindView(R.id.photo_list_et_room) EditText mRoomType;

    private PhotoListAdapter.OnPhotoClick mOnPhotoClick;
    private PhotoListAdapter.OnTextChange mOnTextChange;

    // Constructor
    public PhotoListViewHolder(View photoView) {
        super(photoView);
        ButterKnife.bind(this, photoView);
        mImage.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);
    }

    // Method to update the current item
    public void updateWithPhoto(Class caller, String photo, String description, RequestManager glide,
                                PhotoListAdapter.OnPhotoClick callback_OnPhotoClick,
                                PhotoListAdapter.OnTextChange callback_OnTextChange
                                ){
        Log.d(TAG, "updateWithPhoto: ");
        mOnPhotoClick = callback_OnPhotoClick;
        mOnTextChange = callback_OnTextChange;

        // Display Photo
        if (photo !=null) glide.load(photo).into(mImage);

        // Button Visibility
        if (caller == CreateEstateActivity.class) {
            mRoomType.setVisibility(View.VISIBLE);
            mDeleteButton.setVisibility(View.VISIBLE);
            mRoomType.setHint("room type");
        }
        if (caller == UpdateEstateActivity.class) {
            mRoomType.setVisibility(View.VISIBLE);
            mDeleteButton.setVisibility(View.VISIBLE);
            mRoomType.setHint("room type");
            mRoomType.setText(description);
        }
        if (caller == EstateDetailsFragment.class) {
            mRoomType.setVisibility(View.VISIBLE);
            mRoomType.setFocusable(false);
            mRoomType.setBackgroundColor(Color.TRANSPARENT);
            mRoomType.setText(description);
        }
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: getAdapterPosition = "+getAdapterPosition());
        mOnPhotoClick.onPhotoClick(getAdapterPosition(),view);
        if (view == mImage) Log.d(TAG, "onClick: image");
        if (view == mDeleteButton) Log.d(TAG, "onClick: button delete");
    }

    @OnTextChanged(R.id.photo_list_et_room)
    public void onTextChanged(CharSequence text){
        // Return, position, focus and Value
        Log.d(TAG, "onTextChanged: "+text);
        if (mOnTextChange != null) mOnTextChange.onTextChange(getAdapterPosition(),text.toString());
    }
}
