package com.example.spart.recupmynews.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.spart.recupmynews.Api.ApiClient;
import com.example.spart.recupmynews.Api.SectionsAPI;
import com.example.spart.recupmynews.MyTopStoriesAPI.ArticleTopStories;
import com.example.spart.recupmynews.MyTopStoriesAPI.TopStoriesResponse;
import com.example.spart.recupmynews.R;
import com.example.spart.recupmynews.Utils.InternetConnectionState;
import com.example.spart.recupmynews.View.TopStoriesAdapter;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopStoriesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TopStoriesAdapter mTopStoriesAdapter;
    private ArrayList<ArticleTopStories> resultsTS;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.top_stories_recycler_view, container, false);

        mRecyclerView = view.findViewById( R.id.recycler_view );
        ImageView noInternetConnection = view.findViewById( R.id.no_internet_connection );
        TextView oups = view.findViewById( R.id.txt_oups );
        TextView noInternet = view.findViewById( R.id.txt_no_internet );

        if(InternetConnectionState.noInternetAccess( Objects.requireNonNull( getContext() ) )){
            // If no internet,display image and text message
            noInternetConnection.setVisibility(View.VISIBLE);
            oups.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else {
            // display Articles
            callApi();
        }

        return view;
    }
    private void callApi() {

        SectionsAPI topStoriesListAPI = ApiClient.getClient().create( SectionsAPI.class);
        Call<TopStoriesResponse> topStoriesResponseCall = topStoriesListAPI.TSArticles( );

        topStoriesResponseCall.enqueue(new Callback<TopStoriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<TopStoriesResponse> call, @NonNull Response<TopStoriesResponse> response) {

                assert response.body() != null;
                resultsTS = response.body().getResults();

                layoutManager = new GridLayoutManager(getContext(), 1);
                mTopStoriesAdapter = new TopStoriesAdapter(getActivity(), resultsTS );
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(mTopStoriesAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<TopStoriesResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );


    }
}