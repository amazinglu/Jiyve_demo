package com.example.amazinglu.jiyve_demo.SearchFunc;

import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.amazinglu.jiyve_demo.MRecyclerViewAdapter;
import com.example.amazinglu.jiyve_demo.MainFragment;
import com.example.amazinglu.jiyve_demo.Model.Restaurant;
import com.example.amazinglu.jiyve_demo.R;
import com.example.amazinglu.jiyve_demo.Util.ModelUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchableActivity extends AppCompatActivity {

    @BindView(R.id.search_result_list_view) RecyclerView resultRecyclerView;
//    @BindView(R.id.toolbar) Toolbar toolbar;

    private List<Restaurant> unfilterData, data;
    private MRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        ButterKnife.bind(this);

//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleIntent(Intent intent) {
        if (adapter == null) {
            data = new ArrayList<>();
            resultRecyclerView.setLayoutManager(new LinearLayoutManager(SearchableActivity.this));
            adapter = new MRecyclerViewAdapter(data);
            resultRecyclerView.setAdapter(adapter);
        }

        Bundle bundle = getIntent().getBundleExtra(SearchManager.APP_DATA);
        if (bundle != null) {
            unfilterData = ModelUtil.toObjectList(bundle.getStringArrayList(MainFragment.KEY_RESTAURANT),
                    new TypeToken<Restaurant>(){});
        }

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String intentData = intent.getDataString();
            String[] query = intentData.split("/");
            doSearch(query[0]);
        }
    }

    private void doSearch(String query) {
        SearchAsyncTask asyncTask = new SearchAsyncTask(unfilterData, query);
        asyncTask.execute();
    }

    class SearchAsyncTask extends AsyncTask<Void, Void, List<Restaurant>> {

        private List<Restaurant> unfilterData;
        private String query;

        public SearchAsyncTask(List<Restaurant> unfilterData, String query) {
            this.unfilterData = unfilterData;
            this.query = query;
        }

        @Override
        protected List<Restaurant> doInBackground(Void... voids) {
            List<Restaurant> res = new ArrayList<>();
            for (Restaurant restaurant : unfilterData) {
                if (restaurant.name.toLowerCase().contains(query.toLowerCase())) {
                    res.add(restaurant);
                }
            }
            return res;
        }

        @Override
        protected void onPostExecute(List<Restaurant> restaurantList) {
            data.clear();
            for (Restaurant restaurant : restaurantList) {
                data.add(restaurant);
            }
            adapter.notifyDataSetChanged();
        }
    }


}
