package com.example.amazinglu.jiyve_demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amazinglu.jiyve_demo.Model.HappyHour;
import com.example.amazinglu.jiyve_demo.Model.Restaurant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private List<Restaurant> restaurantList;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        loadData();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MRecyclerViewAdapter adapter = new MRecyclerViewAdapter(restaurantList);
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        restaurantList = new ArrayList<>();
        fakeData();

    }

    /**
     * ====================================================================================
     * fake data for test purpose
     * */

    private void fakeData() {
        restaurantList.add(new Restaurant("Clancy's bar", "Bar",
                setUpHappyHour(Calendar.MONDAY, Calendar.FRIDAY, 5, 7),
                0.2, 4.8));
        restaurantList.add(new Restaurant("Original Fish Co.", "SeaFood",
                setUpHappyHour(true, 2, 9),
                0.2, 4.0));
        restaurantList.add(new Restaurant("Red Wok", "Chinese",
                setUpHappyHour(Calendar.TUESDAY, Calendar.FRIDAY, 4, 8),
                0.4, 3.9));
        restaurantList.add(new Restaurant("Boondocks", "Bar & Food",
                setUpHappyHour(Calendar.MONDAY, Calendar.FRIDAY, 5, 7),
                0.6, 3.1));
        restaurantList.add(new Restaurant("cassies irish Pub", "Bar & food",
                setUpHappyHour(true, 6, 10),
                0.6, 4.4));
        restaurantList.add(new Restaurant("Clancy's bar", "Bar",
                setUpHappyHour(Calendar.MONDAY, Calendar.FRIDAY, 5, 7),
                0.2, 4.8));
        restaurantList.add(new Restaurant("Original Fish Co.", "SeaFood",
                setUpHappyHour(true, 2, 9),
                0.2, 4.0));
        restaurantList.add(new Restaurant("Red Wok", "Chinese",
                setUpHappyHour(Calendar.TUESDAY, Calendar.FRIDAY, 4, 8),
                0.4, 3.9));
        restaurantList.add(new Restaurant("Boondocks", "Bar & Food",
                setUpHappyHour(Calendar.MONDAY, Calendar.FRIDAY, 5, 7),
                0.6, 3.1));
        restaurantList.add(new Restaurant("cassies irish Pub", "Bar & food",
                setUpHappyHour(true, 6, 10),
                0.6, 4.4));
    }

    private HappyHour setUpHappyHour(int beginDate, int endDate, int beginHour, int endHour) {
        HappyHour happyHour = new HappyHour();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, beginDate);
        happyHour.beginDate = c.getTime();
        c.set(Calendar.DAY_OF_WEEK, endDate);
        happyHour.endDate = c.getTime();
        c.set(Calendar.HOUR_OF_DAY, beginHour);
        happyHour.beginHour = c.getTime();
        c.set(Calendar.HOUR_OF_DAY, endHour);
        happyHour.endHour = c.getTime();
        return happyHour;
    }

    private HappyHour setUpHappyHour(boolean isDaily, int beginHour, int endHour) {
        HappyHour happyHour = new HappyHour();
        happyHour.isDaily = isDaily;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, beginHour);
        happyHour.beginHour = c.getTime();
        c.set(Calendar.HOUR_OF_DAY, endHour);
        happyHour.endHour = c.getTime();
        return happyHour;
    }
}