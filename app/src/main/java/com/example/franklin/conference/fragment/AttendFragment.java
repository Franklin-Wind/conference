package com.example.franklin.conference.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franklin.conference.App.MyApplication;
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
import java.util.Random;

import static com.google.android.gms.internal.zzagz.runOnUiThread;


public class AttendFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout swipeRefresh;
    private conferenceAdapter adapter;
    List<ConferenceData.Results> resultsList = new ArrayList<>();

    private String result;

    public AttendFragment() { }

    public static AttendFragment newInstance( ) {
        AttendFragment fragment = new AttendFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("resultsList", "onCreate: "+resultsList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_attend, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Log.i("resultsList", "onCreateView:"+resultsList.toString());

//        下拉刷新
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result = HttpGet.JsonGet("http://39.108.186.78/conference/");
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            String results = jsonArray.toString();
                            Gson gson = new Gson();
                            resultsList = gson.fromJson(results, new TypeToken<List<ConferenceData.Results>>() {
                            }.getType());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
                                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(layoutManager);
                                adapter = new conferenceAdapter(resultsList);
                                recyclerView.setAdapter(adapter);
                                swipeRefresh.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                result = HttpGet.JsonGet("http://39.108.186.78/conference/");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray =jsonObject.getJSONArray("results");
                            String results = jsonArray.toString();
                            Gson gson = new Gson();
                            resultsList = gson.fromJson(results, new TypeToken<List<ConferenceData.Results>>(){}.getType());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        adapter = new conferenceAdapter(resultsList);
                        recyclerView.setAdapter(adapter);

                    }
                });
            }
        }).start();

    }



    //从服务器获取会议数据并进行Json解析
    public void GetresultsList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                result = HttpGet.JsonGet("http://39.108.186.78/conference/");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray =jsonObject.getJSONArray("results");
                            String results = jsonArray.toString();
                            Gson gson = new Gson();
                            resultsList = gson.fromJson(results, new TypeToken<List<ConferenceData.Results>>(){}.getType());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }







    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
