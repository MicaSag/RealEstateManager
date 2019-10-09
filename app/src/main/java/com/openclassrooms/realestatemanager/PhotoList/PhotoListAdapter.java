package com.openclassrooms.realestatemanager.PhotoList;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.EstateList.EstateListAdapter;
import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListViewHolder> {

    // For Debug
    private static final String TAG = PhotoListAdapter.class.getSimpleName();

    // Declaring a Glide object
    private RequestManager mGlide;

    // For Data
    private List<String> mListPhoto;

    // For CALLBACK
    public interface OnPhotoClick{
        void onPhotoClick(String photo,int position);
    }
    private final OnPhotoClick mCallback;

    // CONSTRUCTOR
    public PhotoListAdapter(List<String> listPhoto, RequestManager glide, OnPhotoClick callback) {
        mListPhoto = listPhoto;
        mGlide = glide;
        mCallback = callback;
    }

    @Override
    public PhotoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_photo_list_view_holder, parent, false);

        return new PhotoListViewHolder(view);
    }

    // For update View Holder with Estate
    @Override
    public void onBindViewHolder(PhotoListViewHolder viewHolder, int position) {
        viewHolder.updateWithPhoto(mListPhoto.get(position), mGlide, mCallback);
    }

    // Return the size of the recycler view
    @Override
    public int getItemCount() {
        return mListPhoto.size();
    }

    // Returns the Estate Identifier of the current position
    public String getPhoto(int position){
        return mListPhoto.get(position);
    }

    // Update le recycler view data
    public void updateData(List<String> photo){
        this.mListPhoto = photo;
        this.notifyDataSetChanged();
    }
}
