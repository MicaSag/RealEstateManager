package com.openclassrooms.realestatemanager.adapters.estateList;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class EstateListAdapter extends RecyclerView.Adapter<EstateListViewHolder> {

    // For Debug
    private static final String TAG = EstateListAdapter.class.getSimpleName();

    // Declaring a Glide object
    private RequestManager mGlide;

    // For Data
    private List<Estate> mListEstate;

    // CALLBACK
    private final OnEstateClick mCallback;

    // CONSTRUCTOR
    public EstateListAdapter(List<Estate> listEstate, RequestManager glide, OnEstateClick callback) {
        mListEstate = listEstate;
        mGlide = glide;
        mCallback = callback;
    }

    @Override
    public EstateListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_estate_list_view_holder, parent, false);

        return new EstateListViewHolder(view);
    }

    // For update View Holder with Estate
    @Override
    public void onBindViewHolder(EstateListViewHolder viewHolder, int position) {
        viewHolder.updateWithProperty(mListEstate.get(position), mGlide, mCallback);
    }

    // Return the size of the recycler view
    @Override
    public int getItemCount() {
        return mListEstate.size();
    }

    // Returns the Estate Identifier of the current position
    public Estate getEstate(int position){
        return mListEstate.get(position);
    }

    // Update le recycler view data
    public void updateData(List<Estate> estate){
        this.mListEstate = estate;
        this.notifyDataSetChanged();
    }

    public interface OnEstateClick{
        void onEstateClick(Estate estate);
    }
}
