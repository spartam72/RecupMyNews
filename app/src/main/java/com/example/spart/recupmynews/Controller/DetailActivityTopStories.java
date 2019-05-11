package com.example.spart.recupmynews.Controller;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;


import com.example.spart.recupmynews.MyTopStoriesAPI.ArticleTopStories;
import com.example.spart.recupmynews.R;

import java.util.Objects;


public class DetailActivityTopStories extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_detail_top_stories);

        Toolbar toolbar =  findViewById( R.id.toolbar );
        toolbar.setBackgroundColor( getResources().getColor( R.color.colorTopStories ) );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setTitle( "Details from TopStories Article" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getWindow().setStatusBarColor( ContextCompat.getColor(this, R.color.colorStatusBarHome ));

        WebView webViewDetail = findViewById( R.id.webViewDescription );
        ArticleTopStories result = (ArticleTopStories) Objects.requireNonNull( getIntent().getExtras() ).getSerializable( "result" );
        webViewDetail.loadUrl( Objects.requireNonNull( result ).getUrl());



    }
    
}
