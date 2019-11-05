package com.openclassrooms.realestatemanager.adapters.photoList;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListViewHolder> {

    // For Debug
    private static final String TAG = PhotoListAdapter.class.getSimpleName();

    // Declaring a Glide object
    private RequestManager mGlide;

    // For Data
    private final List<String> mPhotos = new ArrayList<>();

    // For Caller
    private Class mCaller;

    // For CALLBACK
    public interface OnPhotoClick{
        void onPhotoClick(int position,View view);
    }
    private final OnPhotoClick mCallback;

    // CONSTRUCTOR
    public PhotoListAdapter(Class caller, RequestManager glide, OnPhotoClick callback) {
        mCaller = caller;
        mGlide = glide;
        mCallback = callback;
    }

    // Update le recycler view data
    public void setNewData(List<String> photos){
        mPhotos.clear();
        mPhotos.addAll(photos);
        notifyDataSetChanged();
    }

    @Override
    public PhotoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.photo_list_view_holder, parent, false);

        return new PhotoListViewHolder(view);
    }

    // For update View Holder with Estate
    @Override
    public void onBindViewHolder(PhotoListViewHolder viewHolder, int position) {
        viewHolder.updateWithPhoto(mCaller, mPhotos.get(position), mGlide, mCallback);
    }

    // Return the size of the recycler view
    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    // Returns the Estate Identifier of the current position
    public String getPhoto(int position){
        return mPhotos.get(position);
    }
}
