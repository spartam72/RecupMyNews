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
import com.example.spart.recupmynews.MyBusinessAPI.ArticleBusiness;
import com.example.spart.recupmynews.MyBusinessAPI.BusinessResponse;
import com.example.spart.recupmynews.R;
import com.example.spart.recupmynews.Utils.InternetConnectionState;
import com.example.spart.recupmynews.View.BusinessAdapter;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private BusinessAdapter mBusinessAdapter;
    private ArrayList<ArticleBusiness> resultsBU;
    private LinearLayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.business_recycler_view, container, false);

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
            //  display Articles
            callApi();
        }

        return view;
    }
    private void callApi(){

        SectionsAPI businessListAPI = ApiClient.getClient().create( SectionsAPI.class);
        Call<BusinessResponse> businessResponseCall = businessListAPI.BUArticles( "business" );

        businessResponseCall.enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(@NonNull Call<BusinessResponse> call, @NonNull Response<BusinessResponse> response) {

                resultsBU = response.body().getResults();

                layoutManager = new GridLayoutManager(getContext(), 1);
                mBusinessAdapter = new BusinessAdapter(getActivity(), resultsBU );
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(mBusinessAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<BusinessResponse> call, @NonNull Throwable t) {
                t.printStackTrace();

            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );


    }
}