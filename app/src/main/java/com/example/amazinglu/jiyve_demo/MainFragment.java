package com.example.amazinglu.jiyve_demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.amazinglu.jiyve_demo.Model.HappyHour;
import com.example.amazinglu.jiyve_demo.Model.Restaurant;
import com.example.amazinglu.jiyve_demo.SearchFunction.ResultActivity;
import com.example.amazinglu.jiyve_demo.Util.ModelUtil;
import com.example.amazinglu.jiyve_demo.base.MSimpleOnSearchActionListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.search_bar) MaterialSearchBar searchBar;
    @BindView(R.id.recycler_view_refresh_container) SwipeRefreshLayout swipeRefreshLayout;

    public static final String KEY_RESTAURANT = "key_restaurant";
    public static final String KEY_QUERY = "key_query";

    private int counter = 0;

    private List<Restaurant> restaurantList;
    private MRecyclerViewAdapter adapter;

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
        swipeRefreshLayout.setEnabled(false);
        loadData();
        swipeRefreshLayout.setEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MRecyclerViewAdapter(restaurantList);
        recyclerView.setAdapter(adapter);

        setUpSearchBar();

        // swipeRefreshLayout refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshAsyncTask asyncTask = new RefreshAsyncTask();
                asyncTask.execute();
            }
        });
    }

    private void setUpSearchBar() {
        searchBar.setHint(getActivity().getResources().getString(R.string.search_bar_hint));
        searchBar.setOnSearchActionListener(new MainFragmentOnSearchActionListener());
    }

    private void loadData() {
        restaurantList = new ArrayList<>();
        fakeData();
    }

    class MainFragmentOnSearchActionListener extends MSimpleOnSearchActionListener {
        @Override
        protected Context getContext() {
            return MainFragment.this.getContext();
        }

        @Override
        protected void startSearch(String query) {
            Intent intent = new Intent(getContext(), ResultActivity.class);
            intent.putExtra(KEY_QUERY, query);
            intent.putExtra(KEY_RESTAURANT, ModelUtil.toJsonList(restaurantList));
            getActivity().startActivity(intent);
        }
    }

    class RefreshAsyncTask extends AsyncTask<Void, Void, List<Restaurant>> {

        @Override
        protected List<Restaurant> doInBackground(Void... voids) {
            return fakRefresheData();
        }

        @Override
        protected void onPostExecute(List<Restaurant> res) {
            restaurantList.clear();
            restaurantList.addAll(res);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
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

    private List<Restaurant> fakRefresheData() {
        List<Restaurant> res = new ArrayList<>();
        res.add(new Restaurant("Clancy's bar " + counter++, "Bar",
                setUpHappyHour(Calendar.MONDAY, Calendar.FRIDAY, 5, 7),
                0.2, 4.8));
        res.add(new Restaurant("Original Fish Co.", "SeaFood",
                setUpHappyHour(true, 2, 9),
                0.2, 4.0));
        res.add(new Restaurant("Red Wok", "Chinese",
                setUpHappyHour(Calendar.TUESDAY, Calendar.FRIDAY, 4, 8),
                0.4, 3.9));
        res.add(new Restaurant("Boondocks", "Bar & Food",
                setUpHappyHour(Calendar.MONDAY, Calendar.FRIDAY, 5, 7),
                0.6, 3.1));
        res.add(new Restaurant("cassies irish Pub", "Bar & food",
                setUpHappyHour(true, 6, 10),
                0.6, 4.4));
        res.add(new Restaurant("Clancy's bar", "Bar",
                setUpHappyHour(Calendar.MONDAY, Calendar.FRIDAY, 5, 7),
                0.2, 4.8));
        res.add(new Restaurant("Original Fish Co.", "SeaFood",
                setUpHappyHour(true, 2, 9),
                0.2, 4.0));
        res.add(new Restaurant("Red Wok", "Chinese",
                setUpHappyHour(Calendar.TUESDAY, Calendar.FRIDAY, 4, 8),
                0.4, 3.9));
        res.add(new Restaurant("Boondocks", "Bar & Food",
                setUpHappyHour(Calendar.MONDAY, Calendar.FRIDAY, 5, 7),
                0.6, 3.1));
        res.add(new Restaurant("cassies irish Pub", "Bar & food",
                setUpHappyHour(true, 6, 10),
                0.6, 4.4));
        return res;
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
