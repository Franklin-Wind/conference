package com.example.franklin.conference.ui;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.franklin.conference.Data.ConferenceData;
import com.example.franklin.conference.R;
import com.example.franklin.conference.adapter.conferenceAdapter;
import com.example.franklin.conference.util.HttpGet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzagz.runOnUiThread;

public class ManageCreatedActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    List<ConferenceData.Results> resultsList = new ArrayList<>();
    private conferenceAdapter adapter;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_created);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_created);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView imageView = (ImageView) findViewById(R.id.created_image_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle("我的发布");
        Glide.with(this).load(R.drawable.created_image).into(imageView);


        //        下拉刷新
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_created);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshmeets();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                result = HttpGet.JsonGet("http://39.108.186.78/conferenceOP/");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gson gson = new Gson();
                            resultsList = gson.fromJson(result, new TypeToken<List<ConferenceData.Results>>() {
                            }.getType());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_mycreated);
                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new conferenceAdapter(resultsList);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    //refreshmeets代码
    private void refreshmeets() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                result = HttpGet.JsonGet("http://39.108.186.78/conferenceOP/");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gson gson = new Gson();
                            resultsList = gson.fromJson(result, new TypeToken<List<ConferenceData.Results>>() {
                            }.getType());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_mycreated);
                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new conferenceAdapter(resultsList);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}