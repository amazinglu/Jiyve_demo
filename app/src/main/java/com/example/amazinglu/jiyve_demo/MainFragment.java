package com.example.amazinglu.jiyve_demo;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.amazinglu.jiyve_demo.Model.HappyHour;
import com.example.amazinglu.jiyve_demo.Model.Restaurant;
import com.example.amazinglu.jiyve_demo.Util.ModelUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY;
import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;
import static android.view.inputmethod.InputMethodManager.RESULT_HIDDEN;
import static android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT;

public class MainFragment extends Fragment {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.main_search_view) SearchView searchView;
    @BindView(R.id.recycler_view_swipe_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.back_button) ImageButton backButton;

    public static final String KEY_RESTAURANT = "key_restaurant";

    private List<Restaurant> restaurantList;
    private int counter = 0;

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

        setUpSearchView();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MRecyclerViewAdapter(restaurantList);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshTask refreshTask = new RefreshTask();
                refreshTask.execute();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "back button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        restaurantList = new ArrayList<>();
        fakeData();
        swipeRefreshLayout.setEnabled(true);
    }

    @SuppressLint("RestrictedApi")
    private void setUpSearchView() {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        Bundle appData = new Bundle();
        appData.putStringArrayList(KEY_RESTAURANT, ModelUtil.toJsonList(restaurantList));
        searchView.setAppSearchData(appData);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconified(true);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchView.isIconified()) {
                    searchView.setIconified(false);
                }
            }
        });
    }

    class RefreshTask extends AsyncTask<Void, Void, List<Restaurant>> {
        @Override
        protected List<Restaurant> doInBackground(Void... voids) {
            return fakeDataRefresh();
        }

        @Override
        protected void onPostExecute(List<Restaurant> res) {
            restaurantList.clear();
            restaurantList.addAll(res);
            swipeRefreshLayout.setRefreshing(false);
            counter++;
            adapter.notifyDataSetChanged();
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

    private List<Restaurant> fakeDataRefresh() {
        List<Restaurant> res = new ArrayList<>();
        res.add(new Restaurant("Clancy's bar " + counter, "Bar",
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
