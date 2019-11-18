package com.openclassrooms.realestatemanager.adapters.estateList;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.google.android.material.card.MaterialCardView;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.repositories.CurrentEstateDataRepository;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EstateListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = EstateListViewHolder.class.getSimpleName();

    @BindView(R.id.fragment_list_estate_image) ImageView mImage;
    @BindView(R.id.fragment_list_estate_location) TextView mLocation;
    @BindView(R.id.fragment_list_estate_type) TextView mType;
    @BindView(R.id.fragment_list_estate_prize) TextView mPrize;
    @BindView(R.id.fragment_list_estate_sale) ImageView mSale;
    @BindView(R.id.fragment_list_estate_mcv) MaterialCardView mMCV;


    private EstateListAdapter.OnEstateClick mOnEstateClick;
    private Estate mEstate;

    // Constructor
    public EstateListViewHolder(View estateView) {
        super(estateView);
        ButterKnife.bind(this, estateView);
        estateView.setOnClickListener(this);
    }

    // Method to update the current item
    public void updateWithProperty(Estate estate, RequestManager glide, EstateListAdapter.OnEstateClick callback){
        mEstate = estate;
        mOnEstateClick = callback;

        // Display Estate Photo
        if (estate.getPhotos() !=null) if (estate.getPhotos().get(0) != null)
            glide.load(estate.getPhotos().get(0)).into(mImage);

        // Display Estate Type
        mType.setText(estate.getType());

        // Display Estate location
        if (estate.getAddress() != null) mLocation.setText(estate.getAddress().get(3));

        // Display Estate Price
        DecimalFormat decimalFormat = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.US));
        mPrize.setText("$"+decimalFormat.format(estate.getPrice()));

        // Display Sale
        if (estate.getDateOfSale() != null) mSale.setVisibility(View.VISIBLE);
        else mSale.setVisibility(View.INVISIBLE);

        // For indicate item Selected
        if (estate.getEstate_Id() == CurrentEstateDataRepository.getInstance()
                .getCurrentEstate_Id().getValue()) {
            mMCV.setCardBackgroundColor(Color.YELLOW);
        }
        else {
            mMCV.setCardBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnEstateClick != null) mOnEstateClick.onEstateClick(mEstate);
    }
}
