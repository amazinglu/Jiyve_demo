package com.example.amazinglu.jiyve_demo.SearchFunction;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.amazinglu.jiyve_demo.MRecyclerViewAdapter;
import com.example.amazinglu.jiyve_demo.MainFragment;
import com.example.amazinglu.jiyve_demo.Model.Restaurant;
import com.example.amazinglu.jiyve_demo.R;
import com.example.amazinglu.jiyve_demo.Util.ModelUtil;
import com.example.amazinglu.jiyve_demo.base.MSimpleOnSearchActionListener;
import com.google.gson.reflect.TypeToken;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    @BindView(R.id.result_search_bar) MaterialSearchBar searchBar;
    @BindView(R.id.result_recycler_view) RecyclerView recyclerView;

    private List<Restaurant> restaurantList;
    private List<Restaurant> searhResult;
    private String query;
    private MRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        setUpSearchBar();

        handleIntent(getIntent());
    }

    private void setUpSearchBar() {
        searchBar.setHint(getResources().getString(R.string.search_bar_hint));
        searchBar.setOnSearchActionListener(new ResActivityOnSearchActionListener());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }

    private void handleIntent(Intent intent) {
        restaurantList = ModelUtil.toObjectList(intent.getStringArrayListExtra(MainFragment.KEY_RESTAURANT),
                new TypeToken<Restaurant>(){});
        query = intent.getStringExtra(MainFragment.KEY_QUERY);

        if (adapter == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(ResultActivity.this));
            searhResult = new ArrayList<>();
            adapter = new MRecyclerViewAdapter(searhResult);
            recyclerView.setAdapter(adapter);
        }

        doSearch(query);
    }

    private void doSearch(String query) {
        SearchAsyncTask asyncTask = new SearchAsyncTask(restaurantList, query);
        asyncTask.execute();
    }

    // OnSearchActionListener for search bar
    class ResActivityOnSearchActionListener extends MSimpleOnSearchActionListener{
        @Override
        protected Context getContext() {
            return ResultActivity.this;
        }

        @Override
        protected void startSearch(String query) {
            doSearch(query);
        }
    }

    // async task to do the search
    class SearchAsyncTask extends AsyncTask<Void, Void, List<Restaurant>> {

        private List<Restaurant> restaurantList;
        private String query;

        public SearchAsyncTask(List<Restaurant> restaurantList, String query) {
            this.restaurantList = restaurantList;
            this.query = query;
        }

        @Override
        protected List<Restaurant> doInBackground(Void... voids) {
            List<Restaurant> res =  new ArrayList<>();
            for (Restaurant restaurant : restaurantList) {
                if (restaurant.name.toLowerCase().contains(query.toLowerCase())) {
                    res.add(restaurant);
                }
            }
            return res;
        }

        @Override
        protected void onPostExecute(List<Restaurant> res) {
            searhResult.clear();
            for (Restaurant restaurant : res) {
                searhResult.add(restaurant);
            }
            adapter.notifyDataSetChanged();
        }
    }

}
