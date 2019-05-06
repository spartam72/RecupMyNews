package com.example.spart.recupmynews.Controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.example.spart.recupmynews.MySportsAPI.ArticleSport;
import com.example.spart.recupmynews.MyTopStoriesAPI.ArticleTopStories;
import com.example.spart.recupmynews.R;

import java.util.Objects;


public class DetailActivitySports extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_detail_sports);

        Toolbar toolbar =  findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setTitle( "Details from Sports Article" );
        toolbar.setBackgroundColor( getResources().getColor( R.color.colorSport ) );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getWindow().setStatusBarColor( ContextCompat.getColor(this, R.color.colorStatusBarDetailsSPT ));

        WebView webViewDetail = findViewById( R.id.webViewDescription );
        ArticleSport result = (ArticleSport) Objects.requireNonNull( getIntent().getExtras() ).getSerializable( "result" );
        webViewDetail.loadUrl( Objects.requireNonNull( result ).getUrl());



    }
    
}
