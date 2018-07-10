package com.example.amazinglu.jiyve_demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.amazinglu.jiyve_demo.Model.Restaurant;
import com.example.amazinglu.jiyve_demo.Util.DateUtil;

import java.util.List;

public class MRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Restaurant> restaurantList;
    private Context context;

    public MRecyclerViewAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        this.context = parent.getContext();
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Restaurant restaurant = restaurantList.get(position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;

        viewHolder.restaurantName.setText(restaurant.name);
        viewHolder.restaurantType.setText(restaurant.type);
        viewHolder.restaurantDistance.setText(Double.toString(restaurant.distance) + " mi");
        viewHolder.recommend.setText(Double.toString(restaurant.recommend));

        // set happy hour
        if (restaurant.happyHour != null) {
            if (restaurant.happyHour.beginHour != null && restaurant.happyHour.endHour != null
                    && DateUtil.hourComparer(restaurant.happyHour.beginHour, restaurant.happyHour.endHour)) {
                viewHolder.restaurantHappyHour.setText(getContext().getResources().getString(R.string.happy_hour_title)
                + " " + DateUtil.timeToString(restaurant.happyHour.beginHour) + "-" +
                        DateUtil.timeToString(restaurant.happyHour.endHour) + "pm");
            }
            if (restaurant.happyHour.isDaily) {
                viewHolder.restaurantOpenPeriod.setText(getContext().getResources().getString(R.string.daily));
            } else if (restaurant.happyHour.beginDate != null && restaurant.happyHour.endDate != null) {
                viewHolder.restaurantOpenPeriod.setText(DateUtil.dateToString(restaurant.happyHour.beginDate)
                        + " - " + DateUtil.dateToString(restaurant.happyHour.endDate));
            }
        }

        viewHolder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "choose " + restaurant.name, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "like " + restaurant.name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    private Context getContext() {
        return context;
    }
}
