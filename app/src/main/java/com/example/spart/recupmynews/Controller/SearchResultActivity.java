package com.example.spart.recupmynews.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.spart.recupmynews.Api.NewYorkTimesSearchArticles;
import com.example.spart.recupmynews.MyArticleSearchAPI.ArticleSearchArticles;
import com.example.spart.recupmynews.MyArticleSearchAPI.ArticleSearchResponse;
import com.example.spart.recupmynews.R;
import com.example.spart.recupmynews.Utils.ItemClickSupport;
import com.example.spart.recupmynews.View.ArticleSearchAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Disposable disposable;
    private List<ArticleSearchArticles> searchResultArticles;
    private List<ArticleSearchArticles> prefArticles;
    private ArticleSearchAdapter searchResultAdapter;
    private String queryTerm;
    private String newsDesk;
    private String beginDate;
    private String endDate;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        Toolbar toolbar =  findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setTitle( "Search Results" );
        toolbar.setTitleTextColor(getResources().getColor( R.color.colorWhite ));
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        recyclerView = findViewById( R.id.fragment_search_result_recycler_view );

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.configureDataFromPref();
        this.updateUIwithPref(this.prefArticles);
    }
    private void configureRecyclerView() {
        this.searchResultArticles = new ArrayList<>();
        this.searchResultAdapter = new ArticleSearchAdapter(this.searchResultArticles, Glide.with(this));
        this.recyclerView.setAdapter(this.searchResultAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
    }
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.article_item_search )
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        ArticleSearchArticles article = searchResultAdapter.getArticle(position);

                        Intent intent = new Intent(getApplication(), ArticleDetailsActivity.class);
                        intent.putExtra("url", article.getWebUrl());
                        intent.putExtra("parent", "SearchResultActivity");
                        startActivity(intent);
                    }
                });
    }
    private void configureDataFromPref(){
        this.queryTerm = mPreferences.getString( "query", null );
        this.newsDesk = mPreferences.getString("newsDesk", null);
        this.beginDate = mPreferences.getString("beginDate", null);
        this.endDate = mPreferences.getString("endDate", null);

        String listArticlesSerializedToJson = mPreferences.getString("listArticles", null);
        Type listType = new TypeToken<ArrayList<ArticleSearchArticles>>() {}.getType();
        this.prefArticles = new Gson().fromJson(listArticlesSerializedToJson, listType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }
    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }
    private void updateUIwithPref(List<ArticleSearchArticles> articles) {
        searchResultArticles.clear();
        searchResultArticles.addAll(articles);
        searchResultAdapter.notifyDataSetChanged();
    }


}





