package com.example.spart.recupmynews.Controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;


import com.example.spart.recupmynews.MyTopStoriesAPI.ArticleTopStories;
import com.example.spart.recupmynews.R;

import java.util.Objects;


public class DetailActivityTopStories extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_detail_top_stories);

        Toolbar toolbar =  findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setTitle( "Details from TopStories Article" );
        toolbar.setTitleTextColor(getResources().getColor( R.color.colorWhite ));
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        WebView webViewDetail = findViewById( R.id.webViewDescription );
        ArticleTopStories result = (ArticleTopStories) Objects.requireNonNull( getIntent().getExtras() ).getSerializable( "result" );
        webViewDetail.loadUrl( Objects.requireNonNull( result ).getUrl());



    }
    
}
