package com.example.amazinglu.jiyve_demo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.restaurant_view) ImageView restaurantImage;
    @BindView(R.id.item_restaurant_name) TextView restaurantName;
    @BindView(R.id.item_restaurant_type) TextView restaurantType;
    @BindView(R.id.item_restaurant_happy_hour) TextView restaurantHappyHour;
    @BindView(R.id.item_restaurant_open_period) TextView restaurantOpenPeriod;
    @BindView(R.id.item_like_button) ImageView likeButton;
    @BindView(R.id.item_restaurant_distance) TextView restaurantDistance;
    @BindView(R.id.item_recommend) TextView recommend;
    @BindView(R.id.item_cover) View cover;

    public ItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
